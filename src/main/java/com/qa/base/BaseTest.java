package com.qa.base;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;

public class BaseTest {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal<HashMap<String,String>> strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal<String> platform = new ThreadLocal<String>();
    protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static AppiumDriverLocalService server;
    TestUtils utils = new TestUtils();

    public AppiumDriver getDriver() {
        return driver.get();
    }
    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }
    public Properties getProps() {
        return props.get();
    }
    public void setProps(Properties props2) {
        props.set(props2);
    }
    public HashMap<String,String> getString() {
       return strings.get();
    }
    public void setString(HashMap<String, String> strings2) {
        strings.set(strings2);
    }
    public String getPlatform() {
        return platform.get();
    }
    public void setPlatform(String platform2) {
        platform.set(platform2);
    }
    public String getDateTime() {
        return dateTime.get();
    }
    public void setDateTime(String dateTime2) {
        dateTime.set(dateTime2);
    }
    public String getDeviceName() {
        return deviceName.get();
    }
    public void setDeviceName(String deviceName2 ) {
        deviceName.set(deviceName2);
    }
    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()),this);
    }

    @BeforeSuite
    public void beforeSuite() {
        ThreadContext.put("ROUTINGKEY","ServerLogs");
        server = getAppiumServer();
        if (!checkIfAppiumServerIsRunning(4723)) {
            server.start();
            server.clearOutPutStreams();
            utils.log().info("Appium  server started");
        }
        else {
            utils.log().info("Appium server already running");
        }
    }
//
    public AppiumDriverLocalService getAppiumServer() {
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH","/Library/Java/JavaVirtualMachines/jdk-14.0.2.jdk/Contents/Home/bin:/Users/baemin/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin" +
                System.getenv("PATH"));
        environment.put("ANDROID_HOME","/Users/baemin/Library/Android/sdk");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
                .withLogFile(new File("ServerLogs/server.log")));
    }

    @AfterSuite
    public void afterSuite() {
        server.stop();
        utils.log().info("Appium server stop");
    }


    public boolean checkIfAppiumServerIsRunning(int port) {
        boolean isAppiumRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (Exception e) {
            System.out.println("1");
            isAppiumRunning = true;
        } finally {
            socket  = null;
        }
        return isAppiumRunning;
    }
    //
    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    @Parameters({"emulator","platformName","deviceName"})
    @BeforeTest
    public void beforeTest(String emulator, String platformName, String deviceName) throws ParserConfigurationException, SAXException, IOException {
        setDateTime(utils.dateTime());
        setPlatform(platformName);
        setDeviceName(deviceName);
        URL url;
        InputStream inputStream = null;
        InputStream stringis = null;
        Properties props = new Properties();
        AppiumDriver driver;
        String strFile = "log" + File.separator + platformName + deviceName;
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdir();
        }
        ThreadContext.put("ROUTINGKEY",strFile);
        utils.log().info("log path : " +strFile);
        try {
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";
            utils.log().info("log" + propFileName);
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            utils.log().info("log" + xmlFileName);
            stringis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            setString(utils.parseStringXML(stringis));
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName",platformName);
            desiredCapabilities.setCapability("deviceName",deviceName);
            desiredCapabilities.setCapability("appPackage",props.getProperty("androidAppPackage"));
            desiredCapabilities.setCapability("appActivity",props.getProperty("androidAppActivity"));
            url = new URL(props.getProperty("appiumURL"));
            driver = new AndroidDriver(url,desiredCapabilities);
            setDriver(driver);
            utils.log().info("driver initialized : " + driver);
        } catch (Exception e) {
            utils.log().info("driver initialization failure " + e.toString());
            throw  e;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (stringis != null) {
                stringis.close();
            }
        }

    }
    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(getDriver(), utils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    public void waitForVisibility(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void clear(MobileElement e) {
        waitForVisibility(e);
        e.clear();
    }
    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }
    public void click(MobileElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO,msg);
        e.click();
    }
    public void sendKeys(MobileElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }
    public void sendKey(MobileElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO,msg);
        e.sendKeys(txt);
    }
    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }
    public void getTitle(MobileElement e) {
        waitForVisibility(e);
        e.getText();
    }
    public String getText(MobileElement e, String msg) {
        String txt = null;
        txt = getAttribute(e, "text");
        utils.log().info(msg + txt);
        ExtentReport.getTest().log(Status.INFO, msg);
        return txt;
    }
    public void closeApp() {
        getDriver().closeApp();
    }
    public void launchApp() {
        getDriver().launchApp();
    }
    public MobileElement scrollToElement() {
        return (MobileElement) ((FindsByAndroidUIAutomator) getDriver()).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector() " + ".scrollable((true)).scrollIntoView(" +
                        " new UiSelector().description(\"test-Price\"));");
    }

    @AfterTest
    public void afterTest() {
      getDriver().quit();
    }

}
