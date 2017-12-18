package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.io.IOException;

/**
 * @author Magda
 * @since 2017-12-14
 */

public class MainController {

    private static final Target logger = new SharedLogger(Client.class);

    @FXML
    private Pane mainPane;

    @Getter
    private Client client;

    @FXML
    public void initialize(final Client client) {
        final String connectWindowURL = "/fxml/connectWindow.fxml";
        this.client = client;

        try {
            final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowURL));
            final Parent connect = connectLoader.load();

            mainPane.getChildren().clear();
            mainPane.getChildren().add(connect);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void loadWithdrawScreen() {
        try {
            final String serverNotRespondingURL = "/fxml/opponentWithdraw.fxml";
            final FXMLLoader notResponseLoader = new FXMLLoader(getClass().getResource(serverNotRespondingURL));
            final Parent notResponse = notResponseLoader.load();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(notResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
