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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.example.backend.dbFetch;
import com.example.backend.User;
import com.example.backend.Coffee;
import com.example.backend.Review;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import java.util.ArrayList;
public class ProfileController implements Initializable {
    @FXML
    private ScrollPane verticalScrollPane;
    @FXML
    private Label usernameLabel41;
    @FXML
    private Label usernameLabel411;
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
    private ListView<String> coffeeListView;

    @FXML
    private TableView<Coffee> coffeeTableView;

    @FXML
    private TableColumn<Coffee, String> nameColumn;

    @FXML
    private TableColumn<Coffee, Double> priceColumn;

    @FXML
    private TableColumn<Coffee, String> descriptionColumn;
    @FXML
    private TableColumn<Coffee, Integer> coffeeCountColumn;

    @FXML
    private Button logoutbutton;

    private Font euclidBoldFont;
    private User currentUser;
    private dbFetch database;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFonts();

        loadUserData();
        loadCoffeeData();
        setupTableColumns();
        applyCustomFonts();
        applyTableViewStyles();
        database = new dbFetch();
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
            usernameLabel411.setFont(euclidBoldFont);
        }
    }

    private void loadUserData() {
        try {
            currentUser = database.getUserinfo(); // Store the user data
            if (currentUser != null) {
                if (usernameLabel != null) {
                    usernameLabel.setText(currentUser.getUsername());
                }
                if (emailLabel != null) {
                    emailLabel.setText(currentUser.getEmail());
                }
                if (addressLabel != null) {
                    addressLabel.setText(currentUser.getAddress());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }

private void loadCoffeeData() {
    try {
        List<Coffee> allCoffees = database.getAllCoffees();

        // For ListView - display only purchased coffee names
        if (coffeeListView != null) {
            ObservableList<String> purchasedCoffeeNames = FXCollections.observableArrayList();
            
            if (currentUser != null && currentUser.getBuyHistory() != null) {
                for (Coffee coffee : allCoffees) {
                    Integer purchaseCount = currentUser.getBuyHistory().get(coffee.getId());
                    if (purchaseCount != null && purchaseCount > 0) {
                        purchasedCoffeeNames.add(coffee.getName());
                    }
                }
            }
            
            coffeeListView.setItems(purchasedCoffeeNames);
        }

        // For TableView - display only coffees with non-zero purchase count
        if (coffeeTableView != null) {
            List<Coffee> purchasedCoffees = new ArrayList<>();
            
            if (currentUser != null && currentUser.getBuyHistory() != null) {
                for (Coffee coffee : allCoffees) {
                    Integer purchaseCount = currentUser.getBuyHistory().get(coffee.getId());



                    if (purchaseCount != null && purchaseCount > 0) {
                        purchasedCoffees.add(coffee);
                    }
                }
            }
            
            ObservableList<Coffee> filteredCoffeeData = FXCollections.observableArrayList(purchasedCoffees);
            coffeeTableView.setItems(filteredCoffeeData);
        }

    } catch (Exception e) {
        System.err.println("Error loading coffee data: " + e.getMessage());
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

        // Add the coffee count column setup
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
    }

    // Method to refresh data
    @FXML
    private void refreshData() {
        loadUserData();
        loadCoffeeData();
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
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(coffeeTableView, Duration.seconds(0.25));
        }));
        delay.play();

    }
    @FXML
    public void bar1clicked(MouseEvent event)
    {
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
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
}