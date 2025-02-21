package com.app.manager.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

// Product is connected to the product table in the inventory MySQL database
// Uses jaka persistence annotations to map java objects to database records and manage relational data
// @Entity used to denote this class corresponds to the table in a database
// Each instance is a record
// @Inheritance is used to define an inheritance strategy when a sub and super class exist and use the same table but have different record mappings based on a field
// Differentiation is handled by @DiscriminatorColumn and @DiscriminatorValue
// Product is differentiated by the 'product' value in column 'product_type'
// @Id is used to denote the primary key
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

    // Show product details
    @Override
    public String toString() {
        String pattern = "#0.00";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return String.format("ID: %s \nName: %s \nQuantity: %d \nPrice: $%.2f", productId, name, quantity, price);

    }

    // Used during the update product to display details with not product id
    public String toStringNoId() {
        return String.format("Name: %s \nQuantity: %d \nPrice: $%.2f", name, quantity, price);
    }

    // Used to display formatted product information for each product in the database viewing all products
    public String productLineString() {
        return String.format("%-4d| %-17s| %-5d| $%-10.2f", getProductId(), getName(), getQuantity(), getPrice());
    }


}