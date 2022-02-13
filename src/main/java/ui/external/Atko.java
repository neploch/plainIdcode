package ui.external;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ui.Reusable;

import java.util.HashMap;
import java.util.Map;

import static utilities.Common.*;

public class Atko extends Reusable {
    public Atko(WebDriver driver) {
        super(driver);
        application = getRepValue("application");
        daDirectLoginUrl = getRepValue("daDirectLoginUrl");
        envDatacenter = getRepValue("envDatacenter");
        envDomain = getRepValue("envDomain");
        tenantName = getRepValue("tenantName");
        baseUrl = getRepValue("baseUrl");
        baseUrl = String.format(baseUrl,envDatacenter,envDomain);
    }
    String application;
    String daDirectLoginUrl;
    String envDatacenter;
    String envDomain;
    String tenantName;
    String baseUrl;
    String userNameInput = "//input[@name='username']";
    String passwordInput = "//input[@name='password']";
    String submitBtn = "//*[@type='submit']";
    String newPasswordInput = "//input[@name='newPassword']";
    String confirmPassword = "//input[@name='confirmPassword']";
    String userHome = "UserHome";

    String loginUrl = "login/login.";
    public String closeGuide = "//button[contains(@id,'close-guide')]";
    public String dropDownMenuBtn = "//button[contains(@class,'dropdown-menu')]";
    public String signOutBtn = "//div[@class='topbar--items']/a[contains(@href,'signout')]";

    public String changePasswordNewPasswordInput = "//input[@id='change_password.newPassword']";
    public String changePasswordVerifyPasswordInput = "//input[@id='change_password.verifyPassword']";
    public String resetPasswordBtn = "//input[@id='change_password.button.submit']";
    public String daIinviteMemberBtn = "//button[contains(@class,'invite-members-button')]";


    public void loginAtko(WebDriver driver1, Map<String,String> map, String IDP){
        String userName = map.get("email");
        String password = getRepValue("passWord");
        sendTextToVisibleElement(driver1,userNameInput,userName);
        if(IDP.toLowerCase().contains("okta")) {
            clickVisible(driver1,submitBtn);
        }
        sendTextToVisibleElement(driver1, passwordInput,password);
        clickVisible(driver1,submitBtn);
    }

    public void loginAtko(Map<String,String> map, String IDP){
        String userName = map.get("email");
        String password = getRepValue("passWord");
        sendTextToVisibleElement(userNameInput,userName);
        if(IDP.toLowerCase().contains("okta")) {
            clickVisible(submitBtn);
        }
        sendTextToVisibleElement(passwordInput,password);
        clickVisible(submitBtn);
    }

    public void logoutOkta(){
        clickVisible(dropDownMenuBtn);
        clickVisible(signOutBtn);
        waitForUrlContains(loginUrl);
        driver.manage().deleteAllCookies();
    }

    public void resetPassword(){
        sendTextToVisibleElement(newPasswordInput,passWord);
        sendTextToVisibleElement(confirmPassword,passWord);
        clickVisible(submitBtn);
        Assert.assertTrue(waitForUrlContains(userHome,10),"User was not found inside Okta dashboard");
        clickVisible(closeGuide);
        logoutOkta();
    }

    public void loginDaExternally(String workspaceId, Map<String,String> map, String IDP){
        daDirectLoginUrl = String.format(daDirectLoginUrl,tenantName,baseUrl,workspaceId);
//        clearBrowsingSession();
//        driver.manage().deleteAllCookies();
        String OS = System.getProperty("os.name", "unknown").toLowerCase();
        if(OS.contains("wind")) {
            driver = new ChromeDriver();
        }else {
            Map<String,String> prefs = new HashMap<>();
            prefs.put("download.default_directory", downloadsPath); // Bypass default download directory in Chrome
            prefs.put("safebrowsing.enabled", "false"); // Bypass warning message, keep file anyway (for .exe, .jar, etc.)
            ChromeOptions opts = new ChromeOptions();
            opts.setExperimentalOption("prefs", prefs);
            opts.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080","--ignore-certificate-errors","--no-sandbox", "--disable-dev-shm-usage");
            opts.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

            driver = new ChromeDriver(opts);
            driver.manage().window().maximize();
        }
        driver.get(daDirectLoginUrl);
        loginAtko(driver, map, IDP);
        Assert.assertTrue(waitForElementToBeVisible(driver, daIinviteMemberBtn));
        driver.quit();
    }

    public void resetPasswordGuerrilla(String application, String IDP, String email){
        openGuerrillaEmail(email,reset,application, IDP,true);
        scrollPageDown();
        clickVisible(guerillaResetpasswordLink);
        switchToNewWindow();
        clickVisible(changePasswordNewPasswordInput);
        String password = randomPasswordGenerator();
        sendTextToVisibleElement(changePasswordNewPasswordInput,password);
        clickVisible(changePasswordVerifyPasswordInput);
        sendTextToVisibleElement(changePasswordVerifyPasswordInput,password);
        clickVisible(resetPasswordBtn);
        waitForUrlContains(userHome);
    }
}
