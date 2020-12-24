package com.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.Map;

public class ExtentReport {
    static ExtentReports extentReports;
    final static String filePath = "Extent.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap();

    public synchronized static ExtentReports getReport() {
        if (extentReports == null) {
            ExtentHtmlReporter html = new ExtentHtmlReporter(filePath);
            html.config().setDocumentTitle("Appium Framework");
            html.config().setReportName("BaeminDev");
            html.config().setTheme(Theme.DARK);
            extentReports = new ExtentReports();
            extentReports.attachReporter(html);
        }
        return extentReports;
    }
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) Thread.currentThread().getId());
    }
    public static synchronized ExtentTest startTest(String testName, String desc ) {
        ExtentTest test = getReport().createTest(testName,desc);
        extentTestMap.put((int) (long) Thread.currentThread().getId(),test);
        return test;
    }
}
