package webclient;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guushamm on 04/06/16.
 */
public class RegistrationTest {
	private static final RegistrationService registrationService = new RegistrationService();
	private static Registration registration;

	@After
	public void tearDown() throws Exception {
		registration = registrationService.getRegistrationPort();
		registration.clean();
	}

	public static User registerUser(String email){
		registration = registrationService.getRegistrationPort();
		return registration.registerUser(email);
	}

	public static User getUser(String email){
		registration = registrationService.getRegistrationPort();
		return registration.getUser(email);
	}

	@Test
	public void registerUser() throws Exception {
		String email1 = "xxx1@yyy";
		String email2 = "xxx2@yyy2";

		User user1 = registerUser(email1);
		User user2 = registerUser(email2);
		User user2bis = registerUser(email2);

		assertTrue(user1.getEmail().equals("xxx1@yyy"));
		assertTrue(user2.getEmail().equals("xxx2@yyy2"));
		assertEquals(user2bis.getId(), user2.getId());
		//geen @ in het adres
		assertNull(registerUser("abc"));
	}

	@Test
	public void getUser() throws Exception {
		User user1 = registerUser("xxx5@yyy5");
		User userGet = getUser("xxx5@yyy5");
		assertSame(userGet, user1);
		assertNull(getUser("aaa4@bb5"));
		registerUser("abc");
		assertNull(getUser("abc"));
	}

}