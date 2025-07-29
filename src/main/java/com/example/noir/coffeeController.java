package com.example.noir;

import com.example.backend.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.NumberFormat;

import static com.example.noir.HelloApplication.client;

public class coffeeController implements Initializable {
    private Font euclidBoldFont;
    private Font retrokiaFont;


    private int currentQuantity = 1;

    @FXML
    private Text top1;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;
    @FXML
    private Text totalreview;
    @FXML
    private Text top2;
    @FXML
    private Text top3;
    @FXML
    private Text top4;
    @FXML
    private Rectangle grind1;
    @FXML
    private Rectangle grind2;
    @FXML
    private Rectangle grind3;
    @FXML
    private Rectangle grind4;
    @FXML
    private Rectangle grind5;
    @FXML
    private Rectangle grind6;
    @FXML
    private Rectangle grind7;
    @FXML
    private Text grindtext1;
    @FXML
    private Text grindtext2;
    @FXML
    private Text grindtext3;
    @FXML
    private Text grindtext4;
    @FXML
    private Text grindtext5;
    @FXML
    private Text grindtext6;
    @FXML
    private Text grindtext7;
    @FXML
    private ImageView str1;
    @FXML
    private ImageView str2;
    @FXML
    private ImageView str3;
    @FXML
    private ImageView fl1;
    @FXML
    private ImageView fl2;
    @FXML
    private ImageView fl3;
    @FXML
    private ImageView ac1;
    @FXML
    private ImageView ac2;
    @FXML
    private ImageView ac3;
    @FXML
    private ImageView ar1;
    @FXML
    private ImageView ar2;
    @FXML
    private ImageView ar3;

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
    @FXML
    private ImageView cart;

    @FXML
    private Text quantity;


    private com.example.backend.Coffee currentCoffee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFonts();
        new Thread(() -> {
            try {
                

                Platform.runLater(() -> {
                    System.out.println("Connected!");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    e.printStackTrace();
                });
            }
        }).start();


        if (quantity != null) {
            quantity.setText(String.valueOf(currentQuantity));
        }

