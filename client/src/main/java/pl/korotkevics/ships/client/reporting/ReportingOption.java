package pl.korotkevics.ships.client.reporting;

/**
 * @author Sandor Korotkevics
 * @since 2018-01-23
 * @see Reporter
 *
 * Implemented to have type safe reporting options.
 * Used by Reporter.
 */
enum ReportingOption {
  
  FILE,
  SOCKET,
  LOGGER,
  DATABASE;
  
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
