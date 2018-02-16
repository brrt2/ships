package pl.korotkevics.ships.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import pl.korotkevics.ships.client.validators.PortValidator;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Connect window controller.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-14
 */
public class ConnectWindowController implements Initializable {

  private static final Target logger = new SharedLogger(Client.class);

  private Client client;

  @FXML
  private GridPane gridPane;

  @FXML
  private ImageView imCannon;

  @FXML
  private VBox vbWheel;

  @FXML
  private TextField textFieldServerAddress;

  @FXML
  private TextField textFieldServerPort;

  @FXML
  private AnchorPane mainAnchorPane;

  @FXML
  private Label labelInvalidPort;

  @FXML
  private TextField textFieldPlayerName;

  @FXML
  private Button eventButton;

  private PortValidator portValidator;

  private ResourceBundle resourceBundle;


  @FXML
  private void onConnectPressed() {
    try {
      initializeClient();
    } catch (IllegalStateException e) {
      logger.error(e.getMessage());
      return;
    }



    final String serverAddress = textFieldServerAddress.getText();
    logger.info("server address: " + serverAddress);

    int port;
    try {
      port = portValidator.asInt(textFieldServerPort.getText());
    } catch (IllegalArgumentException e) {
      labelInvalidPort.setText(this.resourceBundle.getString("invalidPort"));
      return;
    }
    logger.info("server port: " + port);

    setPlayerName(textFieldPlayerName.getText());
    
    showLoadingWheel();
    final boolean isConnected = client.connect(serverAddress, port, new Socket());

    if (!isConnected) {
      loadServerNotResponseView();
    } else {
      Thread clientThread = new Thread(client);
      clientThread.start();
    }
  }

  @FXML
  private void onTranscriptPressed() {

    TextInputDialog dialog = new TextInputDialog("firstGame");
    dialog.setTitle("Transcript ");
    dialog.setHeaderText("Transcript selection");
    dialog.setContentText("Please enter the date of the game you would like to retrieve ");

// Traditional way to get the response value.
    Optional<String> result = dialog.showAndWait();
    
//    if (result.isPresent()){
//      System.out.println("Your name: " + result.get());
//    }

// The Java 8 way to get the response value (with lambda expression).
    result.ifPresent(name -> System.out.println("Your name: " + name));
  }


  private void initializeClient() {
    final MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
    this.client = mainController.getClient();
    if (client == null) {
      throw new IllegalStateException("client is not initialized!");
    }
    this.client.setEventTrigger(eventButton);
    mainController.disableLocalizationButtons();
  }

  private void setPlayerName(String playerName) {
    final MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
    mainController.setPlayerName(playerName);
  }

  private void showLoadingWheel() {
    final double opacity = 0.4;

    gridPane.setDisable(true);
    gridPane.setOpacity(opacity);
    imCannon.setOpacity(opacity);
    vbWheel.setVisible(true);
  }

  private void loadServerNotResponseView() {
    try {
      final String serverNotRespondingUrl = "/fxml/serverNotResponding.fxml";
      final FXMLLoader notResponseLoader = new FXMLLoader(getClass()
          .getResource(serverNotRespondingUrl));
      notResponseLoader.setResources(this.resourceBundle);
      final Parent notResponse = notResponseLoader.load();
      final Pane mainPane = (Pane) mainAnchorPane.getParent();
      mainPane.getChildren().clear();
      mainPane.getChildren().setAll(notResponse);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  @Override
  public void initialize(final URL location, final ResourceBundle resourceBundle) {
    this.resourceBundle = resourceBundle;

    textFieldServerPort.textProperty().addListener((observableValue, s1, t1) -> {
      if (!labelInvalidPort.getText().isEmpty()) {
        labelInvalidPort.setText("");
      }
    });

    portValidator = new PortValidator();
    eventButton.addEventHandler(OpponentConnectedEvent.OPPONENT_CONNECTED, event ->
        loadFleetPlacementWindow());

    final String defaultHost = "127.0.0.1";
    final String defaultPort = "8189";

    textFieldServerAddress.setText(defaultHost);
    textFieldServerPort.setText(defaultPort);

  }

  private void loadFleetPlacementWindow() {
    try {
      final String gameWindowUrl = "/fxml/fleetPlacement.fxml";
      final FXMLLoader gameWindowLoader = new FXMLLoader(getClass().getResource(gameWindowUrl));
      gameWindowLoader.setResources(this.resourceBundle);
      final Parent gameWindow = gameWindowLoader.load();
      final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

      Stage stage = (Stage) mainPane.getScene().getWindow();
      final int shrinkSize = 300;
      stage.setMinHeight(screenBounds.getHeight() - shrinkSize);
      stage.setMinWidth(screenBounds.getWidth() - shrinkSize);

      mainPane.getChildren().clear();
      mainPane.getChildren().setAll(gameWindow);

      FleetPlacementController fleetPlacementController = gameWindowLoader.getController();
      fleetPlacementController.initializeClient();

      final double margin = 0.0;
      AnchorPane.setTopAnchor(gameWindow, margin);
      AnchorPane.setBottomAnchor(gameWindow, margin);
      AnchorPane.setLeftAnchor(gameWindow, margin);
      AnchorPane.setRightAnchor(gameWindow, margin);

    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
