package pl.korotkevics.database.dao;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transcript {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long playerId;

  private String message;


  public Transcript() {
  }

  public Transcript(String message) {
    this.message = message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
