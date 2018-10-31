package com.ee.eval.model;

import org.apache.commons.lang3.StringUtils;

public class Product {

    private String productName;
    private double unitPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isEmpty(){
        return StringUtils.isBlank(productName);

    }
}
