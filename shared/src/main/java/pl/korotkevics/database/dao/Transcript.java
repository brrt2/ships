package pl.korotkevics.database.dao;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Entity
public class Transcript {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long playerId;

  private String name;


  public void setName(String name) {
    this.name = name;
  }
}
