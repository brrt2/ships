package com.epam.ships.communication.core.json;

import com.epam.ships.communication.api.conversion.Encoder;
import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.core.json.conversion.JSONEncoder;
import com.epam.ships.communication.core.message.MessageBuilder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JSONEncoderTest {

    @Test
    public void itEncodesAsExpected() {
        //given
        Encoder<JSONObject> baseEncoder = new JSONEncoder();
        Message message = new MessageBuilder()
                .withAuthor("Sandor")
                .withHeader("Connection")
                .withStatus("OK")
                .withStatement("The first real test in this project:-)")
                .build();
        //when
        JSONObject jsonObject = baseEncoder.encode(message);
        //then
        assertEquals(jsonObject.toString(), "{\"author\":\"Sandor\",\"statement\":\"The first real test in this project:-)\",\"header\":\"Connection\",\"status\":\"OK\"}");
    }
}
