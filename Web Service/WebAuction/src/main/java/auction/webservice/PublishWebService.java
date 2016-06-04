package auction.webservice;

import javax.xml.ws.Endpoint;

public class PublishWebService {

    private static final String url = "http://localhost:8080/";

    public static void main(String[] args) {
        Endpoint.publish(url+"auction", new Auction());
        Endpoint.publish(url+"registration", new Registration());
    }
}
