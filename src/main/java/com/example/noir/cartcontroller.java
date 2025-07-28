package com.example.noir;

import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.backend.dbFetch;
import com.example.backend.User;
import com.example.backend.Coffee;
import com.example.backend.Cart;
import com.example.backend.Review;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;

public class cartcontroller implements Initializable{
    // FXML Elements from Scene Builder
    @FXML
    private ScrollPane cartScrollPane;
    @FXML
    private AnchorPane cartContentPane;
    @FXML
    private VBox cartItemsContainer;
    @FXML
    private Label emptyCartLabel;
    @FXML
    private Button startShoppingButton;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button checkoutButton;
    @FXML
    private ImageView image;
    @FXML
    private Text pricetxt;
    @FXML
    private Text shiptxt;
    @FXML
    private Text totaltxt;
    @FXML
    private Text plattxt;
    @FXML
    private Text discounttxt;
    @FXML
    private Button apply;
    @FXML
    private TextField couponfield;
    private boolean discounted = false;
    // Legacy elements (keep for compatibility)
    @FXML
    private ScrollPane verticalScrollPane;

    private dbFetch database;
    private Cart currentCart;

    // Store references to cart item UI elements for updates
    private Map<Integer, CartItemUI> cartItemUIMap = new HashMap<>();

    // Inner class to hold UI references for each cart item
    private static class CartItemUI {
        HBox containerBox;
        Label quantityLabel;
        Label subtotalLabel;
        Coffee coffee;

        CartItemUI(HBox containerBox, Label quantityLabel, Label subtotalLabel, Coffee coffee) {
            this.containerBox = containerBox;
            this.quantityLabel = quantityLabel;
            this.subtotalLabel = subtotalLabel;
            this.coffee = coffee;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize database connection
        database = new dbFetch();

        // Set reference for legacy code compatibility
        if (verticalScrollPane == null) {
            verticalScrollPane = cartScrollPane;
        }

        // Load the image when the controller initializes
        loadImageFromResources();

        // Load and display cart data
        loadCartData();
    }

    private void loadCartData() {
        try {
            // Fetch cart from database
            currentCart = database.getCart();

            if (currentCart != null && currentCart.getBuyHistory() != null && !currentCart.getBuyHistory().isEmpty()) {
                displayCartItems();
            } else {
                displayEmptyCart();
            }
        } catch (Exception e) {
            System.err.println("Error loading cart data: " + e.getMessage());
            displayEmptyCart();
        }
    }

    private void displayCartItems() {
        // Hide empty cart elements
        emptyCartLabel.setVisible(false);
        startShoppingButton.setVisible(false);

        // Clear existing cart items and UI map
        cartItemsContainer.getChildren().clear();
        cartItemUIMap.clear();

        HashMap<Integer, Integer> buyHistory = currentCart.getBuyHistory();

        if (buyHistory.isEmpty()) {
            displayEmptyCart();
            return;
        }

        // Calculate required height based on number of items
        int itemCount = buyHistory.size();
        double itemHeight = 120; // Height per cart item
        double totalHeight = Math.max(290, itemCount * itemHeight + 40); // 40 for padding

        // Set the AnchorPane size dynamically
        cartContentPane.setPrefHeight(totalHeight);
        cartContentPane.setMinHeight(totalHeight);

        double totalPrice = 0.0;

        // Add each cart item
        for (Map.Entry<Integer, Integer> entry : buyHistory.entrySet()) {
            Integer coffeeId = entry.getKey();
            Integer quantity = entry.getValue();

            // Get coffee details from database
            Coffee coffee = database.getCoffeeById(coffeeId);

            if (coffee != null) {
                HBox cartItem = createCartItemUI(coffee, quantity);
                cartItemsContainer.getChildren().add(cartItem);
                totalPrice += coffee.getPrice() * quantity;
            }
        }

        // Update total price label and show it (no need to position since it's fixed in FXML)
        updateTotalPriceDisplay();
        totalPriceLabel.setVisible(true);

        // Show checkout button (no need to position since it's fixed in FXML)
        checkoutButton.setVisible(true);
    }

