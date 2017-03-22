package com.excilys.burleon.computerdatabase.integration.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.excilys.burleon.computerdatabase.view.web.constant.Data;
import com.excilys.burleon.computerdatabase.view.web.constant.Servlet;

public class CreateComputerTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private final StringBuffer verificationErrors = new StringBuffer();

    /**
     * Close alert and get its text.
     *
     * @return Alert text
     */
    private String closeAlertAndGetItsText() {
        try {
            final Alert alert = this.driver.switchTo().alert();
            final String alertText = alert.getText();
            if (this.acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            this.acceptNextAlert = true;
        }
    }

    /**
     * Check if the alert is present.
     *
     * @return True Or False
     */
    private boolean isAlertPresent() {
        try {
            this.driver.switchTo().alert();
            return true;
        } catch (final NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Check if an element is present.
     *
     * @param by
     *            Element id
     * @return True Or False
     */
    private boolean isElementPresent(final By by) {
        try {
            this.driver.findElement(by);
            return true;
        } catch (final NoSuchElementException e) {
            return false;
        }
    }

    /**
     *
     * @throws Exception
     *             Exception
     */
    @Before
    public void setUp() throws Exception {
        this.driver = new FirefoxDriver();
        this.baseUrl = "http://localhost:8080";
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /**
     *
     * @throws Exception
     *             Exception
     */
    @After
    public void tearDown() throws Exception {
        this.driver.quit();
        final String verificationErrorString = this.verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    /**
     * Test to create a computer.
     *
     * @throws Exception
     *             Exception
     */
    @Test
    public void testCreateComputer() throws Exception {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertEquals(this.driver.findElement(By.tagName("h1")).getText(), "Manage Computer");

        this.driver.findElement(By.id(Servlet.SERVLET_COMPUTER_MANAGE)).click();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).sendKeys("Test Computer Selenium");
        this.driver.findElement(By.id(Data.COMPUTER_INTRODUCE_DATE)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_INTRODUCE_DATE)).sendKeys("12/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_DISCONTINUE_DATE)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_DISCONTINUE_DATE)).sendKeys("13/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID)).click();
        new Select(this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID))).selectByVisibleText("RCA");
        this.driver.findElement(By.id(Data.SUBMIT_CREATE)).click();

        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
    }
}
