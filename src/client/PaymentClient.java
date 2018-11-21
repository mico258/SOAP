package client;

import ws.payment.Payment;
import payment.PaymentImplService;

public class PaymentClient {



    public String generatePaymentCode(String type, String desc, int sum){
        PaymentImplService paymentImplService=new PaymentImplService();
        Payment payment = paymentImplService.getPaymentImplPort();

        System.out.println("Generate code .....");

        return payment.generatePaymentCode(type, desc, sum);

    }

    public String confirmPayment(String code){
        PaymentImplService paymentImplService=new PaymentImplService();
        Payment payment = paymentImplService.getPaymentImplPort();

        System.out.println("Confirm Payment .....");

        return payment.confirmPayment(code);
    }

    public String getPaidStatus(String code){
        PaymentImplService paymentImplService=new PaymentImplService();
        Payment payment = paymentImplService.getPaymentImplPort();

        System.out.println("Getting Payment status .....");

        return payment.getPaidStatus(code);
    }

    public boolean pay(String code){
        PaymentImplService paymentImplService=new PaymentImplService();
        Payment payment = paymentImplService.getPaymentImplPort();

        System.out.println("Doing Payment .....");

        return payment.doPayment(code);
    }

    public static void main(String[] args){

        PaymentImplService paymentImplService=new PaymentImplService();
        Payment payment = paymentImplService.getPaymentImplPort();

        String code = payment.generatePaymentCode("Pasca", "Barang mahal",2222);

        System.out.println(code);

    }



}
