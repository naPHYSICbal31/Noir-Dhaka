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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    // Add these @FXML fields to your HelloController class:

    // ScrollPane elements
    @FXML
    private ScrollPane horizontalScrollPane;

    // Product ImageViews (coffee products)
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

    // Product Text elements (names)
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

    // Product Text elements (prices)
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
        // Scroll to Products section
        smoothScrollTo(0.27, .375); // Adjust this value to match Products section position
    }

    @FXML
    public void handleTop2Click() {
        // Scroll to Subscriptions section
        smoothScrollTo(0.82, 0.625); // Adjust this value to match Subscriptions section position
    }

    @FXML
    public void handleTop3Click() {
        // Scroll to Reviews section
        smoothScrollTo(0.1478, 0.2); // Adjust this value to match Reviews section position
    }

    @FXML
    public void handleTop4Click() {
        // Scroll to About Us section
        smoothScrollTo(0.465, 0.5); // Adjust this value to match About Us section position
    }

    @FXML
    private void handleCartClick() {
        try {
            if (Client.currentToken != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) cart.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
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
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Stage stage = (Stage) profile.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
                stage.setTitle("Noir Dhaka");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Modified scroll method that takes a target value parameter
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
        // Newsletter functionality
        submitButton.setOnAction(event -> saveEmail());
        newsletterField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveEmail();
            }
        });

        // Initialize animated images list
        animatedImages = Arrays.asList(reviewHead, productsHead, qualitiesHead);
        animationPlayed = new boolean[animatedImages.size()];

        // Add scroll listener
        verticalScrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            checkAndAnimateImages();
        });

        // Set up clipping for blog images after the scene is fully loaded
        Platform.runLater(() -> {
            setupImageClipping(blog1);
            setupImageClipping(blog2);
            setupImageClipping(blog3);
            verticalScrollPane.setVvalue(0.0);
        });
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

    public void scrollToTop() {
        // Reset scroll position to top with a quick animation
        Platform.runLater(() -> {
            smoothScrollTo(0.0, 0.0001); // 0.5 seconds duration
        });
    }

    @FXML
    private void handlehoverzoom(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        // Create a scale transition for zoom effect
        javafx.animation.ScaleTransition scaleTransition = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.05); // Scale to 110% of original size
        scaleTransition.setToY(1.05);
        scaleTransition.play();
        // Add mouse exited handler to zoom back out
        imageView.setOnMouseExited(exitEvent -> {
            javafx.animation.ScaleTransition scaleBack = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
            scaleBack.setToX(1.0); // Scale back to original size
            scaleBack.setToY(1.0);
            scaleBack.play();
        });
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

                    // Add the missing switch statement
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
                            // No specific scroll action for other elements
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

        // Check if image is already zoomed (scaled up)
        boolean isZoomed = imageView.getScaleX() > 1.0;

        if (isZoomed) {
            // Zoom out - return to original size
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);

            // Reset translation to center the image back to original position
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
            translateTransition.setToX(0);
            translateTransition.setToY(0);

            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, translateTransition);
            parallelTransition.play();

            // Change cursor back to hand
            imageView.setStyle("-fx-cursor: hand;");
        } else {
            // Zoom in - scale up to 1.5x while keeping within boundaries
            double zoomFactor = 1.5;

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
            scaleTransition.setToX(zoomFactor);
            scaleTransition.setToY(zoomFactor);

            // Calculate bounds to keep image within its original container
            double originalWidth = imageView.getFitWidth();
            double originalHeight = imageView.getFitHeight();
            double scaledWidth = originalWidth * zoomFactor;
            double scaledHeight = originalHeight * zoomFactor;

            // Calculate maximum translation to keep image within bounds
            double maxTranslateX = (scaledWidth - originalWidth) / 2;
            double maxTranslateY = (scaledHeight - originalHeight) / 2;

            // Limit translation to keep image within original boundaries
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
            translateTransition.setToX(Math.max(-maxTranslateX, Math.min(maxTranslateX, 0)));
            translateTransition.setToY(Math.max(-maxTranslateY, Math.min(maxTranslateY, 0)));

            ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, translateTransition);
            parallelTransition.play();

            // Change cursor to indicate clickable to zoom out
            imageView.setStyle("-fx-cursor: zoom-out;");
        }
    }

    private void setupImageClipping(ImageView imageView) {
        // Get the actual dimensions
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        // Create and set the clipping rectangle
        javafx.scene.shape.Rectangle clipRect = new javafx.scene.shape.Rectangle(width, height);
        imageView.setClip(clipRect);

        // Ensure the image doesn't have preserveRatio issues
        imageView.setPreserveRatio(false);
    }

    @FXML
    private void handleBlogHover(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        // Create a scale transition for zoom effect with clipping in mind
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.15); // Reduced scale to 115% to stay within bounds better
        scaleTransition.setToY(1.15);
        scaleTransition.play();

        // Add mouse exited handler to zoom back out
        imageView.setOnMouseExited(exitEvent -> {
            ScaleTransition scaleBack = new ScaleTransition(Duration.millis(300), imageView);
            scaleBack.setToX(1.0); // Scale back to original size
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

                // Map different text elements to different websites
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
            System.err.println("Failed to open website: " + e.getMessage());
        }
//        if (java.awt.Desktop.isDesktopSupported()) {
//            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
//
//            // Check if browse action is supported
//            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
//                desktop.browse(java.net.URI.create(url));
//            } else {
//                System.err.println("Browse action not supported");
//            }
//        } else {
//            System.err.println("Desktop not supported");
//        }


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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("blog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);

            // Get the controller and reset scroll position
            BlogController blogController = fxmlLoader.getController();

            // Set up the stage
            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}