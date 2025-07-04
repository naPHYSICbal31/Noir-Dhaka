package com.example.noir;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class HelloController {
    @FXML private TextField newsletterField;
    @FXML private Button submitButton;
    @FXML private Button scrollButton;
    @FXML private ImageView reviewHead;
    @FXML private ImageView productsHead;
    @FXML private ImageView frontIntro;
    @FXML private ImageView noirBg;
    @FXML private ImageView qualitiesHead;
    @FXML private ScrollPane verticalScrollPane;

    private List<ImageView> animatedImages;
    private boolean[] animationPlayed;

    @FXML
    private void initialize() {
        // Newsletter functionality
        submitButton.setOnAction(event -> saveEmail());
        newsletterField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveEmail();
            }
        });

        // Initialize animated images list
        animatedImages = Arrays.asList(reviewHead, productsHead, frontIntro, noirBg, qualitiesHead);
        animationPlayed = new boolean[animatedImages.size()];

        // Add scroll listener
        verticalScrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            checkAndAnimateImages();
        });

        // Setup scroll button
        scrollButton.setOnAction(event -> smoothScrollToMiddle());
    }

    private void smoothScrollToMiddle() {
        double targetValue = 0.27; // Middle of the scroll pane
        double startValue = verticalScrollPane.getVvalue();
        Duration duration = Duration.seconds(0.5); // 0.5 seconds animation
        int frames = 120; // 120 frames for smooth animations

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

    private void checkAndAnimateImages() {
        double scrollPosition = verticalScrollPane.getVvalue();
        double viewportHeight = verticalScrollPane.getViewportBounds().getHeight();

        for (int i = 0; i < animatedImages.size(); i++) {
            if (!animationPlayed[i]) {
                ImageView image = animatedImages.get(i);
                double imagePosition = image.getLayoutY() / verticalScrollPane.getContent().getBoundsInLocal().getHeight();

                if (imagePosition <= scrollPosition + (viewportHeight / verticalScrollPane.getContent().getBoundsInLocal().getHeight())) {
                    playFadeAnimation(image);
                    animationPlayed[i] = true;
                }
            }
        }
    }

    private void playFadeAnimation(ImageView imageView) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), imageView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(1000), imageView);
        slideUp.setFromY(50);
        slideUp.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, slideUp);
        parallelTransition.play();
    }

    private void saveEmail() {
        String email = newsletterField.getText().trim();
        if (email.isEmpty()) {
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("newsletters.txt", true))) {
            out.println(email);
            newsletterField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}