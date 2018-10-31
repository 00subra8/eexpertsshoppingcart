package com.ee.eval.service


import spock.lang.Specification
import spock.lang.Unroll

class InputValidatorServiceSpec extends Specification {
    private InputValidatorService unit

    void setup() {
        unit = new InputValidatorService()
    }

    @Unroll("quantity: #quantity is invalid")
    def "for quantities not between 1 and 150 quantity is invalid"(int quantity) {
        expect:
        !unit.isQuantityValid(quantity)

        where:
        quantity << [0, -1, 151]

    }
}

