package com.example.noir;

import com.example.backend.dbFetch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class coffeeController implements Initializable {
    private Font euclidBoldFont;
    private Font retrokiaFont;
    @FXML
    private Text top1;
    @FXML
    private Text top2;
    @FXML
    private Text top3;
    @FXML
    private Text top4;
    @FXML
    private Label coffeeNameLabel;
    @FXML
    private Label coffeePriceLabel;
    @FXML
    private Label coffeeDescriptionLabel;
    @FXML
    private Label coffeePacketSizeLabel;
    @FXML
    private Label coffeeWeightLabel;
    @FXML
    private Label coffeeStrengthLabel;
    @FXML
    private Label coffeeFlavourLabel;
    @FXML
    private Label coffeeAcidityLabel;
    @FXML
    private Label coffeeAromaLabel;
    @FXML
    private Label coffeeStockLabel;
    @FXML
    private Label coffeeSalesLabel;
    @FXML
    private Label coffeeAverageRatingLabel;
    @FXML
    private Label coffeeStatusLabel;
    @FXML
    private Label coffeeSpecialFeaturesLabel;
    @FXML
    private Label coffeeTagsLabel;
    @FXML
    private ImageView coffeeImageView;
    private dbFetch database;
    private com.example.backend.Coffee currentCoffee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFonts();
        applyCustomFonts();
        database = new dbFetch();
        loadCoffeeData(201);
    }

    private void loadCoffeeData(int coffeeId) {
        try {
            currentCoffee = database.getCoffeeById(coffeeId);
            if (currentCoffee != null) {
                displayCoffeeInfo();
            } else {
                System.err.println("Coffee with ID " + coffeeId + " not found");
                displayNotFound();
            }
        } catch (Exception e) {
            System.err.println("Error loading coffee data: " + e.getMessage());
            e.printStackTrace();
            displayError();
        }
    }

    private void displayCoffeeInfo() {
        if (currentCoffee == null) return;

        // Basic information
        if (coffeeNameLabel != null) {
            coffeeNameLabel.setText(currentCoffee.getName());
        }
        if (coffeePriceLabel != null) {
            coffeePriceLabel.setText("$" + String.format("%.2f", currentCoffee.getPrice()));
        }
        if (coffeeDescriptionLabel != null) {
            coffeeDescriptionLabel.setText(currentCoffee.getDescription());
        }

        // Product details
        if (coffeePacketSizeLabel != null) {
            coffeePacketSizeLabel.setText(currentCoffee.getPacketSize());
        }
        if (coffeeWeightLabel != null) {
            coffeeWeightLabel.setText(currentCoffee.getWeight() + "g");
        }

        // Coffee quality ratings
        if (coffeeStrengthLabel != null) {
            coffeeStrengthLabel.setText("Strength: " + currentCoffee.getStrength() + "/10");
        }
        if (coffeeFlavourLabel != null) {
            coffeeFlavourLabel.setText("Flavour: " + currentCoffee.getFlavour() + "/10");
        }
        if (coffeeAcidityLabel != null) {
            coffeeAcidityLabel.setText("Acidity: " + currentCoffee.getAcidity() + "/10");
        }
        if (coffeeAromaLabel != null) {
            coffeeAromaLabel.setText("Aroma: " + currentCoffee.getAroma() + "/10");
        }

        // Stock and sales information
        if (coffeeStockLabel != null) {
            coffeeStockLabel.setText("Stock: " + currentCoffee.getCurrentStock());
        }
        if (coffeeSalesLabel != null) {
            coffeeSalesLabel.setText("Sales: " + currentCoffee.getNumberOfSales());
        }

        // Average rating
        if (coffeeAverageRatingLabel != null) {
            double avgRating = currentCoffee.getAverageRating();
            coffeeAverageRatingLabel.setText("Rating: " + String.format("%.1f", avgRating) + "/5.0");
        }

        // Status information
        if (coffeeStatusLabel != null) {
            StringBuilder status = new StringBuilder();
            if (currentCoffee.isSoldOut()) {
                status.append("SOLD OUT ");
            } else if (currentCoffee.isNearSoldOut()) {
                status.append("NEARLY SOLD OUT ");
            } else {
                status.append("IN STOCK ");
            }
            coffeeStatusLabel.setText(status.toString());
        }

        // Special features
        if (coffeeSpecialFeaturesLabel != null) {
            StringBuilder features = new StringBuilder();
            if (currentCoffee.isRare()) {
                features.append("RARE ");
            }
            if (currentCoffee.isSmallBatch()) {
                features.append("SMALL BATCH ");
            }
            if (currentCoffee.isFarmToCup()) {
                features.append("FARM TO CUP ");
            }
            coffeeSpecialFeaturesLabel.setText(features.toString());
        }

        // Tags
        if (coffeeTagsLabel != null) {
            StringBuilder tags = new StringBuilder("Tags: ");
            if (currentCoffee.getTag() != null) {
                for (int i = 0; i < currentCoffee.getTag().size() && i < 3; i++) {
                    if (currentCoffee.getTag().get(i)) {
                        String[] tagNames = {"Organic", "Fair Trade", "Single Origin"}; // Example tag names
                        tags.append(tagNames[i]).append(" ");
                    }
                }
            }
            coffeeTagsLabel.setText(tags.toString());
        }

        // Load coffee image if available
        if (coffeeImageView != null && currentCoffee.getImageurl() != null && !currentCoffee.getImageurl().isEmpty()) {
            try {
                Image coffeeImage = new Image(currentCoffee.getImageurl(), true);
                coffeeImageView.setImage(coffeeImage);
            } catch (Exception e) {
                System.err.println("Error loading coffee image: " + e.getMessage());
            }
        }

        System.out.println("Coffee data loaded successfully: " + currentCoffee.getName());
    }

    private void displayNotFound() {
        if (coffeeNameLabel != null) {
            coffeeNameLabel.setText("Coffee Not Found");
        }
        if (coffeeDescriptionLabel != null) {
            coffeeDescriptionLabel.setText("The requested coffee with ID 201 was not found in the database.");
        }
    }

    private void displayError() {
        if (coffeeNameLabel != null) {
            coffeeNameLabel.setText("Error Loading Coffee");
        }
        if (coffeeDescriptionLabel != null) {
            coffeeDescriptionLabel.setText("An error occurred while loading the coffee data.");
        }
    }
    private void loadFonts() {
        try {
            // Load EuclidCircularA-Bold font
            euclidBoldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/euclidbold.ttf"), 18);
            if (euclidBoldFont == null) {
                System.err.println("Failed to load euclidbold.ttf font - using default");
                euclidBoldFont = Font.font("Arial", 18);
            } else {
                System.out.println("EuclidCircularA-Bold font loaded successfully");
            }
            
            // Load RetrokiaCaps font with proper error checking
            retrokiaFont = Font.loadFont(getClass().getResourceAsStream("/fonts/RetrokiaCaps-Rough.otf"), 24);
            if (retrokiaFont == null) {
                System.err.println("Failed to load RetrokiaCaps-Rough.otf font - using default");
                retrokiaFont = Font.font("Arial", 24);
            } else {
                System.out.println("RetrokiaCaps font loaded successfully: " + retrokiaFont.getName());
            }
            
        } catch (Exception e) {
            System.err.println("Error loading fonts: " + e.getMessage());
            euclidBoldFont = Font.font("Arial", 18);
            retrokiaFont = Font.font("Arial", 24);
            e.printStackTrace();
        }
    }
    @FXML
    private void addunderline(MouseEvent event) {
        // Get the Text object that triggered the event
        Text textElement = (Text) event.getSource();

        // Add underline when mouse enters
        textElement.setUnderline(true);

        // Add mouse exited handler to remove underline when mouse leaves
        textElement.setOnMouseExited(exitEvent -> {
            textElement.setUnderline(false);
        });
    }

    @FXML
    private void redirectToScene(MouseEvent event) {
        try {
            // Get the source of the event (could be ImageView or Text)
            Object source = event.getSource();

            // Get the current stage
            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
            }

            // Load the specific FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);

            // Get the controller and reset scroll position
            HelloController helloController = fxmlLoader.getController();

            // Set up the stage
            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();

            // Reset scroll position to top after scene is set
            Platform.runLater(() -> {
                helloController.scrollToTop();

                // Switch statement to handle different navigation targets
                if (source instanceof Text) {
                    Text textSource = (Text) source;
                    String fxId = textSource.getId();

                    switch (fxId) {
                        case "top1":
                            helloController.handleTop1Click();
                            break;
                        case "top2":
                            helloController.handleTop2Click();
                            break;
                        case "top3":
                            helloController.handleTop3Click();
                            break;
                        case "top4":
                            helloController.handleTop4Click();
                            break;
                        default:
                            // No specific scroll action for other elements
                            break;
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void applyCustomFonts() {
        // Apply RetrokiaCaps font to navigation text elements
        if (retrokiaFont != null) {
            System.out.println("Applying RetrokiaCaps font to navigation elements");
            if (top1 != null) {
                top1.setFont(retrokiaFont);
                System.out.println("Applied font to top1");
            }
            if (top2 != null) {
                top2.setFont(retrokiaFont);
                System.out.println("Applied font to top2");
            }
            if (top3 != null) {
                top3.setFont(retrokiaFont);
                System.out.println("Applied font to top3");
            }
            if (top4 != null) {
                top4.setFont(retrokiaFont);
                System.out.println("Applied font to top4");
            }
        } else {
            System.err.println("RetrokiaCaps font is null, cannot apply");
        }
        
        // Apply Euclid font to coffee labels
        if (euclidBoldFont != null) {
            if (coffeeNameLabel != null) coffeeNameLabel.setFont(retrokiaFont);
            if (coffeePriceLabel != null) coffeePriceLabel.setFont(retrokiaFont);

            // Add more labels as needed
        }
    }
}