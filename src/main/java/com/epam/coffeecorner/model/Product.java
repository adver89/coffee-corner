package com.epam.coffeecorner.model;

import java.math.BigDecimal;

public class Product {

    private String name;
    private BigDecimal price;
    private ProductType type;

    public Product(String name, BigDecimal price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
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

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
