package saw.gun.blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;

public class BlackjackUI extends Application {
    private BlackjackController mController = new BlackjackController(this);
    private BorderPane tablePane = new BorderPane();
    private GridPane computerPlayerPane;
    private HBox dealerPane;
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jack Black");

        // Root pane
        root = new BorderPane();
        primaryStage.setScene(new Scene(root, 800, 600));
        root.setStyle("-fx-background-color: #5db779"); // Green table!

        // Add table
        computerPlayerPane = new GridPane();
        tablePane.setPadding(new Insets(20));

        root.setCenter(tablePane);
        computerPlayerPane.setHgap(10);
        computerPlayerPane.setVgap(20);

        tablePane.setCenter(computerPlayerPane);

        // Add Dealer's cards
        dealerPane = new HBox();

        dealerPane.setAlignment(Pos.CENTER);
        dealerPane.setSpacing(10);
        dealerPane.setPadding(new Insets(10));
        BorderPane.setAlignment(dealerPane, Pos.TOP_RIGHT);
        root.setTop(dealerPane);

        // Add action panes
        root.setBottom(addActionPane());

        primaryStage.show();
        primaryStage.setMaximized(true);

        mController.prepareNewGame();
        setCurrentUserActionPaneText();
        setCurrentUserDealtProbability();
        setCurrentProgress();
        setControlledPlayer();
        mController.checkPlayerWon();
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

        actionBox.getChildren().addAll(buttonDeal, buttonPass);
        //endregion


        //region New game button
        HBox newGameBox = new HBox();
        newGameBox.setPadding(new Insets(15, 12, 12, 12));
        newGameBox.setSpacing(10);

        Button buttonNewGame = new Button("New Game");
        buttonNewGame.setPrefSize(100, 20);
        buttonNewGame.setId("buttonNewGame");

        Button buttonProgress = new Button("Progress");
        buttonProgress.setPrefSize(100, 20);
        if (mController.currentPlayerIsControlled()) buttonProgress.setDisable(true); else buttonProgress.setDisable(false);

        newGameBox.getChildren().setAll(buttonProgress, buttonNewGame);
        //endregion

        //region Button logic
        if (mController.currentPlayerIsControlled()
                && !mController.controlledPlayerPointsInLimit()) {
            buttonDeal.setDisable(false);
            buttonPass.setDisable(false);
            buttonProgress.setDisable(true);
        } else if (!mController.currentPlayerIsControlled()) {
            buttonDeal.setDisable(true);
            buttonPass.setDisable(true);
            buttonProgress.setDisable(false);
        }

        buttonDeal.setOnMouseClicked(mouseEvent -> {
            mController.handCardToCurrentPlayer();
            mController.checkPlayerWon();
            if (mController.endOfGame) {
                mController.dealDealer();
                disableAllButton();
                return;
            }
            if (mController.currentPlayerIsControlled()) {
                if (mController.controlledPlayerPointsInLimit()) {
                    buttonDeal.setDisable(false);
                    buttonPass.setDisable(false);
                    buttonProgress.setDisable(true);
                } else {
                    mController.toNextPlayer();
                    buttonDeal.setDisable(true);
                    buttonPass.setDisable(true);
                    buttonProgress.setDisable(false);
                }
            }
            else {
                buttonDeal.setDisable(true);
                buttonPass.setDisable(true);
                buttonProgress.setDisable(false);
            }

            setCurrentUserActionPaneText();
            setCurrentUserDealtProbability();
            setCurrentProgress();
            setControlledPlayer();

        });

        buttonPass.setOnMouseClicked(mouseEvent -> {

            if (!mController.endOfList()) {
                mController.toNextPlayer();
                if (mController.currentPlayerIsControlled()) {
                    if (mController.controlledPlayerPointsInLimit()) {
                        buttonDeal.setDisable(false);
                        buttonPass.setDisable(false);
                        buttonProgress.setDisable(true);
                    } else {
                        mController.toNextPlayer();
                        buttonDeal.setDisable(true);
                        buttonPass.setDisable(true);
                        buttonProgress.setDisable(false);
                    }
                }
                else {
                    buttonDeal.setDisable(true);
                    buttonPass.setDisable(true);
                    buttonProgress.setDisable(false);
                }

                setCurrentUserActionPaneText();
                setCurrentUserDealtProbability();
                setCurrentProgress();
                setControlledPlayer();
                mController.checkPlayerWon();
            } else {
                mController.dealDealer();
                disableAllButton();
            }
        });

        buttonNewGame.setOnMouseClicked(mouseEvent -> {
            // Reset UI
            dealerPane.getChildren().clear();
            computerPlayerPane.getChildren().clear();
            buttonDeal.setDisable(false);

            // Create new controller and new game
            mController = new BlackjackController(this);
            mController.endOfGame = false;
            mController.prepareNewGame();
            for (Node n : root.lookupAll("Button")) {
                n.setDisable(false);
            }
            if (mController.currentPlayerIsControlled()) {
                if (mController.controlledPlayerPointsInLimit()) {
                    buttonDeal.setDisable(false);
                    buttonPass.setDisable(false);
                    buttonProgress.setDisable(true);
                } else {
                    mController.toNextPlayer();
                    buttonDeal.setDisable(true);
                    buttonPass.setDisable(true);
                    buttonProgress.setDisable(false);
                }
            }
            else {
                buttonDeal.setDisable(true);
                buttonPass.setDisable(true);
                buttonProgress.setDisable(false);
            }

            setCurrentUserActionPaneText();
            setCurrentUserDealtProbability();
            setCurrentProgress();
            setControlledPlayer();
            mController.checkPlayerWon();
        });

