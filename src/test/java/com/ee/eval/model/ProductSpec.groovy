package com.ee.eval.model


import spock.lang.Specification
import spock.lang.Unroll

class ProductSpec extends Specification {
    private Product unit

    void setup() {
        unit = new Product()
    }

    @Unroll("For productName: #productName product is empty")
    def "If product name is blank then isEmpty should return true"(String productName) {
        given:
        unit.setProductName(productName)

        expect:
        unit.isEmpty()

        where:
        productName << [null, "", "  "]
    }

    @Unroll("For productName: #productName and with a valid unit price product is empty")
    def "If product name is blank and unitprice is valid, even then isEmpty should return true"(String productName) {
        given:
        unit.setProductName(productName)
        unit.setUnitPrice(50.45)

        expect:
        unit.isEmpty()

        where:
        productName << [null, "", "  "]
    }

    def "For valid productName even with 0 unit price isEmpty returns false"() {
        given:
        unit.setProductName("valid Product Name")

        expect:
        !unit.isEmpty()

    }

    def "For valid productName valid unit price isEmpty returns false"() {
        given:
        unit.setProductName("valid Product Name")
        unit.setUnitPrice(50.45)

        expect:
        !unit.isEmpty()

    }
}
