package pl.korotkevics.ships.shared.infra.communication.api.message;

/**
 * It represents a message header defining
 *      a message content (main subject/what a
 *      message has inside).
 *
 * @author Sandor Korotkevics
 * @since 2017-12-22
 */

public enum Header {
  DEFAULT,
  OPPONENT_CONNECTED,
  CONNECTION,
  YOUR_TURN,
  MISS,
  HIT,
  SHIP_DESTROYED,
  LOSE,
  WIN,
  SHOT,
  MANUAL_PLACEMENT,
  CONFIRMATION, RANDOM_PLACEMENT
}
