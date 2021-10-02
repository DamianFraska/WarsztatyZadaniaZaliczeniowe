package Zad2;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;


public class Shop {
    private WebDriver driver;
    String totalAmount;
    String orderReference;


    @Given("Open browser with https://mystore-testlab.coderslab.pl/index.php")
    public void openSite() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mystore-testlab.coderslab.pl/index.php");
    }

    @And("SignIn button clicked")
    public void LogInClick() {
        WebElement logInClick = driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a/span"));
        logInClick.click();
    }

    @Then("Input (.*) and (.*) in LogIn page")
    public void LogInData(String email, String password) {
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @And("Submit LogIn Data")
    public void SubmitLogIn() {
        WebElement submitLogIn = driver.findElement(By.id("submit-login"));
        submitLogIn.click();
    }

    @Then("Search (.*) in search box")
    public void SearchSweater(String searchItem) {
        WebElement searchSweater = driver.findElement(By.name("s"));
        searchSweater.click();
        searchSweater.clear();
        searchSweater.sendKeys(searchItem);
        searchSweater.submit();
    }

    @And("Open product page")
    public void OpenProductPage() {
        WebElement openProductPage = driver.findElement(By.xpath("//*[@id=\"js-product-list\"]/div[1]/article[1]/div/div[1]/h2/a"));
        openProductPage.click();
    }

    @And("Check if there is discount (.*)")
    public void CheckDiscount(String discount) {
        String checkDiscount = driver.findElement(By.className("current-price")).getText();
        Assert.assertTrue("Text not found!", checkDiscount.contains(discount));
    }

    @Then("Select corect (.*)")
    public void SelectSize(String size) {
        WebElement selectSize = driver.findElement(By.cssSelector("[title*='" + size + "']"));
        if (size.equals("S")) { //do zmiany na detect

        } else {
            selectSize.click();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
    }

    @And("Set products (.*)")
    public void NumberOfProducts(String numbers) {
        WebElement numberOfProducts = driver.findElement(By.id("quantity_wanted")); //need wait
        numberOfProducts.click();
        numberOfProducts.clear();
        numberOfProducts.sendKeys(numbers);
    }

    @Then("Add products to shopping cart")
    public void AddProductsToShoppingCart() {
        WebElement addProductToShoppingCart = driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button"));
        addProductToShoppingCart.click();
        // dodac wykrywanie czy produkt dostepny
    }

    @And("Go to checkout")
    public void GoToCheckout() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
        }
        WebElement goToCheckout = driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a"));
        goToCheckout.click();
        WebElement goToCheckout2 = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[1]/div[2]/div/a"));
        goToCheckout2.click();
    }

    @Then("Fill address information with address:(.*), postcode:(.*) and city:(.*)")
    public void FillAddressInformation(String address, String postcode, String city) {
        WebElement eaddress = driver.findElement(By.name("address1"));
        WebElement epostcode = driver.findElement(By.name("postcode"));
        WebElement ecity = driver.findElement(By.name("city"));
        WebElement country = driver.findElement(By.xpath("/html/body/section/div/section/div/div[1]/section[2]/div/div/form/div/div/section/div[9]/div[1]/select/option[2]"));
        eaddress.click();
        eaddress.clear();
        eaddress.sendKeys(address);
        epostcode.click();
        epostcode.clear();
        epostcode.sendKeys(postcode);
        ecity.click();
        ecity.clear();
        ecity.sendKeys(city);
        country.click();
    }

    @And("Continue to shipping method")
    public void ContinueToShipping() {
        WebElement continueToShipping = driver.findElement(By.name("confirm-addresses"));
        continueToShipping.click();
    }

    @Then("Get \"PrestaShop\" Pick up in-store method")
    public void PickUpInStore() {
        WebElement pickUpInStore = driver.findElement(By.xpath("/html/body/section/div/section/div/div[1]/section[3]/div/div[2]/form/div/div[1]/div[1]/label"));
        pickUpInStore.click();
    }

    @And("Continue to payment")
    public void ContinuePayment() {
        WebElement continuePayment = driver.findElement(By.name("confirmDeliveryOption"));
        continuePayment.click();
    }

    @Then("Select \"Pay by check\" method")
    public void PayByCheck() {
        WebElement payByCheck = driver.findElement(By.name("payment-option"));
        payByCheck.click();
    }

    @And("Agree to the terms of service")
    public void AgreeTerms() {
        WebElement agreeTerms = driver.findElement(By.name("conditions_to_approve[terms-and-conditions]"));
        agreeTerms.click();
    }

    @And("Click \"order with an obligation to pay\"")
    public void ObligationToPayButton() {
        WebElement obligationToPayButton = driver.findElement(By.xpath("/html/body/section/div/section/div/div[1]/section[4]/div/div[3]/div[1]/button"));
        obligationToPayButton.click();
    }

    @Then("Take screenshot with order confirmation and total amount")
    public void TakeScreenshotWithOrderConfirmation() throws IOException {
        WebElement elementTotalAmount = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section[2]/div/div/div[1]/div/table/tbody/tr[3]/td[2]"));
        WebElement elementorderReference = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section[2]/div/div/div[2]/ul/li[1]"));
        totalAmount = elementTotalAmount.getText();
        String orderReferenceFull = elementorderReference.getText();
        orderReference = orderReferenceFull.substring(17);
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("C:\\TestScreenshot\\" + orderReference + ".png"));

    }

    @Then("Go to \"Order history and details\"")
    public void OrderHistoryGo() {
        WebElement accountTopButton = driver.findElement(By.className("account"));
        accountTopButton.click();
        WebElement orderHistoryButton = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div/div/a[3]"));
        orderHistoryButton.click();
    }

    @And("Check if your order is there with status \"Awaiting check payment\" and same total amount")
    public void CheckOrderStatusAndTotalAmount() {
        String orderHistory = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/table/tbody/tr[1]")).getText();

        Assert.assertTrue("Text not found!", orderHistory.contains(totalAmount));
        Assert.assertTrue("Text not found!", orderHistory.contains(orderReference));
        Assert.assertTrue("Text not found!", orderHistory.contains("Awaiting check payment"));
    }

    @Then("Delete address information")
    public void deleteAddressInformation() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=addresses");
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[1]/article/div[2]/a[2]")).click();
    }

    @Then("Close browser")
    public void closeBrowser() {
        driver.quit();
    }
}
