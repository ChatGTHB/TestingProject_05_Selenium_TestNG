package project_05_Selenium_TestNG;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseDriverParameter;


public class Tests extends BaseDriverParameter {

    String randomMail;

    @Test(priority = 1)
    void loginTest() {
//        TestsElements te = new TestsElements(driver);
//
//        te.eMail.clear();
//        te.eMail.sendKeys("admin@yourstore.com");
//        te.password.clear();
//        te.password.sendKeys("admin");
//        te.loginButton.click();
//        Assert.assertTrue(te.logoutLink.isDisplayed());
    }

    @Test(priority = 2)
    void checkLeftNavMenu() {

        TestsElements elements = new TestsElements(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 1; i < elements.navMenu.size(); i++) {
            wait.until(ExpectedConditions.visibilityOfAllElements(elements.navMenu));
            js.executeScript("arguments[0].scrollIntoView(true);", elements.navMenu.get(i));
            // System.out.println("elements.navMenu.get(i) = " + elements.navMenu.get(i).getText());
            elements.navMenu.get(i).click();
            Assert.assertTrue(elements.navAltMenu.get(i).isEnabled());
            // System.out.println("elements.navAltMenu.get(0) = " + elements.navAltMenu.get(0).getText());
            Assert.assertTrue(elements.navAltMenu.get(i).isDisplayed());
        }
    }

    @Test(priority = 3)
    void createCustomer() {

        TestsElements elements = new TestsElements(driver);
        Actions actions = new Actions(driver);
        randomMail = "testing" + (int) (Math.random() * 10000) + "@email.com";

        wait.until(ExpectedConditions.visibilityOfAllElements(elements.navMenu));
        elements.navMenu.get(3).click();

        wait.until(ExpectedConditions.visibilityOfAllElements(elements.navAltMenu));
        elements.navAltMenu.get(0).click();

        elements.addButton.click();

        Action action = actions.click(elements.customerCreateInputs.get(0))
                .sendKeys(randomMail)
                .sendKeys(Keys.TAB)
                .sendKeys("password")
                .sendKeys(Keys.TAB)
                .sendKeys("First name")
                .sendKeys(Keys.TAB)
                .sendKeys("Last name").build();
        action.perform();
        elements.customerCreateGenders.get(0).click();

        action = actions.click(elements.customerCreateBirthdayCalender)
                .sendKeys("01.01.2000")
                .sendKeys(Keys.TAB)
                .sendKeys("Company name").build();
        action.perform();

        elements.customerCreateTaxInput.click();
        elements.customerCreateInputs.get(8).click();
        elements.customerCreateNewsletters.get(0).click();

        action = actions.click(elements.customerCreateInputs.get(10))
                .sendKeys("Vendor 1")
                .click()
                .sendKeys(Keys.TAB)
                .sendKeys(Keys.TAB)
                .sendKeys("Admin comment").build();
        action.perform();

        elements.saveButton.click();

        Assert.assertTrue(elements.successMessage.isDisplayed());
    }

    @Test(priority = 4, dependsOnMethods = "createCustomer")
    void editCustomer() {

        TestsElements te = new TestsElements(driver);
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.visibilityOfAllElements(te.navMenu));

        wait.until(ExpectedConditions.elementToBeClickable(te.customersSearchEmail));

        actions.click(te.customersSearchEmail)
                .sendKeys(randomMail)
                .sendKeys(Keys.TAB)
                .sendKeys("First name")
                .sendKeys(Keys.TAB)
                .sendKeys("Last name")
                .build().perform();

        te.customerSearchButton.click();

        js.executeScript("arguments[0].scrollIntoView(true);", te.searchCustomerMailList.get(0));
        Assert.assertFalse(te.searchCustomerMailList.isEmpty());

        js.executeScript("arguments[0].scrollIntoView(true);", te.customerEditButton);
        js.executeScript("arguments[0].click();", te.customerEditButton);

        actions.click(te.customerCreateInputs.get(0))
                .sendKeys(".tr").build().perform();

        te.saveButton.click();

        Assert.assertTrue(te.successMessage.isDisplayed());
    }

    @Test(priority = 5, dependsOnMethods = {"createCustomer", "editCustomer"})
    void deleteCustomer() {

        TestsElements te = new TestsElements(driver);
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        actions.click(te.customersSearchEmail)
                .sendKeys(randomMail + ".tr")
                .sendKeys(Keys.TAB)
                .sendKeys("First name")
                .sendKeys(Keys.TAB)
                .sendKeys("Last name").build().perform();

        te.customerSearchButton.click();

        js.executeScript("arguments[0].scrollIntoView(false);", te.customerEditButton);
        js.executeScript("arguments[0].click();", te.customerEditButton);

        wait.until(ExpectedConditions.elementToBeClickable(te.deleteButton));
        js.executeScript("arguments[0].scrollIntoView(false);", te.deleteButton);
        js.executeScript("arguments[0].click();", te.deleteButton);
        js.executeScript("arguments[0].click();", te.deleteConfirm);

        Assert.assertTrue(te.successMessage.isDisplayed());
    }

    @Test(priority = 6)
    void searchTest() {

        TestsElements te = new TestsElements(driver);
        Actions actions = new Actions(driver);

        te.searchBox.sendKeys("Shipments");

        te.searchShipments.click();

        Assert.assertTrue(te.shipmentsTextConfirm.isDisplayed());
    }
}
