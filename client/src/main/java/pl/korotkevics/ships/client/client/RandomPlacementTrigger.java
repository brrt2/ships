package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

public class RandomPlacementTrigger implements EventTrigger {
  @Override
  public void fire(Button button, Message message) {
    Platform.runLater(() ->
        button.fireEvent(new RandomPlacementEvent(message.getFleet())));
  }
}
