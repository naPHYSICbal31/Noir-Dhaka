package com.example.noir;

import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;

import com.example.backend.dbFetch;
import com.example.backend.User;
import com.example.backend.Coffee;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

import java.util.ArrayList;
public class ProfileController implements Initializable {

    @FXML
    private ScrollPane verticalScrollPane;
    @FXML
    private Label usernameLabel41;
    @FXML
    private Label contact;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel2;
    @FXML
    private Label usernameLabel3;
    @FXML
    private Label usernameLabel4;
    // Add UI components to display database data
    @FXML
    public Label usernameLabel;

    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private ImageView cart;
    @FXML
    private ListView<String> coffeeListView;

    @FXML
    private TableView<Coffee> coffeeTableView;
    @FXML
    private ScrollPane scrollpane1;
    @FXML
    private TableColumn<Coffee, String> nameColumn;

    @FXML
    private TableColumn<Coffee, Double> priceColumn;

    @FXML
    private TableColumn<Coffee, String> descriptionColumn;
    @FXML
    private TableColumn<Coffee, Integer> coffeeCountColumn;
    public TableColumn<Coffee, String> buyDateColumn;

    @FXML
    private Button logoutbutton;
    @FXML
    private Text ordertxt;
    @FXML
    private Line orderline;
    private Font euclidBoldFont;
    private User currentUser;
    private dbFetch database;
    private VBox receiptContainer;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = new dbFetch();
        loadFonts();
        loadUserData();
        loadCoffeeData();
        setupTableColumns();
        applyCustomFonts();
        applyTableViewStyles();
        setupReceiptScrollPane();
    }

    private void setupReceiptScrollPane() {
        // Create a VBox container to hold receipt information
        receiptContainer = new VBox();
        receiptContainer.setSpacing(15);
        receiptContainer.setStyle("-fx-background-color: #e9e9e9; -fx-padding: 20;");
        
        // Set the VBox as the content of the scrollpane
        scrollpane1.setContent(receiptContainer);
        scrollpane1.setFitToWidth(true);
        
        // Load receipt data initially
        loadReceiptData();
    }
    
    private void loadReceiptData() {
        if (receiptContainer == null || currentUser == null) return;
        
        // Clear existing content
        receiptContainer.getChildren().clear();
        
        HashMap<String, LocalDateTime> receipts = currentUser.getRecipts();
        
        if (receipts == null || receipts.isEmpty()) {
            // Show "No receipts" message
            Label noReceiptsLabel = new Label("No receipts found");
            noReceiptsLabel.setFont(euclidBoldFont);
            noReceiptsLabel.setStyle("-fx-text-fill: #666666; -fx-alignment: center;");
            receiptContainer.getChildren().add(noReceiptsLabel);
            return;
        }
        
        // Add title
        Label titleLabel = new Label("BILLING RECEIPTS");
        titleLabel.setFont(Font.font(euclidBoldFont.getFamily(), 22));
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold;");
        receiptContainer.getChildren().add(titleLabel);
        
        // Add a proper line separator instead of dashed text
        javafx.scene.shape.Line separatorLine = new javafx.scene.shape.Line();
        separatorLine.setStartX(0);
        separatorLine.setEndX(200); // Adjusted width to match new container width
        separatorLine.setStroke(javafx.scene.paint.Color.web("#91b08f"));
        separatorLine.setStrokeWidth(2);
        receiptContainer.getChildren().add(separatorLine);
        
        // Sort receipts by date (newest first)
        receipts.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .forEach(entry -> {
                String receiptText = entry.getKey();
                LocalDateTime receiptDate = entry.getValue();
                
                // Create a container for each receipt with reduced width
                VBox receiptBox = new VBox();
                receiptBox.setSpacing(12);
                receiptBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 25; " +
                                  "-fx-background-radius: 10; -fx-border-color: #d0d0d0; " +
                                  "-fx-border-radius: 10; -fx-border-width: 2;");
                
                // Set reduced width for receipt containers
                receiptBox.setPrefWidth(460);
                receiptBox.setMaxWidth(460);
                
                // Receipt date with larger font
                Label dateLabel = new Label("Date: " + formatDateTimePretty(receiptDate));
                dateLabel.setFont(Font.font(euclidBoldFont.getFamily(), 16));
                dateLabel.setStyle("-fx-text-fill: #4e634d; -fx-font-weight: bold;");
                
                // Receipt content - Use Text instead of Label to avoid ellipsis
                Text contentText = new Text(receiptText);
                contentText.setFont(Font.font(euclidBoldFont.getFamily(), 14));
                contentText.setStyle("-fx-fill: #333333;");
                contentText.setWrappingWidth(410); // Adjusted wrapping width for new container size
                
                // Calculate height based on text content more accurately but reduce by 50
                String[] lines = receiptText.split("\n");
                int totalLines = 0;
                for (String line : lines) {
                    // Calculate how many visual lines each logical line will take
                    int lineLength = line.length();
                    int wrappedLines = Math.max(1, (int) Math.ceil(lineLength / 60.0)); // Adjusted for smaller width
                    totalLines += wrappedLines;
                }
                
                // Set height based on calculated lines, reduced by 50
                double estimatedHeight = Math.max(50, totalLines * 22 + 10); // Reduced from 60 to 10 padding, min from 100 to 50
                receiptBox.setMinHeight(estimatedHeight);
                receiptBox.setPrefHeight(estimatedHeight);
                
                // Use Text instead of Label to avoid ellipsis issues
                receiptBox.getChildren().addAll(dateLabel, contentText);
                receiptContainer.getChildren().add(receiptBox);
            });
    
    // Update the container's preferred height to fit all receipts
    Platform.runLater(() -> {
        double totalHeight = 0;
        
        // Calculate height more accurately
        for (Node node : receiptContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                totalHeight += vbox.getPrefHeight();
            } else if (node instanceof Label) {
                totalHeight += 35; // Height for title
            } else if (node instanceof javafx.scene.shape.Line) {
                totalHeight += 15; // Height for line separator
            }
        }
        
        // Add spacing between elements
        totalHeight += receiptContainer.getSpacing() * (receiptContainer.getChildren().size() - 1);
        // Add container padding
        totalHeight += 60;
        
        // Ensure minimum height and set the calculated height
        double finalHeight = Math.max(295, totalHeight);
        receiptContainer.setPrefHeight(finalHeight);
        receiptContainer.setMinHeight(finalHeight);
        
        // Update the scroll pane to accommodate the new height
        scrollpane1.setVvalue(0); // Reset scroll to top
    });
}

    private void loadFonts() {
        try {
            // Load EuclidCircularA-Bold font
            euclidBoldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/euclidbold.ttf"), 18);
            if (euclidBoldFont == null) {
                System.err.println("Failed to load euclidbold.ttf font - using default");
                euclidBoldFont = Font.font("Arial", 18); // Fallback font
            } else {
                System.out.println("EuclidCircularA-Bold font loaded successfully");
            }
        } catch (Exception e) {
            System.err.println("Error loading fonts: " + e.getMessage());
            euclidBoldFont = Font.font("Arial", 18); // Fallback font
            e.printStackTrace();
        }
    }

    private void applyCustomFonts() {
        // Apply the loaded font to UI components
        if (euclidBoldFont != null) {
            usernameLabel.setFont(euclidBoldFont);
            emailLabel.setFont(euclidBoldFont);
            addressLabel.setFont(euclidBoldFont);
            usernameLabel1.setFont(euclidBoldFont);
            usernameLabel2.setFont(euclidBoldFont);
            usernameLabel3.setFont(euclidBoldFont);
            usernameLabel4.setFont(euclidBoldFont);
            usernameLabel41.setFont(euclidBoldFont);
            contact.setFont(euclidBoldFont);
        }
    }

    private void loadUserData() {
        try {
            currentUser = database.getUserinfo();
            if (currentUser != null) {
                if (usernameLabel != null) {
                    usernameLabel.setText(currentUser.getUsername());
                }
                if (emailLabel != null) {
                    emailLabel.setText(currentUser.getEmail());
                    System.out.println(currentUser.getEmail());
                }
                if (addressLabel != null) {
                    addressLabel.setText(currentUser.getAddress());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }
    public static String formatDateTimePretty(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDaySuffix(day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM HH:mm");
        String formatted = dateTime.format(formatter);

        return day + suffix + " " + formatted;
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    private void loadCoffeeData() {
        try {
            if (coffeeTableView != null) {
                List<Coffee> purchasedCoffees = new ArrayList<>();
                User u = database.getUserinfo();
                HashMap<Integer, Integer> bought = u.getBuyHistory();

                if (u != null && bought != null) {
                    for (int coffeeId : bought.keySet()) {

                        Integer purchaseCount = bought.get(coffeeId);
                        System.out.println(coffeeId);
                        Coffee coffee = database.getCoffeeById(coffeeId);

                        System.out.println(coffee.getName());
                        System.out.println(purchaseCount);
                        if (purchaseCount != null && purchaseCount > 0) {
                            System.out.println(coffee.getName());
                            purchasedCoffees.add(coffee);
                        }
                    }
                }

                ObservableList<Coffee> filteredCoffeeData = FXCollections.observableArrayList(purchasedCoffees);
                coffeeTableView.setItems(filteredCoffeeData);
            }

        } catch (Exception e) {
            System.err.println("Error loading coffee data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        if (nameColumn != null) {

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setCellFactory(column -> {
                return new javafx.scene.control.TableCell<Coffee, String>() {
                    private javafx.scene.text.Text text;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            text = new javafx.scene.text.Text(item);
                            text.setWrappingWidth(nameColumn.getWidth() - 20);
                            text.textProperty().bind(itemProperty());
                            setGraphic(text);
                            setText(null);
                        }
                    }
                };
            });
        }

        if (priceColumn != null) {
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        if (descriptionColumn != null) {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            descriptionColumn.setCellFactory(column -> {
                return new javafx.scene.control.TableCell<Coffee, String>() {
                    private javafx.scene.text.Text text;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            text = new javafx.scene.text.Text(item);
                            text.setWrappingWidth(descriptionColumn.getWidth() - 20);
                            text.textProperty().bind(itemProperty());
                            setGraphic(text);
                            setText(null);
                        }
                    }
                };
            });
        }


        if (coffeeCountColumn != null) {
            coffeeCountColumn.setCellValueFactory(cellData -> {
                Coffee coffee = cellData.getValue();

                if (currentUser != null && currentUser.getBuyHistory() != null) {
                    // Get count from buy history using coffee ID
                    Integer count = currentUser.getBuyHistory().get(coffee.getId());
                    return new javafx.beans.property.SimpleIntegerProperty(count != null ? count : 0).asObject();
                }
                return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
            });
        }

        if (buyDateColumn != null) {
            buyDateColumn.setCellValueFactory(cellData -> {
                Coffee coffee = cellData.getValue();

                if (currentUser != null && currentUser.getBuyTime() != null) {

                    LocalDateTime timeObj = currentUser.getBuyTime().get(coffee.getId());

                    if (timeObj != null) {
                        String timeStr = formatDateTimePretty(timeObj);
                        return new javafx.beans.property.SimpleStringProperty(timeStr);
                    }
                }

                return new javafx.beans.property.SimpleStringProperty("N/A");
            });
        }



    }

    // Method to refresh data
    @FXML
    private void refreshData() {
        loadUserData();
        loadCoffeeData();
        loadReceiptData(); // Refresh receipt data as well
    }

    // Your existing methods...
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
            else if(source instanceof Label)
            {
                stage = (Stage) ((Label) source).getScene().getWindow();
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
                String fxId = "";
                // Switch statement to handle different navigation targets
                if (source instanceof Text) {
                    Text textSource = (Text) source;
                    fxId = textSource.getId();
                }
                else if(source instanceof Label)
                    {
                        Text textSource = (Text) source;
                        fxId = textSource.getId();
                    }
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
                        case "contact":
                            helloController.handleTop4Click();
                            break;
                        default:
                            // No specific scroll action for other elements
                            break;
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
        if (verticalScrollPane == null) return;

        double startValue = verticalScrollPane.getVvalue();
        Duration duration = Duration.seconds(time);
        int frames = 240;

        Timeline timeline = new Timeline();
        for (int i = 0; i <= frames; i++) {
            double fraction = (double) i / frames;
            double value = startValue + (targetValue - startValue) * fraction;

            KeyFrame keyFrame = new KeyFrame(
                    duration.multiply(fraction),
                    event -> verticalScrollPane.setVvalue(value)
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
    public void createFadeAway(Node node, Duration fadeOutDuration) {
        if (node == null) {
            System.err.println("Cannot fade null node: " + node);
            return;
        }
        // Fade out transition
        FadeTransition fadeOut = new FadeTransition(fadeOutDuration, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
    public void createFadeIn(Node node, Duration fadeInDuration) {
        if (node == null) {
            System.err.println("Cannot fade null node: " + node);
            return;
        }
        // Fade out transition
        FadeTransition fadeIn = new FadeTransition(fadeInDuration, node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }


    // Fade out method with visibility checker
    @FXML
    public void bar2clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel2, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        scrollpane1.setMouseTransparent(true);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(coffeeTableView, Duration.seconds(0.25));
            coffeeTableView.setMouseTransparent(false);
            fadeInIfHidden(ordertxt, Duration.seconds(0.25));
            fadeInIfHidden(orderline, Duration.seconds(0.25));
        }));
        delay.play();

    }
    @FXML
    public void bar3clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel2, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            // Refresh receipt data before showing
            loadReceiptData();
            fadeInIfHidden(scrollpane1, Duration.seconds(0.25));
            scrollpane1.setMouseTransparent(false);
        }));
        delay.play();

    }
    @FXML
    public void bar1clicked(MouseEvent event)
    {
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        scrollpane1.setMouseTransparent(true);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(usernameLabel, Duration.seconds(0.25));
            fadeInIfHidden(emailLabel, Duration.seconds(0.25));
            fadeInIfHidden(addressLabel, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel1, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel2, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel3, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel4, Duration.seconds(0.25));
        }));
        delay.play();
    }
    private void fadeOutIfVisible(Node node, Duration duration) {
        if (node == null) {
            System.err.println("Cannot fade null node");
            return;
        }

        // Only fade out if the node is currently visible (opacity > 0.2)
        if (node.getOpacity() > 0.2) {
            System.out.println("Fading out: " + node.getClass().getSimpleName());
            FadeTransition fade = new FadeTransition(duration, node);
            fade.setFromValue(node.getOpacity());
            fade.setToValue(0.0);
            fade.play();
        } else {
            System.out.println("Skipping fade out - already faded: " + node.getClass().getSimpleName());
        }
    }

    // Fade in method with visibility checker
    private void fadeInIfHidden(Node node, Duration duration) {
        if (node == null) {
            System.err.println("Cannot fade null node");
            return;
        }

        // Only fade in if the node is currently faded out (opacity <= 0.2)
        if (node.getOpacity() <= 0.2) {
            System.out.println("Fading in: " + node.getClass().getSimpleName());
            FadeTransition fade = new FadeTransition(duration, node);
            fade.setFromValue(node.getOpacity());
            fade.setToValue(1.0);
            fade.play();
        } else {
            System.out.println("Skipping fade in - already visible: " + node.getClass().getSimpleName());
        }
    }
    @FXML
    private void handleLogOut(){
        database.logOut();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 1440, 810);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());

        // Get the current stage and set the new scene
        Stage stage = (Stage) logoutbutton.getScene().getWindow();
        stage.setScene(scene);

        // Reset scroll position to top
        HelloController controller = loader.getController();
        if (controller != null) {
            controller.scrollToTop();
        }

        stage.show();
    }
    @FXML
    private void handleCartClick() {
        try {
            if (dbFetch.currentToken != null) {
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
    private void applyTableViewStyles() {
        if (coffeeTableView != null) {
            coffeeTableView.setStyle(
                    "-fx-font-family: 'euclid circular a';" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;"
            );

            // Remove fixed row height to allow dynamic sizing based on content
            coffeeTableView.setRowFactory(tv -> {
                javafx.scene.control.TableRow<Coffee> row = new javafx.scene.control.TableRow<Coffee>() {
                    @Override
                    protected void updateItem(Coffee item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setPrefHeight(-1); // Use default height
                        } else {
                            setPrefHeight(-1); // Let it calculate height based on content
                            setMinHeight(30); // Set minimum height
                        }
                    }
                };
                return row;
            });
        }
        }
    @FXML
    public void bar4clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel2, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        scrollpane1.setMouseTransparent(true);
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
    }
}