        Platform.runLater(() -> {
            applyCustomFonts();
        });
    }
    private String sourceFxId;
    public void setSourceFxId(String fxId) {
        this.sourceFxId = fxId;
        Image image = null;
        switch(sourceFxId) {

            case "bucaramanga":
            case "prtxt2":
            case "prtk2":
                loadCoffeeData(201);
                image = new Image(getClass().getResourceAsStream("images/bucaramanga.png"));
                coffeeImageView.setImage(image);
                break;
            case "fiveset":
            case "prtxt1":
            case "prtk1":
                loadCoffeeData(200);
                image = new Image(getClass().getResourceAsStream("images/5set.png"));
                coffeeImageView.setImage(image);
                break;
            case "kona":
            case "kona1":
            case "prtxt3":
            case "prtk3":
            case "prtxt31":
            case "prtk31":
                loadCoffeeData(202);
                image = new Image(getClass().getResourceAsStream("images/kona.png"));
                coffeeImageView.setImage(image);
                break;
            case "nepalese":
            case "nepalese1":
            case "prtxt4":
            case "prtk4":
            case "prtxt41":
            case "prtk41":
                loadCoffeeData(203);
                image = new Image(getClass().getResourceAsStream("images/nepalese.png"));
                coffeeImageView.setImage(image);
                break;
            case "ethiopia":
            case "ethiopia1":
            case "prtxt5":
            case "prtk5":
            case "prtxt51":
            case "prtk51":
                loadCoffeeData(204);
                image = new Image(getClass().getResourceAsStream("images/ethiopia.png"));
                coffeeImageView.setImage(image);
                break;
            case "indian":
            case "indian1":
            case "prtxt6":
            case "prtk6":
            case "prtxt61":
            case "prtk61":
                loadCoffeeData(205);
                image = new Image(getClass().getResourceAsStream("images/indian.png"));
                coffeeImageView.setImage(image);
                break;
            case "guatemala":
            case "prtxt7":
            case "prtk7":
            case "guatemala1":
            case "prtxt71":
            case "prtk71":
                loadCoffeeData(206);
                image = new Image(getClass().getResourceAsStream("images/guatemala.png"));
                coffeeImageView.setImage(image);
                break;
            case "costa":
            case "prtxt8":
            case "prtk8":
            case "costa1":
            case "prtxt81":
            case "prtk81":
                loadCoffeeData(207);
                image = new Image(getClass().getResourceAsStream("images/costa.png"));
                coffeeImageView.setImage(image);
                break;
            case "jamaica":
            case "prtxt21":
            case "prtk21":
                loadCoffeeData(208);
                image = new Image(getClass().getResourceAsStream("images/jamaica.png"));
                coffeeImageView.setImage(image);
                break;
            case "kopi":
            case "prtxt22":
            case "prtk22":
                loadCoffeeData(209);
                image = new Image(getClass().getResourceAsStream("images/kopi.png"));
                coffeeImageView.setImage(image);
                break;
            default:
                loadCoffeeData(201);
                image = new Image(getClass().getResourceAsStream("images/bucaramanga.png"));
                coffeeImageView.setImage(image);
                break;
        }

    }


    @FXML
    private void handlePlusClick(MouseEvent event) {
        currentQuantity++;
        updateQuantityAndPrice();
    }


    @FXML
    private void handleMinusClick(MouseEvent event) {
        if (currentQuantity > 1) {
            currentQuantity--;
            updateQuantityAndPrice();
        }
    }


    private void updateQuantityAndPrice() {

        if (quantity != null) {
            quantity.setText(String.valueOf(currentQuantity));
        }


        if (currentCoffee != null && coffeePriceLabel != null) {
            double totalPrice = currentCoffee.getPrice() * currentQuantity;
            String formattedPrice = NumberFormat.getNumberInstance().format(totalPrice);
            coffeePriceLabel.setText("TK " + formattedPrice + " BDT");
        }
    }

    private void loadCoffeeData(int coffeeId) {
        try {
            currentCoffee = client.getCoffeeById(coffeeId);
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


        if (coffeeNameLabel != null) {
            coffeeNameLabel.setText(currentCoffee.getName());
        }
        if (coffeePriceLabel != null) {

            double totalPrice = currentCoffee.getPrice() * currentQuantity;
            String formattedPrice = NumberFormat.getNumberInstance().format(totalPrice);
            coffeePriceLabel.setText("TK " + formattedPrice + " BDT");
        }
        if (coffeeDescriptionLabel != null) {
            coffeeDescriptionLabel.setText(currentCoffee.getDescription());
        }


        if (coffeePacketSizeLabel != null) {
            coffeePacketSizeLabel.setText(currentCoffee.getPacketSize());
        }
        if (coffeeWeightLabel != null) {
            coffeeWeightLabel.setText(currentCoffee.getWeight() + "g");
        }


        if(currentCoffee.getStrength() >= 1)
        {
            str1.setOpacity(1);
            if(currentCoffee.getStrength() >= 2)
            {
                str2.setOpacity(1);
                if(currentCoffee.getStrength() >= 3) str3.setOpacity(1);
            }
        }
        if(currentCoffee.getFlavour() >= 1)
        {
            fl1.setOpacity(1);
            if(currentCoffee.getFlavour() >= 2)
            {
                fl2.setOpacity(1);
                if(currentCoffee.getFlavour() >= 3) fl3.setOpacity(1);
            }
        }
        if(currentCoffee.getAcidity() >= 1)
        {
            ac1.setOpacity(1);
            if(currentCoffee.getAcidity() >= 2)
            {
                ac2.setOpacity(1);
                if(currentCoffee.getAcidity() >= 3) ac3.setOpacity(1);
            }
        }
        if(currentCoffee.getAroma() >= 1)
        {
            ar1.setOpacity(1);
            if(currentCoffee.getAroma() >= 2)
            {
                ar2.setOpacity(1);
                if(currentCoffee.getAroma() >= 3) ar3.setOpacity(1);
            }
        }
        int x = (int)currentCoffee.getAverageRating();
        switch(x)
        {
            case 5:
                star5.setOpacity(1);
            case 4:
                star4.setOpacity(1);
            case 3:
                star3.setOpacity(1);
            case 2:
                star2.setOpacity(1);
            case 1:
                star1.setOpacity(1);
                break;
            default:
                star5.setOpacity(1);
                star4.setOpacity(1);
                star3.setOpacity(1);
                star2.setOpacity(1);
                star1.setOpacity(1);
                break;
        }
        if(totalreview != null)
        {
            totalreview.setText(currentCoffee.getReviews().size() + " Reviews");
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


        if (coffeeStockLabel != null) {
            coffeeStockLabel.setText("Stock: " + currentCoffee.getCurrentStock());
        }
        if (coffeeSalesLabel != null) {
            coffeeSalesLabel.setText("Sales: " + currentCoffee.getNumberOfSales());
        }


        if (coffeeAverageRatingLabel != null) {
            double avgRating = currentCoffee.getAverageRating();
            coffeeAverageRatingLabel.setText("Rating: " + String.format("%.1f", avgRating) + "/5.0");
        }


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

        try {

        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        System.out.println("Coffee data loaded successfully: " + currentCoffee.getName());
    }

    private void displayNotFound() {
        if (coffeeNameLabel != null) {
            coffeeNameLabel.setText("Coffee Not Found");
        }
        if (coffeeDescriptionLabel != null) {
            coffeeDescriptionLabel.setText("The requested coffee with ID 201 was not found in the client.");
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

            euclidBoldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/euclidbold.ttf"), 18);
            if (euclidBoldFont == null) {
                System.err.println("Failed to load euclidbold.ttf font - using default");
                euclidBoldFont = Font.font("Arial", 18);
            } else {
                System.out.println("EuclidCircularA-Bold font loaded successfully");
            }


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

        Text textElement = (Text) event.getSource();


        textElement.setUnderline(true);


        textElement.setOnMouseExited(exitEvent -> {
            textElement.setUnderline(false);
        });
    }

    @FXML
    private void redirectToScene(MouseEvent event) {
        try {

            Object source = event.getSource();


            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


            HelloController helloController = fxmlLoader.getController();


            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();


            Platform.runLater(() -> {
                helloController.scrollToTop();


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

                            break;
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyCustomFonts() {

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

        if (coffeeNameLabel != null) {

            Font coffeeNameFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 30);
            coffeeNameLabel.setFont(coffeeNameFont);
            System.out.println("Applied RetrokiaCaps font to coffeeNameLabel");
        }
        if (coffeeWeightLabel != null) {

            Font coffeeNameFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeWeightLabel.setFont(coffeeNameFont);
        }
        if (coffeeFlavourLabel != null) {
            Font coffeeFlavourFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeFlavourLabel.setFont(coffeeFlavourFont);
        }
        if (coffeeAcidityLabel != null) {
            Font coffeeAcidityFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeAcidityLabel.setFont(coffeeAcidityFont);
        }
        if (coffeeAromaLabel != null) {
            Font coffeeAromaFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeAromaLabel.setFont(coffeeAromaFont);
        }
        if (coffeeStockLabel != null) {
            Font coffeeStockFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeStockLabel.setFont(coffeeStockFont);
        }
        if (coffeeSalesLabel != null) {
            Font coffeeSalesFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeSalesLabel.setFont(coffeeSalesFont);
        }
        if (coffeeAverageRatingLabel != null) {
            Font coffeeAverageRatingFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
        }
        if (coffeeStatusLabel != null) {
            Font coffeeStatusFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeStatusLabel.setFont(coffeeStatusFont);
        }
        if (coffeeSpecialFeaturesLabel != null) {
            Font coffeeSpecialFeaturesFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeSpecialFeaturesLabel.setFont(coffeeSpecialFeaturesFont);
        }
        if (coffeeTagsLabel != null) {
            Font coffeeTagsFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeeTagsLabel.setFont(coffeeTagsFont);
        }
        if (coffeePriceLabel != null) {
            Font coffeePriceFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 24);
            coffeePriceLabel.setFont(coffeePriceFont);
        }

        if (quantity != null) {
            Font quantityFont = Font.font(retrokiaFont.getFamily(), FontWeight.BOLD, 20);
            quantity.setFont(quantityFont);
        }
    }
}

    @FXML
    private void handleclick(MouseEvent event) {
        String fxId = null;
        if(event.getSource() instanceof Text)
        {
            fxId = ((Text)event.getSource()).getId();
        }
        else
        {
            fxId = ((Rectangle)event.getSource()).getId();
        }
        grind1.setFill(Paint.valueOf("#e9e9e9"));
        grindtext1.setFill(Paint.valueOf("#000000"));
        grind2.setFill(Paint.valueOf("#e9e9e9"));
        grindtext2.setFill(Paint.valueOf("#000000"));
        grind3.setFill(Paint.valueOf("#e9e9e9"));
        grindtext3.setFill(Paint.valueOf("#000000"));
        grind4.setFill(Paint.valueOf("#e9e9e9"));
        grindtext4.setFill(Paint.valueOf("#000000"));
        grind5.setFill(Paint.valueOf("#e9e9e9"));
        grindtext5.setFill(Paint.valueOf("#000000"));
        grind6.setFill(Paint.valueOf("#e9e9e9"));
        grindtext6.setFill(Paint.valueOf("#000000"));
        grind7.setFill(Paint.valueOf("#e9e9e9"));
        grindtext7.setFill(Paint.valueOf("#000000"));

        switch (fxId) {
            case "grind1":
            case "grindtext1":
                grind1.setFill(Paint.valueOf("#000000"));
                grindtext1.setFill(Paint.valueOf("#ffffff"));

                break;
            case "grind2":
                case "grindtext2":
                grind2.setFill(Paint.valueOf("#000000"));
                grindtext2.setFill(Paint.valueOf("#ffffff"));

                break;
            case "grind3":
                case "grindtext3":
                grind3.setFill(Paint.valueOf("#000000"));
                grindtext3.setFill(Paint.valueOf("#ffffff"));
                break;
            case "grind4":
                case "grindtext4":
                    grind4.setFill(Paint.valueOf("#000000"));
                    grindtext4.setFill(Paint.valueOf("#ffffff"));
                break;
            case "grind5":
                case "grindtext5":
                grind5.setFill(Paint.valueOf("#000000"));
                grindtext5.setFill(Paint.valueOf("#ffffff"));
                break;
            case "grind6":
            case "grindtext6":
                grind6.setFill(Paint.valueOf("#000000"));
                grindtext6.setFill(Paint.valueOf("#ffffff"));
                break;
            case "grind7":
                case "grindtext7":
                    grind7.setFill(Paint.valueOf("#000000"));
                    grindtext7.setFill(Paint.valueOf("#ffffff"));
                break;
            default:
                System.out.println("Unknown grind type: " + fxId);
                break;
        }
    }
    @FXML
    private void zoomout(MouseEvent event) {

        if(event.getSource() instanceof Text) {
            Text textElement = (Text) event.getSource();
            textElement.setScaleX(1.1);
            textElement.setScaleY(1.1);


            textElement.setOnMouseExited(exitEvent -> {
                textElement.setScaleX(1.0);
                textElement.setScaleY(1.0);
            });
        }
        else if(event.getSource() instanceof ImageView)
        {
            ImageView imageElement = (ImageView) event.getSource();
            imageElement.setScaleX(1.1);
            imageElement.setScaleY(1.1);


            imageElement.setOnMouseExited(exitEvent -> {
                imageElement.setScaleX(1.0);
                imageElement.setScaleY(1.0);
            });
        }

    }
    @FXML
    private void redirect(MouseEvent event) {
        if (Client.currentToken == null) {
            try {

                Stage stage;
                if(event.getSource() instanceof Rectangle) {
                    stage = (Stage) ((Rectangle) event.getSource()).getScene().getWindow();
                }
                else
                {
                    stage = (Stage) ((Text) event.getSource()).getScene().getWindow();
                }


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


                stage.setTitle("Noir Dhaka - Login");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {

                Stage stage;
                if(event.getSource() instanceof Rectangle) {
                    stage = (Stage) ((Rectangle) event.getSource()).getScene().getWindow();
                }
                else
                {
                    stage = (Stage) ((Text) event.getSource()).getScene().getWindow();
                }

                client.addToCart(this.currentCoffee.getId(), currentQuantity);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


                stage.setTitle("Noir Dhaka - Cart");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
        private void redirecttoprofile(MouseEvent event) {
        if (Client.currentToken == null) {
            try {
                   Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka - Login");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {

                Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka - Cart");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    private void handleCartClick() {
        try {
            if (Client.currentToken != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }