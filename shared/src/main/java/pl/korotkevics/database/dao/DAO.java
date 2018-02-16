package pl.korotkevics.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DAO {

  SessionFactory sessionFactory;

  public DAO() {
    this.sessionFactory = SessionFactoryHolder.provide();
  }

  public void launchPersistence() {

    Transcript transcript = new Transcript();
    Transcript transcript2 = new Transcript();
    transcript.setMessage("testName");
    transcript2.setMessage("testName2");

    Session session= sessionFactory.openSession();
    Transaction transaction = session.getTransaction();
    transaction.begin();
    session.save(transcript);
    session.save(transcript2);
    transaction.commit();
    session.close();
  }


  public void addTranscriptItem(String s) {

    Transcript transcript = new Transcript(s);
    Session session= sessionFactory.openSession();
    Transaction transaction = session.getTransaction();
    transaction.begin();
    session.save(transcript);
    transaction.commit();
    session.close();

  }




}
