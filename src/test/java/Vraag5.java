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
public class Vraag5 {
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
	public void refreshTest() {
		/*
		In de vorige opdracht verwijzen de objecten account en found naar dezelfde rij in de database.
		Pas een van de objecten aan, persisteer naar de database.
		Refresh vervolgens het andere object om de veranderde state uit de database te halen.
		Test met asserties dat dit gelukt is.
		 */

		Long expectedBalance = 400L;
		Account account = new Account(114L);
		em.getTransaction().begin();
		em.persist(account);
		account.setBalance(expectedBalance);
		em.getTransaction().commit();
		Long acId = account.getId();

		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		Account found = em2.find(Account.class, acId);

		em2.persist(found);
		found.setBalance(451L);
		em2.getTransaction().commit();

		EntityManager em3 = emf.createEntityManager();
		em3.getTransaction().begin();
		account = em3.find(Account.class, acId);

		assertEquals(account.getBalance(), found.getBalance());

//		2.	Welke SQL statements worden gegenereerd?
//      INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?);
//		select lastval();
//		SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?);
//		UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)
//		UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)

// 		3.	Wat is het eindresultaat in de database?
//      1 Account in de database met account nummer 114, balance 451 & threshold 0.

// 		4.	Verklaring van bovenstaande drie observaties.
//		Het nieuwe object wordt uit de database gehaald en heeft dus dezelfde waarde als het andere gepersisteerde object, dus slaagt de assert
	}
}
