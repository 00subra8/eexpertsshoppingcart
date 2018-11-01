package com.ee.eval.service

import com.ee.eval.action.EEShoppingCartSpec
import com.ee.eval.configuration.ApplicationProperties
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
        unit.applicationProperties = new ApplicationProperties()


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
        unit.applicationProperties = new ApplicationProperties()

        when:
        CartOrder actualCartOrder = unit.constructCartOrder(EEShoppingCartSpec.getDoveProduct(), quantity)

        then:
        actualCartOrder != null
        actualCartOrder.productList != null
        actualCartOrder.productList.size() == quantity + getFilledCatOrder5().productList.size()
        actualCartOrder.productList[0].productName == productName
        actualCartOrder.productList[0].unitPrice == unitPrice
        actualCartOrder.totalPrice == totalPrice

        where:
        initialCartOrder     | productName                                     | unitPrice                                     | quantity | totalPrice
        getFilledCatOrder5() | EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 3        | BigDecimal.valueOf(319.92)
        getFilledCatOrder5() | EEShoppingCartSpec.getDoveProduct().productName | EEShoppingCartSpec.getDoveProduct().unitPrice | 10       | BigDecimal.valueOf(599.85)

    }


    @Unroll("Sales tax: 12.5 + Starting with non empty Cart, for Product: #productName with unitPrice: #unitPrice for quantity: #quantity totalPrice is #totalPrice")
    def "Sales tax + Starting with non empty Cart, check if all products are added to cart and correct total proce is calculated"(CartOrder initialCartOrder, String productName,
                                                                                                                                  double unitPrice, int quantity,
                                                                                                                                  BigDecimal totalPrice, BigDecimal salesTaxComponent) {

        given:
        unit.cartOrder = initialCartOrder
        ApplicationProperties properties = new ApplicationProperties()
        properties.salesTax = "12.5"
        unit.applicationProperties = properties

        when:
        CartOrder actualCartOrder = unit.constructCartOrder(EEShoppingCartSpec.getAxeProduct(), quantity)

        then:
        actualCartOrder != null
        actualCartOrder.productList != null
        actualCartOrder.productList.size() == quantity + getFilledCatOrder2().productList.size()
        actualCartOrder.productList[2].productName == productName
        actualCartOrder.productList[2].unitPrice == unitPrice
        actualCartOrder.totalPrice == totalPrice
        actualCartOrder.salesTaxComponent == salesTaxComponent

        where:
        initialCartOrder     | productName                                    | unitPrice                                    | quantity | totalPrice                  | salesTaxComponent
        getFilledCatOrder2() | EEShoppingCartSpec.getAxeProduct().productName | EEShoppingCartSpec.getAxeProduct().unitPrice | 2        | BigDecimal.valueOf(314.96)  | BigDecimal.valueOf(35.00)
        getFilledCatOrder2() | EEShoppingCartSpec.getAxeProduct().productName | EEShoppingCartSpec.getAxeProduct().unitPrice | 10       | BigDecimal.valueOf(1214.87) | BigDecimal.valueOf(134.99)

    }

    CartOrder getFilledCatOrder2() {
        CartOrder filledCartOrder = new CartOrder()
        filledCartOrder.productList = [EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct()]
        filledCartOrder.totalPrice = BigDecimal.valueOf(89.98)
        filledCartOrder.salesTaxComponent = BigDecimal.valueOf(10.00)

        filledCartOrder
    }

    CartOrder getFilledCatOrder5() {
        CartOrder filledCartOrder = new CartOrder()
        filledCartOrder.productList = [EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct(), EEShoppingCartSpec.getDoveProduct()
                                       , EEShoppingCartSpec.getDoveProduct()]
        filledCartOrder.totalPrice = BigDecimal.valueOf(199.95)

        filledCartOrder
    }

}
