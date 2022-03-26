package com.oracle.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {


   protected WebDriver driver;
    public Utils(){}
    public Utils(WebDriver driver){
        this.driver=driver;
    }

    public static final String ARTIFACTS_LOCATION=System.getProperty("user.dir")+"/artifacts/";

    public String getJsonFileContent(String filePath){

        try {
            return  FileUtils.readLines(new File(Constants.INPUT_FILE_DIRECTORY+filePath), StandardCharsets.UTF_8)
                    .stream()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getFilesInDirectory(String path){

        File file = new File(path);
        if(file.isDirectory()){
            //Check for .json file
           return Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(File::isFile)
                    .map(File::getName)
                    .filter(fileName -> fileName.endsWith(".json"))
                    .collect(Collectors.toList());
        }else{
            Reporter.log("There is no such directory",2,true);
        }

        return null;
    }


    public void copyContentToClipBoard(String contentToCopy){
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(contentToCopy),
                        null
                );
    }

    public void pasteContentUsingKeyBoard(WebElement element){

        Actions actions = new Actions(driver);
        actions.keyDown(element, Keys.COMMAND)
                .sendKeys(element,String.valueOf('\u0076'))
                .keyUp(element,Keys.COMMAND)
                .build()
                .perform();
    }

    public void takeScreenShot(String ScreenshotName,boolean isSuccess){

        if(isSuccess)
            ScreenshotName=ScreenshotName+"_VALID";
        else
            ScreenshotName=ScreenshotName+"_INVALID";

        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        File screenshotLocation = new File(ARTIFACTS_LOCATION+ScreenshotName+".png");
        try {
            FileUtils.copyFile(screenshot, screenshotLocation);
            Reporter.setEscapeHtml(false);

            byte[] fileContent = FileUtils.readFileToByteArray(screenshotLocation);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            String path = "<img src=\"data:image/png;base64, " + encodedString + "\" width=\"auto\" height=\"auto\" />";
            Reporter.log(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void javascriptExecutor(String jsScript){
        ((JavascriptExecutor)driver).executeScript(jsScript);
    }

   public boolean isJsonValid(String resultText){
        return resultText.contains("Valid JSON");
   }

}
