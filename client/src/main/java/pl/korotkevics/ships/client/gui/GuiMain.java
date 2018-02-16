package pl.korotkevics.ships.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.client.MessageHandlerBuilder;
import pl.korotkevics.ships.client.gui.controllers.MainController;

/**
 * Main class of gui to initialize and start gui thread.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class GuiMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    Thread.currentThread().setName("gui - thread");
    final String fxmlMainWindowPath = "/fxml/root.fxml";
    final String windowTitle = "Battleships";
    final boolean clientShouldRun = true;
    final Client client = new Client(new MessageHandlerBuilder().withEnumMap()
        .withDefaultSetsOfTriggers().withReporter().build(), clientShouldRun);
    final int sceneWidth = 800;
    final int sceneHeight = 400;

    FXMLLoader rootLoader = new FXMLLoader(getClass().getResource(fxmlMainWindowPath));
    Parent root = rootLoader.load();
    MainController mainController = rootLoader.getController();
    mainController.initialize(client);

    primaryStage.setTitle(windowTitle);
    primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
    primaryStage.setResizable(false);
    primaryStage.show();


    primaryStage.setOnHiding((WindowEvent event) -> {
      client.closeClient();
      Platform.exit();
    });
  }




}
