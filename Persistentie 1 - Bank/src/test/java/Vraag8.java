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
import static org.junit.Assert.assertNull;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag8 {
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
	public void removeTest() {
		Account acc1 = new Account(88L);
		em.getTransaction().begin();
		em.persist(acc1);
		em.getTransaction().commit();
		Long id = acc1.getId();
		//Database bevat nu een account.

		em.remove(acc1);
		assertEquals(id, acc1.getId());
		Account accFound = em.find(Account.class, id);
		assertNull(accFound);
//		De eerste assert is een assert waarbij we een in memory versie van Account veglijken met de id die we hebben verkregen voordat we de account hebben weg gegeooid.
//		de tweede assert controleert dat het object ook echt verwijderd is uit de database.

//		2.	Welke SQL statements worden gegenereerd?
//      INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?);
//		select lastval();

// 		3.	Wat is het eindresultaat in de database?
//      1 Account in de database met account nummer 88, balance 0 & threshold 0.

// 		4.	Verklaring van bovenstaande drie observaties.
//		De account is weliswaar verwijderd uit de EntityManager in de database blijk deze echter nog wel bestaan,
//		het object lijkt dus verwijderd te zijn maar dit is in werkelijkheid niet zo.
	}
}
