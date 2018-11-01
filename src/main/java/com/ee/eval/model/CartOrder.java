package com.ee.eval.model;

import java.math.BigDecimal;
import java.util.List;

public class CartOrder {
    private List<Product> productList;
    private BigDecimal totalPrice;
    private BigDecimal salesTaxComponent;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public BigDecimal getSalesTaxComponent() {
        return salesTaxComponent;
    }

    public void setSalesTaxComponent(BigDecimal salesTaxComponent) {
        this.salesTaxComponent = salesTaxComponent;
    }
}
