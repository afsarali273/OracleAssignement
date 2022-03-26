package com.oracle.pageobjects;

import com.oracle.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JsonLintPage {

   private WebDriver driver;
    public JsonLintPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = ".CodeMirror-scroll .CodeMirror-sizer div .CodeMirror-lines")
    public WebElement codeEditorTextBox;

    @FindBy(css = ".validate button[type='submit']")
     public WebElement btnValidateJson;

    @FindBy(id = "result")
    public WebElement textResult;

    public void enterTextToEditorTextField(String escapedString){
        String jsScript ="document.querySelector('.CodeMirror').CodeMirror.setValue(\""+escapedString+"\");";
        new Utils(driver).javascriptExecutor(jsScript);
    }

}
