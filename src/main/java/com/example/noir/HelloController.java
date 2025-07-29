package com.example.noir;
import com.example.backend.Client;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

public class HelloController {
    @FXML
    private TextField newsletterField;
    @FXML
    private Button submitButton;
    @FXML
    private Button scrollButton;
    @FXML
    private ImageView reviewHead;
    @FXML
    private ImageView productsHead;
    @FXML
    private ImageView frontIntro;
    @FXML
    private ImageView noirBg;
    @FXML
    private ImageView qualitiesHead;
    @FXML
    private ScrollPane verticalScrollPane;
    @FXML
    private ImageView txtbg;
    @FXML
    private ImageView logobg;
    @FXML
    private ImageView bggreen;
    @FXML
    private ImageView blog1;
    @FXML
    private ImageView blog2;
    @FXML
    private ImageView blog3;
    private List<ImageView> animatedImages;
    private boolean[] animationPlayed;
    @FXML
    private ImageView cart;
    @FXML
    private Text top1Text;
    @FXML
    private Text top2Text;
    @FXML
    private Text top3Text;
    @FXML
    private Text top4Text;
    @FXML
    private ImageView profile;



    @FXML
    private ScrollPane horizontalScrollPane;


    @FXML
    private ImageView bucaramanga;
    @FXML
    private ImageView fiveset;
    @FXML
    private ImageView kona;
    @FXML
    private ImageView nepalese;
    @FXML
    private ImageView ethiopia;
    @FXML
    private ImageView indian;
    @FXML
    private ImageView guatemala;
    @FXML
    private ImageView costa;
    @FXML
    private ImageView jamaica;
    @FXML
    private ImageView kopi;
    @FXML
    private ImageView kona1;
    @FXML
    private ImageView nepalese1;
    @FXML
    private ImageView ethiopia1;
    @FXML
    private ImageView indian1;
    @FXML
    private ImageView guatemala1;
    @FXML
    private ImageView costa1;


    @FXML
    private Text prtxt1;
    @FXML
    private Text prtxt2;
    @FXML
    private Text prtxt3;
    @FXML
    private Text prtxt4;
    @FXML
    private Text prtxt5;
    @FXML
    private Text prtxt6;
    @FXML
    private Text prtxt7;
    @FXML
    private Text prtxt8;
    @FXML
    private Text prtxt21;
    @FXML
    private Text prtxt22;
    @FXML
    private Text prtxt31;
    @FXML
    private Text prtxt41;
    @FXML
    private Text prtxt51;
    @FXML
    private Text prtxt61;
    @FXML
    private Text prtxt71;
    @FXML
    private Text prtxt81;


    @FXML
    private Text prtk1;
    @FXML
    private Text prtk2;
    @FXML
    private Text prtk3;
    @FXML
    private Text prtk4;
    @FXML
    private Text prtk5;
    @FXML
    private Text prtk6;
    @FXML
    private Text prtk7;
    @FXML
    private Text prtk8;
    @FXML
    private Text prtk21;
    @FXML
    private Text prtk22;
    @FXML
    private Text prtk31;
    @FXML
    private Text prtk41;
    @FXML
    private Text prtk51;
    @FXML
    private Text prtk61;
    @FXML
    private Text prtk71;
    @FXML
    private Text prtk81;
    public static String url = "";
    @FXML
    public void handleTop1Click() {

        smoothScrollTo(0.27, .375);
    }

    @FXML
    public void handleTop2Click() {

        smoothScrollTo(0.82, 0.625);
    }

    @FXML
    public void handleTop3Click() {

        smoothScrollTo(0.1478, 0.2);
    }

    @FXML
    public void handleTop4Click() {

        smoothScrollTo(0.465, 0.5);
    }

    @FXML
    private void handleCartClick() {
        try {
            if (Client.currentToken != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
                ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
                scene.setCursor(customCursor);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
                ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
                scene.setCursor(customCursor);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleProfileClick() {
        try {
            if (Client.currentToken != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
                Stage stage = (Stage) profile.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
                ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
                scene.setCursor(customCursor);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) profile.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
                ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
                scene.setCursor(customCursor);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            }
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

    @FXML
    private void initialize() {

        submitButton.setOnAction(event -> saveEmail());
        newsletterField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveEmail();
            }
        });


        animatedImages = Arrays.asList(reviewHead, productsHead, qualitiesHead);
        animationPlayed = new boolean[animatedImages.size()];


        verticalScrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            checkAndAnimateImages();
        });

