package com.clevertap.utils;

import com.sendgrid.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Gmail {

    public static final String INBOX = "#inbox";
    private WebDriver driver;
    @FindBy(xpath = "//*[@type='email']")
    private WebElement googleEmailField;
    @FindBy(id = "identifierNext")
    private WebElement nextButtonGMailBox;
    @FindBy(xpath = "//*[@type='password']")
    private WebElement passwordFieldGoogle;
    @FindBy(id = "passwordNext")
    private WebElement nextButtonPasswordBox;
    @FindBy(xpath = "//div[@class='Cp']//table")
    private WebElement emailsTable;
    @FindBy(xpath = "//div[@role='listitem']")
    private List<WebElement> listSingUpInvites;
    @FindBy(xpath = "//span[text()='CleverTap Reset Password']")
    private WebElement resetPasswordEmail;
    @FindBy(xpath = "//a[text()='Reset password']")
    private WebElement resetPasswordCleverTapLink;
    @FindBy(xpath = "//input[@type='password' and @placeholder='Password']")
    private WebElement resetPasswordInputBox;
    @FindBy(xpath = "//input[@type='submit']")
    private WebElement resetPassSubmitBtn;
    @FindBy(xpath = "//input[@aria-label='Search mail']")
    private WebElement googleMailSearchTextBox;
    @FindBy(xpath = "//button[@aria-label='Search Mail']")
    private WebElement seachMailButton;
    @FindBy(xpath = "//div[@gh='tm']//div[@aria-label='Delete']")
    private WebElement deleteButtonAfterSearch;
    @FindBy(xpath = "//div[@data-tooltip='Inbox']//*[@title='Inbox']")
    private WebElement inboxButton;


    public Gmail(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void logInToGMail(String uName, String password) {
        SeleniumUtils.pause(1);
        driver.get("http://mail.google.com");
        if(driver.findElements(By.xpath("//div[@data-tooltip='Inbox']//*[@title='Inbox']")).size()>0){
            Reporter.log("User already logged in gmail",true);
            return;
        }
        SeleniumUtils.enterInputText(driver, googleEmailField, uName);
        SeleniumUtils.pause(2);
        if(driver.findElements(By.id("identifierNext")).size()>0){
            nextButtonGMailBox=driver.findElement(By.id("identifierNext"));
        }else{
            nextButtonGMailBox=driver.findElement(By.id("next"));
        }
        SeleniumUtils.performClick(driver, nextButtonGMailBox);
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldGoogle));
//        driver.navigate().refresh();
        passwordFieldGoogle = driver.findElement(By.xpath("//*[@type='password']"));
        SeleniumUtils.enterInputText(driver, passwordFieldGoogle, password);
        if(driver.findElements(By.id("passwordNext")).size()>0){
            nextButtonPasswordBox = driver.findElement(By.id("passwordNext"));
        }else{
            nextButtonPasswordBox = driver.findElement(By.xpath("//input[@value='Sign in']"));
        }
        SeleniumUtils.waitAndClick(driver, nextButtonPasswordBox);
    }

    public void resetPassword(String newPassword) {
        SeleniumUtils.enterInputText(driver, googleMailSearchTextBox, "CleverTap Support");
        SeleniumUtils.performClick(driver, seachMailButton);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver, resetPasswordEmail);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver, resetPasswordCleverTapLink);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.switchToLastWindowHandle(driver);
        SeleniumUtils.enterInputText(driver, resetPasswordInputBox, newPassword);
        SeleniumUtils.performClick(driver, resetPassSubmitBtn);
        SeleniumUtils.waitForPageLoaded(driver);
    }

    public void deleteEmailsWithHeader(String header) {

        SeleniumUtils.enterInputText(driver, googleMailSearchTextBox, header);
        SeleniumUtils.performClick(driver, seachMailButton);
        while(driver.findElements(By.xpath("//span[contains(text(),'View messages.')]")).size()==0){
            SeleniumUtils.pause(1);
        }
        List<WebElement> searchedMails = driver.findElements(By.xpath("//div[@role='main']//div[@role='checkbox']"));
        if (!searchedMails.isEmpty()) {
            for (WebElement element : searchedMails) {
                element.click();
            }
            SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id=':4']/div[2]/div[1]/div[1]/div/div/div[2]/div[3]/div")));
            driver.findElement(By.xpath("//*[@id=':4']/div[2]/div[1]/div[1]/div/div/div[2]/div[3]/div")).click();
        }
        SeleniumUtils.waitForElementToClickable(driver,inboxButton,10);
        inboxButton.click();
    }

    public static void sendReportViaEmail(final String username, final String password, String sendEmailTo) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(sendEmailTo));

            message.setSubject("Automation suit run");
            message.setText("Ran the TestNG suit and results are as attached.");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            //String file = "./ExtentReports/ExtentReportResults.html";
            String fileName = "Automation run report.html";
            //DataSource source = new FileDataSource(file);
            messageBodyPart.attachFile(new File("./ExtentReports/ExtentReportResults.html"));
            //messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
    }

    public static void sendGridEmail(String sendEmailTo) {


        List<String> toEmails = Arrays.asList(sendEmailTo.split(","));
        Mail mail = new Mail();

        Email from = new Email("jay.magdani@clevertap.com");
        from.setName("CleverTap | Automation suit");
        String subject = "Automation run report";

        //Content content = new Content("text/plain", "and easy to do anywhere, even with Java");

        for (String email : toEmails) {
            Personalization personalization = new Personalization();
            personalization.addTo(new Email(email));
            mail.addPersonalization(personalization);
        }


        mail.setFrom(from);

        mail.setSubject(subject);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        Attachments attachments = new Attachments();
        attachments.setType("application/html");
        attachments.setFilename("Automation Report.html");
        attachments.setDisposition("attachment");
        attachments.setContentId("Balance Sheet");
        mail.addAttachments(attachments);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

        } catch (IOException ex) {

        }

    }

}