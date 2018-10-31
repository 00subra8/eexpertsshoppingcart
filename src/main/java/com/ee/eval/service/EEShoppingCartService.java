package com.ee.eval.service;

import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EEShoppingCartService {

    public CartOrder constructCartOrder(Product product, int quantity) {
        CartOrder cartOrder = new CartOrder();

        List<Product> productList = new ArrayList<>();

        addProductsToList(product, quantity, productList);

        cartOrder.setProductList(productList);

        BigDecimal calculatedTotalPrice = calculateTotalPrice(product.getUnitPrice(), quantity);
        cartOrder.setTotalPrice(calculatedTotalPrice);

        return cartOrder;
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