package ui.gigya;

import org.openqa.selenium.WebDriver;
import ui.Reusable;

import java.util.ArrayList;
import java.util.List;

import static utilities.Common.*;

public class GigyaPage extends Reusable {
    public GigyaPage(WebDriver driver) {
        super(driver);
        environment = getFromRepository("environment","gigyaLegacy","/general/environment");

        if(environment.toLowerCase().contains("prod")) {
            siteName = getFromRepository("siteName","gigyaLegacy", "//siteNameProd");
        }
    }

    String environment;
    String siteName;
    String plainId1 = "PlainID1";
    String tenantSelector = "//div[@class='dd-control__trigger-element']";
    String partnerSwitcher = "//div[@class='partner-switcher__trigger']";
    String plainId1Selector = "//div[@class='partner-switcher__dropdown-item-content']";

    String customerDataCloudExpander = "//div[@class='left-part__products-holder__expand']/span[@class='left-part__products-holder__expand__arrow']";
    String organizationManagementBtn = "//div[@class='gig_popMenu_items']//a[contains(@href,'AuthorizationConsole')]";
    String expandBtnLast = "(//button[contains(@data-test-id,'expand-button')])[last()]";
    String expandBtn = "//button[contains(@data-test-id,'expand-button')]";
    String openAsDA = "//button[contains(@data-test-id,'delegated')]";
    String folder = "//div[@class='folder']";
    String folderLast = "(//div[@class='folder'])[last()]";

    public void goThroughConsole(){
        clickVisible(tenantSelector);
        clickVisible(partnerSwitcher);
        plainId1Selector = plainId1Selector+stringFormat(andContains,plainId1);
        clickVisible(plainId1Selector);
        waitForTextInElement(tenantSelector,plainId1);
        clickVisible(customerDataCloudExpander);
        clickVisible(organizationManagementBtn);
    }

    public void goThroughGigya() {
        goThroughConsole();
        switchToNewWindow();
        waitElementAmountToBeMoreThan(folder,0);
        hoverAndClick(folderLast, expandBtnLast);
        waitElementAmountToBeMoreThan(listItem,2);
        sendTextToVisibleElement(input,siteName);
        clickVisible(searchBtn);
        String siteXpath = stringFormat(listItemNamedTmpl,siteName);
        hoverAndClickOnElement(siteXpath,expandBtn);
        hoverAndClickOnElement(folder,openAsDA);
        wait(2000);
        switchToNewWindow();
    }
}