    private HBox createCartItemUI(Coffee coffee, int quantity) {
        HBox cartItem = new HBox(15);
        cartItem.setStyle("-fx-background-color: #e9e9e9; -fx-padding: 10; -fx-border-color: #000000; -fx-border-width: 1; -fx-border-radius: 5;");
        cartItem.setPrefWidth(500);
        cartItem.setPrefHeight(100);
        cartItem.setAlignment(Pos.CENTER_LEFT);

        // Coffee image
        ImageView coffeeImage = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream(coffee.getImageurl()));
            coffeeImage.setImage(img);
        } catch (Exception e) {
            // Use placeholder image if coffee image not found
            try {
                coffeeImage.setImage(new Image(getClass().getResourceAsStream("/com/example/noir/images/bucaramanga.png")));
            } catch (Exception ex) {
                // If placeholder also fails, create a simple rectangle placeholder
                coffeeImage.setImage(null);
            }
        }
        coffeeImage.setFitWidth(80);
        coffeeImage.setFitHeight(80);
        coffeeImage.setPreserveRatio(true);

        // Coffee details VBox
        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(250);

        Label nameLabel = new Label(coffee.getName());
        nameLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label priceLabel = new Label(String.format("$%.2f each", coffee.getPrice()));
        priceLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-text-fill: #666;");

        Label subtotalLabel = new Label(String.format("Subtotal: $%.2f", coffee.getPrice() * quantity));
        subtotalLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        detailsBox.getChildren().addAll(nameLabel, priceLabel, subtotalLabel);

        // Quantity controls VBox
        VBox quantityBox = new VBox(5);
        quantityBox.setAlignment(Pos.CENTER);

        Label quantityLabel = new Label("Quantity : " + quantity);
        quantityLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 14px; -fx-font-weight: bold;");

        // HBox to hold all buttons horizontally
        HBox buttonBox = new HBox(8); // Increased spacing slightly
        buttonBox.setAlignment(Pos.CENTER);

        // Decrease quantity button
        Button decreaseBtn = new Button("-");
        decreaseBtn.setStyle("-fx-font-size: 14px; -fx-min-width: 30; -fx-min-height: 30; -fx-background-color: #91B08F; -fx-cursor: hand");
        decreaseBtn.setOnAction(e -> updateQuantity(coffee.getId(), -1));

        // Increase quantity button
        Button increaseBtn = new Button("+");
        increaseBtn.setStyle("-fx-font-size: 14px; -fx-min-width: 30; -fx-min-height: 30; -fx-cursor: hand; -fx-background-color: #91B08F");
        increaseBtn.setOnAction(e -> updateQuantity(coffee.getId(), 1));

        // Remove item button with trash can icon only
        Button removeBtn = new Button();
        removeBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #e9e9e9; -fx-border-width: 1; -fx-border-radius: 3; -fx-padding: 8; -fx-cursor: hand;");
        removeBtn.setOnAction(e -> removeItem(coffee.getId()));

        // Create ImageView for the bin icon
        ImageView binIcon = new ImageView();
        try {
            Image binImage = new Image(getClass().getResourceAsStream("/com/example/noir/images/bin.png"));
            binIcon.setImage(binImage);
            binIcon.setFitWidth(24); // Enlarged icon
            binIcon.setFitHeight(24); // Enlarged icon
            binIcon.setPreserveRatio(true);

            removeBtn.setGraphic(binIcon);

            // Add hover effect
            removeBtn.setOnMouseEntered(event -> {
                removeBtn.setStyle("-fx-background-color: #e9e9e9; -fx-border-color: #e9e9e9; -fx-border-width: 1; -fx-border-radius: 3; -fx-padding: 8; -fx-cursor: hand;");
            });

            removeBtn.setOnMouseExited(event -> {
                removeBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #e9e9e9; -fx-border-width: 1; -fx-border-radius: 3; -fx-padding: 8; -fx-cursor: hand;");
            });

        } catch (Exception ex) {
            // If image loading fails, fall back to text
            removeBtn.setText("×");
            removeBtn.setStyle("-fx-background-color: #e9e9e9; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 5 10; -fx-cursor: hand;");
            System.err.println("Could not load bin.png: " + ex.getMessage());
        }

