package com.example.noir;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initially hide the text elements by moving them off-screen to the left
        login1.setTranslateX(-400);
        login1.setOpacity(0);
        login2.setTranslateX(-400);
        login2.setOpacity(0);

        // Create animation for login1 text
        Timeline login1Animation = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(login1.translateXProperty(), -400),
                new KeyValue(login1.opacityProperty(), 0)
            ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(login1.translateXProperty(), 0),
                new KeyValue(login1.opacityProperty(), 1)
            )
        );

        // Create animation for login2 text with delay
        Timeline login2Animation = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(login2.translateXProperty(), -400),
                new KeyValue(login2.opacityProperty(), 0)
            ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(login2.translateXProperty(), 0),
                new KeyValue(login2.opacityProperty(), 1)
            )
        );

        // Start login1 animation immediately
        login1Animation.play();

        // Start login2 animation after a 400ms delay
        login2Animation.setDelay(Duration.millis(500));
        login2Animation.play();
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