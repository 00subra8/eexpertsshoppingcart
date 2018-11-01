package com.ee.eval.service;

import com.ee.eval.configuration.ApplicationProperties;
import com.ee.eval.exception.EESCInternalException;
import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class EEShoppingCartService {
    private Logger logger = LoggerFactory.getLogger(EEShoppingCartService.class);

    @Autowired
    private CartOrder cartOrder;

    @Autowired
    private ApplicationProperties applicationProperties;

    public CartOrder constructCartOrder(Product product, int quantity) {
        List<Product> productList = new ArrayList<>();

        addProductsToList(product, quantity, productList);
        addCurrentProductsWithExisting(productList);


        BigDecimal calculatedTotalPrice = calculateTotalPrice(product.getUnitPrice(), quantity);
        addTotalPriceToExisting(cartOrder, calculatedTotalPrice);

        return cartOrder;
    }

    private void addTotalPriceToExisting(CartOrder cartOrder, BigDecimal calculatedTotalPrice) {
        BigDecimal existingTotalPrice = cartOrder.getTotalPrice();
        if (existingTotalPrice == null || Objects.equals(existingTotalPrice, BigDecimal.ZERO)) {
            cartOrder.setTotalPrice(calculatedTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            BigDecimal addedTotalPrice = existingTotalPrice.add(calculatedTotalPrice);
            cartOrder.setTotalPrice(addedTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }

    private void addSalesTaxToExisting(BigDecimal salesTaxComponent) {
        BigDecimal existingSalesTaxComponent = cartOrder.getSalesTaxComponent();
        if (existingSalesTaxComponent == null || Objects.equals(existingSalesTaxComponent, BigDecimal.ZERO)) {
            cartOrder.setSalesTaxComponent(salesTaxComponent.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            BigDecimal addedSalesTaxComponent = existingSalesTaxComponent.add(salesTaxComponent);
            cartOrder.setSalesTaxComponent(addedSalesTaxComponent.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }

    private void addCurrentProductsWithExisting(List<Product> productList) {
        List<Product> existingProductsInCart = cartOrder.getProductList();
        if (CollectionUtils.isEmpty(existingProductsInCart)) {
            cartOrder.setProductList(productList);
        } else {
            cartOrder.setProductList(ListUtils.union(existingProductsInCart, productList));
        }
    }

    private BigDecimal calculateTotalPrice(double unitPrice, int quantity) {
        BigDecimal totalPrice = BigDecimal.valueOf(unitPrice * quantity);

        BigDecimal salesTaxComponent = getSalesTaxComponent(totalPrice);
        addSalesTaxToExisting(salesTaxComponent);
        totalPrice = totalPrice.add(salesTaxComponent);

        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getSalesTaxComponent(BigDecimal totalPrice) {
        String salesTax = applicationProperties.getSalesTax();
        BigDecimal salesTaxComponent = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(salesTax)) {
            try {
                double intSalesTax = Double.parseDouble(salesTax);
                salesTaxComponent = totalPrice.multiply(BigDecimal.valueOf(intSalesTax / 100));
            } catch (NumberFormatException nfe) {
                String errorMessage = "Unable to convert given Sales tax: " + salesTax + " to a number";
                logger.error(errorMessage);
                throw new EESCInternalException(errorMessage);
            }
        }
        return salesTaxComponent;
    }

    private void addProductsToList(Product product, int quantity, List<Product> productList) {
        IntStream.range(0, quantity)
                .forEach(currentQuantity -> productList.add(product));
    }


}