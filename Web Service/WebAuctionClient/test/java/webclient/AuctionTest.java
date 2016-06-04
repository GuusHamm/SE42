package webclient;

import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by guushamm on 04/06/16.
 */
public class AuctionTest {
	private static final AuctionService auctionService = new AuctionService();
	private static final RegistrationService registrationService = new RegistrationService();
	private static Registration registration;
	private static Auction auction;

	@After
	public void tearDown() throws Exception {
		registration = registrationService.getRegistrationPort();
		registration.clean();
	}

	private static User registerUser(String email){
		registration = registrationService.getRegistrationPort();
		return registration.registerUser(email);
	}

	public static Item getItem(Long id){
		auction = auctionService.getAuctionPort();
		return auction.getItem(id);
	}

	public static List<Item> findItemByDescription(String description){
		auction = auctionService.getAuctionPort();
		return auction.findItemByDescription(description);
	}

	public static Bid newBid(Item item, User buyer, Money amount){
		auction = auctionService.getAuctionPort();
		return auction.newBid(item,buyer,amount);
	}

	public static Item offerItem(User seller, Category cat, String description){
		auction = auctionService.getAuctionPort();
		return auction.offerItem(seller,cat,description);
	}

	public static boolean revokeItem(Item item){

		auction = auctionService.getAuctionPort();
		return auction.revokeItem(item);
	}

	public static Money newMoney(long cents, String currency){
		auction = auctionService.getAuctionPort();
		return auction.newMoney(cents,currency);
	}

	public static Category newCategory(String descriptoin){
		auction = auctionService.getAuctionPort();
		return auction.newCategory(descriptoin);
	}

	@Test
	public void getItem() throws Exception {

		String email = "xx2@nl";
		String omsch = "omsch";


		User seller1 = registration.registerUser(email);
		Category cat = newCategory("cat2");
		Item item1 = offerItem(seller1, cat, omsch);
		Item item2 = getItem(item1.getId());
		assertEquals(omsch, item2.getDescription());
		assertEquals(email, item2.getSeller().getEmail());
	}

	@Test
	public void findItemByDescription() throws Exception {
		String email3 = "xx3@nl";
		String omsch = "omsch";
		String email4 = "xx4@nl";
		String omsch2 = "omsch2";

		User seller3 = registerUser(email3);
		User seller4 = registerUser(email4);
		Category cat = newCategory("cat3");
		Item item1 = offerItem(seller3, cat, omsch);
		Item item2 = offerItem(seller4, cat, omsch);

		List<Item> res = findItemByDescription(omsch2);
		assertEquals(0, res.size());

		res =  findItemByDescription(omsch);
		assertEquals(2, res.size());
	}

	@Test
	public void newBid() throws Exception {
		String email = "ss2@nl";
		String emailb = "bb@nl";
		String emailb2 = "bb2@nl";
		String omsch = "omsch_bb";

		User seller = registerUser(email);
		User buyer = registerUser(emailb);
		User buyer2 = registerUser(emailb2);
		// eerste bod
		Category cat = newCategory("cat9");
		Item item1 = offerItem(seller, cat, omsch);
		Bid new1 = newBid(item1, buyer, newMoney(10, "eur"));
		assertEquals(emailb, new1.getBuyer().getEmail());

		// lager bod
		Bid new2 = newBid(item1, buyer2, newMoney(9, "eur"));
		assertNull(new2);

		// hoger bod
		Bid new3 = newBid(item1, buyer2, newMoney(11, "eur"));
		assertEquals(emailb2, new3.getBuyer().getEmail());
	}

	@Test
	public void offerItem() throws Exception {
		String omsch = "omsch";

		User user1 = registerUser("xx@nl");
		Category cat = newCategory("cat1");
		Item item1 = offerItem(user1, cat, omsch);
		assertEquals(omsch, item1.getDescription());
		assertNotNull(item1.getId());
	}

	@Test
	public void revokeItem() throws Exception {
		String omsch = "omsch";
		String omsch2 = "omsch2";


		User seller = registerUser("sel@nl");
		User buyer = registerUser("buy@nl");
		Category cat = newCategory("cat1");

		// revoke before bidding
		System.out.println("Before anything size: " + findItemByDescription(omsch).size() + System.lineSeparator());
		Item item1 = offerItem(seller, cat, omsch);

		System.out.println("After Offer size: " + findItemByDescription(omsch).size() + System.lineSeparator());

		boolean res = revokeItem(item1);
		assertTrue(res);

		System.out.println("After Revoke size: " + findItemByDescription(omsch).size() + System.lineSeparator());

		List<Item> itemsFromDatabase = findItemByDescription(omsch);
		int count = itemsFromDatabase.size();
		assertEquals(0, count);

		// revoke after bid has been made
		Item item2 = offerItem(seller, cat, omsch2);
		newBid(item2, buyer, newMoney(100, "Euro"));
		boolean res2 = revokeItem(item2);
		assertFalse(res2);
		int count2 = findItemByDescription(omsch2).size();
		assertEquals(1, count2);
	}

}