import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag2 {
	EntityManagerFactory emf;
	EntityManager em;


	@Before
	public void setup() {
		emf = Persistence.createEntityManagerFactory("bankPU");
		em = emf.createEntityManager();

		DatabaseCleaner databaseCleaner = new DatabaseCleaner(em);
		try {
			databaseCleaner.clean();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			em = emf.createEntityManager();
		}
	}

	@After
	public void after() {

	}

	/*
    Voor elke test moet je in ieder geval de volgende vragen beantwoorden:
        Wat is de waarde van asserties en printstatements?
            Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        Welke SQL statements worden gegenereerd?
        Wat is het eindresultaat in de database?
        Verklaring van bovenstaande drie observaties.
    De antwoorden op de vragen kun je als commentaar bij de testen vastleggen.
     */
	@Test
	public void rollbackTest() {
		Account account = new Account(111L);
		em.getTransaction().begin();
		em.persist(account);
		//Het account is nog niet gecommit, dus er is nog geen ID
		assertNull(account.getId());
		em.getTransaction().rollback();

		AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
		//Omdat je wilt weten of de tabel geen records heeft, haal ik alles op en kijk of die lijst leeg is
		assertTrue(accountDAOJPA.findAll().isEmpty());

		//2.	Welke SQL statements worden gegenereerd?
		//      Er wordt een rollback gedaan.
		//		SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT : Om alle Accounts op te halen van de database

		//3.	Wat is het eindresultaat in de database?
		//      Account is helemaal leeg

		//4.	Verklaring van bovenstaande drie observaties.
		//      Aangezien hier rollback wordt gedaan, worden alle items in de transactie weggegooid.
		//		Er wordt niks gecommit dus daarom is account ook gewoon helemaal leeg.


	}
}
