package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.core.json.conversion.JSONDecoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Piotr, Magda, Sandor
 * @see Receiver
 * @see JSONReceiver
 * @see Decoder
 * @see JSONDecoder
 * @see Message
 * <p>
 * It receives a message from an input stream.
 * @since 2017-12-07
 */

public class JSONReceiver implements Receiver {

    private final InputStream inputStream;

    /**
     * @param inputStream it will read from this stream.
     */
    public JSONReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * It reads from input stream, coverts it first
     * to a JSONObject and the to a Message.
     * <p>
     * If there is nothing to read from an input stream,
     * it returns an corresponding Message as well.
     *
     * @return Message input interpreted as a message.
     */
    @Override
    public Message receive() {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        if (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        } else {
            return new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("END")
                    .withAuthor("Auto")
                    .withStatement("End of a message")
                    .build();
        }
        Decoder<JSONObject> jsonDecoder = new JSONDecoder();
        return jsonDecoder.decode(new JSONObject(stringBuilder.toString()));
    }
}
