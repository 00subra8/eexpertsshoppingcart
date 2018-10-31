package com.ee.eval.service;

public class InputValidatorService {

    public boolean isQuantityValid(int quantity) {
        return quantity > 0 && quantity <= 150;
    }
}
