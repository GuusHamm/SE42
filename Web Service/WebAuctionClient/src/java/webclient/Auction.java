
package webclient;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Auction", targetNamespace = "http://webservice.auction/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Auction {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<webclient.Item>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findItemByDescription", targetNamespace = "http://webservice.auction/", className = "webclient.FindItemByDescription")
    @ResponseWrapper(localName = "findItemByDescriptionResponse", targetNamespace = "http://webservice.auction/", className = "webclient.FindItemByDescriptionResponse")
    @Action(input = "http://webservice.auction/Auction/findItemByDescriptionRequest", output = "http://webservice.auction/Auction/findItemByDescriptionResponse")
    public List<Item> findItemByDescription(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns webclient.Item
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "offerItem", targetNamespace = "http://webservice.auction/", className = "webclient.OfferItem")
    @ResponseWrapper(localName = "offerItemResponse", targetNamespace = "http://webservice.auction/", className = "webclient.OfferItemResponse")
    @Action(input = "http://webservice.auction/Auction/offerItemRequest", output = "http://webservice.auction/Auction/offerItemResponse")
    public Item offerItem(
        @WebParam(name = "arg0", targetNamespace = "")
        User arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Category arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "revokeItem", targetNamespace = "http://webservice.auction/", className = "webclient.RevokeItem")
    @ResponseWrapper(localName = "revokeItemResponse", targetNamespace = "http://webservice.auction/", className = "webclient.RevokeItemResponse")
    @Action(input = "http://webservice.auction/Auction/revokeItemRequest", output = "http://webservice.auction/Auction/revokeItemResponse")
    public boolean revokeItem(
        @WebParam(name = "arg0", targetNamespace = "")
        Item arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns webclient.Money
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newMoney", targetNamespace = "http://webservice.auction/", className = "webclient.NewMoney")
    @ResponseWrapper(localName = "newMoneyResponse", targetNamespace = "http://webservice.auction/", className = "webclient.NewMoneyResponse")
    @Action(input = "http://webservice.auction/Auction/newMoneyRequest", output = "http://webservice.auction/Auction/newMoneyResponse")
    public Money newMoney(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns webclient.Category
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newCategory", targetNamespace = "http://webservice.auction/", className = "webclient.NewCategory")
    @ResponseWrapper(localName = "newCategoryResponse", targetNamespace = "http://webservice.auction/", className = "webclient.NewCategoryResponse")
    @Action(input = "http://webservice.auction/Auction/newCategoryRequest", output = "http://webservice.auction/Auction/newCategoryResponse")
    public Category newCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns webclient.Bid
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "newBid", targetNamespace = "http://webservice.auction/", className = "webclient.NewBid")
    @ResponseWrapper(localName = "newBidResponse", targetNamespace = "http://webservice.auction/", className = "webclient.NewBidResponse")
    @Action(input = "http://webservice.auction/Auction/newBidRequest", output = "http://webservice.auction/Auction/newBidResponse")
    public Bid newBid(
        @WebParam(name = "arg0", targetNamespace = "")
        Item arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        User arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        Money arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns webclient.Item
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItem", targetNamespace = "http://webservice.auction/", className = "webclient.GetItem")
    @ResponseWrapper(localName = "getItemResponse", targetNamespace = "http://webservice.auction/", className = "webclient.GetItemResponse")
    @Action(input = "http://webservice.auction/Auction/getItemRequest", output = "http://webservice.auction/Auction/getItemResponse")
    public Item getItem(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0);

}
