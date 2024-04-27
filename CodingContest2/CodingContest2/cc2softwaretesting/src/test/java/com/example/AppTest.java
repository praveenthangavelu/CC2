package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
    WebDriver driver;
    ExtentReports reports;
    ExtentTest test;
    Logger logger = Logger.getLogger(AppTest.class);

    @BeforeTest
    public void setup() {
        reports = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(
                "C:\\Users\\kapil\\Downloads\\cc2softwaretesting\\cc2softwaretesting\\src\\test\\java\\com\\example\\report.html");
        reports.attachReporter(spark);
        test = reports.createTest("Demo Result");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        PropertyConfigurator.configure(
                "C:\\Users\\kapil\\Downloads\\cc2softwaretesting\\cc2softwaretesting\\src\\test\\java\\com\\resources\\log4j.properties");
    }

    @BeforeMethod
    public void navigateUrl() {
        driver.navigate().to("https://www.barnesandnoble.com/");
    }

    @Test(priority = 0)
    public void testCase1() throws IOException, InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[1]/a")).click();
        driver.findElement(By.linkText("Books")).click();
        FileInputStream fs = new FileInputStream(
                "C:\\Users\\kapil\\Downloads\\cc2softwaretesting\\cc2softwaretesting\\src\\test\\java\\com\\resources\\cc2.xlsx");
        XSSFWorkbook work = new XSSFWorkbook(fs);
        XSSFSheet sheet = work.getSheet("Bank login");
        // XSSFRow row = sheet.getRow(6);
        // String input = row.getCell(0).getStringCellValue();
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/div[2]/div/input[1]"))
                .sendKeys("Chetan Bhagat");
        driver.findElement(By.xpath("//*[@id=\"rhf_header_element\"]/nav/div/div[3]/form/div/span/button")).click();
        Thread.sleep(5000);
        String name = driver
                .findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1"))
                .getText();
        if (name.contains("Chetan Bhagat")) {
            logger.info("The text contains Chetan Bhagat");
        } else {
            logger.error("The text doesnot contains Chetan Bhagat");
        }
    }

    @Test(priority = 1)
    public void testCase2() throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//*[@id=\"rhfCategoryFlyout_Audiobooks\"]"))).perform();
        ;
        driver.findElement(
                By.xpath("//*[@id=\"navbarSupportedContent\"]/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]"))
                .click();
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler"))).click();

        driver.findElement(By.xpath(
                "/html/body/main/div[2]/div[1]/div/div[2]/div/div[2]/section[2]/ol/li[1]/div/div[2]/div[5]/div[2]/div/div/form/input[11]"))
                .click();
    }

    @Test(priority = 2)
    public void testCase3() throws InterruptedException, IOException {
        // Wait for the cookie consent button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
        acceptButton.click();

        // Click on the desired element after the cookie consent is accepted
        driver.findElement(By.xpath("/html/body/div[4]/div/dd/div/div/div[1]/div/a[5]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"rewards-modal-link\"]")).click();
        Thread.sleep(2000);

        // Take a screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "C:\\Users\\kapil\\Downloads\\cc2softwaretesting\\cc2softwaretesting\\src\\test\\java\\com\\resources\\calculator.png";
        FileUtils.copyFile(screenshotFile, new File(screenshotPath));
    }
}
