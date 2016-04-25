import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static junit.framework.TestCase.assertSame;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag7 {
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
	public void findAndClearTest() {
		Account acc1 = new Account(77L);
		em.getTransaction().begin();
		em.persist(acc1);
		em.getTransaction().commit();
//Database bevat nu een account.

// scenario 1
		Account accF1;
		Account accF2;
		accF1 = em.find(Account.class, acc1.getId());
		accF2 = em.find(Account.class, acc1.getId());
		assertSame(accF1, accF2);

// scenario 2
		accF1 = em.find(Account.class, acc1.getId());
		em.clear();
		accF2 = em.find(Account.class, acc1.getId());
		assertSame(accF1, accF2);
//TODO verklaar verschil tussen beide scenario's
	}
}
