package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Server not responding window.
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */

public class ServerNotRespondingController {

  private static final Target logger = new SharedLogger(Client.class);

  @FXML
  private AnchorPane mainAnchorPane;

  @FXML
  private void onTryAgainPressed() {
    try {
      final String connectWindowUrl = "/fxml/connectWindow.fxml";
      final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowUrl));
      final Parent connectWindow = connectLoader.load();
      final Pane mainPane = (Pane) mainAnchorPane.getParent();
      mainPane.getChildren().clear();
      mainPane.getChildren().setAll(connectWindow);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
