package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.WinEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to win the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class WinTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new WinEvent()));
  }
  
  @Override
  public String provideDescription() {
    return "Victory!! Yeah";
  }
}
