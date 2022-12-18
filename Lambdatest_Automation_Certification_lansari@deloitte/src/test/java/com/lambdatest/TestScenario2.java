package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario2 {

  private RemoteWebDriver driver;

  @BeforeMethod
  public void setup(Method m, ITestContext ctx) throws MalformedURLException {
    String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
    String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");

    String hub = "@hub.lambdatest.com/wd/hub";

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("platform", "macOS Sierra");
    caps.setCapability("browserName", "edge");
    caps.setCapability("version", "87.0");
    caps.setCapability("build", "Lambdatest Automation Exame");
    caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
    caps.setCapability("plugin", "java-testNG");
    caps.setCapability("terminal", true);
    caps.setCapability("console", true);
    caps.setCapability("network", true);
    caps.setCapability("username", "lansari");
    caps.setCapability("accessKey", "kb1Ai3Ld1Fpi7RWyd55zGDy97h0fLQgsfQmUOErJS9kJxga6k5");
    caps.setCapability("video", true);
    caps.setCapability("visual", true);

    String[] customTags = new String[] { "Leonardo Ansari", "lansari@deloitte.pt",
        "Module 2: Automation Testing Certification", "Edge + 87.0 + Mac (Test Scenario 2)" };
    caps.setCapability("tags", customTags);

    driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
  }

  @Test(timeOut = 20000) // 2. TimeOut of the test duration should be set to 20 seconds.
  public void basicTest() throws InterruptedException {
    System.out.println("Loading Url");

    driver.manage().window().maximize();
    System.out.println("1. Navigate to https://www.lambdatest.com.\n"
        + "2. Perform an explicit wait till the time all the elements in the DOM are available.");
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    driver.get("https://lambdatest.com");
    System.out.println("3. scrolling down");
    // Using more than 3 different Locators (className, linkText and xpath)
    driver.findElements(
      By.className("uppercase font-bold text-black text-size-16 tracking-widest inline-block hover:underline"));
    driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.linkText("SEE ALL INTEGRATIONS")));
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollBy(0,-200)");
      Thread.sleep(500);

    System.out.println("4. Click on the link and ensure that it opens in a new Tab.");
    // Open the link in a new tab
    driver.findElement(By.xpath(
        "//a[@href='https://www.lambdatest.com/integrations' and @class='uppercase font-bold text-black text-size-16 tracking-widest inline-block hover:underline']"))
        .sendKeys(Keys.COMMAND, Keys.RETURN);

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