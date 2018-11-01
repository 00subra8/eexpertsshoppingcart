package features.steps;

import com.ee.eval.EESCApplication;
import com.ee.eval.action.EEShoppingCart;
import com.ee.eval.model.CartOrder;
import com.ee.eval.model.Product;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
@SpringBootTest(classes = EESCApplication.class)
@ActiveProfiles("INTEGRATION_TEST")
@ContextConfiguration
public class AddProductCart {
    @Autowired
    private EEShoppingCart eeShoppingCart;

    private CartOrder cartOrder;

    @When("^Product with Below details and quantity \"([^\"]+)\" is added$")
    public void addItemsToCart(String quantity, List<Product> productList) {
        cartOrder = eeShoppingCart.addProductsToCart(productList.get(0), Integer.valueOf(quantity));
    }

    @Then("^Expected totalPrice is \"([^\"]+)\" with cart quantity \"([^\"]+)\" and Sales tax \"([^\"]+)\"$")
    public void expectedTotalPriceIsWithCartQuantity(String totalPrice, String expectedQuantity, String salesTaxComponent) {
        assertNotNull(cartOrder);
        assertNotNull(cartOrder.getProductList());
        assertEquals(cartOrder.getProductList().size(), Integer.valueOf(expectedQuantity).intValue());
        assertEquals(cartOrder.getTotalPrice().doubleValue(), Double.valueOf(totalPrice), 0.00);
        assertEquals(cartOrder.getSalesTaxComponent().doubleValue(), Double.valueOf(salesTaxComponent), 0.00);
    }
}
