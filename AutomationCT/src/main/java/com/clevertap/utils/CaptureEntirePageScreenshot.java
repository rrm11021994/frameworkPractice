package com.clevertap.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CaptureEntirePageScreenshot {


    public static void main(String[] args) throws IOException, InterruptedException {

        Properties prop = new Properties();

        {
            try {
                FileInputStream fis = new FileInputStream("./config.properties");
                prop.load(fis);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        System.setProperty("webdriver.chrome.driver", "/Users/manmohanroy/Downloads/CleverTapInHouseAutomationFramework/lib/chromeDriver/chromedriver");
        WebDriver driver=new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get(prop.getProperty("URL"));
        driver.findElement(By.xpath("//*[@name='email']")).sendKeys(prop.getProperty("UserName"));
        driver.findElement(By.xpath("//*[@name='password']")).sendKeys(prop.getProperty("Password"));
        driver.findElement(By.xpath("//*[@id='submitBtn']")).click();

        driver.navigate().refresh();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@class='hamburger-box']")).click();
        driver.findElement(By.xpath("//*[@id='menu']/ul/li//div[text()='boards']/../../ul//a[text()='All boards']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"boards-table\"]/div/div[1]/div[3]/div[1]/a")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//i[contains(@class,'topbar__fullscreen')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@class='hamburger-box']")).click();
        Thread.sleep(1000);
            String dest = System.getProperty("user.dir") + "/FullScreenshot/" + "image.png";

//        FixedCutStrategy cutting = new FixedCutStrategy(1000,1000);
//        ShootingStrategy rotating = new RotatingDecorator(cutting, ShootingStrategies.viewportPasting(100));
//        ShootingStrategy pasting = new ViewportPastingDecorator(rotating)
//                .withScrollTimeout(1000);
//        Screenshot screenshot=new AShot()
//                .shootingStrategy(pasting)
//                .takeScreenshot(driver);

        /* BELOW LINE TAKES THE SCREENSHOT OF FULL PAGE*/

            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.iPad2WithIOS8RetinaSimulator()).takeScreenshot(driver);

            ImageIO.write(screenshot.getImage(), "PNG", new File(dest));
            driver.quit();


    }
}
