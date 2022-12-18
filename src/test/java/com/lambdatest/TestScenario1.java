package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario1 {

  private RemoteWebDriver driver;

  @BeforeMethod
  public void setup(Method m, ITestContext ctx) throws MalformedURLException {
    String username = "lansari";
    String accessKey = "kb1Ai3Ld1Fpi7RWyd55zGDy97h0fLQgsfQmUOErJS9kJxga6k5";

    String hub = "@hub.lambdatest.com/wd/hub";

    ChromeOptions browserOptions = new ChromeOptions();
    browserOptions.setPlatformName("Windows 10");
    browserOptions.setBrowserVersion("86.0");
    HashMap<String, Object> ltOptions = new HashMap<String, Object>();
    ltOptions.put("username", "lansari");
    ltOptions.put("accessKey", "kb1Ai3Ld1Fpi7RWyd55zGDy97h0fLQgsfQmUOErJS9kJxga6k5");
    ltOptions.put("visual", true);
    ltOptions.put("video", true);
    ltOptions.put("network", true);
    ltOptions.put("console", true);
    ltOptions.put("terminal", true);
    ltOptions.put("build", "Lambdatest Automation Exame");
    ltOptions.put("project", "Lambdatest Module 2 Exame");
    ltOptions.put("name", m.getName() + " - " + this.getClass().getName());
    String[] customTags = { "Leonardo Ansari", "lansari@deloitte.pt", "Module 2: Automation Testing Certification",
        "Chrome + 86.0 + Windows 10 (Test Scenario 1)" };
    ltOptions.put("tags", customTags);
    ltOptions.put("selenium_version", "4.1.0");
    ltOptions.put("driver_version", "86.0");
    ltOptions.put("plugin", "java-testNG");
    ltOptions.put("w3c", true);
    browserOptions.setCapability("LT:Options", ltOptions);

    driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), browserOptions);
  }

  @Test(timeOut = 20000) // 2. TimeOut of the test duration should be set to 20 seconds.
  public void basicTest() throws InterruptedException {
    System.out.println("Loading Url");

    System.out.println("1. Navigate to https://www.lambdatest.com.\n");
    driver.manage().window().maximize();
    driver.get("https://lambdatest.com");

    System.out.println("2. Perform an explicit wait till the time all the elements in the DOM are available.");
    // Using more than 3 different Locators (cssSelector, linkText and xpath)
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*")));

    System.out.println("3. scrolling down");
    driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.linkText("SEE ALL INTEGRATIONS")));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,-220)");
    Thread.sleep(400);

    System.out.println("4. Click on the link and ensure that it opens in a new Tab.");
    // Open the link in a new tab
    driver.findElement(By.xpath(
        "//a[@href='https://www.lambdatest.com/integrations' and @class='uppercase font-bold text-black text-size-16 tracking-widest inline-block hover:underline']"))
        .sendKeys(Keys.CONTROL, Keys.SHIFT, Keys.RETURN);

    System.out.println(
        "5. Save the window handles in a List (or array). Print the window handles of the opened windows (now there are two windows open).\n");

    // Get all the window handles
    List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());

    // Print the window handles
    for (String handle : windowHandles) {
      System.out.println(handle);
    }
    driver.switchTo().window(windowHandles.get(1));

    System.out.println("\n6. Verify whether the URL is the same as the expected URL (if not, throw an Assert).\n");

    // Verify that the current URL is the same as the expected URL
    // Get the current URL of the page
    String currentURL = driver.getCurrentUrl();
    // Set the expected URL
    String expectedURL = "https://www.lambdatest.com/integrations";
    // Throwing an Assert
    Assert.assertEquals(currentURL, expectedURL);
    System.out.println("The URLs are the same. Pass.\n");
    Thread.sleep(255);

    System.out.println("7. Close the current browser window.");

    // Close browse window
    driver.close();
    Thread.sleep(600);
  }

  @AfterMethod
  public void tearDown() {
    System.out.println("TestFinished");
    driver.quit();
  }
}
