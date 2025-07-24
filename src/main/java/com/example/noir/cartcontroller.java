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
import javafx.scene.image.Image;
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
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;

import com.example.backend.dbFetch;
import com.example.backend.User;
import com.example.backend.Coffee;
import com.example.backend.Review;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import java.util.ArrayList;

public class cartcontroller implements Initializable{
    @FXML
    private ScrollPane verticalScrollPane;
    @FXML
    private ImageView image;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load the image when the controller initializes
        loadImageFromResources();
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

}
