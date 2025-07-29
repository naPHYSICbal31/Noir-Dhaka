package com.example.noir;

import com.example.backend.Client;
import com.example.backend.StarRatingCell;

import com.example.backend.server.dbFetch;
import javafx.scene.Node;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;

import com.example.backend.User;
import com.example.backend.Coffee;
import javafx.animation.FadeTransition;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static com.example.noir.HelloApplication.client;

public class ProfileController implements Initializable {


    @FXML
    private ScrollPane verticalScrollPane;
    @FXML
    private Label usernameLabel41;
    @FXML
    private Label contact;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel3;
    @FXML
    private Label usernameLabel4;
    @FXML
    private Text bar11;

    @FXML
    public Label usernameLabel;
    @FXML
    private TableColumn<Coffee, Integer> ratingColumn;

    public HashMap<Integer, Integer> coffeeRatings = new HashMap<>();

    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private ImageView cart;
    @FXML
    private ListView<String> coffeeListView;

    @FXML
    private TableView<Coffee> coffeeTableView;
    @FXML
    private ScrollPane scrollpane1;
    @FXML
    private TableColumn<Coffee, String> nameColumn;

    @FXML
    private TableColumn<Coffee, Double> priceColumn;

    @FXML
    private TableColumn<Coffee, String> descriptionColumn;
    @FXML
    private TableColumn<Coffee, Integer> coffeeCountColumn;
    public TableColumn<Coffee, String> buyDateColumn;

    @FXML
    private Button logoutbutton;
    @FXML
    private Text ordertxt;
    @FXML
    private Line orderline;
    @FXML
    private CheckBox check1;
    @FXML
    private CheckBox check2;
    @FXML
    private CheckBox check3;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private Line line1;
    private Font euclidBoldFont;
    public User currentUser;
    
    private VBox receiptContainer;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        

