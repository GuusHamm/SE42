import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nekkyou on 25-4-2016.
 */
public class Vraag1
{
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
    public void PersistAndCommitTest() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);

        //Voordat de transactie gecommit is, wordt er nog geen id aangemaakt. Wat wel aangemaakt is in een account nummer.
        //De id is puur voor de database.
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //Nadat em.getTransaction().commit() is aangeroepen zet hij de data van de transactie in de database en maakt er ook een id voor, Daarom is id niet meer null
        assertTrue(account.getId() > 0L);

        //2.	Welke SQL statements worden gegenereerd?
        //      Er wordt een create table account gemaakt en een insert in het account
        //      INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?)

        //3.	Wat is het eindresultaat in de database?
        //      1 Account in de database met id : 7, account nummer 111 en balance en threshold 0.

        //4.	Verklaring van bovenstaande drie observaties.
        //      Het ID wordt pas aangemaakt bij het committen, waardoor hij bij de eerste assertie null was, en bij de 2de niet meer.
        //      Na de commit is het account ook in de database gezet met de waardes.
    }

}
