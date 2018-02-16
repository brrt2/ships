package pl.korotkevics.database.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryHolder {

  private static SessionFactory sessionFactory;

  private SessionFactoryHolder() { }


  static SessionFactory provide() {

    if(sessionFactory == null) {

      Configuration configuration = new Configuration();
      configuration.addAnnotatedClass(Transcript.class);
      configuration.configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
          applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
    }else {
      return sessionFactory;
    }

  }



}
