package com.clevertap.ui;

import com.clevertap.BaseTest;
import com.clevertap.utils.FileUtility;
import org.testng.annotations.Test;

import java.io.File;

public class CompareCamapignReports extends BaseTest {

    @Test
    public void compareCampaignReports() {
        String path="/Users/manmohanroy/Downloads";
        FileUtility fileUtility=new FileUtility();
        fileUtility.compareCsv(new File(path+"/SummaryWithOldPolarDeployed.csv"),new File(path+"/DaywiseWithOldPolarDeployed.csv"));
    }
}
