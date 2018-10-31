package com.ee.eval.configuration;

import com.ee.eval.action.EEShoppingCart;
import com.ee.eval.service.EEShoppingCartService;
import com.ee.eval.service.InputValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class EESCConfiguration {

    @Bean
    public EEShoppingCart eeShoppingCart() {
        return new EEShoppingCart();
    }

    @Bean
    public InputValidatorService inputValidatorService() {
        return new InputValidatorService();
    }

    @Bean
    public EEShoppingCartService eeShoppingCartService() {
        return new EEShoppingCartService();
    }
}
