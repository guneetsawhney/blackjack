package saw.gun.blackjack;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BlackjackUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jack Black");

        // Root pane
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root, 800, 600));

        // Add action panes
        root.setBottom(addActionPane());

//        // TEST: add SVG
//        SvgImageLoaderFactory.install();
//        Image image = new Image("deck/1s.svg");
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//
//        root.setTop(imageView);

        primaryStage.show();
        root.setStyle("-fx-background-color: #5db779");
    }

    // Set up user action pane
    private static BorderPane addActionPane() {
        BorderPane wrapper = new BorderPane();

        //region Action buttons
        HBox actionBox = new HBox();
        actionBox.setPadding(new Insets(15, 12, 12, 12));
        actionBox.setSpacing(10);

        Button buttonDeal = new Button("Deal");
        buttonDeal.setPrefSize(100, 20);
        Button buttonPass = new Button("Pass");
        buttonPass.setPrefSize(100, 20);

        actionBox.getChildren().addAll(buttonDeal, buttonPass);
        //endregion


        //region New game buttons
        HBox newGameBox = new HBox();
        newGameBox.setPadding(new Insets(15, 12, 12, 12));
        newGameBox.setSpacing(10);

        Button buttonNewGame = new Button("New Game");
        buttonNewGame.setPrefSize(100, 20);

        newGameBox.getChildren().setAll(buttonNewGame);
        //endregion

        wrapper.setLeft(actionBox);
        wrapper.setRight(newGameBox);
        wrapper.setStyle("-fx-background-color: #336699");

        return wrapper;


    }
}
