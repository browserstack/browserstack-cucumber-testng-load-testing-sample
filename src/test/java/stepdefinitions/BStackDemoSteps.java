package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.URL;
import java.time.Duration;

public class BStackDemoSteps {

    private RemoteWebDriver driver;
    private String notedProductName;

    @Given("I open the bstack demo homepage")
    public void i_open_the_bstack_demo_homepage() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        // Set the
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bstackdemo.com");
    }

    @Then("the bstack demo title should be {string}")
    public void the_bstack_demo_title_should_be(String expected) {
        Assert.assertTrue(driver.getTitle().contains(expected), "Expected title to contain: " + expected + " but was: " + driver.getTitle());
    }

    @When("I note the name of the first product")
    public void i_note_the_name_of_the_first_product() {
        WebElement nameEl = driver.findElement(By.xpath("//*[@id='1']/p"));
        notedProductName = nameEl.getText();
        Assert.assertNotNull(notedProductName, "Product name should not be null");
    }

    @And("I add the first product to the cart")
    public void i_add_the_first_product_to_the_cart() {
        driver.findElement(By.xpath("//*[@id='1']/div[4]"))
              .click();
    }

    @Then("the mini cart should be displayed")
    public void the_mini_cart_should_be_displayed() {
        WebElement cart = driver.findElement(By.cssSelector(".float\\-cart__content"));
        Assert.assertTrue(cart.isDisplayed(), "Mini cart not displayed");
    }

    @And("the product name in the cart should match noted name")
    public void the_product_name_in_the_cart_should_match_noted_name() {
        WebElement nameInCart = driver.findElement(By.xpath("//*[@id='__next']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"));
        String cartName = nameInCart.getText();
        Assert.assertEquals(cartName, notedProductName, "Product names differ between main page and cart");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try { driver.quit(); } catch (Exception ignored) {}
            driver = null;
        }
    }
}
