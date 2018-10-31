package com.ee.eval.action

import com.ee.eval.exception.EESCInputException
import com.ee.eval.model.CartOrder
import com.ee.eval.model.Product
import com.ee.eval.service.EEShoppingCartService
import com.ee.eval.service.InputValidatorService
import org.slf4j.Logger
import spock.lang.Specification
import spock.lang.Unroll

class EEShoppingCartSpec extends Specification {
    private EEShoppingCart unit

    void setup() {
        unit = new EEShoppingCart()
        unit.logger = Mock(Logger)
        unit.inputValidatorService = Mock(InputValidatorService)
        unit.eeShoppingCartService = Mock(EEShoppingCartService)
    }

    @Unroll("product: #product will throw an exception even with valid quantity")
    def "Try to add a null cart items"(Product product) {
        when:
        unit.addProductsToCart(product, 1)

        then:
        1 * unit.logger.error("No Product received")
        thrown(EESCInputException)

        where:
        product << [null, new Product()]
    }

    @Unroll("Invalid quantity: #quantity throws exception")
    def "One valid item is added to Cart with invalid quantity exception is thrown"(int quantity) {
        given:
        Product doveProduct = getDoveProduct()
        unit.inputValidatorService.isQuantityValid(quantity) >> false

        when:
        unit.addProductsToCart(doveProduct, quantity)

        then:
        1 * unit.logger.error("Invalid quantity: " + quantity + " Quantity should be between 1 and 150.")
        thrown(EESCInputException)

        where:
        quantity << [0, -1, 151]
    }

    def "Add valid products into the cart"() {
        given:
        Product doveProduct = getDoveProduct()
        CartOrder expectedCartOrder = new CartOrder()
        expectedCartOrder.productList = [getDoveProduct(), getDoveProduct(), getDoveProduct(), getDoveProduct(), getDoveProduct()]
        expectedCartOrder.totalPrice = BigDecimal.valueOf(199.95)
        int quantity = 5
        unit.inputValidatorService.isQuantityValid(quantity) >> true
        unit.eeShoppingCartService.constructCartOrder(doveProduct, quantity) >> expectedCartOrder

        expect:
        unit.addProductsToCart(doveProduct, quantity) == expectedCartOrder
    }

    static Product getDoveProduct() {
        Product doveProduct = new Product()
        doveProduct.productName = 'Dove Soap'
        doveProduct.unitPrice = 39.99

        doveProduct
    }

}
