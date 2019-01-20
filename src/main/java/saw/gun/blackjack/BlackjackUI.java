package saw.gun.blackjack;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BlackjackUI extends Application {
    private BlackjackController mController = new BlackjackController(this);
    BorderPane tablePane = new BorderPane();
    GridPane computerPlayerPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jack Black");

        // Root pane
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root, 800, 600));
        root.setStyle("-fx-background-color: #5db779"); // Green table!

        // Add table
        computerPlayerPane = new GridPane();
        root.setCenter(tablePane);
        computerPlayerPane.setHgap(0);
        computerPlayerPane.setVgap(10);

        tablePane.setCenter(computerPlayerPane);

        // Add action panes
        root.setBottom(addActionPane());

        primaryStage.show();
        primaryStage.setMaximized(true);

        mController.prepareNewGame();
    }

    // Set up user action pane
    private BorderPane addActionPane() {
        BorderPane wrapper = new BorderPane();

        //region Action buttons
        HBox actionBox = new HBox();
        actionBox.setPadding(new Insets(15, 12, 12, 12));
        actionBox.setSpacing(10);

        Button buttonDeal = new Button("Deal");
        buttonDeal.setPrefSize(100, 20);
        Button buttonPass = new Button("Pass");
        buttonPass.setPrefSize(100, 20);

        buttonDeal.setOnMouseClicked(mouseEvent -> mController.drawCard());

        actionBox.getChildren().addAll(buttonDeal, buttonPass);
        //endregion


        //region New game button
        HBox newGameBox = new HBox();
        newGameBox.setPadding(new Insets(15, 12, 12, 12));
        newGameBox.setSpacing(10);

        Button buttonNewGame = new Button("New Game");
        buttonNewGame.setPrefSize(100, 20);
        buttonNewGame.setOnMouseClicked(mouseEvent -> mController.prepareNewGame());

        newGameBox.getChildren().setAll(buttonNewGame);
        //endregion

        wrapper.setLeft(actionBox);
        wrapper.setRight(newGameBox);
        wrapper.setStyle("-fx-background-color: #336699");

        return wrapper;
    }

    void paintCard(int location, Card card, int cardOrder) {
        int cardLocationX = location * 100;
        int cardLocationY = 100 + cardOrder * 20;

        SvgImageLoaderFactory.install();

        // Get deck image's path
        String faceString = "_b";
        switch (card.getFace()) {
            case ACE:
                faceString = "1";
                break;
            case TWO:
                faceString = "2";
                break;
            case THREE:
                faceString = "3";
                break;
            case FOUR:
                faceString = "4";
                break;
            case FIVE:
                faceString = "5";
                break;
            case SIX:
                faceString = "6";
                break;
            case SEVEN:
                faceString = "7";
                break;
            case EIGHT:
                faceString = "8";
                break;
            case NINE:
                faceString = "9";
                break;
            case TEN:
                faceString = "t";
                break;
            case JACK:
                faceString = "j";
                break;
            case QUEEN:
                faceString = "q";
                break;
            case KING:
                faceString = "k";
                break;
        }

        String suitString = "g";
        switch (card.getSuit()) {
            case DIAMONDS:
                suitString = "d";
                break;
            case HEARTS:
                suitString = "h";
                break;
            case CLUBS:
                suitString = "c";
                break;
            case SPADES:
                suitString = "s";
                break;
        }

        ImageView imageView = new ImageView();
        Image image;

        try {
            image = new Image("deck/" + faceString + suitString + ".png");
        } catch (IllegalArgumentException e) {
            image = new Image("deck/_bg.png");
        }

        imageView.setImage(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        computerPlayerPane.add(imageView, location, cardOrder);

    }
}
