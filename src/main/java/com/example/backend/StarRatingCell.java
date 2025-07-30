package com.example.backend;
import com.example.backend.Coffee;
import com.example.noir.ProfileController;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

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




        for (int i = 1; i <= 5; i++) {
            Polygon star = createStar();
            final int starIndex = i;
//            if(i < numberOfStars){
//                star.setFill(Color.GOLD);
//            }
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
                8.0, 0.0,
                9.5, 5.0,
                14.0, 5.0,
                10.5, 8.0,
                12.0, 13.0,
                8.0, 10.0,
                4.0, 13.0,
                5.5, 8.0,
                2.0, 5.0,
                6.5, 5.0
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
        } else {
            currentCoffee = (Coffee) getTableRow().getItem();

            Integer existingRating = controller.coffeeRatings.get(currentCoffee.getId());
            if (existingRating != null) {
                selectedRating = existingRating;
            } else {
                Review r = client.getUserReviewForCoffee(controller.currentUser.getUserId(), currentCoffee.getId());
                if(r != null) {
                    selectedRating = (int) r.getStars();
                }else{
                    selectedRating = 0;
                }

            }

            updateStarDisplay();
            setGraphic(starContainer);
        }
    }

    private void saveRating() {
        if (currentCoffee != null && selectedRating > 0) {

            controller.coffeeRatings.put(currentCoffee.getId(), selectedRating);

            try {

                boolean hasExistingReview = client.hasUserReviewedCoffee(
                        controller.currentUser.getUserId(),
                        currentCoffee.getId()
                );

                boolean success = false;

                if (hasExistingReview) {

                    success = client.updateReview(
                            controller.currentUser.getUserId(),
                            currentCoffee.getId(),
                            selectedRating,
                            "Updated review from order history - " + selectedRating + " stars"
                    );

                    if (success) {

                    }
                } else {

                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    String timestamp = java.time.LocalDateTime.now().format(formatter);

                    Review review = new Review(
                            controller.currentUser.getUserId(),
                            selectedRating,
                            currentCoffee.getId(),
                            "Review from order history - " + selectedRating + " stars",
                            timestamp,
                            true,
                            "Order Review"
                    );


                    client.addReview(review);
                    success = true;

                    if (success) {

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