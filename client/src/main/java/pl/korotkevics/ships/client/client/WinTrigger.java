package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.WinEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Trigger event reacting to win the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class WinTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new WinEvent()));
  }
}
