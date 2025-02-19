package com.app.manager.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("product")
public class Product implements Serializable {

    @Id
    private int productId;
    private String name;
    private BigDecimal price;
    private int quantity;

    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return
                "\nID: " + productId +
                "\nName: " + name +
                "\nQuantity: "+ quantity+
                "\nPrice: $" + price;
    }

    public String toStringNoId() {
        return
                "\nName: " + name +
                "\nQuantity: "+ quantity+
                "\nPrice: $" + price;
    }
}
