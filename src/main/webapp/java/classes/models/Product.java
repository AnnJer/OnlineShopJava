package classes.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private long id;
    private String name;
    private long amount;
    private double price;
    private long categoryId;

    @JsonCreator
    public Product(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                   @JsonProperty("amount")long amount, @JsonProperty("price") double price,
                   @JsonProperty("categoryId") long categoryId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }
}