        loadFonts();
        loadUserData();
        loadCoffeeData();
        setupTableColumns();
        applyCustomFonts();
        applyTableViewStyles();
        setupReceiptScrollPane();
        Platform.runLater(this::styleCheckboxes);
    }
    private void styleCheckboxes() {
        // Apply styles to the checkbox boxes after they're rendered
        String checkboxStyle = "-fx-background-color: #91b08f; -fx-border-color: #91b08f; -fx-mark-color: #000000;";

        if (check1 != null && check1.lookup(".box") != null) {
            check1.lookup(".box").setStyle(checkboxStyle);
            check1.setFont(euclidBoldFont);
        }
        if (check2 != null && check2.lookup(".box") != null) {
            check2.lookup(".box").setStyle(checkboxStyle);
            check2.setFont(euclidBoldFont);
        }
        if (check3 != null && check3.lookup(".box") != null) {
            check3.lookup(".box").setStyle(checkboxStyle);
            check3.setFont(euclidBoldFont);
        }
    }


    private void setupReceiptScrollPane() {

        receiptContainer = new VBox();
        receiptContainer.setSpacing(15);
        receiptContainer.setStyle("-fx-background-color: #e9e9e9; -fx-padding: 20;");
        

        scrollpane1.setContent(receiptContainer);
        scrollpane1.setFitToWidth(true);
        

        loadReceiptData();
    }
    
    private void loadReceiptData() {
        if (receiptContainer == null || currentUser == null) return;
        

        receiptContainer.getChildren().clear();
        
        HashMap<String, LocalDateTime> receipts = currentUser.getRecipts();
        
        if (receipts == null || receipts.isEmpty()) {

            Label noReceiptsLabel = new Label("No receipts found");
            noReceiptsLabel.setFont(euclidBoldFont);
            noReceiptsLabel.setStyle("-fx-text-fill: #666666; -fx-alignment: center;");
            receiptContainer.getChildren().add(noReceiptsLabel);
            return;
        }
        

        Label titleLabel = new Label("BILLING RECEIPTS");
        titleLabel.setFont(Font.font(euclidBoldFont.getFamily(), 22));
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold;");
        receiptContainer.getChildren().add(titleLabel);
        

        javafx.scene.shape.Line separatorLine = new javafx.scene.shape.Line();
        separatorLine.setStartX(0);
        separatorLine.setEndX(200);
        separatorLine.setStroke(javafx.scene.paint.Color.web("#91b08f"));
        separatorLine.setStrokeWidth(2);
        receiptContainer.getChildren().add(separatorLine);
        

        receipts.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .forEach(entry -> {
                String receiptText = entry.getKey();
                LocalDateTime receiptDate = entry.getValue();
                

                VBox receiptBox = new VBox();
                receiptBox.setSpacing(12);
                receiptBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 25; " +
                                  "-fx-background-radius: 10; -fx-border-color: #d0d0d0; " +
                                  "-fx-border-radius: 10; -fx-border-width: 2;");
                

                receiptBox.setPrefWidth(460);
                receiptBox.setMaxWidth(460);
                

                Label dateLabel = new Label("Date: " + formatDateTimePretty(receiptDate));
                dateLabel.setFont(Font.font(euclidBoldFont.getFamily(), 16));
                dateLabel.setStyle("-fx-text-fill: #4e634d; -fx-font-weight: bold;");
                

                Text contentText = new Text(receiptText);
                contentText.setFont(Font.font(euclidBoldFont.getFamily(), 14));
                contentText.setStyle("-fx-fill: #333333;");
                contentText.setWrappingWidth(410);
                

                String[] lines = receiptText.split("\n");
                int totalLines = 0;
                for (String line : lines) {

                    int lineLength = line.length();
                    int wrappedLines = Math.max(1, (int) Math.ceil(lineLength / 60.0));
                    totalLines += wrappedLines;
                }
                

                double estimatedHeight = Math.max(50, totalLines * 22 + 10);
                receiptBox.setMinHeight(estimatedHeight);
                receiptBox.setPrefHeight(estimatedHeight);
                

                receiptBox.getChildren().addAll(dateLabel, contentText);
                receiptContainer.getChildren().add(receiptBox);
            });
    

    Platform.runLater(() -> {
        double totalHeight = 0;
        

        for (Node node : receiptContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                totalHeight += vbox.getPrefHeight();
            } else if (node instanceof Label) {
                totalHeight += 35;
            } else if (node instanceof javafx.scene.shape.Line) {
                totalHeight += 15;
            }
        }
        

        totalHeight += receiptContainer.getSpacing() * (receiptContainer.getChildren().size() - 1);

        totalHeight += 60;
        

        double finalHeight = Math.max(295, totalHeight);
        receiptContainer.setPrefHeight(finalHeight);
        receiptContainer.setMinHeight(finalHeight);
        

        scrollpane1.setVvalue(0);
    });
}

    private void loadFonts() {
        try {

            euclidBoldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/euclidbold.ttf"), 18);
            if (euclidBoldFont == null) {
                System.err.println("Failed to load euclidbold.ttf font - using default");
                euclidBoldFont = Font.font("Arial", 18);
            } else {
                System.out.println("EuclidCircularA-Bold font loaded successfully");
            }
        } catch (Exception e) {
            System.err.println("Error loading fonts: " + e.getMessage());
            euclidBoldFont = Font.font("Arial", 18);
            e.printStackTrace();
        }
    }

    private void applyCustomFonts() {

        if (euclidBoldFont != null) {
            usernameLabel.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            emailLabel.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            addressLabel.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            usernameLabel1.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            //bar11.setFont(euclidBoldFont);
            usernameLabel3.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            usernameLabel4.setFont(Font.font(euclidBoldFont.getFamily(), 32));
            usernameLabel41.setFont(euclidBoldFont);
            contact.setFont(euclidBoldFont);
        }
    }

    private void loadUserData() {
        try {
            currentUser = client.getUserinfo();
            if (currentUser != null) {
                if (usernameLabel != null) {
                    usernameLabel.setText(currentUser.getUsername());
                }
                if (emailLabel != null) {
                    emailLabel.setText(currentUser.getEmail());
                    System.out.println(currentUser.getEmail());
                }
                if (addressLabel != null) {
                    addressLabel.setText(currentUser.getAddress());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }
    public static String formatDateTimePretty(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String suffix = getDaySuffix(day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM HH:mm");
        String formatted = dateTime.format(formatter);

        return day + suffix + " " + formatted;
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    private void loadCoffeeData() {
        try {
            if (coffeeTableView != null) {
                List<Coffee> purchasedCoffees = new ArrayList<>();
                User u = client.getUserinfo();
                HashMap<Integer, Integer> bought = u.getBuyHistory();

                if (u != null && bought != null) {
                    for (int coffeeId : bought.keySet()) {

                        Integer purchaseCount = bought.get(coffeeId);
                        System.out.println(coffeeId);
                        Coffee coffee = client.getCoffeeById(coffeeId);

                        System.out.println(coffee.getName());
                        System.out.println(purchaseCount);
                        if (purchaseCount != null && purchaseCount > 0) {
                            System.out.println(coffee.getName());
                            purchasedCoffees.add(coffee);
                        }
                    }
                }

                ObservableList<Coffee> filteredCoffeeData = FXCollections.observableArrayList(purchasedCoffees);
                coffeeTableView.setItems(filteredCoffeeData);
            }

        } catch (Exception e) {
            System.err.println("Error loading coffee data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        if (nameColumn != null) {

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setCellFactory(column -> {
                return new javafx.scene.control.TableCell<Coffee, String>() {
                    private javafx.scene.text.Text text;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            text = new javafx.scene.text.Text(item);
                            text.setWrappingWidth(nameColumn.getWidth() - 20);
                            text.textProperty().bind(itemProperty());
                            setGraphic(text);
                            setText(null);
                        }
                    }
                };
            });
        }

        if (priceColumn != null) {
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        if (descriptionColumn != null) {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            descriptionColumn.setCellFactory(column -> {
                return new javafx.scene.control.TableCell<Coffee, String>() {
                    private javafx.scene.text.Text text;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            text = new javafx.scene.text.Text(item);
                            text.setWrappingWidth(descriptionColumn.getWidth() - 20);
                            text.textProperty().bind(itemProperty());
                            setGraphic(text);
                            setText(null);
                        }
                    }
                };
            });
        }


        if (coffeeCountColumn != null) {
            coffeeCountColumn.setCellValueFactory(cellData -> {
                Coffee coffee = cellData.getValue();

                if (currentUser != null && currentUser.getBuyHistory() != null) {

                    Integer count = currentUser.getBuyHistory().get(coffee.getId());
                    return new javafx.beans.property.SimpleIntegerProperty(count != null ? count : 0).asObject();
                }
                return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
            });
        }

        if (buyDateColumn != null) {
            buyDateColumn.setCellValueFactory(cellData -> {
                Coffee coffee = cellData.getValue();

                if (currentUser != null && currentUser.getBuyTime() != null) {

                    LocalDateTime timeObj = currentUser.getBuyTime().get(coffee.getId());

                    if (timeObj != null) {
                        String timeStr = formatDateTimePretty(timeObj);
                        return new javafx.beans.property.SimpleStringProperty(timeStr);
                    }
                }

                return new javafx.beans.property.SimpleStringProperty("N/A");
            });
        }
        if (ratingColumn != null) {
            ratingColumn.setCellValueFactory(cellData -> {
                Coffee coffee = cellData.getValue();
                Integer rating = coffeeRatings.get(coffee.getId());
                return new javafx.beans.property.SimpleIntegerProperty(rating != null ? rating : 0).asObject();
            });

            ratingColumn.setCellFactory(column -> new StarRatingCell(this));
        }




    }


    @FXML
    private void refreshData() {
        loadUserData();
        loadCoffeeData();
        loadReceiptData();
    }


    @FXML
    private void handlehoverzoom(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();


        javafx.animation.ScaleTransition scaleTransition = new javafx.animation.ScaleTransition(Duration.millis(300), imageView);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
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
    private void redirectToScene(MouseEvent event) {
        try {

            Object source = event.getSource();


            Stage stage = null;
            if (source instanceof ImageView) {
                stage = (Stage) ((ImageView) source).getScene().getWindow();
            } else if (source instanceof Text) {
                stage = (Stage) ((Text) source).getScene().getWindow();
            }
            else if(source instanceof Label)
            {
                stage = (Stage) ((Label) source).getScene().getWindow();
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);


            HelloController helloController = fxmlLoader.getController();


            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();


            Platform.runLater(() -> {
                helloController.scrollToTop();
                String fxId = "";

                if (source instanceof Text) {
                    Text textSource = (Text) source;
                    fxId = textSource.getId();
                }
                else if(source instanceof Label)
                    {
                        Label textSource = (Label) source;
                        fxId = textSource.getId();
                    }
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
                        case "contact":
                            helloController.handleTop4Click();
                            break;
                        default:

                            break;
                    }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void scrollToTop() {

        Platform.runLater(() -> {
            smoothScrollTo(0.0, 0.0001);
        });
    }


    private void smoothScrollTo(double targetValue, double time) {
        if (verticalScrollPane == null) return;

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
    public void createFadeAway(Node node, Duration fadeOutDuration) {
        if (node == null) {
            System.err.println("Cannot fade null node: " + node);
            return;
        }

        FadeTransition fadeOut = new FadeTransition(fadeOutDuration, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
    public void createFadeIn(Node node, Duration fadeInDuration) {
        if (node == null) {
            System.err.println("Cannot fade null node: " + node);
            return;
        }

        FadeTransition fadeIn = new FadeTransition(fadeInDuration, node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }



    @FXML
    public void bar2clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(bar11, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        fadeOutIfVisible(anchor1, Duration.seconds(0.25));
        fadeOutIfVisible(line1, Duration.seconds(0.25));
        anchor1.setMouseTransparent(true);
        scrollpane1.setMouseTransparent(true);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(coffeeTableView, Duration.seconds(0.25));
            coffeeTableView.setMouseTransparent(false);
            fadeInIfHidden(ordertxt, Duration.seconds(0.25));
            fadeInIfHidden(orderline, Duration.seconds(0.25));
        }));
        delay.play();

    }
    @FXML
    public void bar3clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(bar11, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
        fadeOutIfVisible(anchor1, Duration.seconds(0.25));
        fadeOutIfVisible(line1, Duration.seconds(0.25));
        anchor1.setMouseTransparent(true);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {

            loadReceiptData();
            fadeInIfHidden(scrollpane1, Duration.seconds(0.25));
            scrollpane1.setMouseTransparent(false);
        }));
        delay.play();

    }
    @FXML
    public void bar1clicked(MouseEvent event)
    {
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        scrollpane1.setMouseTransparent(true);
        fadeOutIfVisible(anchor1, Duration.seconds(0.25));
        anchor1.setMouseTransparent(true);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(usernameLabel, Duration.seconds(0.25));
            fadeInIfHidden(emailLabel, Duration.seconds(0.25));
            fadeInIfHidden(addressLabel, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel1, Duration.seconds(0.25));
            fadeInIfHidden(bar11, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel3, Duration.seconds(0.25));
            fadeInIfHidden(usernameLabel4, Duration.seconds(0.25));
            fadeInIfHidden(line1, Duration.seconds(0.25));
        }));
        delay.play();
    }
    private void fadeOutIfVisible(Node node, Duration duration) {
        if (node == null) {
            System.err.println("Cannot fade null node");
            return;
        }


        if (node.getOpacity() > 0.2) {
            System.out.println("Fading out: " + node.getClass().getSimpleName());
            FadeTransition fade = new FadeTransition(duration, node);
            fade.setFromValue(node.getOpacity());
            fade.setToValue(0.0);
            fade.play();
        } else {
            System.out.println("Skipping fade out - already faded: " + node.getClass().getSimpleName());
        }
    }


    private void fadeInIfHidden(Node node, Duration duration) {
        if (node == null) {
            System.err.println("Cannot fade null node");
            return;
        }


        if (node.getOpacity() <= 0.2) {
            System.out.println("Fading in: " + node.getClass().getSimpleName());
            FadeTransition fade = new FadeTransition(duration, node);
            fade.setFromValue(node.getOpacity());
            fade.setToValue(1.0);
            fade.play();
        } else {
            System.out.println("Skipping fade in - already visible: " + node.getClass().getSimpleName());
        }
    }
    @FXML
    private void handleLogOut(){
        client.logOut();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 1440, 810);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());


        Stage stage = (Stage) logoutbutton.getScene().getWindow();
        stage.setScene(scene);


        HelloController controller = loader.getController();
        if (controller != null) {
            controller.scrollToTop();
        }

        stage.show();
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
    private void applyTableViewStyles() {
        if (coffeeTableView != null) {
            coffeeTableView.setStyle(
                    "-fx-font-family: 'euclid circular a';" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;"
            );


            coffeeTableView.setRowFactory(tv -> {
                javafx.scene.control.TableRow<Coffee> row = new javafx.scene.control.TableRow<Coffee>() {
                    @Override
                    protected void updateItem(Coffee item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setPrefHeight(-1);
                        } else {
                            setPrefHeight(-1);
                            setMinHeight(30);
                        }
                    }
                };
                return row;
            });
        }
        }
    @FXML
    public void bar4clicked(MouseEvent event)
    {
        fadeOutIfVisible(usernameLabel, Duration.seconds(0.25));
        fadeOutIfVisible(emailLabel, Duration.seconds(0.25));
        fadeOutIfVisible(addressLabel, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel1, Duration.seconds(0.25));
        fadeOutIfVisible(bar11, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel3, Duration.seconds(0.25));
        fadeOutIfVisible(usernameLabel4, Duration.seconds(0.25));
        fadeOutIfVisible(coffeeTableView, Duration.seconds(0.25));
        fadeOutIfVisible(scrollpane1, Duration.seconds(0.25));
        scrollpane1.setMouseTransparent(true);
        coffeeTableView.setMouseTransparent(true);
        fadeOutIfVisible(line1, Duration.seconds(0.25));
        fadeOutIfVisible(ordertxt, Duration.seconds(0.25));
        fadeOutIfVisible(orderline, Duration.seconds(0.25));
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
            fadeInIfHidden(anchor1, Duration.seconds(0.25));
            anchor1.setMouseTransparent(false);
        }));
        delay.play();
    }
}