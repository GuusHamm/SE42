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
import static org.junit.Assert.assertNotSame;

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
		assertNotSame(accF1, accF2);
//		het verschil tussen beide scenario's is dat in scenerio 2 de Entity Manager wordt leeg gemaakt waardoor alle persistente objecten worden losgelaten, met als gevolg dat accF2 null wordt
//		en null is niet gelijk aan een account object. Dit is het verschil tussen beide scenario's

//		2.	Welke SQL statements worden gegenereerd?
//      INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?);
//		select lastval();
//		SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?);

// 		3.	Wat is het eindresultaat in de database?
//      1 Account in de database met account nummer 77, balance 0 & threshold 0.

// 		4.	Verklaring van bovenstaande drie observaties.
//		Door em.clear(); aan te roepen worden de gepersisteerde objecten losgelaten met als gevolg dat de accF2 geen account kan vinden
//		en dus ook dat de assertSame niet zal werken omdat de twee obejcten niet hetzelfde zijn
	}
}
