package com.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.BaseTest;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestListeners implements ITestListener {
    TestUtils utils = new TestUtils();
    @Override
    public void onTestStart(ITestResult result) {
        BaseTest baseTest = new BaseTest();
        ExtentReport.startTest(result.getName(),result.getMethod().getDescription())
                .assignCategory(baseTest.getPlatform() + baseTest.getDeviceName())
                .assignAuthor("Truong");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS,"Test passed");
        BaseTest test = new BaseTest();
        File file = test.getDriver().getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> params = new HashMap<String, String>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator + params.get("platformName")
                + params.get("deviceName") + File.separator  + test.getDateTime() + File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file,new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExtentReport.getTest().pass("Test passed",MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
            ExtentReport.getTest().pass("Test passed",MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded,StandardCharsets.US_ASCII)).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace();
            utils.log().error(pw.toString());
        }
        BaseTest baseTest = new BaseTest();
        File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> params = new HashMap<String, String>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator + params.get("platformName")
                + "-" + params.get("deviceName") + File.separator + baseTest.getDateTime() + File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file, new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ExtentReport.getTest().fail("Test Failed" ,
                    MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
            ExtentReport.getTest().fail("Test Failed",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        } catch(Exception e) {
            e.printStackTrace();
        }
        ExtentReport.getTest().fail(result.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP,"Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getReport().flush();
    }
}
