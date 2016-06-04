import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.*;

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
		AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
// scenario 1
		Long balance1 = 100L;
		em.getTransaction().begin();
		em.persist(acc);
		acc.setBalance(balance1);
		//Na het setten van de balance staat deze ook goed in het object
		assertEquals(acc.getBalance(), balance1);
		em.getTransaction().commit();
		//Kijken of het account in de database ook de goede balance heeft.
		Account accountFromDatabase = accountDAOJPA.find(acc.getId());
		assertEquals(accountFromDatabase.getBalance(), balance1);

		//2.	Welke SQL statements worden gegenereerd?
		//		INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)

		//3.	Wat is het eindresultaat in de database?
		//		Het account met de juiste balance

		//4.	Verklaring van bovenstaande drie observaties.
		//		Niet veel over te zeggen. het wordt juist in de database gezet.


// scenario 2
		Long balance2a = 211L;
		acc = new Account(2L);
		em.getTransaction().begin();
		acc9 = em.merge(acc);
		//Check if acc9 and acc are now the same with Account nr
		assertEquals(acc9.getAccountNr(), acc.getAccountNr());

		acc.setBalance(balance2a);
		acc9.setBalance(balance2a+balance2a);
		//Kijk of de balances kloppen
		assertTrue(acc.getBalance() * 2 == acc9.getBalance());
		em.getTransaction().commit();

		Account accFromDatabase = accountDAOJPA.findByAccountNr(acc9.getAccountNr());
		//Aangezien zowel acc als acc9 accountNr 2 hebben denk ik dat acc9 in de database zal staan.
		assertTrue(accFromDatabase.getBalance() == acc9.getBalance());

		//2.	Welke SQL statements worden gegenereerd?
		//INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
		//SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ACCOUNTNR = ?)

		//3.	Wat is het eindresultaat in de database?
		//		acc9 is in de database gezet.

		//4.	Verklaring van bovenstaande drie observaties.
		//		Na het mergen is het account nummer gelijk. daarna wordt zowel van acc en acc9 het balance aangepast. aan het einde stond alleen acc9 in de database


// scenario 3
		Long balance3b = 322L;
		Long balance3c = 333L;
		acc = new Account(3L);
		em.getTransaction().begin();
		Account acc3 = em.merge(acc);
//		assertTrue(em.contains(acc)); // verklaar: Deze zit niet in de em. aangezien je hier niks mee doet in de transactie
		assertFalse(em.contains(acc));

		assertTrue(em.contains(acc3)); // verklaar: 3 zit in de em aangezien hier net iets is mee gedaan.

//		assertEquals(acc,acc3);  //verklaar: Het zijn 2 verschillende objecten, waardoor dit niet waar is
		assertNotEquals(acc, acc3);

		acc3.setBalance(balance3b);
		acc.setBalance(balance3c);
		em.getTransaction().commit();

		accountFromDatabase = accountDAOJPA.findByAccountNr(acc3.getAccountNr());
		assertNotNull(accountFromDatabase);
		assertEquals(balance3b, accountFromDatabase.getBalance());

		//2.	Welke SQL statements worden gegenereerd?
		//INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
		//SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ACCOUNTNR = ?)

		//3.	Wat is het eindresultaat in de database?
		//		acc3 is naar de database geschreven met zijn laatste waardes.

		//4.	Verklaring van bovenstaande drie observaties.
		//		acc staat niet in de em dus deze zal ook niet gecommit worden. acc3 wel, dus deze gaat naar de database

// scenario 4
		System.out.println("Begin Scenario 4 \n");
		Account account = new Account(114L);
		account.setBalance(450L);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

		Account account2 = new Account(114L);
		Account tweedeAccountObject = account2;
		tweedeAccountObject.setBalance(650l);
		assertEquals((Long)650L,account2.getBalance());  //verklaar : Bij setBalance worden zowel de balance voor tweedeAccountObject en account 2 aangepast
		account2.setId(account.getId());
		em.getTransaction().begin();
		account2 = em.merge(account2);
		assertSame(account,account2);  //verklaar : Je hebt het ID van account 2 naar hetzelfde gezet als account, na de merge heeft hij alle waardes goed gezet
		assertTrue(em.contains(account2));  //verklaar : Account 2 is aangepast in de transactie dus zit deze ook in de EntityManager
		assertFalse(em.contains(tweedeAccountObject));  //verklaar : Deze is niet aangepast in deze sessie dys staat hij er niet in
		tweedeAccountObject.setBalance(850l);
		assertEquals((Long)650L,account.getBalance());  //verklaar tweedeAccountObject heeft niets meer met account of account 2 te maken
		assertEquals((Long)650L,account2.getBalance());  //verklaar tweedeAccountObject heeft niets meer met account of account 2 te maken
		em.getTransaction().commit();
		em.close();

		//2.	Welke SQL statements worden gegenereerd?
		//INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)
		//UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?)

		//3.	Wat is het eindresultaat in de database?
		//		Account 2 staat in de database

		//4.	Verklaring van bovenstaande drie observaties.
		//		Aangezien de balance upgedate is met id, veranderde ze allebei.

	}
}
