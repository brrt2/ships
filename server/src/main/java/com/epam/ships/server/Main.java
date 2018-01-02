package com.epam.ships.server;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;

/**
 * Main class of server application.
 *
 * @author Piotr Czyż
 * @since 2017-12-09
 */
public class Main {
  private static final Target logger = new SharedLogger(Main.class);

  /**
   * Main server method. It creates communication bus and starts the game.
   */
  public static void main(String[] args) throws IOException {

    CommunicationBus communicationBus = new CommunicationBus();
    boolean shouldRun = true;
    while (shouldRun) {
      communicationBus.start();
      final Game game = new Game(communicationBus);
      game.play();
      try {
        Thread.sleep(300);
      } catch (final InterruptedException e) {
        logger.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
      communicationBus.stop();
    }
  }
}
