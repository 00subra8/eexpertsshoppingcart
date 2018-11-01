package com.ee.eval.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eesc")
public class ApplicationProperties {
    private String salestax;

    public String getSalesTax() {
        return salestax;
    }

    public void setSalesTax(String salesTax) {
        this.salestax = salesTax;
    }
}
