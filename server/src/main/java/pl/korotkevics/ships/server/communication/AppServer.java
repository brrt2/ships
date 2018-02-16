package pl.korotkevics.ships.server.communication;

import pl.korotkevics.database.dao.DAO;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * It starts a server socket and accepts two client sockets.
 *
 * @author Piotr Czyż, Magdalena Aarsman, Sandor Korotkevics
 * @since 2017-12-09
 */
public class AppServer {

  private DAO dao;

  private final Target logger = new SharedLogger(AppServer.class);

  private final ServerSocket serverSocket;

  public AppServer(int port) throws IOException {
    logger.info("Server is up and waiting for clients..");
    this.serverSocket = new ServerSocket(port);
    dao = new DAO();
  }

  /**
   * It connects two clients.
   */
  public List<Socket> connectClients() {
    List<Socket> clients = new ArrayList<>();
    logger.info("Waiting for the 1st client.. ");
    acceptClient(clients);
    logger.info("1st client connected... ");

    dao.addTranscriptItem("First player connected");
    logger.info("waiting for the 2nd client..");
    acceptClient(clients);
    logger.info("2nd client connected... ");
    dao.addTranscriptItem("Second player connected");
    logger.info("Clients are connected");
    return clients;
  }

  /**
   * It accepts a client socket
   * while storing it in a list.
   */
  private void acceptClient(List<Socket> clients) {
    try {
      Socket client = serverSocket.accept();
      clients.add(client);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

}
