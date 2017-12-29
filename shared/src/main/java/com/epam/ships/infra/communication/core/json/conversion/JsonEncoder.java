package com.epam.ships.infra.communication.core.json.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.message.BaseMessage;

/**
 * It converts a BaseMessage instance into a JsonElement.
 * @author Sandor Korotkevics
 * @see Encoder
 * @see Message
 * @since 2017-12-10
 */
public class JsonEncoder implements Encoder<JsonElement> {

  /**
   * It converts an instance of a class implementing
   * BaseMessage interface into a JsonElement instance.
   *
   * @param message an instance of a class implementing
   *                BaseMessage interface.
   * @return a result of conversion of an instance of a class implementing
   *     BaseMessage interface into a JsonElement instance.
   */
  @Override
  public JsonElement encode(Message message) {
    Gson gson = new GsonBuilder()
        .enableComplexMapKeySerialization()
        .create();
    return gson.toJsonTree(message, BaseMessage.class);
  }

}
