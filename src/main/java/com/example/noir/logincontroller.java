package com.example.noir;

import com.example.backend.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.backend.*;

public class logincontroller implements Initializable {
    public static final int TRANSLATE_X = -400;
    // Login form fields
    private Client client;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button continuebutton;

    // Register form fields
    @FXML
    private TextField regUsernameField;

    @FXML
    private TextField regEmailField;

    @FXML
    private TextField regAddressField;

    @FXML
    private PasswordField regPasswordField;

    @FXML
    private PasswordField regConfirmPasswordField;

    @FXML
    private Button regContinueButton;

    // Form containers
    @FXML
    private AnchorPane loginForm;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private Button loginButton;

    @FXML
    private Button regbutton;
    
    @FXML
    private Button googleButton1;
    
    @FXML
    private Button appleButton1;
    
    @FXML
    private Button githubButton1;
    
    @FXML
    private Button facebookButton1;
    @FXML
    private Text orText1;
    // Register social buttons
    @FXML
    private Button googleButton11;
    
    @FXML
    private Button appleButton11;
    
    @FXML
    private Button githubButton11;
    
    @FXML
    private Button facebookButton11;
    
    @FXML
    private HBox socialButtonsContainer;
    
    @FXML
    private Line line;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    // Register social elements
    @FXML
    private Line line21;

    @FXML
    private Line line31;

    @FXML
    private Text orText2;

    @FXML
    private Text orText3;

    @FXML
    private Text orText;
    @FXML
    private Text continueWithText;

    // Register social elements

    @FXML
    private Text continueWithText1;

    @FXML
    private ImageView loginbg;

    @FXML
    private ImageView noir_logo;

    // Track which button is currently active
    private boolean isLoginActive = true;

