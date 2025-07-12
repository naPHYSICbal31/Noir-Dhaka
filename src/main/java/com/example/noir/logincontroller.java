package com.example.noir;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private Button googleButton1;
    
    @FXML
    private Button appleButton1;
    
    @FXML
    private Button githubButton1;
    
    @FXML
    private Button facebookButton1;
    
    @FXML
    private HBox socialButtonsContainer;
    
    @FXML
    private Line line;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    @FXML
    private Text login1;

    @FXML
    private Text login2;

    @FXML
    private Text orText;

    @FXML
    private Text continueWithText;

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

    @FXML
    private void handleGoogleLogin() {
        System.out.println("Google login clicked");
        // Add your Google OAuth logic here
        // For example:
        // GoogleAuthService.authenticate();
    }

    @FXML
    private void handleAppleLogin() {
        System.out.println("Apple login clicked");
        // Add your Apple OAuth logic here
        // For example:
        // AppleAuthService.authenticate();
    }

    @FXML
    private void handleGithubLogin() {
        System.out.println("GitHub login clicked");
        // Add your GitHub OAuth logic here
        // For example:
        // GitHubAuthService.authenticate();
    }

    @FXML
    private void handleFacebookLogin() {
        System.out.println("Facebook login clicked");
        // Add your Facebook OAuth logic here
        // For example:
        // FacebookAuthService.authenticate();
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

        // Hide new text elements by moving them off-screen to the left
        if (orText != null) {
            orText.setTranslateX(-400);
            orText.setOpacity(0);
        }

        if (continueWithText != null) {
            continueWithText.setTranslateX(-400);
            continueWithText.setOpacity(0);
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

        // Hide social buttons
        if (googleButton1 != null) {
            googleButton1.setTranslateY(100);
            googleButton1.setOpacity(0);
        }
        if (appleButton1 != null) {
            appleButton1.setTranslateY(100);
            appleButton1.setOpacity(0);
        }
        if (githubButton1 != null) {
            githubButton1.setTranslateY(100);
            githubButton1.setOpacity(0);
        }
        if (facebookButton1 != null) {
            facebookButton1.setTranslateY(100);
            facebookButton1.setOpacity(0);
        }

        // Hide line separators
        if (line != null) {
            line.setTranslateY(100);
            line.setOpacity(0);
        }

        if (line2 != null) {
            line2.setTranslateY(100);
            line2.setOpacity(0);
        }

        if (line3 != null) {
            line3.setTranslateY(100);
            line3.setOpacity(0);
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

        // Step 10: Animate continue button sliding up from bottom (starts after password field)
        if (continuebutton != null) {
            Timeline continueButtonAnimation = createButtonSlideUpAnimation(continuebutton, 100, Duration.millis(400));
            continueButtonAnimation.setDelay(Duration.millis(1600));
            continueButtonAnimation.play();
        }

        // Step 11: Animate OR text sliding in from left (starts after continue button)
        if (orText != null) {
            Timeline orTextAnimation = createSlideInAnimation(orText, Duration.millis(400));
            orTextAnimation.setDelay(Duration.millis(1700));
            orTextAnimation.play();
        }

        // Step 12: Animate line2 sliding up from bottom (starts with OR text)
        if (line2 != null) {
            Timeline line2Animation = createLineSlideUpAnimation(line2, 100, Duration.millis(400));
            line2Animation.setDelay(Duration.millis(1700));
            line2Animation.play();
        }

        // Step 13: Animate line3 sliding up from bottom (starts with OR text)
        if (line3 != null) {
            Timeline line3Animation = createLineSlideUpAnimation(line3, 100, Duration.millis(400));
            line3Animation.setDelay(Duration.millis(1700));
            line3Animation.play();
        }

        // Step 14: Animate "continue with" text sliding in from left (starts after OR text)
        if (continueWithText != null) {
            Timeline continueWithTextAnimation = createSlideInAnimation(continueWithText, Duration.millis(400));
            continueWithTextAnimation.setDelay(Duration.millis(1800));
            continueWithTextAnimation.play();
        }

        // Step 15: Animate social buttons sliding up from bottom (starts after continue with text)
        if (googleButton1 != null) {
            Timeline googleButtonAnimation = createButtonSlideUpAnimation(googleButton1, 100, Duration.millis(400));
            googleButtonAnimation.setDelay(Duration.millis(1900));
            googleButtonAnimation.play();
        }
        if (appleButton1 != null) {
            Timeline appleButtonAnimation = createButtonSlideUpAnimation(appleButton1, 100, Duration.millis(400));
            appleButtonAnimation.setDelay(Duration.millis(1950));
            appleButtonAnimation.play();
        }
        if (facebookButton1 != null) {
            Timeline facebookButtonAnimation = createButtonSlideUpAnimation(facebookButton1, 100, Duration.millis(400));
            facebookButtonAnimation.setDelay(Duration.millis(2000));
            facebookButtonAnimation.play();
        }
        if (githubButton1 != null) {
            Timeline githubButtonAnimation = createButtonSlideUpAnimation(githubButton1, 100, Duration.millis(400));
            githubButtonAnimation.setDelay(Duration.millis(2050));
            githubButtonAnimation.play();
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