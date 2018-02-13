package pl.korotkevics.ships.client.reporting;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Sandor Korotkevics
 * @since 2018-01-24
 * @see ReportingOption
 *
 * Reports game messages to a configurable target.
 */
public class Reporter {
  
  private static final Target logger = new SharedLogger(Reporter.class);
  
  private static final String PATH_SEPARATOR = ".";
  
  private static final String TARGET = "target";
  
  private static final String OPTIONS = "options";
  
  private static final String DESTINATION = "destination";
  
  private static final String LOGGER_OPTION = "logger";
  
  private static final String SOCKET_OPTION = "socket";
  
  private static final String FILE_OPTION = "file";
  
  private static final String WINDOW_OPTION = "window";
  
  private static final String NAME = "name";
  
  private static final String HOST = "host";
  
  private static final String PORT = "port";
  
  private static final String TITLE = "title";
  
  private static final String HEIGHT = "height";
  
  private static final String WIDTH = "width";
  
  private final Config config;
  
  private Socket socket;
  
  /**
   * Reports a game message to a configurable target.
   * Target is configured with a .conf file.
   *
   * @param message from a game trigger to be reported.
   */
  public void report(final String message) {
    try {
      if (this.getCurrentDestination().equals(ReportingOption.FILE.toString())) {
        this.writeToFile(message);
      } else if (this.getCurrentDestination().equals(ReportingOption.SOCKET.toString())) {
        this.establishConnection();
        this.writeToSocket(message);
      }else if (this.getCurrentDestination().equals(ReportingOption.DATABASE.toString())) {
          this.establishConnection();
          this.writeToDatabase(message);
      } else if (this.getCurrentDestination().equals(ReportingOption.LOGGER.toString())) {
        this.log(message);
      }
    } catch (ConfigException e) {
      logger.error(e.getMessage());
    }
  }
  
  private void log(final String message) {
    logger.report(message, this.getDestinationLoggerName());
  }
  
  private void writeToFile(final String message) {
    try {
      Files.write(Paths.get(this.getDestinationFileName()), StringUtils.defaultIfEmpty(message
                                                                                           +
                                                                                           System.lineSeparator(), StringUtils.EMPTY).getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
  
  private void writeToSocket(final String message) {
    PrintWriter printWriter;
    try {
      printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"), true);
      printWriter.println(message);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  private void writeToDatabase(final String message) {
    PrintWriter printWriter;
    try {
      printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"), true);
      printWriter.println(message);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  private void establishConnection() {
    if (this.socket == null) {
      try {
        final InetAddress inetAddress = InetAddress.getByName(this.getDestinationHost());
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(inetAddress, this.getDestinationPort()));
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
  }
  
  public Reporter(final String fileName) {
    this.config = ConfigFactory.load(fileName);
  }
  
  String getCurrentDestination() {
    return this.config.getString(DESTINATION + PATH_SEPARATOR + TARGET);
  }
  
  String getDestinationFileName() {
    return this.config.getString(this.getOptionsPath() + FILE_OPTION + PATH_SEPARATOR + NAME);
  }
  
  String getDestinationHost() {
    return this.config.getString(this.getOptionsPath() + SOCKET_OPTION + PATH_SEPARATOR + HOST);
  }
  
  int getDestinationPort() {
    return this.config.getInt(this.getOptionsPath() + SOCKET_OPTION + PATH_SEPARATOR + PORT);
  }
  
  String getDestinationLoggerName() {
    return this.config.getString(this.getOptionsPath() + LOGGER_OPTION + PATH_SEPARATOR + NAME);
  }
  
  String getDestinationWindowTitle() {
    return this.config.getString(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + TITLE);
  }
  
  int getDestinationWindowHeight() {
    return this.config.getInt(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + HEIGHT);
  }
  
  int getDestinationWindowWidth() {
    return this.config.getInt(this.getOptionsPath() + WINDOW_OPTION + PATH_SEPARATOR + WIDTH);
  }
  
  private String getOptionsPath() {
    return OPTIONS + PATH_SEPARATOR;
  }
}
