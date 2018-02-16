package pl.korotkevics.database.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany
  List<Transcript> transcripts;

  
}
