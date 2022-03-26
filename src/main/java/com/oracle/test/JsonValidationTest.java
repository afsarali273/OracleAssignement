package com.oracle.test;

import com.oracle.pageobjects.JsonLintPage;
import com.oracle.utils.Constants;
import com.oracle.utils.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class JsonValidationTest extends Utils {


    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions().setHeadless(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    //This test was first attempt it works well in local, in headless mode it fails
    @Test(description = "Validation of Json file using online linter",dataProvider = "fileReader", enabled = false)
    public void validation_of_json_content_with_online_validator_v1(String fileName){

        JsonLintPage jsonLintPage = new JsonLintPage(driver);

        System.out.println("Executing test for file : "+ fileName);

        driver.get(Constants.URL_JSONLINT);
        String fileContent = getJsonFileContent(fileName);
        copyContentToClipBoard(fileContent);

        pasteContentUsingKeyBoard(jsonLintPage.codeEditorTextBox);

        jsonLintPage.btnValidateJson.click();

        String status =  jsonLintPage.textResult.getText();

        takeScreenShot(fileName,isJsonValid(status));

        Assert.assertEquals(status,"Valid JSON","Json is invalid");

    }


    @Test(dataProvider = "fileReader", enabled = true)
    public void validation_of_json_content_with_online_validator_v2(String fileName) throws IOException {
        JsonLintPage jsonLintPage = new JsonLintPage(driver);
        System.out.println("Executing test for file : "+ fileName);

        driver.get(Constants.URL_JSONLINT);

        String escapedJsonString = StringEscapeUtils.escapeJson(getJsonFileContent(fileName));

        jsonLintPage.enterTextToEditorTextField(escapedJsonString);

        jsonLintPage.btnValidateJson.click();

        String status =  jsonLintPage.textResult.getText();

        takeScreenShot(fileName,isJsonValid(status));

        Assert.assertEquals(status,"Valid JSON","Json is invalid");



    }

    @DataProvider(name = "fileReader")
    private Object[][] getFileName(){

        List<String> filePaths = getFilesInDirectory(Constants.INPUT_FILE_DIRECTORY);

        int noOfFiles = filePaths.size();

        Object[][] data = new Object[noOfFiles][1];

        IntStream.range(0,noOfFiles).forEach(i->{
            data[i][0] = filePaths.get(i);
        });
        return data;
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
