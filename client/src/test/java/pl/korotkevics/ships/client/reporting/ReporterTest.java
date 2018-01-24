package pl.korotkevics.ships.client.reporting;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = "integration")
public class ReporterTest {
  
  private final String REPORTING_TO_FILE_CONFIG = "reportingToFile";
  
  private final String REPORTING_TO_SOCKET_CONFIG = "reportingToSocket";
  
  private final String REPORTING_TO_LOGGER_CONFIG = "reportingToLogger";
  
  private final int PORT = 9999;
  
  private final String LOG_FILE = "../target/test-reporting.log";
  
  public void shouldRecognizeActiveTargetAsFile() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.FILE.toString());
  }
  
  public void shouldRecognizeConfiguredFileName() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationFileName(), LOG_FILE);
  }
  
  public void shouldReportToFile() throws IOException {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when
    reporter.report("Just a casual message");
    //then
    assertEquals(Files.readFile(new File(LOG_FILE)), "Just a casual message" + System
                                                                                   .lineSeparator
                                                                                        ());
  }
  
  public void shouldNotReportToFile() throws IOException {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    this.makeSureLogFileExistsAndIsEmpty();
    //when
    reporter.report("A message which should not be reported int" + "o a file since it is not the " +
                        "" + "" + "" + "" + "active destination.");
    //then
    assertEquals(Files.readFile(new File(LOG_FILE)), StringUtils.EMPTY);
  }
  
  public void shouldRecognizeActiveTargetAsSocket() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.SOCKET.toString());
  }
  
  public void shouldRecognizeHost() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationHost(), "127.0.0.1");
  }
  
  public void shouldRecognizePort() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationPort(), PORT);
  }
  
  public void shouldReportToSocket() {
    //given
    ExternalPrinterThread t = new ExternalPrinterThread(PORT);
    new Thread(() -> {
      t.run();
    }).start();
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when
    reporter.report("Just a casual message");
    //then
    //-> assertion is within the anonymous thread, couldn't do it better,
    //and it is not entirely reliable
  }
  
  public void shouldRecognizeActiveTargetAsLogger() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_LOGGER_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.LOGGER.toString());
  }
  
  public void shouldRecognizeLoggerName() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_LOGGER_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationLoggerName(), "Snarky Reporter");
  }
  
  private void makeSureLogFileExistsAndIsEmpty() throws IOException {
    FileOutputStream writer = new FileOutputStream(LOG_FILE);
    writer.write(("").getBytes());
    writer.close();
  }
}

class ExternalPrinterThread extends Thread {
  
  private final int PORT;
  
  ExternalPrinterThread(final int port) {PORT = port;}
  
  @Override
  public void run() {
  
    try (ServerSocket serverSocket = new ServerSocket(PORT); Socket clientSocket = serverSocket
                                                                                       .accept
                                                                                            ()) {
      Scanner scanner = new Scanner(clientSocket.getInputStream(), "UTF-8");
      StringBuilder stringBuilder = new StringBuilder();
      while (scanner.hasNext()) {
        stringBuilder.append(scanner.nextLine());
      }
      //then
      assertTrue("Just a casual message".equals(stringBuilder.toString()));
      clientSocket.getOutputStream().write(stringBuilder.toString().getBytes());
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}