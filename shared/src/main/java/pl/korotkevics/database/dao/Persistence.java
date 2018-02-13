package pl.korotkevics.database.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Persistence {

  static final EntityManagerFactory entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory("testPersistence");

  void create( String name) {

    Game game = new Game();

    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    entityManager.persist(message);

    entityManager.getTransaction().commit();

    entityManager.close();

  }



}
