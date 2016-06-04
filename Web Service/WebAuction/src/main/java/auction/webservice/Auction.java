package auction.webservice;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import nl.fontys.util.Money;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by guushamm on 04/06/16.
 */
@WebService
public class Auction {
	AuctionMgr auctionMgr;
	SellerMgr sellerMgr;

	public Auction() {
		auctionMgr = new AuctionMgr();
		sellerMgr = new SellerMgr();
	}

	public Item getItem(Long id){
		return auctionMgr.getItem(id);
	}

	public List<Item> findItemByDescription(String description){
		return auctionMgr.findItemByDescription(description);
	}

	public Bid newBid(Item item, User buyer, Money amount){
		return auctionMgr.newBid(item,buyer,amount);
	}

	public Item offerItem(User seller, Category cat, String description){
		return sellerMgr.offerItem(seller,cat,description);
	}

	public boolean revokeItem(Item item){
		return sellerMgr.revokeItem(item);
	}

	public Money newMoney(long cents, String currency){
		return sellerMgr.newMoney(cents,currency);
	}

	public Category newCategory(String descriptoin){
		return sellerMgr.newCategory(descriptoin);
	}

}
