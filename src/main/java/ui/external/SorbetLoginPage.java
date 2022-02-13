package ui.external;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.BasePage;

public class SorbetLoginPage extends BasePage {
    public SorbetLoginPage(WebDriver driver){
        super(driver);
    }

    String sorbetLoginPageUrl = "https://admin.qa.sorbetapp.com/login";
    String loginWithGoogleBtnXpath = "//button[contains(text(),'Sign in with Google')]";
    String emailInputXpath = "//input[@autocomplete='username']";
    public String email = "sukituli8@gmail.com";
    public String password = "Mylove!@";


    public void getSorbetLoginPage(){
        driver.get(sorbetLoginPageUrl);
    }

    public void loginWithGoogle(String email, String password){
//        clickVisible(loginWithGoogleBtnXpath);
//        switchToNewWindow();
//        sendTextToVisibleElement(emailInputXpath,email);
//        sendTextToVisibleElement(emailInputXpath,email);
        Assert.assertEquals("AA","BB");
    }

}
