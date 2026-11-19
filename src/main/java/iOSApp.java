import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class iOSApp {

    String userName = LTCredentials.USERNAME; // Loaded from .env
    String accessKey = LTCredentials.ACCESS_KEY; // Loaded from .env
    String app_id = "lt://APP10160412181794557783431808";      //Enter your LambdaTest App ID at the place of lt://proverbial-android
    String grid_url = System.getenv("LT_GRID_URL") == null ? "mobile-hub.lambdatest.com" : System.getenv("LT_GRID_URL");

    AppiumDriver driver;

    @Test
    @org.testng.annotations.Parameters(value = {"device", "version", "platform"})
    public void iOSApp1(String device, String version, String platform) throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "TestNG Run");
        capabilities.setCapability("name", platform + " " + device + " " + version);
        capabilities.setCapability("deviceName", device);
        capabilities.setCapability("platformVersion", version);
        capabilities.setCapability("platformName", platform);
        capabilities.setCapability("isRealMobile", true);
        capabilities.setCapability("app", app_id); //Enter your app url
        capabilities.setCapability("network", false);
        capabilities.setCapability("project", "MaverickProjectTesting");
        capabilities.setCapability("visual", true);
        capabilities.setCapability("devicelog", true);
        //capabilities.setCapability("geoLocation", "HK");

        String hub = "https://" + userName + ":" + accessKey + "@" + grid_url + "/wd/hub";
        driver = new AppiumDriver(new URL(hub), capabilities);

        WebDriverWait Wait = new WebDriverWait(driver, 30);

        //Changes the color of the text
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("color"))).click();
        Thread.sleep(1000);

        //Changes the text to "Proverbial"
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Text"))).click();
        Thread.sleep(1000);

        //Toast will be visible
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("toast"))).click();
        Thread.sleep(1000);

        //Notification will be visible
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("notification"))).click();
        Thread.sleep(4000);

        //Opens the geolocation page
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("geoLocation"))).click();
        Thread.sleep(4000);

        //Takes back
        driver.navigate().back();

        //Takes to speedtest page
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("speedTest"))).click();
        Thread.sleep(4000);
        driver.navigate().back();

        //Opens the browser
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Browser"))).click();
        Thread.sleep(1000);
        MobileElement url = (MobileElement) driver.findElementByAccessibilityId("url");
        url.click();
        url.sendKeys("https://www.lambdatest.com");
        Wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("find"))).click();
        Thread.sleep(1000);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return; // session was never created
        }
        try {
            // Mark the session on the LambdaTest dashboard
            String status = result.isSuccess() ? "passed" : "failed";
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
        } catch (Exception e) {
            System.err.println("Could not set lambda-status: " + e.getMessage());
        } finally {
            // Always end the session so it never sits until the idle timeout
            driver.quit();
            driver = null;
        }
    }
}
