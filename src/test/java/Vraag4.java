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
//		account.getBalance zou de waarde van 400 moeten hebben. dit is namelijk de waarde die we er aan hebben gegeven op regel 58
		Long acId = account.getId();

		account = null;
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		Account found = em2.find(Account.class, acId);
//		account.getBalance() zou dezelfde waarde moeten hebben als we op regel 58 hebben geset omdat we de account hebben opgeslagen en vervolgens uitlezen wat de opgeslagen waarde is.
		assertEquals(expectedBalance, found.getBalance());

		//2.	Welke SQL statements worden gegenereerd?
		//      INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?);
//		select lastval();
//		SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?);

		//3.	Wat is het eindresultaat in de database?
		//      1 Account in de database met account nummer 114, balance 400 & threshold 0.

		//4.	Verklaring van bovenstaande drie observaties.
		//      Er wordt een account gemaakt en deze wordt vervolgens opgeslagen, deze account komt dus in de database. Vervolgens wordt de lokale variabele veranderd in de database is deze echter nog hetzelfde met als gevolg dat zodra we uit de database de waarde opvragen deze de orginele waarde is
	}
}
