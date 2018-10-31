package com.ee.eval.service

import com.ee.eval.action.EEShoppingCartSpec
import com.ee.eval.model.CartOrder
import spock.lang.Specification
import spock.lang.Unroll

class EEShoppingCartServiceSpec extends Specification {
    private EEShoppingCartService unit

    void setup() {
        unit = new EEShoppingCartService()
    }

    @Unroll("For Product: #productName with unitPrice: #unitPrice for quantity: #quantity totalPrice is #totalPrice")
    def "Check if all products are added to cart and correct total proce is calculated"(String productName,
                                                                                        double unitPrice, int quantity,
                                                                                        BigDecimal totalPrice) {


        when:
        CartOrder actualCartOrder = unit.constructCartOrder(EEShoppingCartSpec.getDoveProduct(), quantity)

        then:
        actualCartOrder != null
        actualCartOrder.productList != null
        actualCartOrder.productList.size() == quantity
        actualCartOrder.productList[0].productName == productName
        actualCartOrder.productList[0].unitPrice == unitPrice
        actualCartOrder.totalPrice == totalPrice

        where:
        productName                                     | unitPrice                                     | quantity | totalPrice
        EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 5        | BigDecimal.valueOf(199.95)
        EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 10       | BigDecimal.valueOf(399.90)
        EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 55       | BigDecimal.valueOf(2199.45)

    }
}