        verticalScrollPane.getStylesheets().add("scrollbar.css");
        Platform.runLater(() -> {
            setupImageClipping(blog1);
            setupImageClipping(blog2);
            setupImageClipping(blog3);
            verticalScrollPane.setVvalue(0.0);
        });
    }

    private void smoothScrollToMiddle() {
        double targetValue = 0.27;
        double startValue = verticalScrollPane.getVvalue();
        Duration duration = Duration.seconds(0.5);
        int frames = 120;

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

    public void scrollToTop() {

        Platform.runLater(() -> {
            smoothScrollTo(0.0, 0.0001);
        });
    }

    @FXML
    private void handlehoverzoom(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();


        javafx.animation.ScaleTransition scaleTransition = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.play();

        imageView.setOnMouseExited(exitEvent -> {
            javafx.animation.ScaleTransition scaleBack = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
            scaleBack.setToX(1.0);
            scaleBack.setToY(1.0);
            scaleBack.play();
        });
    }

    @FXML
    private void addunderline(MouseEvent event) {

        Text textElement = (Text) event.getSource();


        textElement.setUnderline(true);


        textElement.setOnMouseExited(exitEvent -> {
            textElement.setUnderline(false);
        });
    }

    @FXML
    public void redirectToCoffee(MouseEvent event) {
        try {

            Object source = event.getSource();
            String sourceFxId = null;

            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
                sourceFxId = ((ImageView) source).getId();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
                sourceFxId = ((Text) source).getId();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("coffee.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


            coffeeController coffeeController = fxmlLoader.getController();
            coffeeController.setSourceFxId(sourceFxId);

            stage.setTitle("Noir Dhaka");
            Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
            ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
            scene.setCursor(customCursor);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectToScene(MouseEvent event) {
        try {

            Object source = event.getSource();


            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


            HelloController helloController = fxmlLoader.getController();
            Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
            ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
            scene.setCursor(customCursor);

            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();


            Platform.runLater(() -> {
                helloController.scrollToTop();


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
                        default:

                            break;
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBlogImageClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();


        boolean isZoomed = imageView.getScaleX() > 1.0;

        if (isZoomed) {

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);


            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
            translateTransition.setToX(0);
            translateTransition.setToY(0);

            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, translateTransition);
            parallelTransition.play();


            imageView.setStyle("-fx-cursor: hand;");
        } else {

            double zoomFactor = 1.5;

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
            scaleTransition.setToX(zoomFactor);
            scaleTransition.setToY(zoomFactor);


            double originalWidth = imageView.getFitWidth();
            double originalHeight = imageView.getFitHeight();
            double scaledWidth = originalWidth * zoomFactor;
            double scaledHeight = originalHeight * zoomFactor;


            double maxTranslateX = (scaledWidth - originalWidth) / 2;
            double maxTranslateY = (scaledHeight - originalHeight) / 2;


            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
            translateTransition.setToX(Math.max(-maxTranslateX, Math.min(maxTranslateX, 0)));
            translateTransition.setToY(Math.max(-maxTranslateY, Math.min(maxTranslateY, 0)));

            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, translateTransition);
            parallelTransition.play();


            imageView.setStyle("-fx-cursor: zoom-out;");
        }
    }

    private void setupImageClipping(ImageView imageView) {

        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();


        javafx.scene.shape.Rectangle clipRect = new javafx.scene.shape.Rectangle(width, height);
        imageView.setClip(clipRect);


        imageView.setPreserveRatio(false);
    }

    @FXML
    private void handleBlogHover(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();


        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.15);
        scaleTransition.setToY(1.15);
        scaleTransition.play();


        imageView.setOnMouseExited(exitEvent -> {
            ScaleTransition scaleBack = new ScaleTransition(Duration.millis(300), imageView);
            scaleBack.setToX(1.0);
            scaleBack.setToY(1.0);
            scaleBack.play();
        });
    }

    @FXML
    private void redirectToWebsite(MouseEvent event) throws IOException{
        try {
            Object source = event.getSource();
            if (source instanceof ImageView) {
                ImageView imageView = (ImageView) source;
                String imageId = imageView.getId();


                switch (imageId) {
                    case "blog1":
                        url = "https://sprudge.com/the-truth-about-travel-coffee-mugs-197148.html";
                        break;
                    case "blog2":
                        url = "https://sprudge.com/the-sprudge-guide-to-coffee-in-okinawa-japan-338633.html";
                        break;
                    case "blog3":
                        url = "https://www.baristahustle.com/water-chemistry-for-tea/";
                        break;
                    default:
                        url = "https://google.com"; // Default website
                        break;
                }
            } else if (source instanceof Text) {
                Text textElement = (Text) source;
                String textId = textElement.getId();


                switch (textId) {
                    case "blogtxt1":
                        url = "https://sprudge.com/the-truth-about-travel-coffee-mugs-197148.html";
                        break;
                        case "blogtxt2":
                            url = "https://sprudge.com/the-sprudge-guide-to-coffee-in-okinawa-japan-338633.html";
                            break;
                            case "blogtxt3":
                                url = "https://www.baristahustle.com/water-chemistry-for-tea/";
                                break;
                    default:
                        url = "https://google.com"; // Default website
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }



        try {

            Object source = event.getSource();


            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("blog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


            BlogController blogController = fxmlLoader.getController();


            stage.setTitle("Noir Dhaka");
            Image cursorImage = new Image(getClass().getResourceAsStream("images/maccursor.png"));
            ImageCursor customCursor = new ImageCursor(cursorImage, 5, 5);
            scene.setCursor(customCursor);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}