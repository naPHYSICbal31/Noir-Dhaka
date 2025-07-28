package com.example.noir;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.Text;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static com.example.noir.HelloController.url;

public class BlogController {
    @FXML
    private WebView webView;

    @FXML
    private ScrollPane verticalScrollPane;

    @FXML
    private ProgressBar loadingBar;

    public void initialize() {
        WebEngine engine = webView.getEngine();


        loadingBar.progressProperty().bind(engine.getLoadWorker().progressProperty());

        // Show/hide progress bar during loading
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            switch (newState) {
                case RUNNING -> loadingBar.setVisible(true);
                case SUCCEEDED, FAILED, CANCELLED -> loadingBar.setVisible(false);
            }
        });

        engine.load(url);
    }

    @FXML
    public void redirectToCoffee(MouseEvent event) {
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("coffee.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);

            // Get the controller and reset scroll position
            coffeeController coffeeController = fxmlLoader.getController();

            // Set up the stage
            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
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

    public void smoothScrollTo(double targetValue, double time) {
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
}
