package pl.korotkevics.ships.server;

import pl.korotkevics.database.dao.DAO;
import pl.korotkevics.ships.server.communication.AppServer;
import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.game.Game;
import pl.korotkevics.ships.server.game.gamestates.GameState;
import pl.korotkevics.ships.server.game.gamestates.connection.WaitingForPlayersState;

import java.io.IOException;

/**
 * Main class of server application.
 *
 * @author Piotr Czyż
 * @since 2017-12-09
 */
public class SeverMain {

  private static final int SERVER_PORT = 8189;
  /**
   * Main server method. It creates communication bus and starts the game.
   *
   * @param args is a conventional main method parameter.
   * @throws IOException can be thrown due to establishing a socket connection.
   */
  public static void main(String[] args) throws IOException {
    launchPersistence();
    AppServer appServer = new AppServer(SERVER_PORT);
    int gameId = 1;
    /*
    Sonar reports an infinitive loop as a critical bug, and therefore
    we could not think of a better workaround than presenting it as a
    an effective infinitive loop (with true extracted to a flag).
     */
    boolean shouldRun = true;
    while (shouldRun) {
      CommunicationBus communicationBus = new CommunicationBus(appServer.connectClients());
      final int finalGameId = gameId;
      new Thread(() -> {
        Thread.currentThread().setName("GAME: " + finalGameId);
        GameState initialState = new WaitingForPlayersState(communicationBus);
        new Game(initialState).play();
      }).start();
      gameId++;
    }
  }

  static void launchPersistence() {
    DAO DAO = new DAO();
    DAO.launchPersistence();
  }


}
