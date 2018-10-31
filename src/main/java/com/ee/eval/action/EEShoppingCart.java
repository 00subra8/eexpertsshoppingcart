package com.ee.eval.action;

import com.ee.eval.exception.EESCInputException;
import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;
import com.ee.eval.service.EEShoppingCartService;
import com.ee.eval.service.InputValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EEShoppingCart {
    public Logger logger = LoggerFactory.getLogger(EEShoppingCart.class);

    @Autowired
    private InputValidatorService inputValidatorService;

    @Autowired
    private EEShoppingCartService eeShoppingCartService;

    public CartOrder addProductsToCart(Product product, int quantity) {
        if (product == null || product.isEmpty()) {
            logAndThrowInputException("No Product received");
        }

        if (!inputValidatorService.isQuantityValid(quantity)) {
            logAndThrowInputException("Invalid quantity: " + quantity + " Quantity should be between 1 and 150.");
        }

        return eeShoppingCartService.constructCartOrder(product, quantity);
    }

    private void logAndThrowInputException(String exceptionMessage) {
        logger.error(exceptionMessage);
        throw new EESCInputException(exceptionMessage);
    }

}
