package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.HitShotEvent;
import com.epam.ships.client.gui.events.LooseEvent;
import com.epam.ships.client.gui.events.MissShotEvent;
import com.epam.ships.client.gui.events.OpponentShotEvent;
import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.client.gui.events.WinEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Magda
 * @since 2017-12-15
 */

public class GameController {

    private static final Target logger = new SharedLogger(Client.class);

    private static final int BOARD_SIZE = 10;

    @FXML
    private GridPane yourBoard;

    @FXML
    private GridPane opponentBoard;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button eventButton;

    @FXML
    private Label winLabel;

    private int shotIndex;

    @FXML
    public void initialize() {
        eventButton.addEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentConnectedEvent -> opponentWithdraw());
        eventButton.addEventHandler(OpponentShotEvent.OPPONENT_SHOT,
                opponentShotEvent -> setOpponentShot(opponentShotEvent.getShotIndex()));
        eventButton.addEventHandler(TurnChangeEvent.TURN_EVENT, event -> setMyTurn());
        eventButton.addEventHandler(MissShotEvent.MISS_SHOT, event -> changeTurn());
        eventButton.addEventHandler(HitShotEvent.HIT_SHOT, event -> setHit());
        eventButton.addEventHandler(WinEvent.GAME_WIN, event -> setWin());
        eventButton.addEventHandler(LooseEvent.GAME_LOSE, event -> setLose());

        initializeTurn(false);
    }

    private void initializeTurn(boolean myTurn) {
        logger.info("initialize turn: " + myTurn);

        if(!myTurn) {
            opponentBoard.setDisable(true);
            final double opacity = 0.4;
            opponentBoard.setOpacity(opacity);
        }
    }

    void initializeClient() {
        getClient().setEventTrigger(eventButton);
    }

    void initializeBoards(GridPane yourBoard) {
        final int margin = 50;
        final NumberBinding allRectanglesWidth = Bindings.min(this.yourBoard.heightProperty(),
                this.yourBoard.widthProperty().add(-margin));
        final NumberBinding allRectanglesHeight = Bindings.min(this.yourBoard.heightProperty(),
                this.yourBoard.widthProperty()).add(-margin);

        for(int i = 0; i < BOARD_SIZE; i++ ) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                final int shotIndex = j * BOARD_SIZE + i;

                Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
                Rectangle opponentRect = getOpponentRectangle(allRectanglesWidth, allRectanglesHeight, shotIndex);

                this.yourBoard.add(yourRect, i, j);
                opponentBoard.add(opponentRect, i, j);

                GridPane.setHalignment(yourRect, HPos.CENTER);
                GridPane.setHalignment(opponentRect, HPos.CENTER);
            }
        }

        copyYourBoard(yourBoard);
    }

    private void copyYourBoard(GridPane yourBoard) {
        for(int i =1; i < yourBoard.getChildren().size(); i++) {
            if(((Rectangle) yourBoard.getChildren().get(i)).getFill() == Color.GREEN) {
                ((Rectangle) this.yourBoard.getChildren().get(i)).setFill(Color.GREEN);
            }
        }
    }

    private Client getClient() {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        return mainController.getClient();
    }

    private Rectangle getYourRect(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight) {
        final int initialSize = 15;
        Rectangle yourRect = new Rectangle(initialSize, initialSize, Color.GRAY);
        yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return yourRect;
    }

    private Rectangle getOpponentRectangle(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight,
                                           final int shotIndex) {
        final int initialSize = 15;
        Rectangle opponentRect = new Rectangle(initialSize, initialSize, Color.GRAY);

        opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
            opponentRect.setFill(Color.BLACK);
            logger.info(shotIndex);
            this.shotIndex = shotIndex;
            getClient().sendShot(shotIndex);
        });

        opponentRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        opponentRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return opponentRect;
    }

    private void opponentWithdraw() {
        getClient().closeClient();
        loadWithdrawScreen();
    }

    private void loadWithdrawScreen() {
        try {
            final String opponentWithdrawURL = "/fxml/opponentWithdraw.fxml";
            final FXMLLoader opponentWithdrawLoader = new FXMLLoader(getClass().getResource(opponentWithdrawURL));
            final Parent opponentWithdraw = opponentWithdrawLoader.load();
            final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();
            final int sceneHeight = 400;
            final int sceneWidth = 600;
            final double margin = 0.0;

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setHeight(sceneHeight);
            stage.setWidth(sceneWidth);

            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(opponentWithdraw);

            AnchorPane.setTopAnchor(opponentWithdraw, margin);
            AnchorPane.setBottomAnchor(opponentWithdraw, margin);
            AnchorPane.setLeftAnchor(opponentWithdraw, margin);
            AnchorPane.setRightAnchor(opponentWithdraw, margin);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setOpponentShot(int shotIndex) {
        int column = shotIndex / BOARD_SIZE;
        int row = shotIndex - (column * BOARD_SIZE);
        int newShotIndex = row * BOARD_SIZE + column;
        logger.info("new shotIndex: " + newShotIndex);
        Rectangle rec = (Rectangle) (yourBoard.getChildren().get(newShotIndex + 1));
        if(rec.getFill()==Color.GREEN) {
            rec.setFill(Color.RED);
        } else {
            rec.setFill(Color.BLACK);
        }
    }

    private void changeTurn() {
        final double opacity = 0.4;
        opponentBoard.setDisable(true);
        opponentBoard.setOpacity(opacity);
    }

    private void setHit() {
        int column = shotIndex / BOARD_SIZE;
        int row = shotIndex - (column * BOARD_SIZE);
        int newShotIndex = row * BOARD_SIZE + column;
        Rectangle rec = (Rectangle) (opponentBoard.getChildren().get(newShotIndex + 1));
        rec.setFill(Color.RED);
    }

    private void setMyTurn() {
        logger.info("set my turn game controller");
        final double noOpacity = 1.0;
        opponentBoard.setDisable(false);
        opponentBoard.setOpacity(noOpacity);
    }

    private void setWin() {
        getClient().closeClient();
        eventButton.removeEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentConnectedEvent -> opponentWithdraw());
        winLabel.setStyle("-fx-color: green");
        winLabel.setText("YOU WIN!");
        final double opacity = 0.4;
        yourBoard.setDisable(true);
        yourBoard.setOpacity(opacity);
        opponentBoard.setDisable(true);
        opponentBoard.setOpacity(opacity);
    }

    private void setLose() {
        getClient().closeClient();
        eventButton.removeEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentConnectedEvent -> opponentWithdraw());
        winLabel.setStyle("-fx-color: green");
        winLabel.setText("YOU LOSE!");
        final double opacity = 0.4;
        yourBoard.setDisable(true);
        yourBoard.setOpacity(opacity);
        opponentBoard.setDisable(true);
        opponentBoard.setOpacity(opacity);
    }
}
