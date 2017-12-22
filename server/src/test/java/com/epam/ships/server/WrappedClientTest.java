package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

@Test
public class WrappedClientTest {
    private static final String ENCODING = "UTF-8";

    public void wrappedClientShouldBeAbleToSendMessageToServer() throws IOException{
        //given
        int port = 9090;
        AppServer appServer = new AppServer(port);
        sendClient(port);
        dumpClient(port);
        //when
        appServer.connectClients();
        Scanner scanner = new Scanner(appServer.getClientSockets().get(0).getInputStream());
        String receivedMessage = scanner.nextLine();
        String expectedMessage = "{\"header\":\"\",\"status\":\"\",\"author\":\"testMessage\",\"statement\":\"\"}";
        //then
        Assert.assertEquals(receivedMessage, expectedMessage);
    }

    private void dumpClient(int port){
        new Thread(() -> {
            try {
                Thread.sleep(20);
                new WrappedClient(new Socket("127.0.0.1", port));
            } catch (final InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendClient(int testPort) {
        new Thread(() -> {
            try {
                Thread.sleep(10);
                WrappedClient wrappedClient = new WrappedClient(new Socket("127.0.0.1", testPort));
                Thread.sleep(10);
                wrappedClient.send(new MessageBuilder().withAuthor("testMessage").build());
            } catch (final InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}