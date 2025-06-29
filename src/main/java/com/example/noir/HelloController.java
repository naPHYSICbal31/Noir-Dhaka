package com.example.noir;

import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HelloController {
    @FXML private ScrollPane verticalScrollPane;
    @FXML private ScrollPane horizontalScrollPane;

    @FXML private ImageView productsHead;
    @FXML private ImageView reviewHead;
    @FXML private ImageView frontIntro;
    @FXML private ImageView noirBg;
    @FXML private ImageView qualitiesHead;
    @FXML private javafx.scene.text.Text title1;
    private boolean[] animated;
    private ImageView[] animatedImages;


    @FXML
    public void initialize() {
        animatedImages = new ImageView[]{
            productsHead, reviewHead, frontIntro, noirBg, qualitiesHead
        };
        animated = new boolean[animatedImages.length];

        for (ImageView img : animatedImages) {
            img.setTranslateY(100);
            img.setOpacity(0);
        }

        verticalScrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            checkAndAnimateImages();
        });

        // Add touchpad horizontal scrolling
        horizontalScrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaX() != 0) { // Touchpad horizontal gesture
                double deltaX = event.getDeltaX();
                double currentH = horizontalScrollPane.getHvalue();
                double newH = currentH - (deltaX / horizontalScrollPane.getContent().getBoundsInLocal().getWidth() * 2.0);
                horizontalScrollPane.setHvalue(clamp(newH));
                event.consume();
            }
        });
    }
    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    private void checkAndAnimateImages() {
        double scrollPercentage = verticalScrollPane.getVvalue();
        double viewportHeight = verticalScrollPane.getHeight();

        for (int i = 0; i < animatedImages.length; i++) {
            if (!animated[i]) {
                ImageView img = animatedImages[i];
                double imagePosition = img.getLayoutY();

                if (isImageInViewport(imagePosition, scrollPercentage, viewportHeight)) {
                    animated[i] = true;
                    animateImage(img);
                }
            }
        }
    }

    private boolean isImageInViewport(double imagePosition, double scrollPercentage, double viewportHeight) {
        double scrollPosition = scrollPercentage * verticalScrollPane.getContent().getBoundsInLocal().getHeight();
        return imagePosition < (scrollPosition + viewportHeight) &&
               imagePosition > (scrollPosition - viewportHeight/2);
    }
    @FXML


    private void animateImage(ImageView img) {
        TranslateTransition slideUp = new TranslateTransition(Duration.millis(1000), img);
        slideUp.setFromY(100);
        slideUp.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), img);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        slideUp.play();
        fadeIn.play();
    }
}