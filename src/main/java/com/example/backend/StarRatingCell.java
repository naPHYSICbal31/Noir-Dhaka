package com.example.backend;// Add this as an inner class in ProfileController or as a separate class
import com.example.backend.Coffee;
import com.example.noir.ProfileController;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static com.example.noir.HelloApplication.client;

public class StarRatingCell extends TableCell<Coffee, Integer> {
    private HBox starContainer;
    private Coffee currentCoffee;
    private int selectedRating = 0;
    private ProfileController controller;

    public StarRatingCell(ProfileController controller) {
        this.controller = controller;
        createStarContainer();
    }

    private void createStarContainer() {
        starContainer = new HBox(3);
        starContainer.setAlignment(Pos.CENTER);

        // Create 5 stars
        for (int i = 1; i <= 5; i++) {
            Polygon star = createStar();
            final int starIndex = i;

            star.setOnMouseClicked(event -> {
                selectedRating = starIndex;
                updateStarDisplay();
                saveRating();
            });

            star.setOnMouseEntered(event -> {
                star.setFill(Color.GOLD);
                star.setStyle("-fx-cursor: hand;");
            });

            star.setOnMouseExited(event -> {
                if (starIndex > selectedRating) {
                    star.setFill(Color.LIGHTGRAY);
                }
            });

            starContainer.getChildren().add(star);
        }
    }

    private Polygon createStar() {
        Polygon star = new Polygon();
        star.getPoints().addAll(new Double[]{
                8.0, 0.0,   // top point
                9.5, 5.0,   // top right
                14.0, 5.0,  // right point
                10.5, 8.0,  // bottom right
                12.0, 13.0, // bottom point
                8.0, 10.0,  // bottom left
                4.0, 13.0,  // left bottom
                5.5, 8.0,   // left point
                2.0, 5.0,   // left top
                6.5, 5.0    // top left
        });
        star.setFill(Color.LIGHTGRAY);
        star.setStroke(Color.GRAY);
        star.setStrokeWidth(0.5);

        return star;
    }

    private void updateStarDisplay() {
        for (int i = 0; i < starContainer.getChildren().size(); i++) {
            Polygon star = (Polygon) starContainer.getChildren().get(i);
            if (i < selectedRating) {
                star.setFill(Color.GOLD);
            } else {
                star.setFill(Color.LIGHTGRAY);
            }
        }
    }
    @Override
    protected void updateItem(Integer rating, boolean empty) {
        super.updateItem(rating, empty);

        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
            setGraphic(null);
            currentCoffee = null;
        } else {
            currentCoffee = (Coffee) getTableRow().getItem();

            // Check if there's an existing rating for this coffee
            Integer existingRating = controller.coffeeRatings.get(currentCoffee.getId());
            if (existingRating != null) {
                selectedRating = existingRating;
            } else {
                selectedRating = rating != null ? rating : 0;
            }

            updateStarDisplay();
            setGraphic(starContainer);
        }
    }

    private void saveRating() {
        if (currentCoffee != null && selectedRating > 0) {
            // Store the rating locally
            controller.coffeeRatings.put(currentCoffee.getId(), selectedRating);

            try {
                // Check if user has already reviewed this coffee
                boolean hasExistingReview = client.hasUserReviewedCoffee(
                        controller.currentUser.getUserId(),
                        currentCoffee.getId()
                );

                boolean success = false;

                if (hasExistingReview) {
                    // Update existing review
                    success = client.updateReview(
                            controller.currentUser.getUserId(),
                            currentCoffee.getId(),
                            selectedRating,
                            "Updated review from order history - " + selectedRating + " stars"
                    );

                    if (success) {
                        System.out.println("Review updated: " + selectedRating + " stars for " + currentCoffee.getName());
                    }
                } else {
                    // Create new review with properly formatted timestamp
                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    String timestamp = java.time.LocalDateTime.now().format(formatter);

                    Review review = new Review(
                            controller.currentUser.getUserId(),
                            selectedRating,
                            currentCoffee.getId(),
                            "Review from order history - " + selectedRating + " stars",
                            timestamp,
                            true, // verified purchase
                            "Order Review"
                    );

                    // Use the existing addReview method instead of saveReview
                    controller.client.addReview(review);
                    success = true;

                    if (success) {
                        System.out.println("New review saved: " + selectedRating + " stars for " + currentCoffee.getName());
                    }
                }

                if (!success) {
                    System.err.println("Failed to save review to database");
                }

            } catch (Exception e) {
                System.err.println("Error saving review: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}