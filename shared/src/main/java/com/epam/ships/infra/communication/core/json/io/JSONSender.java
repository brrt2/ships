package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.conversion.JSONEncoder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * It sends a message to an output stream.
 * @author Piotr Czyż, Magda Aarsman, Sandor Korotkevics
 * @see Sender
 * @see JSONSender
 * @see Encoder
 * @see JSONEncoder
 * @see Message
 * @since 2017-12-07
 */

public class JSONSender implements Sender {

  private static final String ENCODING = "UTF-8";

  private final Target logger = new SharedLogger(JSONSender.class);

  private final OutputStream outputStream;

  /**
   * @param outputStream it will write to this stream.
   */
  public JSONSender(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  /**
   * It converts a message into a JsonElement,
   * and then sends it to an output stream.
   *
   * @param message a message to send.
   */
  @Override
  public void send(Message message) {
    PrintWriter printWriter = null;
    try {
      printWriter = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING), true);
      Encoder encoder = new JSONEncoder();
      printWriter.println(encoder.encode(message));
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage());
    }
  }
}
