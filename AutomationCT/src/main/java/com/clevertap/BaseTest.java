package com.clevertap;

import com.clevertap.api.ApiLibrary;
import com.clevertap.api.ResponseFactory;
import com.clevertap.utils.report_utils.extent_report.TestNGListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners({ BaseTest.class, TestNGListener.class})
public class BaseTest implements ISuiteListener {

    private static BaseTest instance = null;
    public static ThreadLocal<WebDriver> webDriverThreadSafe = new ThreadLocal<WebDriver>();
    private final static Logger logger = Logger.getLogger("BaseTest");
    private static Properties prop;
    private static int lastResult = 0;
    public ResponseFactory responseFactory;
    public ApiLibrary apiLibrary;
    ChromeOptions chromeOptions = new ChromeOptions();;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        loadPropertyFile("./config.properties","./resources/properties_files/url.properties","./resources/properties_files/settings.properties");
        responseFactory=new ResponseFactory();
        apiLibrary=new ApiLibrary();
    }

    public static BaseTest getInstance() {
        if (instance == null) {
            instance = new BaseTest();
        }
        return instance;
    }

//    public void setDriver(String browser) {
//        switch (browser) {
//            case "chrome":
//                System.setProperty("webdriver.chrome.driver","./resources/drivers_mac/chromedriver");
//                //BaseTest.class.getResource("/drivers_mac/chromedriver").getPath()
//                System.out.println("******************************");
//                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("--headless");
//                chromeOptions.addArguments("--no-sandbox");
//                webDriverThreadSafe.set(new ChromeDriver(chromeOptions));
//                webDriverThreadSafe.get().manage().deleteAllCookies();
//                webDriverThreadSafe.get().manage().window().maximize();
//                webDriverThreadSafe.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        }
//
//    }

    public void setDriver(String browser) {
        String osName=System.getProperty("os.name").toLowerCase();
        if (osName.contains("nux") || osName.contains("nix")){
            switch (browser) {
                case "chrome":
                    Reporter.log("Jobs are running on linux OS",true);
                    Reporter.log("Setting up linux chrome driver path",true);
//                    System.setProperty("webdriver.chrome.driver","./drivers_linux/chromedriver");
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("--headless --disable-gpu");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--headless");
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--no-sandbox");
//                    chromeOptions.addArguments("--remote-debugging-port=9222");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    webDriverThreadSafe.set(new ChromeDriver(chromeOptions));

            }
        }else if (osName.contains("mac")) switch (browser) {
            case "chrome":
                Reporter.log("Jobs are running on mac OS", true);
                Reporter.log("Setting up mac chrome driver path", true);
//                System.setProperty("webdriver.chrome.driver", BaseTest.class.getResource("/drivers_mac/chromedriver").getPath());
                WebDriverManager.chromedriver().setup();
                chromeOptions.addArguments("--window-size=1400,800");
//                chromeOptions.addArguments("--headless");
//                chromeOptions.setHeadless(true);

//                chromeOptions.addArguments("--remote-debugging-port=9222");
                chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                WebDriver webDriver=new ChromeDriver(chromeOptions);
                webDriverThreadSafe.set(webDriver);

        }

        webDriverThreadSafe.get().manage().deleteAllCookies();
        webDriverThreadSafe.get().manage().window().maximize();
        webDriverThreadSafe.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        webDriverThreadSafe.get().manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
//        SeleniumUtils.waitForPageLoaded(webDriverThreadSafe.get());


    }


    public WebDriver getDriver() {
        setDriver(prop.getProperty("Browser"));
        return webDriverThreadSafe.get();
    }

    public static Logger configureLogger(Class className) {
        PropertyConfigurator.configure("log4j.properties");
        Logger logger = Logger.getLogger(className.getSimpleName());
        return logger;
    }

    private static void loadPropertyFile(String... properties) {
        FileInputStream fis;
        prop = new Properties();
        try {
            for(String propertyFile:properties){
                fis = new FileInputStream(propertyFile);
                prop.load(fis);
            }
        } catch (java.io.IOException e) {
            logger.error("Could not load Property File : " + e.getMessage());
        }
    }

    public static String getValue(String key){

        return prop.getProperty(key);
    }

    @Override
    public void onStart(ISuite iSuite) {

    }

    @Override
    public void onFinish(ISuite iSuite) {

    }

    //@AfterClass(alwaysRun = true)
    public void afterClass(){
        if (Objects.nonNull(webDriverThreadSafe.get())){
            webDriverThreadSafe.get().close();
            webDriverThreadSafe.get().quit();
            webDriverThreadSafe.remove();
        }
    }


}
