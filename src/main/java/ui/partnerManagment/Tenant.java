package ui.partnerManagment;

import ui.BasePage;
import org.openqa.selenium.WebDriver;

public class Tenant extends BasePage {
    public Tenant(WebDriver driver) {
        super(driver);
    }

    public String logoutMenuAvatar = "//div[@class='right-section']//span[contains(@class,'avatar')]";
    public String logoutBtn = "//div[contains(@class,'logout')]";

    public void logout(){
        hoverOverVisibleElement(logoutMenuAvatar);
        clickVisible(logoutBtn);
        waitForUrlContains("logout");
    }
}
