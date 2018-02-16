package pl.korotkevics.ships.server.game.gamestates.endgame;

import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.server.game.gamestates.GameState;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Represents game state in which game has a winner.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class GameEndWithWinState implements GameState {
  private final MessageSender messageSender;
  private final Target logger = new SharedLogger(GameEndWithWinState.class);
  private CommunicationBus communicationBus;
  private TurnManager turnManager;

  /**
   * It creates state in which we have a winner.
   *
   * @param communicationBus client server communication bus
   * @param turnManager      changes players' turns
   */
  public GameEndWithWinState(final CommunicationBus communicationBus,
                             final TurnManager turnManager) {
    this.communicationBus = communicationBus;
    this.turnManager = turnManager;
    messageSender = new MessageSender(communicationBus, logger);
  }

  /**
   * Process end of game with the winner, and close communication bus.
   *
   * @return GameState
   */
  @Override
  public GameState process() {
    handleEndOfGame();
    communicationBus.stop();
    logger.info("Transcript ended");
    return this;
  }

  /**
   * Set that game should not by continued since we have a winner.
   *
   * @return boolean
   */
  @Override
  public boolean shouldBeContinued() {
    return false;
  }

  private void handleEndOfGame() {
    this.messageSender.send(this.turnManager.getCurrentPlayer(), Header.WIN);
    this.messageSender.send(this.turnManager.getOtherPlayer(), Header.LOSE);
    this.rest();
  }

  private void rest() {
    final int restTime = 5000;
    try {
      Thread.sleep(restTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.error(e.getMessage());
    }
  }
}
