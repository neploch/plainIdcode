package ui.external;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.BasePage;

import static utilities.Common.randomNumberInRange;
import static utilities.Common.randomStringGenerator;

public class BiziHomePage extends BasePage {
    public BiziHomePage(WebDriver driver){
        super(driver);
    }

    public String homepageCreditCalculatorWrapper = "//div[@id='dashboard-home-page-calculator-widget']";
    public String newWithdrawalCalculatorWrapper = "//div[@id = 'new-withdrawal-drawer-calculator-wrapper']";
    public String creditCarBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'car')]";
    public String creditBackBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'choice2')]";
    public String creditCommitmentBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'Commitment')]";
    public String creditNewBusinessBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'newBusiness')]";
    public String creditRealEstateBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'realEstate')]";
    public String creditOtherBtn = homepageCreditCalculatorWrapper + "//button[contains(@id,'other')]";
    public String creditFreeTextInput = "//input[@id='dashboard-widget-calculator-free-text-input']";
    public String homepageCreditFreeTextInput = homepageCreditCalculatorWrapper + creditFreeTextInput;
    public String offerFreeTextInput = "//input[@id = 'drawer-calculator-free-text-input']";
    public String creditAmountInput = "//input[@id='dashboard-widget-calculator-amount-input']";
    public String homepageCreditAmountInput = homepageCreditCalculatorWrapper + creditAmountInput;
    public String offerCreditAmountInput = "//input[@id = 'drawer-calculator-amount-input']";
    public String creditInstallments3 = "//div[@id='dashboard-widget-calculator-installments-3']";
    public String creditInstallments6 = "//div[@id='dashboard-widget-calculator-installments-6']";
    public String creditInstallments12 ="//div[@id='dashboard-widget-calculator-installments-12']";
    public String drawerCreditInstallments3 = "//div[@id='drawer-calculator-installments-3']";
    public String drawerCreditInstallments6 = "//div[@id='drawer-calculator-installments-6']";
    public String drawerCreditInstallments12 = "//div[@id='drawer-calculator-installments-12']";
    public String calculatorBtn = "//button[@id='dashboard-widget-calculator-button']";
    public String drawerCalculatorBtn = "//button[@id='drawer-calculator-button']";
    public String monthlyPayment = "//span[@id='monthly_payment']";

    public void selectCreditForCar(){
        scrollElementIntoView(creditFreeTextInput);
        wait(2);
        clickVisible(creditCarBtn);
    }

    public String insertFreeCreditText(){
        String freeText = randomStringGenerator(10);
        sendTextToVisibleElement(homepageCreditFreeTextInput,freeText);
        return freeText;
    }

    public int insertCreditAmount(){
        int amount =  randomNumberInRange(10001,50000);
        sendTextToVisibleElement(homepageCreditAmountInput,String.valueOf(amount));
        return amount;
    }

    public void selectInstallments3(){
        clickVisible(creditInstallments3);
    }

    public void selectInstallments6(){
        clickVisible(creditInstallments6);
    }

    public void selectInstallments12(){
        clickVisible(creditInstallments12);
    }

    public void selectDrawerInstallments3(){
        clickVisible(drawerCreditInstallments3);
    }

    public void selectDrawerInstallments6(){
        clickVisible(drawerCreditInstallments6);
    }

    public void selectDrawerInstallments12(){
        clickVisible(drawerCreditInstallments12);
    }

    public void calculateCreditOffer(){
        clickVisible(calculatorBtn);
    }

    public void recalculateCreditOffer(){
        clickVisible(drawerCalculatorBtn);
    }

    public Boolean validateOfferFreeText(String initialFreeText){
        return waitForAttributeToContain(offerFreeTextInput,"value",initialFreeText);
    }

    public Boolean validateOfferAmount(int initialAmount){
        return waitForAttributeToContain(offerCreditAmountInput,"value", String.valueOf(initialAmount));
    }

    public String getMonthlyPayment(){
        return getVisibleElementText(monthlyPayment);
    }





}
