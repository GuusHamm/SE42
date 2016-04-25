import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag2 {
	/*
    Voor elke test moet je in ieder geval de volgende vragen beantwoorden:
    Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        Welke SQL statements worden gegenereerd?
        Wat is het eindresultaat in de database?
        Verklaring van bovenstaande drie observaties.
    De antwoorden op de vragen kun je als commentaar bij de testen vastleggen.
     */

	EntityManagerFactory emf;
	EntityManager em;


	@Before
	public void setup() {
		emf = Persistence.createEntityManagerFactory("BankPU");
		em = emf.createEntityManager();
	}

	@After
	public void after() {

	}
}
