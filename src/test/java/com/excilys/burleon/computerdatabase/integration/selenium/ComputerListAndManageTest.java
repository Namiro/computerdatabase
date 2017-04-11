
package com.excilys.burleon.computerdatabase.integration.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.excilys.burleon.computerdatabase.view.web.constant.Data;

public class ComputerListAndManageTest {

    @BeforeClass
    public static void setUpBeforeClass() {
        // System.setProperty("webdriver.chrome.driver",
        // "/home/excilys/Programs/chromedriver");

    }

    private WebDriver driver;
    private String baseUrl;

    private final StringBuffer verificationErrors = new StringBuffer();

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
        final DesiredCapabilities cap = new DesiredCapabilities();
        cap.setJavascriptEnabled(true);
        cap.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/opt/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        this.driver = new PhantomJSDriver(cap);
        this.baseUrl = "http://localhost:8080";
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
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
     * Test to change page
     *
     * @throws Exception
     *             Exception
     */
    @Ignore
    @Test
    public void testChangeNumberPerPage() {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));

        this.driver.findElement(By.id("paginationRecordsByPage50")).click();
        Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 51);

        this.driver.findElement(By.id("paginationRecordsByPage100")).click();
        Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 101);

        this.driver.findElement(By.id("paginationRecordsByPage20")).click();
        Assert.assertTrue(this.driver.findElements(By.xpath("//tr")).size() == 21);
    }

    /**
     * Test to create a computer OK.
     *
     * @throws Exception
     *             Exception
     */
    @Ignore
    @Test
    public void testCreateComputerKo() {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));

        this.driver.findElement(By.id("addComputer")).click();
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Manage Computer"));
        Assert.assertFalse(this.isElementPresent(By.id(Data.MESSAGE_SUCCESS)));
        Assert.assertFalse(this.isElementPresent(By.id(Data.MESSAGE_ERROR)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_CREATE)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_CREATE)));
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).sendKeys("");
        this.driver.findElement(By.id(Data.COMPUTER_INTRODUCE_DATE)).sendKeys("24/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_DISCONTINUE_DATE)).sendKeys("21/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID)).click();
        new Select(this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID))).selectByVisibleText("Netronics");
        Assert.assertTrue(this.driver.findElement(By.id(Data.SUBMIT_CREATE)).isEnabled());

        this.driver.findElement(By.id(Data.COMPUTER_NAME)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).sendKeys("testSelenium");
        this.driver.findElement(By.id(Data.SUBMIT_CREATE)).click();

        Assert.assertTrue(this.isElementPresent(By.id(Data.MESSAGE_ERROR)));
    }

    /**
     * Test to create a computer OK.
     *
     * @throws Exception
     *             Exception
     */
    @Ignore
    @Test
    public void testCreateComputerOk() {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));

        this.driver.findElement(By.id("addComputer")).click();
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Manage Computer"));
        Assert.assertFalse(this.isElementPresent(By.id(Data.MESSAGE_SUCCESS)));
        Assert.assertFalse(this.isElementPresent(By.id(Data.MESSAGE_ERROR)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_CREATE)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_CREATE)));
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).sendKeys("Test Computer Selenium");
        Assert.assertTrue(this.driver.findElement(By.id(Data.SUBMIT_CREATE)).isEnabled());
        this.driver.findElement(By.id(Data.COMPUTER_INTRODUCE_DATE)).sendKeys("12/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_DISCONTINUE_DATE)).sendKeys("13/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID)).click();
        new Select(this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID))).selectByVisibleText("Netronics");
        this.driver.findElement(By.id(Data.SUBMIT_CREATE)).click();

        Assert.assertTrue(this.isElementPresent(By.id(Data.MESSAGE_SUCCESS)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_DELETE)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_SAVE)));
    }

    /**
     * Test to delete a computer.
     *
     * @throws Exception
     *             Exception
     */
    @Ignore
    @Test
    public void testDeleteComputer() {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));

        this.driver.findElement(By.id("addComputer")).click();
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Manage Computer"));
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).clear();
        this.driver.findElement(By.id(Data.COMPUTER_NAME)).sendKeys("Test Computer Selenium");
        this.driver.findElement(By.id(Data.COMPUTER_INTRODUCE_DATE)).sendKeys("12/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_DISCONTINUE_DATE)).sendKeys("13/02/2010");
        this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID)).click();
        new Select(this.driver.findElement(By.id(Data.COMPUTER_COMPANY_ID))).selectByVisibleText("Netronics");
        this.driver.findElement(By.id(Data.SUBMIT_CREATE)).click();

        Assert.assertTrue(this.isElementPresent(By.id(Data.MESSAGE_SUCCESS)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_DELETE)));
        Assert.assertTrue(this.isElementPresent(By.id(Data.SUBMIT_SAVE)));

        this.driver.findElement(By.id(Data.SUBMIT_DELETE)).click();
        Assert.assertTrue(this.isElementPresent(By.id(Data.MESSAGE_SUCCESS)));
    }

    /**
     * Test to search a computer.
     *
     * @throws Exception
     *             Exception
     */
    @Ignore
    @Test
    public void testSearchComputer() {
        this.driver.get(this.baseUrl + "/ComputerDatabase/ComputerList");
        Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("Computers found"));

        this.driver.findElement(By.id(Data.SEARCH_WORD)).clear();
        this.driver.findElement(By.id(Data.SEARCH_WORD)).sendKeys("MC");

        this.driver.findElement(By.id(Data.SUBMIT_SEARCH)).click();

        // Assert.assertTrue(this.driver.findElement(By.tagName("h1")).getText().contains("4"));
    }
}
