package pl.korotkevics.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DAO {

  public void launchPersistence() {

    Transcript transcript = new Transcript();
    Transcript transcript2 = new Transcript();
    transcript.setName("testName");
    transcript2.setName("testName2");
    Configuration  configuration = new Configuration();
    configuration.addAnnotatedClass(Transcript.class);
    configuration.configure();
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
        applySettings(configuration.getProperties());
    SessionFactory factory = configuration.buildSessionFactory(builder.build());

    Session session= factory.openSession();

    Transaction transaction = session.getTransaction();
    transaction.begin();
    session.save(transcript);
    session.save(transcript2);
    transaction.commit();
    session.close();

  }

}
