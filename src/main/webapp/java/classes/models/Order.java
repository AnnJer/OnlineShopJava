package classes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    private Long id;
    private Long clientId;
    private Long productId;
    private Long amount;
    private Integer payed;

    @JsonCreator
    public Order(@JsonProperty("id") Long id, @JsonProperty("clientId") Long clientId,
                 @JsonProperty("productId") Long productId,
                 @JsonProperty("amount") Long amount, @JsonProperty("payed") int payed) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.amount = amount;
        this.payed = payed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", amount=" + amount +
                ", payed=" + payed +
                '}';
    }
}
