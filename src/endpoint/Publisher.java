
package endpoint;
import ws.index.IndexImpl;
import ws.payment.PaymentImpl;

import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String[] args) {

        Endpoint.publish("http://localhost:3003/services/payment", new PaymentImpl());
        Endpoint.publish("http://localhost:3003/", new IndexImpl());
    }
}
