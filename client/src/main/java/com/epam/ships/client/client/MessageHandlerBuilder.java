package com.epam.ships.client.client;

import com.epam.ships.shared.infra.communication.api.message.Header;

import java.util.EnumMap;
import java.util.Map;

/**
 * Helper class to build MessageHandler.
 * @author Magdalena Aarsman
 * @since 2018-01-02
 */

public class MessageHandlerBuilder {
  private Map<Header, EventTrigger> triggers;

  /**
   * Set triggers map to be enum map with Header as key.
   * @return MessageHandlerBuilder
   */
  public MessageHandlerBuilder withEnumMap() {
    triggers = new EnumMap<>(Header.class);
    return this;
  }

  /**
   * Set default set of triggers and put it into map.
   * @return MessageHandlerBuilder
   */
  public MessageHandlerBuilder withDefaultSetsOfTriggers() {
    triggers.put(Header.OPPONENT_CONNECTED, new OpponentConnectedTrigger());
    triggers.put(Header.SHOT, new OpponentShotTrigger());
    triggers.put(Header.CONNECTION, new ConnectionEndTrigger());
    triggers.put(Header.YOUR_TURN, new TurnTrigger());
    triggers.put(Header.MISS, new MissShotTrigger());
    triggers.put(Header.HIT, new HitShotTrigger());
    triggers.put(Header.SHIP_DESTRUCTED, new HitShotTrigger());
    triggers.put(Header.WIN, new WinTrigger());
    triggers.put(Header.LOSE, new LoseTrigger());
    return this;
  }

  /**
   * Build MessageHandler.
   * @return MessageHandler
   */
  public MessageHandler build() {
    return new MessageHandler(triggers);
  }
}
