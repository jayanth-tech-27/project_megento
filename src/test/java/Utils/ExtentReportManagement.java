package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManagement {

    private static ExtentReports extent;
    public static ExtentTest test;

    // Initialize Extent Reports
    public static void initializeReport() {
        if (extent == null) {
        	String reportPath = "reports/ExtentReport_" + ".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
    }

    // Start a new test
    public static void startTest(String testName) {
        if (extent == null) {
            initializeReport();
        }
        test = extent.createTest(testName);
    }

    // Log messages safely
    public static void logInfo(String message) {
        if (test != null) {
            test.info(message);
        } else {
            System.out.println("ExtentTest is null. Make sure to call startTest() before logging.");
        }
    }

    public static void logPass(String message) {
        if (test != null) {
            test.pass(message);
        }
    }

    public static void logFail(String message) {
        if (test != null) {
            test.fail(message);
        }
    }

    // Flush report after execution
    public static void endTest() {
        if (extent != null) {
            extent.flush();
        }
    }

	public static void logWarning(String message) {
		test.log(Status.WARNING, message);		
	}
}