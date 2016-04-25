import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag4 {
	/*
    Voor elke test moet je in ieder geval de volgende vragen beantwoorden:
        Wat is de waarde van asserties en printstatements?
            Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        Welke SQL statements worden gegenereerd?
        Wat is het eindresultaat in de database?
        Verklaring van bovenstaande drie observaties.
    De antwoorden op de vragen kun je als commentaar bij de testen vastleggen.
     */

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

	@Test
	public void changesAfterPersistTest() {
		Long expectedBalance = 400L;
		Account account = new Account(114L);
		em.getTransaction().begin();
		em.persist(account);
		account.setBalance(expectedBalance);
		em.getTransaction().commit();
		assertEquals(expectedBalance, account.getBalance());
//TODO: verklaar de waarde van account.getBalance
		Long acId = account.getId();
		account = null;
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		Account found = em2.find(Account.class, acId);
//TODO: verklaar de waarde van found.getBalance
		assertEquals(expectedBalance, found.getBalance());

	}
}
