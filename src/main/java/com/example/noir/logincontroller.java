package com.example.noir;

import com.example.backend.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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
import java.util.regex.Pattern;

import com.example.backend.*;

import static com.example.noir.HelloApplication.client;

public class logincontroller implements Initializable {
    public static final int TRANSLATE_X = -400;


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button continuebutton;


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

    @FXML
    private Button googleButton11;
    
    @FXML
    private Button appleButton11;
    @FXML
    private Text errortxt;
    
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



    @FXML
    private Text continueWithText1;

    @FXML
    private ImageView loginbg;

    @FXML
    private ImageView noir_logo;


    private boolean isLoginActive = true;

    @FXML
    private ImageView profile;

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Registration Failed");
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("alert.css").toExternalForm()
        );
        alert.showAndWait();
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        loadCustomFonts();


        initializeElements();


        setupButtonHandlers();


        setInitialButtonStates();


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


            loginButton.getStyleClass().add("active-button");
            regbutton.getStyleClass().add("inactive-button");

            isLoginActive = true;


            slideAndFadeToLogin();


        }
    }

    @FXML
    private void switchToRegister() {
        if (isLoginActive) {

            loginButton.getStyleClass().removeAll("active-button", "inactive-button");
            regbutton.getStyleClass().removeAll("active-button", "inactive-button");


            regbutton.getStyleClass().add("active-button");
            loginButton.getStyleClass().add("inactive-button");

            isLoginActive = false;


            slideAndFadeToRegister();


        }
    }

    private void slideAndFadeToLogin() {

        ParallelTransition registerSlideOut = new ParallelTransition();
        
        TranslateTransition registerSlide = new TranslateTransition(Duration.millis(500), registerForm);
        registerSlide.setToX(400);
        
        FadeTransition registerFade = new FadeTransition(Duration.millis(500), registerForm);
        registerFade.setToValue(0);
        
        registerSlideOut.getChildren().addAll(registerSlide, registerFade);


        ParallelTransition loginSlideIn = new ParallelTransition();
        

        loginForm.setTranslateX(TRANSLATE_X);
        loginForm.setOpacity(0);

        TranslateTransition loginSlide = new TranslateTransition(Duration.millis(500), loginForm);
        loginSlide.setToX(0);
        
        FadeTransition loginFade = new FadeTransition(Duration.millis(500), loginForm);
        loginFade.setToValue(1);
        
        loginSlideIn.getChildren().addAll(loginSlide, loginFade);


        slideAndFadeRegisterSocialElementsOut();


        slideAndFadeLoginSocialElementsIn();


        registerSlideOut.play();
        loginSlideIn.setDelay(Duration.millis(00));
        loginSlideIn.play();
    }

    private void slideAndFadeToRegister() {

        ParallelTransition loginSlideOut = new ParallelTransition();
        
        TranslateTransition loginSlide = new TranslateTransition(Duration.millis(500), loginForm);
        loginSlide.setToX(TRANSLATE_X);
        
        FadeTransition loginFade = new FadeTransition(Duration.millis(500), loginForm);
        loginFade.setToValue(0);
        
        loginSlideOut.getChildren().addAll(loginSlide, loginFade);


        ParallelTransition registerSlideIn = new ParallelTransition();
        

        registerForm.setTranslateX(400);
        registerForm.setOpacity(0);
        errortxt.setOpacity(0);
        
        TranslateTransition registerSlide = new TranslateTransition(Duration.millis(500), registerForm);
        registerSlide.setToX(0);
        
        FadeTransition registerFade = new FadeTransition(Duration.millis(500), registerForm);
        registerFade.setToValue(1);
        
        registerSlideIn.getChildren().addAll(registerSlide, registerFade);


        slideAndFadeLoginSocialElementsOut();


        slideAndFadeRegisterSocialElementsIn();


        loginSlideOut.play();
        registerSlideIn.setDelay(Duration.millis(100));
        registerSlideIn.play();
    }

    private void slideAndFadeLoginSocialElementsOut() {

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


    }

    @FXML
    private void handleAppleLogin() {


    }

    @FXML
    private void handleGithubLogin() {


    }

    @FXML
    private void handleFacebookLogin() {


    }

    private void initializeElements() {

        if (loginbg != null) {
            loginbg.setTranslateY(800);
            loginbg.setOpacity(0);
        }

        if (noir_logo != null) {
            noir_logo.setTranslateY(800);
            noir_logo.setOpacity(0);
        }


        if (orText2 != null) {
            orText2.setTranslateX(TRANSLATE_X);
            orText2.setOpacity(0);
        }
        if (orText3 != null) {
            orText3.setTranslateX(TRANSLATE_X);
            orText3.setOpacity(0);
        }


        if (orText != null) {
            orText.setTranslateX(TRANSLATE_X);
            orText.setOpacity(0);
        }

        if (continueWithText != null) {
            continueWithText.setTranslateX(TRANSLATE_X);
            continueWithText.setOpacity(0);
        }


        if (orText1 != null) {
            orText1.setTranslateX(400);
            orText1.setOpacity(0);
        }

        if (continueWithText1 != null) {
            continueWithText1.setTranslateX(400);
            continueWithText1.setOpacity(0);
        }


        if (loginButton != null) {
            loginButton.setTranslateY(100);
            loginButton.setOpacity(0);
        }

        if (regbutton != null) {
            regbutton.setTranslateY(100);
            regbutton.setOpacity(0);
        }


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


        if (line21 != null) {
            line21.setTranslateX(400);
            line21.setOpacity(0);
        }

        if (line31 != null) {
            line31.setTranslateX(400);
            line31.setOpacity(0);
        }


        if (loginForm != null) {
            loginForm.setTranslateY(100);
            loginForm.setOpacity(0);
        }

        if (registerForm != null) {
            registerForm.setTranslateX(400);
            registerForm.setOpacity(0);
        }
    }

    private void startAnimationSequence() {

        if (loginbg != null) {
            loginbg.setTranslateY(0);
            loginbg.setOpacity(1);
        }



        if (noir_logo != null) {
            noir_logo.setTranslateY(0);
            noir_logo.setOpacity(1);
        }

        if (orText2 != null) {
            orText2.setTranslateX(0);
            orText2.setOpacity(1);
        }

        if (orText3 != null) {
            orText3.setTranslateX(0);
            orText3.setOpacity(1);
        }


        if (loginButton != null) {
            Timeline loginButtonAnimation = createButtonSlideUpAnimation(loginButton, 100, Duration.millis(400));
            loginButtonAnimation.setDelay(Duration.millis(200));
            loginButtonAnimation.play();
        }

        if (line != null) {
            Timeline lineAnimation = createLineSlideUpAnimation(line, 100, Duration.millis(400));
            lineAnimation.setDelay(Duration.millis(200));
            lineAnimation.play();
        }

        if (regbutton != null) {
            Timeline regButtonAnimation = createButtonSlideUpAnimation(regbutton, 100, Duration.millis(400));
            regButtonAnimation.setDelay(Duration.millis(300));
            regButtonAnimation.play();
        }

        if (loginForm != null) {
            Timeline loginFormAnimation = createFormSlideUpAnimation(loginForm, 100, Duration.millis(400));
            loginFormAnimation.setDelay(Duration.millis(400));
            loginFormAnimation.play();
        }

        if (orText != null) {
            Timeline orTextAnimation = createSlideInAnimation(orText, Duration.millis(400));
            orTextAnimation.setDelay(Duration.millis(700));
            orTextAnimation.play();
        }

        if (line2 != null) {
            Timeline line2Animation = createLineSlideUpAnimation(line2, 100, Duration.millis(400));
            line2Animation.setDelay(Duration.millis(700));
            line2Animation.play();
        }

        if (line3 != null) {
            Timeline line3Animation = createLineSlideUpAnimation(line3, 100, Duration.millis(400));
            line3Animation.setDelay(Duration.millis(700));
            line3Animation.play();
        }

        if (continueWithText != null) {
            Timeline continueWithTextAnimation = createSlideInAnimation(continueWithText, Duration.millis(400));
            continueWithTextAnimation.setDelay(Duration.millis(800));
            continueWithTextAnimation.play();
        }

        if (googleButton1 != null) {
            Timeline googleButtonAnimation = createButtonSlideUpAnimation(googleButton1, 100, Duration.millis(400));
            googleButtonAnimation.setDelay(Duration.millis(900));
            googleButtonAnimation.play();
        }
        if (appleButton1 != null) {
            Timeline appleButtonAnimation = createButtonSlideUpAnimation(appleButton1, 100, Duration.millis(400));
            appleButtonAnimation.setDelay(Duration.millis(950));
            appleButtonAnimation.play();
        }
        if (facebookButton1 != null) {
            Timeline facebookButtonAnimation = createButtonSlideUpAnimation(facebookButton1, 100, Duration.millis(400));
            facebookButtonAnimation.setDelay(Duration.millis(1000));
            facebookButtonAnimation.play();
        }
        if (githubButton1 != null) {
            Timeline githubButtonAnimation = createButtonSlideUpAnimation(githubButton1, 100, Duration.millis(400));
            githubButtonAnimation.setDelay(Duration.millis(1050));
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
                36
            );



        } catch (Exception e) {

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



        }catch(Exception e){
            usernameField.clear();
            passwordField.clear();
            errortxt.setOpacity(1);
        }

        //.println("Login attempt with username: " + username);

    }


    @FXML
    private void handleRegister() {
        String username = regUsernameField.getText();
        String email = regEmailField.getText();
        String address = regAddressField.getText();
        String password = regPasswordField.getText();
        String confirmPassword = regConfirmPasswordField.getText();
        errortxt.setOpacity(0);


        if (password.equals(confirmPassword)) {
            //.println("Registration attempt with username: " + username + ", email: " + email);


            User user = new User(username, password, email, address, true);
            try{
                if(!isValidEmail(email)){
                    throw new RuntimeException("Email is not valid");
                }
                client.register(user);
                client.validateLogin(user.getUsername(), password);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Scene scene = new Scene(loader.load(), 1440, 810);


                scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());
                Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
                ImageCursor customCursor = new ImageCursor(cursorImage, 0, 0);
                scene.setCursor(customCursor);

                Stage stage = (Stage) regContinueButton.getScene().getWindow();
                stage.setScene(scene);


                HelloController controller = loader.getController();
                if (controller != null) {
                    controller.scrollToTop();
                }

                stage.show();
            }catch(Exception e){
                regUsernameField.clear();
                regEmailField.clear();
                regAddressField.clear();
                regPasswordField.clear();
                regConfirmPasswordField.clear();
                showAlert(e.getMessage());
            }
        } else {
            regUsernameField.clear();
            regEmailField.clear();
            regAddressField.clear();
            regPasswordField.clear();
            regConfirmPasswordField.clear();
            showAlert("Passwords do not match");
        }
    }
    @FXML
    private void handleLogoClick() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 810);


            scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());
            Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
            ImageCursor customCursor = new ImageCursor(cursorImage, 0, 0);
            scene.setCursor(customCursor);

            Stage stage = (Stage) noir_logo.getScene().getWindow();
            stage.setScene(scene);


            HelloController controller = loader.getController();
            if (controller != null) {
                controller.scrollToTop();
            }

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }



}
