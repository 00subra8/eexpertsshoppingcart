package com.ee.eval.service;

import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class EEShoppingCartService {
    @Autowired
    private CartOrder cartOrder;

    public CartOrder constructCartOrder(Product product, int quantity) {
        List<Product> productList = new ArrayList<>();

        addProductsToList(product, quantity, productList);
        addCurrentProductsWithExisting(cartOrder, productList);


        BigDecimal calculatedTotalPrice = calculateTotalPrice(product.getUnitPrice(), quantity);
        addTotalPriceToExisting(cartOrder, calculatedTotalPrice);

        return cartOrder;
    }

    private void addTotalPriceToExisting(CartOrder cartOrder, BigDecimal calculatedTotalPrice) {
        BigDecimal existingTotalPrice = cartOrder.getTotalPrice();
        if (existingTotalPrice == null || Objects.equals(existingTotalPrice, BigDecimal.ZERO)) {
            cartOrder.setTotalPrice(calculatedTotalPrice);
        } else {
            BigDecimal addedTotalPrice = existingTotalPrice.add(calculatedTotalPrice);
            cartOrder.setTotalPrice(addedTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }

    private void addCurrentProductsWithExisting(CartOrder cartOrder, List<Product> productList) {
        List<Product> existingProductsInCart = cartOrder.getProductList();
        if (CollectionUtils.isEmpty(existingProductsInCart)) {
            cartOrder.setProductList(productList);
        } else {
            cartOrder.setProductList(ListUtils.union(existingProductsInCart, productList));
        }
    }

    private BigDecimal calculateTotalPrice(double unitPrice, int quantity) {
        BigDecimal totalPrice = BigDecimal.valueOf(unitPrice * quantity);

        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private void addProductsToList(Product product, int quantity, List<Product> productList) {
        IntStream.range(0, quantity)
                .forEach(currentQuantity -> productList.add(product));
    }


}