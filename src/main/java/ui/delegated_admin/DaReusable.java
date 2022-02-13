package ui.delegated_admin;

import org.openqa.selenium.WebDriver;

public class DaReusable extends ui.Reusable {
    public DaReusable(WebDriver driver) {
        super(driver);
    }

    public String editIcon = "//i[contains(@class,'edit')]";
    public String manageIcon = "//i[contains(@class,'manage')]";
    public String editBtn = "//button[contains(@class,'edit')]";
    public String hideBtn = "//button[contains(@class,'hide-button')]";
    public String manageBtn = "//button[contains(@class,'manage-button')]";
    public String resendInvitationBtn = "//*[contains(@class,'resend-invitation-button')]";
    public String resetPasswordBtn = "//*[contains(@class,'reset-password-button')]";
    public String rolesContent = "//div[contains(@class,'RolesList')]//div[@class='content']";

    public void clickEditButton(){
        clickVisible(editBtn);
    }
    public void clickManageButton(){
        clickVisible(manageBtn);
    }
    public void clickResendInvitationBtn(){clickVisible(resendInvitationBtn);}
    public void clickResetPasswordBtn(){clickVisible(resetPasswordBtn);}
}
