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
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag3 {


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
	public void FlushTest() {
		Long expected = -100L;
		Account account = new Account(111L);
		account.setId(expected);
		em.getTransaction().begin();
		em.persist(account);
		//TODO: verklaar en pas eventueel aan
		//Hier moest nog een ) achter.
//		assertNotEquals(expected, account.getId()); -- Dit werkte niet aangezien Id en expected hetzelfde waren.
		assertEquals(expected, account.getId());
		em.flush();
		//TODO: verklaar en pas eventueel aan

		//Hier moest ook nog een ) achter.
//		assertEquals(expected, account.getId()); -- ID was hier een echte value
		//Na de flush heeft account een echt ID gekregen
		assertNotEquals(expected, account.getId());
		em.getTransaction().commit();

		//2.	Welke SQL statements worden gegenereerd?
		//		Bij de flush wordt: INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?) uitgevoerd

		//3.	Wat is het eindresultaat in de database?
		//		Het account staat in de database met een goed ID

		//4.	Verklaring van bovenstaande drie observaties.
		//		Eerst wordt het ID geset. deze is dan gelijk tot de flush. Bij de flush wordt hij in de database gezet met een goed id

	}
}
