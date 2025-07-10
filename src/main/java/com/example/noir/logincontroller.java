package com.example.noir;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class logincontroller implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text login1;

    @FXML
    private Text login2;

    @FXML
    private ImageView loginbg; // Changed to match FXML id

    @FXML
    private ImageView noir_logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load custom fonts
        loadCustomFonts();
        
        // Initialize all elements in their starting positions
        initializeElements();
        
        // Start the animation sequence
        startAnimationSequence();
    }

    private void initializeElements() {
        // Check if ImageViews exist before animating them
        if (loginbg != null) {
            loginbg.setTranslateY(200);
            loginbg.setOpacity(0);
        }
        
        if (noir_logo != null) {
            noir_logo.setTranslateY(150);
            noir_logo.setOpacity(0);
        }
        
        // Hide text elements by moving them off-screen to the left
        if (login1 != null) {
            login1.setTranslateX(-400);
            login1.setOpacity(0);
        }
        
        if (login2 != null) {
            login2.setTranslateX(-400);
            login2.setOpacity(0);
        }
    }

    private void startAnimationSequence() {
        // Step 1: Animate background image sliding up from bottom
        if (loginbg != null) {
            Timeline backgroundAnimation = createSlideUpAnimation(loginbg, 800, Duration.millis(400));
            backgroundAnimation.play();
        }
        
        // Step 2: Animate logo sliding up from bottom (starts 200ms after background)
        if (noir_logo != null) {
            Timeline logoAnimation = createSlideUpAnimation(noir_logo, 800, Duration.millis(400));
            logoAnimation.setDelay(Duration.millis(200));
            logoAnimation.play();
        }
        
        // Step 3: Animate login1 text sliding in from left (starts after logo completes)
        if (login1 != null) {
            Timeline login1Animation = createSlideInAnimation(login1, Duration.millis(500));
            login1Animation.setDelay(Duration.millis(600)); // 200ms + 600ms logo animation
            login1Animation.play();
        }
        
        // Step 4: Animate login2 text sliding in from left (starts 300ms after login1)
        if (login2 != null) {
            Timeline login2Animation = createSlideInAnimation(login2, Duration.millis(500));
            login2Animation.setDelay(Duration.millis(900)); // 800ms + 300ms delay
            login2Animation.play();
        }
    }

    private Timeline createSlideUpAnimation(ImageView imageView, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(imageView.translateYProperty(), startOffset),
                new KeyValue(imageView.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(imageView.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(imageView.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    private Timeline createSlideInAnimation(Text textElement, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(textElement.translateXProperty(), -400),
                new KeyValue(textElement.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(textElement.translateXProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(textElement.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    private void loadCustomFonts() {
        try {
            // Load Euclid Circular A Regular font (using correct filename)
            Font euclidRegular = Font.loadFont(
                getClass().getResourceAsStream("/fonts/euclidregular.ttf"), 
                36 // Default size, can be overridden by CSS
            );
            
            // Load Euclid Circular A Bold font (using correct filename)
            Font euclidBold = Font.loadFont(
                getClass().getResourceAsStream("/fonts/euclidbold.ttf"), 
                36 // Default size, can be overridden by CSS
            );
            
            // Apply fonts to text elements
            if (euclidRegular != null && login1 != null) {
                login1.setFont(euclidBold);
                System.out.println("Euclid Circular A Bold loaded successfully");
            } else {
                System.err.println("Failed to load Euclid Circular A Regular font or login1 is null");
            }
            
            if (euclidBold != null && login2 != null) {
                login2.setFont(euclidRegular);
                System.out.println("Euclid Circular A Regular loaded successfully");
            } else {
                System.err.println("Failed to load Euclid Circular A Bold font or login2 is null");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading custom fonts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Add your login logic here
        // For example:
        // if (authenticate(username, password)) {
        //     // Login successful
        // } else {
        //     // Login failed
        // }
    }
}