    @FXML
    private ImageView profile;

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
        loginButton.setOnAction(event -> switchToLogin());
        regbutton.setOnAction(event -> switchToRegister());
    }

    private void setInitialButtonStates() {

        loginButton.getStyleClass().add("active-button");
        regbutton.getStyleClass().add("inactive-button");
    }

    @FXML
    private void switchToLogin() {
        if (!isLoginActive) {

            loginButton.getStyleClass().removeAll("active-button", "inactive-button");
            regbutton.getStyleClass().removeAll("active-button", "inactive-button");

            // Set login as active
            loginButton.getStyleClass().add("active-button");
            regbutton.getStyleClass().add("inactive-button");

            isLoginActive = true;

            // Animate slide and fade to login form
            slideAndFadeToLogin();

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

            // Animate slide and fade to register form
            slideAndFadeToRegister();

            System.out.println("Switched to Register");
        }
    }

    private void slideAndFadeToLogin() {
        // Slide register form to the right while fading out
        ParallelTransition registerSlideOut = new ParallelTransition();
        
        TranslateTransition registerSlide = new TranslateTransition(Duration.millis(500), registerForm);
        registerSlide.setToX(400); // Slide to the right
        
        FadeTransition registerFade = new FadeTransition(Duration.millis(500), registerForm);
        registerFade.setToValue(0);
        
        registerSlideOut.getChildren().addAll(registerSlide, registerFade);

        // Slide login form in from the right while fading in
        ParallelTransition loginSlideIn = new ParallelTransition();
        
        // Start login form from the right
        loginForm.setTranslateX(TRANSLATE_X);
        loginForm.setOpacity(0);

        TranslateTransition loginSlide = new TranslateTransition(Duration.millis(500), loginForm);
        loginSlide.setToX(0); // Slide to original position
        
        FadeTransition loginFade = new FadeTransition(Duration.millis(500), loginForm);
        loginFade.setToValue(1);
        
        loginSlideIn.getChildren().addAll(loginSlide, loginFade);

        // Slide and fade register social elements out to the right
        slideAndFadeRegisterSocialElementsOut();

        // Slide and fade login social elements in from the right
        slideAndFadeLoginSocialElementsIn();

        // Start animations with slight delay
        registerSlideOut.play();
        loginSlideIn.setDelay(Duration.millis(00));
        loginSlideIn.play();
    }

    private void slideAndFadeToRegister() {
        // Slide login form to the left while fading out
        ParallelTransition loginSlideOut = new ParallelTransition();
        
        TranslateTransition loginSlide = new TranslateTransition(Duration.millis(500), loginForm);
        loginSlide.setToX(TRANSLATE_X); // Slide to the left
        
        FadeTransition loginFade = new FadeTransition(Duration.millis(500), loginForm);
        loginFade.setToValue(0);
        
        loginSlideOut.getChildren().addAll(loginSlide, loginFade);

        // Slide register form in from the right while fading in
        ParallelTransition registerSlideIn = new ParallelTransition();
        
        // Start register form from the right
        registerForm.setTranslateX(400);
        registerForm.setOpacity(0);
        
        TranslateTransition registerSlide = new TranslateTransition(Duration.millis(500), registerForm);
        registerSlide.setToX(0); // Slide to original position
        
        FadeTransition registerFade = new FadeTransition(Duration.millis(500), registerForm);
        registerFade.setToValue(1);
        
        registerSlideIn.getChildren().addAll(registerSlide, registerFade);

        // Slide and fade login social elements out to the left
        slideAndFadeLoginSocialElementsOut();

        // Slide and fade register social elements in from the right
        slideAndFadeRegisterSocialElementsIn();

        // Start animations with slight delay
        loginSlideOut.play();
        registerSlideIn.setDelay(Duration.millis(100));
        registerSlideIn.play();
    }

    private void slideAndFadeLoginSocialElementsOut() {
        // Slide and fade out all login social elements to the left
        ParallelTransition socialSlideOut = new ParallelTransition();
        
        if (orText != null) {
            TranslateTransition orSlide = new TranslateTransition(Duration.millis(500), orText);
            orSlide.setToX(TRANSLATE_X);
            FadeTransition orFade = new FadeTransition(Duration.millis(500), orText);
            orFade.setToValue(0);
            socialSlideOut.getChildren().addAll(orSlide, orFade);
        }

        if (continueWithText != null) {
            TranslateTransition continueSlide = new TranslateTransition(Duration.millis(500), continueWithText);
            continueSlide.setToX(TRANSLATE_X);
            FadeTransition continueFade = new FadeTransition(Duration.millis(500), continueWithText);
            continueFade.setToValue(0);
            socialSlideOut.getChildren().addAll(continueSlide, continueFade);
        }

        if (line2 != null) {
            TranslateTransition line2Slide = new TranslateTransition(Duration.millis(500), line2);
            line2Slide.setToX(TRANSLATE_X);
            FadeTransition line2Fade = new FadeTransition(Duration.millis(500), line2);
            line2Fade.setToValue(0);
            socialSlideOut.getChildren().addAll(line2Slide, line2Fade);
        }

        if (line3 != null) {
            TranslateTransition line3Slide = new TranslateTransition(Duration.millis(500), line3);
            line3Slide.setToX(TRANSLATE_X);
            FadeTransition line3Fade = new FadeTransition(Duration.millis(500), line3);
            line3Fade.setToValue(0);
            socialSlideOut.getChildren().addAll(line3Slide, line3Fade);
        }

        // Slide and fade out social buttons to the left
        Button[] socialButtons = {googleButton1, appleButton1, facebookButton1, githubButton1};
        for (Button button : socialButtons) {
            if (button != null) {
                TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(500), button);
                buttonSlide.setToX(TRANSLATE_X);
                FadeTransition buttonFade = new FadeTransition(Duration.millis(500), button);
                buttonFade.setToValue(0);
                socialSlideOut.getChildren().addAll(buttonSlide, buttonFade);
            }
        }

        socialSlideOut.play();
    }

    private void slideAndFadeLoginSocialElementsIn() {
        // Slide and fade in all login social elements from the left
        ParallelTransition socialSlideIn = new ParallelTransition();
        if (orText != null) {
            orText.setTranslateX(TRANSLATE_X);
            orText.setOpacity(0);
            TranslateTransition orSlide = new TranslateTransition(Duration.millis(500), orText);
            orSlide.setToX(0);
            FadeTransition orFade = new FadeTransition(Duration.millis(500), orText);
            orFade.setToValue(1);
            socialSlideIn.getChildren().addAll(orSlide, orFade);
        }

        if (continueWithText != null) {
            continueWithText.setTranslateX(TRANSLATE_X);
            continueWithText.setOpacity(0);
            TranslateTransition continueSlide = new TranslateTransition(Duration.millis(500), continueWithText);
            continueSlide.setToX(0);
            FadeTransition continueFade = new FadeTransition(Duration.millis(500), continueWithText);
            continueFade.setToValue(1);
            socialSlideIn.getChildren().addAll(continueSlide, continueFade);
        }

        if (line2 != null) {
            line2.setTranslateX(TRANSLATE_X);
            line2.setOpacity(0);
            TranslateTransition line2Slide = new TranslateTransition(Duration.millis(500), line2);
            line2Slide.setToX(0);
            FadeTransition line2Fade = new FadeTransition(Duration.millis(500), line2);
            line2Fade.setToValue(1);
            socialSlideIn.getChildren().addAll(line2Slide, line2Fade);
        }

        if (line3 != null) {
            line3.setTranslateX(TRANSLATE_X);
            line3.setOpacity(0);
            TranslateTransition line3Slide = new TranslateTransition(Duration.millis(500), line3);
            line3Slide.setToX(0);
            FadeTransition line3Fade = new FadeTransition(Duration.millis(500), line3);
            line3Fade.setToValue(1);
            socialSlideIn.getChildren().addAll(line3Slide, line3Fade);
        }

        // Slide and fade in social buttons from the right
        Button[] socialButtons = {googleButton1, appleButton1, facebookButton1, githubButton1};
        for (Button button : socialButtons) {
            if (button != null) {
                button.setTranslateX(TRANSLATE_X);
                button.setOpacity(0);
                TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(500), button);
                buttonSlide.setToX(0);
                FadeTransition buttonFade = new FadeTransition(Duration.millis(500), button);
                buttonFade.setToValue(1);
                socialSlideIn.getChildren().addAll(buttonSlide, buttonFade);
            }
        }

        socialSlideIn.setDelay(Duration.millis(200));
        socialSlideIn.play();
    }
    private void slideAndFadeRegisterSocialElementsOut() {
        // Slide and fade out all register social elements to the right
        ParallelTransition socialSlideOut = new ParallelTransition();
        
        if (orText1 != null) {
            TranslateTransition orSlide = new TranslateTransition(Duration.millis(500), orText1);
            orSlide.setToX(400);
            FadeTransition orFade = new FadeTransition(Duration.millis(500), orText1);
            orFade.setToValue(0);
            socialSlideOut.getChildren().addAll(orSlide, orFade);
        }

        if (continueWithText1 != null) {
            TranslateTransition continueSlide = new TranslateTransition(Duration.millis(500), continueWithText1);
            continueSlide.setToX(400);
            FadeTransition continueFade = new FadeTransition(Duration.millis(500), continueWithText1);
            continueFade.setToValue(0);
            socialSlideOut.getChildren().addAll(continueSlide, continueFade);
        }

        if (line21 != null) {
            TranslateTransition line21Slide = new TranslateTransition(Duration.millis(500), line21);
            line21Slide.setToX(400);
            FadeTransition line21Fade = new FadeTransition(Duration.millis(500), line21);
            line21Fade.setToValue(0);
            socialSlideOut.getChildren().addAll(line21Slide, line21Fade);
        }

        if (line31 != null) {
            TranslateTransition line31Slide = new TranslateTransition(Duration.millis(500), line31);
            line31Slide.setToX(400);
            FadeTransition line31Fade = new FadeTransition(Duration.millis(500), line31);
            line31Fade.setToValue(0);
            socialSlideOut.getChildren().addAll(line31Slide, line31Fade);
        }

        // Slide and fade out register social buttons to the right
        Button[] socialButtons = {googleButton11, appleButton11, facebookButton11, githubButton11};
        for (Button button : socialButtons) {
            if (button != null) {
                TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(500), button);
                buttonSlide.setToX(400);
                FadeTransition buttonFade = new FadeTransition(Duration.millis(500), button);
                buttonFade.setToValue(0);
                socialSlideOut.getChildren().addAll(buttonSlide, buttonFade);
            }
        }

        socialSlideOut.play();
    }

    private void slideAndFadeRegisterSocialElementsIn() {
        // Slide and fade in all register social elements from the right
        ParallelTransition socialSlideIn = new ParallelTransition();
        
        if (orText1 != null) {
            orText1.setTranslateX(400);
            orText1.setOpacity(0);
            TranslateTransition orSlide = new TranslateTransition(Duration.millis(500), orText1);
            orSlide.setToX(0);
            FadeTransition orFade = new FadeTransition(Duration.millis(500), orText1);
            orFade.setToValue(1);
            socialSlideIn.getChildren().addAll(orSlide, orFade);
        }

        if (continueWithText1 != null) {
            continueWithText1.setTranslateX(400);
            continueWithText1.setOpacity(0);
            TranslateTransition continueSlide = new TranslateTransition(Duration.millis(500), continueWithText1);
            continueSlide.setToX(0);
            FadeTransition continueFade = new FadeTransition(Duration.millis(500), continueWithText1);
            continueFade.setToValue(1);
            socialSlideIn.getChildren().addAll(continueSlide, continueFade);
        }

        if (line21 != null) {
            line21.setTranslateX(400);
            line21.setOpacity(0);
            TranslateTransition line21Slide = new TranslateTransition(Duration.millis(500), line21);
            line21Slide.setToX(0);
            FadeTransition line21Fade = new FadeTransition(Duration.millis(500), line21);
            line21Fade.setToValue(1);
            socialSlideIn.getChildren().addAll(line21Slide, line21Fade);
        }

        if (line31 != null) {
            line31.setTranslateX(400);
            line31.setOpacity(0);
            TranslateTransition line31Slide = new TranslateTransition(Duration.millis(500), line31);
            line31Slide.setToX(0);
            FadeTransition line31Fade = new FadeTransition(Duration.millis(500), line31);
            line31Fade.setToValue(1);
            socialSlideIn.getChildren().addAll(line31Slide, line31Fade);
        }

        // Slide and fade in register social buttons from the right
        Button[] socialButtons = {googleButton11, appleButton11, facebookButton11, githubButton11};
        for (Button button : socialButtons) {
            if (button != null) {
                button.setTranslateX(400);
                button.setOpacity(0);
                TranslateTransition buttonSlide = new TranslateTransition(Duration.millis(500), button);
                buttonSlide.setToX(0);
                FadeTransition buttonFade = new FadeTransition(Duration.millis(500), button);
                buttonFade.setToValue(1);
                socialSlideIn.getChildren().addAll(buttonSlide, buttonFade);
            }
        }

        socialSlideIn.setDelay(Duration.millis(200));
        socialSlideIn.play();
    }

    @FXML
    private void handleGoogleLogin() {
        System.out.println("Google login clicked");
        // Add your Google Oclient logic here
    }

    @FXML
    private void handleAppleLogin() {
        System.out.println("Apple login clicked");
        // Add your Apple Oclient logic here
    }

    @FXML
    private void handleGithubLogin() {
        System.out.println("GitHub login clicked");
        // Add your GitHub Oclient logic here
    }

    @FXML
    private void handleFacebookLogin() {
        System.out.println("Facebook login clicked");
        // Add your Facebook Oclient logic here
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
        if (orText2 != null) {
            orText2.setTranslateX(TRANSLATE_X);
            orText2.setOpacity(0);
        }
        if (orText3 != null) {
            orText3.setTranslateX(TRANSLATE_X);
            orText3.setOpacity(0);
        }

        // Hide login social elements by moving them off-screen to the left
        if (orText != null) {
            orText.setTranslateX(TRANSLATE_X);
            orText.setOpacity(0);
        }

        if (continueWithText != null) {
            continueWithText.setTranslateX(TRANSLATE_X);
            continueWithText.setOpacity(0);
        }

        // Hide register social elements by moving them off-screen to the right
        if (orText1 != null) {
            orText1.setTranslateX(400);
            orText1.setOpacity(0);
        }

        if (continueWithText1 != null) {
            continueWithText1.setTranslateX(400);
            continueWithText1.setOpacity(0);
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

        // Hide login social buttons
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

        // Hide register social buttons
        if (googleButton11 != null) {
            googleButton11.setTranslateX(400);
            googleButton11.setOpacity(0);
        }
        if (appleButton11 != null) {
            appleButton11.setTranslateX(400);
            appleButton11.setOpacity(0);
        }
        if (githubButton11 != null) {
            githubButton11.setTranslateX(400);
            githubButton11.setOpacity(0);
        }
        if (facebookButton11 != null) {
            facebookButton11.setTranslateX(400);
            facebookButton11.setOpacity(0);
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

        // Hide register line separators
        if (line21 != null) {
            line21.setTranslateX(400);
            line21.setOpacity(0);
        }

        if (line31 != null) {
            line31.setTranslateX(400);
            line31.setOpacity(0);
        }

        // Initialize form containers
        if (loginForm != null) {
            loginForm.setTranslateY(100);
            loginForm.setOpacity(0);
        }

        if (registerForm != null) {
            registerForm.setTranslateX(400); // Position register form off-screen to the right
            registerForm.setOpacity(0); // Initially hidden
        }
    }

    private void startAnimationSequence() {

        if (loginbg != null) {
            Timeline backgroundAnimation = createSlideUpAnimation(loginbg, 800, Duration.millis(400));
            backgroundAnimation.play();
        }


        if (noir_logo != null) {
            Timeline logoAnimation = createSlideUpAnimation(noir_logo, 800, Duration.millis(400));
            logoAnimation.setDelay(Duration.millis(200));
            logoAnimation.play();
        }

        if (orText2 != null) {
            Timeline login1Animation = createSlideInAnimation(orText2, Duration.millis(500));
            login1Animation.setDelay(Duration.millis(600));
            login1Animation.play();
        }

        if (orText3 != null) {
            Timeline login2Animation = createSlideInAnimation(orText3, Duration.millis(500));
            login2Animation.setDelay(Duration.millis(900));
            login2Animation.play();
        }

        if (loginButton != null) {
            Timeline loginButtonAnimation = createButtonSlideUpAnimation(loginButton, 100, Duration.millis(400));
            loginButtonAnimation.setDelay(Duration.millis(1200));
            loginButtonAnimation.play();
        }

        if (line != null) {
            Timeline lineAnimation = createLineSlideUpAnimation(line, 100, Duration.millis(400));
            lineAnimation.setDelay(Duration.millis(1200));
            lineAnimation.play();
        }
        if (regbutton != null) {
            Timeline regButtonAnimation = createButtonSlideUpAnimation(regbutton, 100, Duration.millis(400));
            regButtonAnimation.setDelay(Duration.millis(1300));
            regButtonAnimation.play();
        }

        if (loginForm != null) {
            Timeline loginFormAnimation = createFormSlideUpAnimation(loginForm, 100, Duration.millis(400));
            loginFormAnimation.setDelay(Duration.millis(1400));
            loginFormAnimation.play();
        }

        if (orText != null) {
            Timeline orTextAnimation = createSlideInAnimation(orText, Duration.millis(400));
            orTextAnimation.setDelay(Duration.millis(1700));
            orTextAnimation.play();
        }

        if (line2 != null) {
            Timeline line2Animation = createLineSlideUpAnimation(line2, 100, Duration.millis(400));
            line2Animation.setDelay(Duration.millis(1700));
            line2Animation.play();
        }

        // Step 11: Animate line3 sliding up from bottom (starts with OR text)
        if (line3 != null) {
            Timeline line3Animation = createLineSlideUpAnimation(line3, 100, Duration.millis(400));
            line3Animation.setDelay(Duration.millis(1700));
            line3Animation.play();
        }

        if (continueWithText != null) {
            Timeline continueWithTextAnimation = createSlideInAnimation(continueWithText, Duration.millis(400));
            continueWithTextAnimation.setDelay(Duration.millis(1800));
            continueWithTextAnimation.play();
        }

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
                new KeyValue(textElement.translateXProperty(), TRANSLATE_X),
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

    private Timeline createFormSlideUpAnimation(AnchorPane form, double startOffset, Duration duration) {
        return new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(form.translateYProperty(), startOffset),
                new KeyValue(form.opacityProperty(), 0)
            ),
            new KeyFrame(duration,
                new KeyValue(form.translateYProperty(), 0, Interpolator.EASE_OUT),
                new KeyValue(form.opacityProperty(), 1, Interpolator.EASE_OUT)
            )
        );
    }

    private void loadCustomFonts() {
        try{

            Font retrokia = Font.loadFont(
                getClass().getResourceAsStream("/fonts/RetrokiaCaps-Rough.otf"),
                36 // Default size, can be overridden by CSS
            );

            // Apply fonts to text elements

        } catch (Exception e) {
            System.err.println("Error loading custom fonts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try{
            client.validateLogin(username, password);
            handleLogoClick();

            /* TODO
             * Navigate back to the HelloApplication
             */

        }catch(Exception e){
            usernameField.clear();
            passwordField.clear();

            System.out.println(e.getMessage());

            /* TODO
            *  Show error message in a notification or smth
            * */
        }

        System.out.println("Login attempt with username: " + username);

    }


    @FXML
    private void handleRegister() {
        String username = regUsernameField.getText();
        String email = regEmailField.getText();
        String address = regAddressField.getText();
        String password = regPasswordField.getText();
        String confirmPassword = regConfirmPasswordField.getText();

        // Add your registration logic here
        if (password.equals(confirmPassword)) {
            System.out.println("Registration attempt with username: " + username + ", email: " + email);

            /* TODO
            * implement isAds
            * */
            User user = new User(username, password, email, address, true);
            try{
                client.register(user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Scene scene = new Scene(loader.load(), 1440, 810);

                // Add the stylesheet if needed
                scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) regContinueButton.getScene().getWindow();
                stage.setScene(scene);

                // Reset scroll position to top
                HelloController controller = loader.getController();
                if (controller != null) {
                    controller.scrollToTop();
                }

                stage.show();
            }catch(Exception e){
                /* TODO
                    Error message ... maybe same username alr registered
                 */
            }
        } else {
            System.out.println("Passwords do not match!");
        }
    }
    @FXML
    private void handleLogoClick() {
        try {
            // Load the hello-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);

            // Add the stylesheet if needed
            scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) noir_logo.getScene().getWindow();
            stage.setScene(scene);

            // Reset scroll position to top
            HelloController controller = loader.getController();
            if (controller != null) {
                controller.scrollToTop();
            }

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading hello-view.fxml: " + e.getMessage());
        }
    }



}
