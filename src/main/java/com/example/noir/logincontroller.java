package com.example.noir;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
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
    private Button regbutton;

    @FXML
    private Button continuebutton;
    @FXML
    private Line line;

    @FXML
    private Text login1;

    @FXML
    private Text login2;

    @FXML
    private ImageView loginbg;

    @FXML
    private ImageView noir_logo;

    // Track which button is currently active
    private boolean isLoginActive = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load custom fonts
        loadCustomFonts();

        // Initialize all elements in their starting positions
        initializeElements();

        // Set up button click handlers
        setupButtonHandlers();

        // Set initial button states
        setInitialButtonStates();

        // Start the animation sequence
        startAnimationSequence();
    }

    private void setupButtonHandlers() {
        // Add click handlers for both buttons
        loginButton.setOnAction(event -> switchToLogin());
        regbutton.setOnAction(event -> switchToRegister());
    }

    private void setInitialButtonStates() {
        // Initially set login button as active (green background)
        loginButton.getStyleClass().add("active-button");
        regbutton.getStyleClass().add("inactive-button");
    }

    @FXML
    private void switchToLogin() {
        if (!isLoginActive) {
            // Remove current styles
            loginButton.getStyleClass().removeAll("active-button", "inactive-button");
            regbutton.getStyleClass().removeAll("active-button", "inactive-button");

            // Set login as active
            loginButton.getStyleClass().add("active-button");
            regbutton.getStyleClass().add("inactive-button");

            isLoginActive = true;

            // Add your login form logic here
            System.out.println("Switched to Login");
        }
    }

    @FXML
    private void switchToRegister() {
        if (isLoginActive) {
            // Remove current styles
            loginButton.getStyleClass().removeAll("active-button", "inactive-button");
            regbutton.getStyleClass().removeAll("active-button", "inactive-button");

            // Set register as active
            regbutton.getStyleClass().add("active-button");
            loginButton.getStyleClass().add("inactive-button");

            isLoginActive = false;

            // Add your register form logic here
            System.out.println("Switched to Register");
        }
    }

    private void initializeElements() {
        // Check if ImageViews exist before animating them
        if (loginbg != null) {
            loginbg.setTranslateY(800);
            loginbg.setOpacity(0);
        }

        if (noir_logo != null) {
            noir_logo.setTranslateY(800);
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

        // Hide buttons by moving them below the screen
        if (loginButton != null) {
            loginButton.setTranslateY(100);
            loginButton.setOpacity(0);
        }

        if (regbutton != null) {
            regbutton.setTranslateY(100);
            regbutton.setOpacity(0);
        }
        if (continuebutton != null) {
            continuebutton.setTranslateY(100);
            continuebutton.setOpacity(0);
        }

        // Hide line separator
        if (line != null) {
            line.setTranslateY(100);
            line.setOpacity(0);
        }

        // Hide text fields by moving them below the screen
        if (usernameField != null) {
            usernameField.setTranslateY(100);
            usernameField.setOpacity(0);
        }

        if (passwordField != null) {
            passwordField.setTranslateY(100);
            passwordField.setOpacity(0);
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
            login1Animation.setDelay(Duration.millis(600));
            login1Animation.play();
        }

        // Step 4: Animate login2 text sliding in from left (starts 300ms after login1)
        if (login2 != null) {
            Timeline login2Animation = createSlideInAnimation(login2, Duration.millis(500));
            login2Animation.setDelay(Duration.millis(900));
            login2Animation.play();
        }

        // Step 5: Animate login button sliding up from bottom (starts after login2)
        if (loginButton != null) {
            Timeline loginButtonAnimation = createButtonSlideUpAnimation(loginButton, 100, Duration.millis(400));
            loginButtonAnimation.setDelay(Duration.millis(1200));
            loginButtonAnimation.play();
        }

        // Step 6: Animate line separator sliding up (starts with login button)
        if (line != null) {
            Timeline lineAnimation = createLineSlideUpAnimation(line, 100, Duration.millis(400));
            lineAnimation.setDelay(Duration.millis(1200));
            lineAnimation.play();
        }

        // Step 7: Animate register button sliding up from bottom (starts 100ms after login button)
        if (regbutton != null) {
            Timeline regButtonAnimation = createButtonSlideUpAnimation(regbutton, 100, Duration.millis(400));
            regButtonAnimation.setDelay(Duration.millis(1300));
            regButtonAnimation.play();
        }
        if (continuebutton != null) {
            Timeline continueButtonAnimation = createButtonSlideUpAnimation(continuebutton, 100, Duration.millis(400));
            continueButtonAnimation.setDelay(Duration.millis(1300));
            continueButtonAnimation.play();
        }

        // Step 8: Animate username field sliding up from bottom (starts after register button)
        if (usernameField != null) {
            Timeline usernameAnimation = createTextFieldSlideUpAnimation(usernameField, 100, Duration.millis(400));
            usernameAnimation.setDelay(Duration.millis(1400));
            usernameAnimation.play();
        }

        // Step 9: Animate password field sliding up from bottom (starts 100ms after username field)
        if (passwordField != null) {
            Timeline passwordAnimation = createTextFieldSlideUpAnimation(passwordField, 100, Duration.millis(400));
            passwordAnimation.setDelay(Duration.millis(1500));
            passwordAnimation.play();
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

    private Timeline createButtonSlideUpAnimation(Button button, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(button.translateYProperty(), startOffset),
                new KeyValue(button.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(button.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(button.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    private Timeline createLineSlideUpAnimation(Line line, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(line.translateYProperty(), startOffset),
                new KeyValue(line.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(line.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(line.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    // New method for text field animations
    private Timeline createTextFieldSlideUpAnimation(TextField textField, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(textField.translateYProperty(), startOffset),
                new KeyValue(textField.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(textField.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(textField.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    // Overloaded method for PasswordField
    private Timeline createTextFieldSlideUpAnimation(PasswordField passwordField, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(passwordField.translateYProperty(), startOffset),
                new KeyValue(passwordField.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(passwordField.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(passwordField.opacityProperty(), 1, Interpolator.EASE_OUT)
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