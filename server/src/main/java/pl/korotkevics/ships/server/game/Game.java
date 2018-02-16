package pl.korotkevics.ships.server.game;

import pl.korotkevics.ships.server.game.gamestates.GameState;

/**
 * Represents Transcript with states.
 *
 * @author Piotr Czyż
 * @since 2017-12-13
 */
public class Game {
  private GameState currentState;

  public Game(final GameState gameState) {
    currentState = gameState;
  }

  /**
   * Executes the game.
   */
  public void play() {
    boolean shouldBeContinued = true;
    GameState nextState;
    while (shouldBeContinued) {
      nextState = currentState.process();
      shouldBeContinued = currentState.shouldBeContinued();
      currentState = nextState;
    }
  }

}
