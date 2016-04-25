import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag6 {
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
	public void MergeTest() {

		//Merge is een van de lastigere methoden uit JPA api. Het is belangrijk dat je deze opgave daarom zorgvuldig uitvoert.

		Account acc = new Account(1L);
		Account acc2 = new Account(2L);
		Account acc9 = new Account(9L);

// scenario 1
		Long balance1 = 100L;
		em.getTransaction().begin();
		em.persist(acc);
		acc.setBalance(balance1);
		em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


// scenario 2
		Long balance2a = 211L;
		acc = new Account(2L);
		em.getTransaction().begin();
		acc9 = em.merge(acc);
		acc.setBalance(balance2a);
		acc9.setBalance(balance2a+balance2a);
		em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
// HINT: gebruik acccountDAO.findByAccountNr


// scenario 3
		Long balance3b = 322L;
		Long balance3c = 333L;
		acc = new Account(3L);
		em.getTransaction().begin();
		Account acc3 = em.merge(acc);
		assertTrue(em.contains(acc)); // verklaar
		assertTrue(em.contains(acc3)); // verklaar
		assertEquals(acc,acc3);  //verklaar
		acc3.setBalance(balance3b);
		acc.setBalance(balance3c);
		em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


// scenario 4
		Account account = new Account(114L);
		account.setBalance(450L);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

		Account account2 = new Account(114L);
		Account tweedeAccountObject = account2;
		tweedeAccountObject.setBalance(650l);
		assertEquals((Long)650L,account2.getBalance());  //verklaar
		account2.setId(account.getId());
		em.getTransaction().begin();
		account2 = em.merge(account2);
		assertSame(account,account2);  //verklaar
		assertTrue(em.contains(account2));  //verklaar
		assertFalse(em.contains(tweedeAccountObject));  //verklaar
		tweedeAccountObject.setBalance(850l);
		assertEquals((Long)650L,account.getBalance());  //verklaar
		assertEquals((Long)650L,account2.getBalance());  //verklaar
		em.getTransaction().commit();
		em.close();

	}
}
