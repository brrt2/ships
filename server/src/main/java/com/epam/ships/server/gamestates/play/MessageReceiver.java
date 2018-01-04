package com.epam.ships.server.gamestates.play;

import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.api.message.Header;
import com.epam.ships.shared.infra.logging.api.Target;
import com.epam.ships.shared.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;

/**
 * Receive message from client.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class MessageReceiver {
  private final Target logger = new SharedLogger(MessageReceiver.class);
  private CommunicationBus communicationBus;
  private Message lastMessage;

  MessageReceiver(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  void receive(final WrappedClient player) {
    lastMessage = communicationBus.receive(player);
    logger.info(lastMessage);
  }

  boolean isAShot() {
    return Header.SHOT.equals(lastMessage.getHeader());
  }

  Message getMessage() {
    return lastMessage;
  }
}
