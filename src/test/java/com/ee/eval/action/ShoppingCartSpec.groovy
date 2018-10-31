package com.ee.eval.action

import com.ee.eval.exception.EESCInputException
import com.ee.eval.model.Product
import spock.lang.Specification
import spock.lang.Unroll
import org.slf4j.Logger


class ShoppingCartSpec extends Specification {
    private ShoppingCart unit

    void setup() {
        unit = new ShoppingCart()
        unit.logger = Mock(Logger)
    }

    @Unroll("product: #product will throw an exception")
    def "Try to add a null cart items"(Product product) {
        when:
        unit.addProduct(product)

        then:
        1 * unit.logger.error("No cart items received")
        thrown(EESCInputException)

        where:
        product << [null, new Product()]
    }

}
