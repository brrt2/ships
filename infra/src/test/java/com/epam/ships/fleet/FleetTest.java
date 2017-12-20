package com.epam.ships.fleet;

import com.epam.ships.infra.communication.api.Attachable;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.json.conversion.JSONDecoder;
import com.epam.ships.infra.communication.core.json.conversion.JSONEncoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class FleetTest {
    /*@Test
    public void itShouldCreateNonEmptyFleetWhenGivenWithShips() {
        //given
        Ship firstShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex(4), Mast.ofIndex(1));
        //when
        Fleet fleet = Fleet.ofShips(firstShip, secondShip);
        //then
        assertFalse(fleet.isEmpty());
    }
    */
    @Test
    public void temp() {
        Ship ship = Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"));
        Attachable fleetSent = Fleet.ofShips(ship);
        
        Message messageSent = new MessageBuilder().withAttachment(fleetSent).build();
        Encoder<JSONObject> baseEncoder = new JSONEncoder();
        JSONObject jsonObject = baseEncoder.encode(messageSent);
        System.out.println(jsonObject);
        
        Decoder<JSONObject> baseDecoder = new JSONDecoder();
        Message messageReceived = baseDecoder.decode(jsonObject);
        
        Attachable attachable = messageReceived.getAttachment();
        Mast mastToFind = Mast.ofIndex("3");
        System.out.println(((Fleet) attachable).isEmpty());
    }
}
