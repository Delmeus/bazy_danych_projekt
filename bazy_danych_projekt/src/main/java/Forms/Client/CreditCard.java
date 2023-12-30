package Forms.Client;

import java.sql.Date;

public record CreditCard(String cardNumber, java.sql.Date expiryDate, String producerName) {

    @Override
    public String toString() {
        return cardNumber + " " + expiryDate.toString() + " " + producerName;
    }
}
