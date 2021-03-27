package com.clevertap.ui.boards_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.boards.Boards;
import com.clevertap.utils.Data;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TableUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class BoardsTest extends BaseTest{

    private static final String PASSED = "Passed";
    private static final String EVENTS = "Events";
    private static final String SEGMENTS = "Segments";
    private static final String FUNNELS = "Funnels";
    private static final String FIND_PEOPLE = "Find people";
    private Logger logger;
    private WebDriver driver;
    private String boardDynamicName = "";

    @BeforeClass
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), "");
        logger = baseTest.configureLogger(getClass());

    }


    @Test(priority = 1, description = "Verify boards Page")
    public void openBoardsPage() {
        logger.info("open boards Page Test Finished");
        Boards boards = new Boards(driver);
        String boardsPageHeaderText = boards.getHeaderText();
        Assert.assertTrue(boardsPageHeaderText.contains("Boards\n" + "All Boards"), "My AllBoards successfully launched");
        logger.info("open Profile Page Test Finished");

    }


    @Test(priority = 2, description = "verify and New boards on All boards")
    public void addNewBoards() {
        logger.info("open boards and add new boards as Private Test Finished");
        Boards boards = new Boards(driver);
        boards.createNewBoards();
        boardDynamicName = Data.randomAlphaNumeric("Test-Board-Automation", 4);
        boards.newBoardform(boardDynamicName);
        boards.selectPublicPrivateRadioButton();
        String newBoardCreatedText = boards.BoardText();
        Assert.assertTrue(newBoardCreatedText.contains("to pin a card on any board"));
        logger.info("create boards Page Test Finished");

    }

    @Test(priority = 3, description = "Open SubFeatures in new Custom boards")
    public void clickOnSubFeatures() throws InterruptedException {
        logger.info("open boards and add new Page Test Finished");
        Boards boards = new Boards(driver);
        String BoardsPageHeaderText = boards.switchToTabs(EVENTS);
        Assert.assertTrue(BoardsPageHeaderText.contains("Analyze\n" + "Events"), "My AllBoards page for events successfully launched");
        BoardsPageHeaderText = boards.switchToTabs(SEGMENTS);
        Assert.assertTrue(BoardsPageHeaderText.contains("Segments\n" + "Overview"), "My AllBoards page for segments successfully launched");
        BoardsPageHeaderText = boards.switchToTabs(FUNNELS);
        Assert.assertTrue(BoardsPageHeaderText.contains("Analyze\n" + "Funnels"), "My AllBoards page for funnels successfully launched");
        BoardsPageHeaderText = boards.switchToTabs(FIND_PEOPLE);
        Assert.assertTrue(BoardsPageHeaderText.contains("Segment\n" + "Find People"), "My AllBoards page for find people successfully launched");
        driver.navigate().back();
        logger.info("create boards Page Test Finished");
    }

    @Test(priority = 4, description = "Search New Board created in the List")
    public void getBoardFromList() throws InterruptedException {
        logger.info("open boards and add new Page Test Finished");
        Boards boards = new Boards(driver);
        boards.sendTextToSearchBox(boardDynamicName);
        List<String> data = TableUtility.getDataFromRightTable("//*[@class='CT-table__left']", 1, driver);
        Assert.assertTrue(data.contains(boardDynamicName), "New board is created");
        logger.info("create boards Page Test Finished");

    }
}