        buttonProgress.setOnMouseClicked(mouseEvent -> {
            mController.progress();
            if (mController.endOfGame) {
                return;
            }

            mController.checkPlayerWon();

            if (mController.currentPlayerIsControlled()) {
                if (mController.controlledPlayerPointsInLimit()) {
                    buttonDeal.setDisable(false);
                    buttonPass.setDisable(false);
                    buttonProgress.setDisable(true);
                } else {
                    mController.toNextPlayer();
                    buttonDeal.setDisable(true);
                    buttonPass.setDisable(true);
                    buttonProgress.setDisable(false);
                }
            }
            else {
                buttonDeal.setDisable(true);
                buttonPass.setDisable(true);
                buttonProgress.setDisable(false);
            }

            setCurrentUserActionPaneText();
            setCurrentUserDealtProbability();
            setCurrentProgress();
            setControlledPlayer();

        });
        //endregion

        wrapper.setLeft(actionBox);
        wrapper.setRight(newGameBox);
        wrapper.setStyle("-fx-background-color: #336699");

        return wrapper;
    }

    void paintDealerCard(int order, Card card, boolean faceForward) {
        ImageView imageView = new ImageView();
        Image image;
        if (!faceForward) {
            image = new Image("deck/_bg.png");
            imageView.setId("faceBackward");
        } else {
            try {
                image = new Image(getImagePath(card));
            } catch (IllegalArgumentException e) {
                image = new Image("deck/_bg.png");
            }
        }

        imageView.setImage(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        dealerPane.getChildren().add(imageView);
    }

    void paintCard(int location, Card card, int cardOrder) {
//        int cardLocationX = location * 100;
//        int cardLocationY = 100 + cardOrder * 20;

        ImageView imageView = new ImageView();
        Image image;

        String imagePath = getImagePath(card);

        try {
            image = new Image(imagePath);
        } catch (IllegalArgumentException e) {
            image = new Image("deck/_bg.png");
        }

        imageView.setImage(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        computerPlayerPane.add(imageView, cardOrder, location);

    }

    @NotNull
    private static String getImagePath(Card card) {

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
        return "deck/" + faceString + suitString + ".png";
    }

    private void setCurrentUserActionPaneText() {
        Text currentPoints = new Text();
        currentPoints.setId("currentUserPoint");
        StringBuilder pointText = new StringBuilder("Current user point: ");
        HashSet<Integer> userPointsEnquired = mController.getCurrentPlayerPoints();
        if (userPointsEnquired.size() == 1) pointText.append(Integer.toString(userPointsEnquired.iterator().next()));
        else if (userPointsEnquired.size() > 1) {
            Iterator<Integer> userPointsEnquiredIte = userPointsEnquired.iterator();
            while (userPointsEnquiredIte.hasNext()) {
                pointText.append(Integer.toString(userPointsEnquiredIte.next()));
                if (userPointsEnquiredIte.hasNext()) pointText.append("/");
            }
        }

        currentPoints.setText(pointText.toString());

        BorderPane bottomWrapper = (BorderPane) root.getBottom();
        HBox actionBox = (HBox) bottomWrapper.getLeft();
        actionBox.getChildren().remove(actionBox.lookup("#currentUserPoint"));
        actionBox.getChildren().add(currentPoints);
    }

    private void setCurrentUserDealtProbability() {
        Text currentPoints = new Text();
        currentPoints.setId("dealtProbability");


        currentPoints.setText("Probability: " + mController.currentUserDealtProb());

        BorderPane bottomWrapper = (BorderPane) root.getBottom();
        HBox actionBox = (HBox) bottomWrapper.getLeft();
        actionBox.getChildren().remove(actionBox.lookup("#dealtProbability"));
        actionBox.getChildren().add(currentPoints);
    }

    private void setCurrentProgress() {
        Text currentPoints = new Text();
        currentPoints.setId("currentProgress");


        currentPoints.setText("currentProgress: " + mController.getCurrentPlayerLocation());

        BorderPane bottomWrapper = (BorderPane) root.getBottom();
        HBox actionBox = (HBox) bottomWrapper.getLeft();
        actionBox.getChildren().remove(actionBox.lookup("#currentProgress"));
        actionBox.getChildren().add(currentPoints);
    }

    private void setControlledPlayer() {
        Text currentPoints = new Text();
        currentPoints.setId("controlledPlayer");


        currentPoints.setText("Your controlled player: " + mController.getControlledPlayer());

        BorderPane bottomWrapper = (BorderPane) root.getBottom();
        HBox actionBox = (HBox) bottomWrapper.getLeft();
        actionBox.getChildren().remove(actionBox.lookup("#controlledPlayer"));
        actionBox.getChildren().add(currentPoints);
    }

    void disableAllButton() {
        for (Node n : root.lookupAll("Button")) {
            n.setDisable(true);
        }
        root.lookup("#buttonNewGame").setDisable(false);
    }

    void removeBackwardCard() {
        dealerPane.getChildren().remove(dealerPane.lookup("#faceBackward"));
    }
}
