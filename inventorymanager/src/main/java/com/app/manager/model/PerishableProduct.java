package com.app.manager.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@DiscriminatorValue("perishable")
public class PerishableProduct extends Product{

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    public PerishableProduct() {
    }

    public PerishableProduct(String name, BigDecimal price, int quantity, Date expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "\nExpiration Date:" + expirationDate;
    }
}
