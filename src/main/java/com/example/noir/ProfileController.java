package com.example.noir;

import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import javafx.scene.control.ScrollPane;

import com.example.backend.dbFetch;
import com.example.backend.User;
import com.example.backend.Coffee;
import com.example.backend.Review;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
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
    
    private dbFetch database;
    private Font euclidBoldFont;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFonts();
        database = new dbFetch();
        loadUserData();
        loadCoffeeData();
        setupTableColumns();
        applyCustomFonts();
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
            User currentUser = database.getUserinfo();
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
            
            // For ListView - display coffee names
            if (coffeeListView != null) {
                ObservableList<String> coffeeNames = FXCollections.observableArrayList();
                for (Coffee coffee : allCoffees) {
                    coffeeNames.add(coffee.getName());
                }
                coffeeListView.setItems(coffeeNames);
            }
            
            // For TableView - display full coffee details
            if (coffeeTableView != null) {
                ObservableList<Coffee> coffeeData = FXCollections.observableArrayList(allCoffees);
                coffeeTableView.setItems(coffeeData);
            }
            
        } catch (Exception e) {
            System.err.println("Error loading coffee data: " + e.getMessage());
        }
    }
    
    private void setupTableColumns() {
        if (nameColumn != null) {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (priceColumn != null) {
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        if (descriptionColumn != null) {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
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

    @FXML
    public void fadeawaylogin(MouseEvent event) {
        System.out.println("fadeawaylogin method called!");
        
        // Consume event to prevent bubbling
        event.consume();
        
        // Toggle fade for each element
        toggleFade(usernameLabel1, Duration.seconds(0.2));
        toggleFade(usernameLabel2, Duration.seconds(0.2));
        toggleFade(usernameLabel3, Duration.seconds(0.2));
        toggleFade(usernameLabel4, Duration.seconds(0.2));
        toggleFade(emailLabel, Duration.seconds(0.2));
        toggleFade(addressLabel, Duration.seconds(0.2));
        toggleFade(usernameLabel, Duration.seconds(0.2));
    }

    // Toggle method that fades out if visible, fades in if faded
    public void toggleFade(Node node, Duration duration) {
        if (node == null) {
            System.err.println("Cannot fade null node");
            return;
        }
        
        FadeTransition fade = new FadeTransition(duration, node);
        
        if (node.getOpacity() > 0.2) {
            // Node is visible, fade it out
            System.out.println("Fading out: " + node.getClass().getSimpleName());
            fade.setFromValue(node.getOpacity());
            fade.setToValue(0.0);
        } else {
            // Node is faded, fade it in
            System.out.println("Fading in: " + node.getClass().getSimpleName());
            fade.setFromValue(node.getOpacity());
            fade.setToValue(1.0);
        }
        
        fade.play();
    }
}