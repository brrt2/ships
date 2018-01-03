package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;
import com.epam.ships.server.WrappedClient;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@Test
public class DamageDestructedNotifierTest {

  public void shouldNotifyAboutDamage() {
    //given
    MessageSender messageSender = mock(MessageSender.class);
    TurnManager turnManager = mock(TurnManager.class);
    WrappedClient player = mock(WrappedClient.class);
    WrappedClient opponent = mock(WrappedClient.class);
    when(turnManager.getCurrentPlayer()).thenReturn(player);
    when(turnManager.getOtherPlayer()).thenReturn(opponent);
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.SHIP_DESTRUCTED)
        .build();

    //when
    new DamageDestructedNotifier(messageSender, turnManager).notify(message);

    //then
    verify(messageSender, times(1)).send(player, Header.SHIP_DESTRUCTED);
    verify(messageSender, times(1)).send(opponent, message);
  }

}