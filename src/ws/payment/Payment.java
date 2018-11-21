package ws.payment;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)

public interface Payment {

    @WebMethod String generatePaymentCode(String tipe, String Deskripsi, int jumlahpembayaran);
    @WebMethod String confirmPayment(String code);
    @WebMethod String getPaidStatus(String code);
    @WebMethod Boolean doPayment(String code);

}
