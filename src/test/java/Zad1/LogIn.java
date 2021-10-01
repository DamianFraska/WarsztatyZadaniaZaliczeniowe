package Zad1;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;


public class LogIn {
    private WebDriver driver;


    @Given("Open browser with https://mystore-testlab.coderslab.pl/index.php")
    public void openSite() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

    @Then("Addresses button clicked")
    public void AddressesButtonClick() {
        WebElement addressesButtonClick = driver.findElement(By.xpath("//*[@id=\"footer_account_list\"]/li[4]/a"));
        addressesButtonClick.click();
    }

    @Then("Create new address button clicked")
    public void CreateNewAddressButtonClick() {
        WebElement createNewAddressButtonClick = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/a/span"));
        createNewAddressButtonClick.click();
    }

    @Then("Fill sheet with (.*), (.*), (.*), (.*), (.*), (.*), (.*) and (.*)")
    public void FillSheet(String alias, String company, String vat, String address1, String address2, String postcode, String city, String phone) {
        WebElement aliasField = driver.findElement(By.name("alias"));
        WebElement companyField = driver.findElement(By.name("company"));
        WebElement vatField = driver.findElement(By.name("vat_number"));
        WebElement address1Field = driver.findElement(By.name("address1"));
        WebElement address2Field = driver.findElement(By.name("address2"));
        WebElement postcodeField = driver.findElement(By.name("postcode"));
        WebElement cityField = driver.findElement(By.name("city"));
        WebElement phoneField = driver.findElement(By.name("phone"));
        WebElement countryField = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/form/section/div[10]/div[1]/select/option[2]"));


        aliasField.click();
        aliasField.clear();
        aliasField.sendKeys(alias);
        companyField.click();
        companyField.clear();
        companyField.sendKeys(company);
        vatField.click();
        vatField.clear();
        vatField.sendKeys(vat);
        address1Field.click();
        address1Field.clear();
        address1Field.sendKeys(address1);
        address2Field.click();
        address2Field.clear();
        address2Field.sendKeys(address2);
        postcodeField.click();
        postcodeField.clear();
        postcodeField.sendKeys(postcode);
        cityField.click();
        cityField.clear();
        cityField.sendKeys(city);
        phoneField.click();
        phoneField.clear();
        phoneField.sendKeys(phone);
        countryField.click();
    }

    @And("Submit sheet")
    public void SubmitSheet() {
        WebElement submitSheet = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/form/footer/button"));
        submitSheet.click();
    }

    @And("Check if address data is corect (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*) and (.*)")
    public void CheckAddressData(String firstName, String lastName, String alias, String company, String vat, String address1, String address2, String postcode, String city, String country, String phone) {


        String bodyText = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[1]/article/div[1]")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains(alias));
        Assert.assertTrue("Text not found!", bodyText.contains(firstName + " " + lastName));
        Assert.assertTrue("Text not found!", bodyText.contains(company));
        Assert.assertTrue("Text not found!", bodyText.contains(vat));
        Assert.assertTrue("Text not found!", bodyText.contains(address1));
        Assert.assertTrue("Text not found!", bodyText.contains(address2));
        Assert.assertTrue("Text not found!", bodyText.contains(postcode));
        Assert.assertTrue("Text not found!", bodyText.contains(city));
        Assert.assertTrue("Text not found!", bodyText.contains(phone));
        Assert.assertTrue("Text not found!", bodyText.contains(country));
    }

    @Then("Delete address")
    public void DeleteAddress() {
        WebElement deleteAddress = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[1]/article/div[2]/a[2]"));
        deleteAddress.click();
    }

    @And("Check if address is deleted")
    public void DeleteAddressCheck() {
        Assert.assertEquals(0, driver.findElements(By.xpath("/html/body/main/section/div/div/section/section/div[1]/article/div[2]/a[2]")).size());
    }

    @Then("Quit browser")
    public void quitBrowser() {
        driver.quit();
    }
}