package com.clevertap.utils.report_utils.extent_report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.clevertap.BaseTest;
import com.clevertap.utils.SeleniumUtils;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.HashMap;
import java.util.Map;

public class CTReports {

    public static ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/output/reports/Clevertap_Automation_Report"+ SeleniumUtils.getCurrentLocalDateTime()
            .replace("/","-").replaceAll(" ","")+".html");
    public static ExtentReports extent = new ExtentReports();
    protected static Map<String,ExtentTest> extentTestMap =new HashMap<String,ExtentTest>();
    protected static Map<String,ExtentTest> extenNodeMap=new HashMap<String,ExtentTest>();


    synchronized public void startTest(ITestResult result){
        String testClass = result.getTestClass().getName();
        testClass = testClass.substring(testClass.lastIndexOf('.')+1,testClass.length());
        String testCase=result.getName();

        ExtentTest test=null;
        ExtentTest node=null;
        if(!extentTestMap.containsKey(testClass)){
            test = extent.createTest(testClass);
            extentTestMap.put(testClass,test);
        }
        if(!extenNodeMap.containsKey(testCase)) {
            test=extentTestMap.get(testClass);
            node = test.createNode(testCase);
            extenNodeMap.put(testClass+testCase,node);
        }

        if(extent.getStartedReporters().size() == 0) {
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Environment", BaseTest.getValue("environment"));
            extent.setSystemInfo("User Name",System.getProperty("user.name"));
            htmlReporter.config().enableTimeline(false);
            htmlReporter.config().setReportName("CT Automation Report");
            htmlReporter.config().setDocumentTitle("CT Automation Report");
        }

    }

    synchronized public void getResult(ITestResult result) {
        String testClass = result.getTestClass().getName();
        testClass = testClass.substring(testClass.lastIndexOf('.')+1,testClass.length());

        String testCase=result.getName();
        ExtentTest test=null;
        ExtentTest node=null;

        if(!extentTestMap.containsKey(testClass)){
            test = extent.createTest(testClass);
            extentTestMap.put(testClass,test);
        }

        if(!extenNodeMap.containsKey(testClass+testCase)) {
            test=extentTestMap.get(testClass);
            node = test.createNode(testCase);
            extenNodeMap.put(testClass+testCase,node);
        }

        node= extenNodeMap.get(testClass+testCase);
        for(String message : Reporter.getOutput(result)){
            node.log(Status.INFO, message);
        }

        if(result.getStatus() == ITestResult.FAILURE){
            node.log(Status.FAIL, MarkupHelper.createLabel("Test Case Failed : "+result.getName(), ExtentColor.RED));
            node.log(Status.FAIL, MarkupHelper.createLabel("Reason : "+result.getThrowable(), ExtentColor.RED));
            node.log(Status.FAIL, result.getThrowable());
            node.assignCategory(testClass);
        }
        else if(result.getStatus() == ITestResult.SKIP && result.getThrowable() instanceof Exception) {
            node.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped due to failed configuration", ExtentColor.ORANGE));
            node.assignCategory(testClass);
        }
        else if(result.getStatus() == ITestResult.SKIP ) {
            node.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped because  it depends on not successfully finished methods", ExtentColor.ORANGE));
            node.assignCategory(testClass);
        }
        else if(result.getStatus() == ITestResult.SUCCESS){
            node.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
            node.assignCategory(testClass);
        }
    }

    synchronized public void saveReport() {
        extent.flush();
    }

}
