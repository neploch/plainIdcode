package tests.gigya;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

public class GigyaMainRunner extends BaseTest {

    @Test(priority = 1 ,groups= "Gigya2.5_Sanity", description= "Login DA via amazon AWS HTML page")
    public void loginDAShort(){
        amazonAWS.loginDA();
        da.switchToNewWindow();
        da.validateInsideDA();
    }

    @Test(priority = 2 ,groups= "Gigya2.5_Sanity", description= "Login DA via Gigya")
    public void loginDALong(){
        gigyaLogin.doLogin();
        gigya.goThroughGigya();
        da.validateInsideDA();
    }



}
