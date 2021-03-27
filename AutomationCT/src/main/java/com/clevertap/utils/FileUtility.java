package com.clevertap.utils;

import com.clevertap.BaseTest;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;
import java.util.*;

public class FileUtility {
    private static Logger logger = Logger.getLogger("FileUtility");
    private static String fieldNameWithDiff=null;


    /*Below method will find files with extension .csv*/

    public static File[] fileFinder(String dirName) {
        File file = new File(dirName);
        File[] files = file.listFiles(new FilenameFilter() {


            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith(".csv")) {
                    return true;
                } else {
                    return false;
                }
            }
        });

//        for (File f:files){
//            System.out.println("CSV files present in download folder are:: "+f);
//        }
        return files;
    }

    public static void deleteCSVFiles() {
        String downloadDir = System.getProperty("user.home") + "/Downloads";

        File[] listOfCSVFiles = fileFinder(downloadDir);
        System.out.println("************* "+listOfCSVFiles.length);
        for (File f : listOfCSVFiles) {
            boolean deleted = f.delete();
            if (deleted) {
                logger.info("File deleted");
            } else {
                System.out.println("Error occured during deleting the file");
            }
        }
    }


    /*Below method reads thwe csv file line by line and return a list*/
    public static List<String> readDataLineByLine(File file) {
        List<String> csvList = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    csvList.add(cell);

                }
            }

        } catch (Exception e) {
                //
        }
        return csvList;
    }

    /*Below method reads the csv file line by line and return a list*/
    public static List<String> readDataLines(File file) {
        List<String> csvList = new ArrayList<String>();
        String line = "";
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                line = "";
                for (String cell : nextRecord) {
                    line = line + " " + cell;
                }
                csvList.add(line);
            }
            logger.info("Size of list  :" + csvList.size());
        } catch (Exception e) {
                    //
        }
        return csvList;
    }

    public boolean compareCsv(File file1, File file2) {
        boolean matched = true;
        List<String> file1Content = readDataLines(file1);
        List<String> file2Content = readDataLines(file2);
        for (String line : file1Content) {
            if (!file2Content.contains(line)) {
                matched = false;
                logger.info("Difference :" + line);
            }
        }
        for (String line : file2Content) {
            if (!file1Content.contains(line)) {
                matched = false;
                logger.info("Difference :" + line);
            }
        }
        return matched;
    }

    public static void uploadImage(File file) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(file.getAbsolutePath());
            clipboard.setContents(strSel, null);
            Robot robot = new Robot();
            // Cmd + Tab is needed since it launches a Java app and the browser looses focus
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(500);

            //Open Goto window
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_G);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_G);

            //Paste the clipboard value
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_V);

            //Press Enter key to close the Goto window and Upload window
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(1500);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }catch (AWTException e) {
            logger.info("something went wrong while uploading image"+e.getMessage());
        }
    }


    public static Map<String, Map<String, String>> readCSVForSegmentQuery(String filePath) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(filePath));
        String[] wordsPerLine=null;
        String line=null;
        Map<String,Map<String,String>> finalCSVMap=new HashMap<>();

        while ((line=br.readLine())!=null){
            Map<String,String> csvMap=new HashMap<>();
            if (!line.isEmpty()){

                if (!line.startsWith("#")) {
                    wordsPerLine = line.split(",");

                    int totalCols = wordsPerLine.length;
                    for (int j = 1; j < totalCols; j++) {

                        String[] kv = null;
                        kv = wordsPerLine[j].split(":");
                        if(!(kv.length<2))
                        csvMap.put(kv[0], kv[1]);
                    }

                    finalCSVMap.put(wordsPerLine[0], csvMap);
                }
            }
        }


        return finalCSVMap;
    }


    /*compare 2 JSON object and find the difference */
    public static JSONObject compare2JsonObject(Object obj1,Object obj2){

        JSONObject diff=new JSONObject();
        if (obj1 instanceof JSONObject && obj2 instanceof JSONObject){
            JSONObject jsonObject1=(JSONObject)obj1;
            JSONObject jsonObject2=(JSONObject)obj2;

            List<String> names1=null;
            List<String> names2=null;
            if (!jsonObject1.isEmpty() && !jsonObject2.isEmpty()){
                 names1 = new ArrayList(Arrays.asList(JSONObject.getNames(jsonObject1)));
                 names2 = new ArrayList(Arrays.asList(JSONObject.getNames(jsonObject2)));
            }


            if (names1!=null && !names1.isEmpty() && names2!=null && !names2.isEmpty()){
                if (!names1.containsAll(names2) && names2.removeAll(names1)){
                    for (String fieldName:names2){
                        fieldNameWithDiff=fieldName;
                        if (jsonObject1.has(fieldName)){
                            diff.put(fieldName,jsonObject1.get(fieldName));
                        }else if (jsonObject2.has(fieldName)){
                            diff.put(fieldName,jsonObject2.get(fieldName));
                        }
                    }

                    names2=Arrays.asList(JSONObject.getNames(jsonObject2));
                }

                if (names1.containsAll(names2)){
                    for (String fieldName:names1){
                        fieldNameWithDiff=fieldName;
                        Object obj1FieldValue=jsonObject1.get(fieldName);
                        Object obj2FieldValue=jsonObject2.get(fieldName);
                        Object obj=compare2JsonObject(obj1FieldValue,obj2FieldValue);
                        if (obj != null && !checkObjectIsEmpty(obj))
                            diff.put(fieldName, " Found " +obj);
                    }
                }
            }
            }else {
            if (!obj1.toString().equalsIgnoreCase(obj2.toString())){
                diff.put(fieldNameWithDiff," Found "+obj1.toString()+" And "+obj2.toString());
            }
        }

        return diff;

        }

    private static boolean checkObjectIsEmpty(Object obj) {
        if (obj == null)
            return true;
        String objData = obj.toString();
        if (objData.length() == 0)
            return true;
        if (objData.equalsIgnoreCase("{}"))
            return true;
        return false;
    }

    public FileUtility() {
        logger = BaseTest.getInstance().configureLogger(getClass());
    }
}
