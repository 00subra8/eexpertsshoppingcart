package com.ee.eval.service

import com.ee.eval.action.EEShoppingCartSpec
import com.ee.eval.model.CartOrder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class EEShoppingCartServiceSpec extends Specification {
    @Shared
    private EEShoppingCartService unit

    void setupSpec() {
        unit = new EEShoppingCartService()
    }

    @Unroll("Starting with Empty Cart, for Product: #productName with unitPrice: #unitPrice for quantity: #quantity totalPrice is #totalPrice")
    def "Starting with Empty Cart, check if all products are added to cart and correct total proce is calculated"(String productName,
                                                                                                                  double unitPrice, int quantity,
                                                                                                                  BigDecimal totalPrice) {
        given:
        unit.cartOrder = new CartOrder()


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

    @Unroll("Starting with non empty Cart, for Product: #productName with unitPrice: #unitPrice for quantity: #quantity totalPrice is #totalPrice")
    def "Starting with non empty Cart,, check if all products are added to cart and correct total proce is calculated"(CartOrder initialCartOrder, String productName,
                                                                                                                       double unitPrice, int quantity,
                                                                                                                       BigDecimal totalPrice) {

        given:
        unit.cartOrder = initialCartOrder

        when:
        CartOrder actualCartOrder = unit.constructCartOrder(EEShoppingCartSpec.getDoveProduct(), quantity)

        then:
        actualCartOrder != null
        actualCartOrder.productList != null
        actualCartOrder.productList.size() == quantity + getFilledCatOrder().productList.size()
        actualCartOrder.productList[0].productName == productName
        actualCartOrder.productList[0].unitPrice == unitPrice
        actualCartOrder.totalPrice == totalPrice

        where:
        initialCartOrder    | productName                                     | unitPrice                                     | quantity | totalPrice
        getFilledCatOrder() | EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 3        | BigDecimal.valueOf(319.92)
        getFilledCatOrder() | EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 10       | BigDecimal.valueOf(599.85)

    }

    CartOrder getFilledCatOrder() {
        CartOrder filledCartOrder = new CartOrder()
        filledCartOrder.productList = [EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct(),
                                       EEShoppingCartSpec.getDoveProduct()]
        filledCartOrder.totalPrice = BigDecimal.valueOf(199.95)

        filledCartOrder
    }

}
