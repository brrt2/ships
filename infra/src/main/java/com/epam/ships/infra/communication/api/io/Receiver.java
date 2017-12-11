package com.epam.ships.infra.communication.api.io;


import com.epam.ships.infra.communication.api.Message;

import java.io.IOException;

/**
 * @author Piotr, Magda, Sandor
 * @since 2017-12-07
 * @see Message
 *
 * An implementing class receives an instance of
 * a class implementing Message interface.
 */
public interface Receiver {
    Message receive() throws IOException;
}
