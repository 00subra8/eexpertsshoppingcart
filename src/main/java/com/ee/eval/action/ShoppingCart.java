package com.ee.eval.action;

import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCart {

    private Logger logger = LoggerFactory.getLogger(ShoppingCart.class);

    public CartOrder addProduct(Product product) {

        if(product == null || isProductEmpty(product)){
            logger.error("No Products received");
        }

        return null;
    }

    private boolean isProductEmpty(Product product) {
        return false;
    }
}
