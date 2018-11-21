package ws.payment;

import util.DatabaseConnector;
import ws.payment.Payment;

import javax.jws.WebService;
import javax.xml.ws.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@WebService(endpointInterface = "ws.payment.Payment")
public class PaymentImpl implements Payment {
    @Override
    public String generatePaymentCode(String tipe, String deskripsi, int jumlah_pembayaran) {
        String Kode_pembayaran = "";
        Connection connection = DatabaseConnector.connect("payment_gateway");
        PreparedStatement preparedStatement;

        try {
            Random random = new Random();

            Kode_pembayaran = tipe + Integer.toString(jumlah_pembayaran);
            String command = "INSERT INTO payment (tipe, deskripsi, jumlah_pembayaran, kode_pembayaran) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, tipe);
            preparedStatement.setString(2, deskripsi);
            preparedStatement.setInt(3, jumlah_pembayaran);
            preparedStatement.setString(4, Kode_pembayaran);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnector.close(connection);
        return Kode_pembayaran;
    }

    public String confirmPayment(String code) {

        Connection connection = DatabaseConnector.connect("payment_gateway");
        PreparedStatement preparedStatement;
        StringBuilder result = new StringBuilder();


        try {

            String command = "SELECT * FROM payment WHERE Kode_pembayaran = ?";
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String payment_id = resultSet.getString("payment_id");
                String tipe = resultSet.getString("tipe");
                String deskripsi = resultSet.getString("deskripsi");
                String jumlah_pembayaran = resultSet.getString("jumlah_pembayaran");
                String kode_pembayaran = resultSet.getString("kode_pembayaran");
                String is_paid = resultSet.getString("is_paid");

                String add = String.format("id=%s&tipe=%s&deskripsi=%s&jumlah_pembayaran=%s&kode_pembayaran=%s&is_paid=%s\n-\n",
                        payment_id, tipe, deskripsi, jumlah_pembayaran, kode_pembayaran, is_paid);
                result.append(add);


            }
            return result.toString();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnector.close(connection);

        return "Data Not Exist";
    }

    public String getPaidStatus(String code) {

        Connection connection = DatabaseConnector.connect("payment_gateway");
        PreparedStatement preparedStatement;

        String paid_status = "Belum Lunas";


        try {

            String command = "SELECT paid_status FROM payment WHERE kode_pembayaran = ?";
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String is_paid = resultSet.getString("is_paid");

                if (is_paid.equals("1"))
                    paid_status = "Lunas";
            }

            return paid_status;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnector.close(connection);

        return "Data Not Exist";
    }

    @Override
    public Boolean doPayment(String code) {

        Connection connection = DatabaseConnector.connect("payment_gateway");
        PreparedStatement preparedStatement;

        try {

            String command = "UPDATE payment set is_paid = ? where kode_pembayaran = ? ";
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConnector.close(connection);


        return true;
    }
}
