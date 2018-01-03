package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;
import com.epam.ships.server.gamestates.GameEndWithWalkoverState;
import com.epam.ships.server.gamestates.GameEndWithWinState;
import com.epam.ships.server.gamestates.GameState;

import java.util.List;

public class PlayState implements GameState {
  private CommunicationBus communicationBus;
  MessageReceiver messageReceiver;
  ShotHandler shotHandler;
  private boolean isGameWon;
  private final Target logger = new SharedLogger(PlayState.class);
  private final TurnManager turnManager;

  public PlayState(CommunicationBus communicationBus, List<Fleet> fleets) {
    this.communicationBus = communicationBus;
    this.messageReceiver = new MessageReceiver(communicationBus);
    isGameWon = false;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
    this.shotHandler = new ShotHandler(communicationBus, turnManager, fleets);
  }

  @Override
  public GameState process() {
    sendYourTurnMessage();
    messageReceiver.receive(turnManager.getCurrentPlayer());
    if (messageReceiver.isAShot()) {
      Message shot = messageReceiver.getMessage();

      isGameWon = shotHandler.handle(turnManager.isCurrentPlayerFirstPlayer(), shot);

    } else {
      return new GameEndWithWalkoverState(communicationBus, turnManager);
    }

    if (isGameWon) {
      return new GameEndWithWinState(communicationBus, turnManager);
    }

    rest();
    return this;
  }


  private void sendYourTurnMessage() {
    final Message turn = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.YOUR_TURN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    logger.info("send your turn");
  }

  private void rest() {
    final long restTime = 300;
    try {
      Thread.sleep(restTime);
    } catch (final InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }


}