        // Add all buttons to the horizontal button box: decrease, increase, then remove
        buttonBox.getChildren().addAll(decreaseBtn, increaseBtn, removeBtn);

        // Add quantity label and button box to the vertical quantity box
        quantityBox.getChildren().addAll(quantityLabel, buttonBox);

        cartItem.getChildren().addAll(coffeeImage, detailsBox, quantityBox);

        // Store UI references for later updates
        cartItemUIMap.put(coffee.getId(), new CartItemUI(cartItem, quantityLabel, subtotalLabel, coffee));

        return cartItem;
    }

    private void displayEmptyCart() {
        // Clear cart items and UI map
        cartItemsContainer.getChildren().clear();
        cartItemUIMap.clear();

        // Hide cart summary elements
        totalPriceLabel.setVisible(false);
        checkoutButton.setVisible(false);
        pricetxt.setText(String.format("$%.2f", 0.00));
        shiptxt.setText(String.format("$%.2f", 0.00));
        plattxt.setText(String.format("$%.2f", 0.00));
        totaltxt.setText(String.format("$%.2f", 0.00));
        discounttxt.setText(String.format("-$%.2f", 0.00));

        // Show empty cart elements
        emptyCartLabel.setVisible(true);
        startShoppingButton.setVisible(true);

        // Reset content pane height
        cartContentPane.setPrefHeight(353);
    }

    private void updateQuantity(int coffeeId, int change) {
        if (currentCart != null) {
            // Get current quantity before update
            Integer currentQuantity = currentCart.getBuyHistory().get(coffeeId);
            if (currentQuantity == null) return;

            // Update the cart model
            if (change > 0) {
                currentCart.addToCart(coffeeId, change);
            } else {
                if(currentQuantity <= 1)
                {
                    removeItem(coffeeId);

                }
                else{
                    currentCart.removeFromCart(coffeeId);
                }

            }

            // Check if item was removed completely
            Integer newQuantity = currentCart.getBuyHistory().get(coffeeId);

            if (newQuantity == null || newQuantity <= 0) {
                // Item was removed, refresh the entire display
                loadCartData();
            } else {
                // Item still exists, update UI directly
                updateCartItemUI(coffeeId, newQuantity);
                updateTotalPriceDisplay();

                // Update database in background
                try {
                    // database.updateCart(currentCart);
                } catch (Exception e) {
                    System.err.println("Error updating cart: " + e.getMessage());
                }
            }
        }
    }

    private void updateCartItemUI(int coffeeId, int newQuantity) {
        CartItemUI cartItemUI = cartItemUIMap.get(coffeeId);
        if (cartItemUI != null) {
            // Update quantity label
            cartItemUI.quantityLabel.setText("Quantity : " + newQuantity);

            // Update subtotal label
            double subtotal = cartItemUI.coffee.getPrice() * newQuantity;
            cartItemUI.subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
        }
    }

    private void updateTotalPriceDisplay() {
        if (currentCart != null) {
            double totalPrice = currentCart.getTotalPrice();
            totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
            pricetxt.setText(String.format("$%.2f", totalPrice));
            shiptxt.setText(String.format("$%.2f", 0.05*totalPrice));
            plattxt.setText(String.format("$%.2f", 0.003*totalPrice));
            totaltxt.setText(String.format("$%.2f", 0.803*totalPrice));
            if(discounted){discounttxt.setText(String.format("-$%.2f", 0.25*totalPrice));}
            else discounttxt.setText(String.format("-$%.2f", 0.00));
        }
    }

    private void removeItem(int coffeeId) {
        if (currentCart != null) {
            currentCart.removeFromCartEntirely(coffeeId);
            // Update database
            try {
                database.updateCart(currentCart);
                // Refresh display since item is completely removed
                loadCartData();
            } catch (Exception e) {
                System.err.println("Error removing item from cart: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleCheckout() {
        if (currentCart != null && !currentCart.getBuyHistory().isEmpty()) {
            // Build detailed receipt content
            StringBuilder receiptContent = new StringBuilder();
            receiptContent.append("═══════════════════════════════════════\n");
            receiptContent.append("           NOIR DHAKA RECEIPT           \n");
            receiptContent.append("═══════════════════════════════════════\n\n");

            // Add current date and time (centered)
            String dateTime = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            receiptContent.append(String.format("%15s%s\n", "Date: ", dateTime));

            String orderId = "ORD-" + (System.currentTimeMillis() % 100000);
            receiptContent.append(String.format("%22s%s\n\n", "Order ID: ", orderId));

            receiptContent.append("            ITEMS PURCHASED\n");
            receiptContent.append("───────────────────────────────────────\n");

            double grandTotal = 0.0;
            HashMap<Integer, Integer> buyHistory = currentCart.getBuyHistory();

            // Loop through each item in the cart
            for (Map.Entry<Integer, Integer> entry : buyHistory.entrySet()) {
                Integer coffeeId = entry.getKey();
                Integer quantity = entry.getValue();

                // Get coffee details from database
                Coffee coffee = database.getCoffeeById(coffeeId);

                if (coffee != null) {
                    double itemPrice = coffee.getPrice();
                    double itemTotal = itemPrice * quantity;
                    grandTotal += itemTotal;
                    // Center coffee name
                    String coffeeName = coffee.getName();
                    int nameLength = coffeeName.length();
                    int padding = Math.max(1, (39 - nameLength) / 2); // Ensure padding is at least 1
                    receiptContent.append(String.format("%" + padding + "s%s\n", "", coffeeName));

                    // Center size and weight info
                    String sizeWeight = String.format("Size: %s | Weight: %.0fg",
                        coffee.getPacketSize(), coffee.getWeight());
                    int sizeWeightLength = sizeWeight.length();
                    int sizeWeightPadding = Math.max(1, (39 - sizeWeightLength) / 2); // Ensure padding is at least 1
                    receiptContent.append(String.format("%" + sizeWeightPadding + "s%s\n", "", sizeWeight));

                    // Center price and quantity info
                    String priceQty = String.format("$%.2f × %d = $%.2f", itemPrice, quantity, itemTotal);
                    int priceQtyLength = priceQty.length();
                    int priceQtyPadding = Math.max(1, (39 - priceQtyLength) / 2); // Ensure padding is at least 1
                    receiptContent.append(String.format("%" + priceQtyPadding + "s%s\n", "", priceQty));

                    receiptContent.append("\n");
                }
            }

            receiptContent.append("───────────────────────────────────────\n");

            // Center total items
            String totalItems = String.format("TOTAL ITEMS: %d",
                buyHistory.values().stream().mapToInt(Integer::intValue).sum());
            int totalItemsLength = totalItems.length();
            int totalItemsPadding = Math.max(1, (39 - totalItemsLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + totalItemsPadding + "s%s\n", "", totalItems));

            // Center total amount

            if(discounted) grandTotal = 0.803*grandTotal;
            else grandTotal = 1.053*grandTotal;
            String totalAmount = String.format("TOTAL AMOUNT: $%.2f", grandTotal);
            int totalAmountLength = totalAmount.length();
            int totalAmountPadding = Math.max(1, (39 - totalAmountLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + totalAmountPadding + "s%s\n", "", totalAmount));

            receiptContent.append("───────────────────────────────────────\n\n");

            // Center payment method and status
            String paymentMethod = "Payment Method: Cash on Delivery";
            int paymentLength = paymentMethod.length();
            int paymentPadding = Math.max(1, (39 - paymentLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + paymentPadding + "s%s\n", "", paymentMethod));

            String status = "Status: Order Confirmed";
            int statusLength = status.length();
            int statusPadding = Math.max(1, (39 - statusLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + statusPadding + "s%s\n\n", "", status));

            // Center thank you message
            String thankYou = "Thank you for choosing Noir Dhaka!";
            int thankYouLength = thankYou.length();
            int thankYouPadding = Math.max(1, (39 - thankYouLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + thankYouPadding + "s%s\n", "", thankYou));

            String delivery = "Your coffee will be delivered soon.";
            int deliveryLength = delivery.length();
            int deliveryPadding = Math.max(1, (39 - deliveryLength) / 2); // Ensure padding is at least 1
            receiptContent.append(String.format("%" + deliveryPadding + "s%s\n\n", "", delivery));

            receiptContent.append("═══════════════════════════════════════");

            // Create custom alert with scrollable content
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Confirmation");
            alert.setHeaderText("Your Order Has Been Placed Successfully!");
            alert.setResizable(false);
            int setX = 330,setY = 550;
            alert.getDialogPane().setPrefSize(setX,setY); // Width: 700, Height: 600
            alert.getDialogPane().setMinSize(setX, setY);  // Minimum size
            alert.getDialogPane().setMaxSize(setX,setY);

            // Create a TextArea for the receipt content
            TextArea receiptArea = new TextArea(receiptContent.toString());
            receiptArea.setEditable(false);
            receiptArea.setWrapText(false);
            receiptArea.setPrefRowCount(20);
            receiptArea.setPrefColumnCount(50);
            receiptArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-alignment: center;");

            // Put the TextArea in a ScrollPane
            ScrollPane scrollPane = new ScrollPane(receiptArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefSize(550, 400);

            // Set the ScrollPane as the alert's content
            alert.getDialogPane().setContent(scrollPane);

            // Resize the alert dialog
            alert.getDialogPane().setPrefSize(550, 500);
            alert.setResizable(true);

            // Center the dialog on screen
            alert.getDialogPane().setStyle("-fx-alignment: center;");

            alert.showAndWait();

            // Clear cart after checkout
            currentCart.getBuyHistory().clear();
            try {
                // database.updateCart(currentCart);
                loadCartData();
            } catch (Exception e) {
                System.err.println("Error clearing cart: " + e.getMessage());
            }
        }
    }

    @FXML
    private void navigateToProducts() {
        try {
            Stage stage = (Stage) cartScrollPane.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        case "contact":
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

    // Fixed scrollToTop method - moved outside redirectToScene
    public void scrollToTop() {
        // Reset scroll position to top with a quick animation
        Platform.runLater(() -> {
            smoothScrollTo(0.0, 0.0001); // 0.25 seconds duration
        });
    }

    // Add the smoothScrollTo method
    private void smoothScrollTo(double targetValue, double time) {
        ScrollPane scrollPane = (verticalScrollPane != null) ? verticalScrollPane : cartScrollPane;
        if (scrollPane == null) return;

        double startValue = scrollPane.getVvalue();
        Duration duration = Duration.seconds(time);
        int frames = 240;

        Timeline timeline = new Timeline();
        for (int i = 0; i <= frames; i++) {
            double fraction = (double) i / frames;
            double value = startValue + (targetValue - startValue) * fraction;

            KeyFrame keyFrame = new KeyFrame(
                    duration.multiply(fraction),
                    event -> scrollPane.setVvalue(value)
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
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
    private void handlehoverzoom(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        // Create a scale transition for zoom effect
        javafx.animation.ScaleTransition scaleTransition = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.1); // Scale to 110% of original size
        scaleTransition.setToY(1.1);
        scaleTransition.play();
        // Add mouse exited handler to zoom back out
        imageView.setOnMouseExited(exitEvent -> {
            javafx.animation.ScaleTransition scaleBack = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
            scaleBack.setToX(1.0); // Scale back to original size
            scaleBack.setToY(1.0);
            scaleBack.play();
        });
    }

    private void loadImageFromResources() {
        try {
            // Load from resources (src/main/resources/)
            //Image imageObj = new Image(getClass().getResourceAsStream(""));
            //image.setImage(imageObj);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
    @FXML
    private void handleCoupon() {
        String couponCode = couponfield.getText();
        if(couponCode.equalsIgnoreCase("noir-dhk")) {
            discounted = true;
        }
        else
        {
            discounted = false;
        }
        updateTotalPriceDisplay();
        couponfield.clear();
    }
}