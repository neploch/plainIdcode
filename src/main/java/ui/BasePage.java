package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;
    public Actions action;
    public WebElement webElement;
    public WebElement webElement1;
    public static Random rnd = new Random();
    public final int globalDelay = 30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, globalDelay);
    }

    public String textElement = "//*[contains(text(),'%s')]";
    public String andContains = "[contains(.,'%s')]";
    public String twoTextsElement = "//*[contains(text(),'%s') and contains(text(),'%s')]";
    public String input = "//input";
    public String inputFile = "//input[@type='file']";
    public String img = "//img";
    public String textarea = "//textarea";
    public String titleTextElement = "//*[contains(@title,'%s')]";
    public By logo = By.xpath("//img[contains(@class,'logo')]");

    public void inputInput(String text){
        sendTextToVisibleElement(input,text);
    }

    public void  refreshScreen(){
        driver.navigate().refresh();
    }


    public void clickOnElement(By element) {
        driver.findElement(element).click();
    }

    public void clickOnElement(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void clickDelayed(By element, int delay) {
        driver.findElement(element).click();
        delay = Math.max(delay,200);
        wait(delay);
    }

    public void clickVisibleDisappear(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public void clickVisibleDisappear(By element, int delay){
        wait = new WebDriverWait(driver, delay);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
        wait = new WebDriverWait(driver, globalDelay);
    }

    public void clickVisibleDisappear(String xpath, int delay){
        clickVisibleDisappear(By.xpath(xpath),delay);
    }

    public void clickVisible(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }

    public void clickVisible(WebDriver driver1, By element){
        WebDriverWait wait1 = new WebDriverWait(driver1,globalDelay);
        wait1.until(ExpectedConditions.elementToBeClickable(element));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver1.findElement(element).click();
    }

    public void hoveLogo(){
        hoverOverVisibleElement(logo);
    }

    public void clickVisibleSuccess(String xpath){
        clickVisibleSuccess(By.xpath(xpath));
    }

    public void clickVisibleSuccess(By element){
        WebDriverWait wait1 = new WebDriverWait(driver, 4);
        for(int i=0;i<50;i++) {
            try {
                wait1.until(ExpectedConditions.elementToBeClickable(element));
                wait1.until(ExpectedConditions.visibilityOfElementLocated(element));
                driver.findElement(element).click();
                break;
            }catch (Throwable t){
                hoveLogo();
                wait(400);
            }
        }
    }

    public void clickVisibleTry(String xpath){
        clickVisibleTry(By.xpath(xpath));
    }

    public void clickVisibleTry(By element){
        WebDriverWait wait1 = new WebDriverWait(driver, 4);
        for(int i=0;i<50;i++) {
            try {
                wait1.until(ExpectedConditions.elementToBeClickable(element));
                wait1.until(ExpectedConditions.visibilityOfElementLocated(element));
                driver.findElement(element).click();
                wait1.until(ExpectedConditions.invisibilityOfElementLocated(element));
                break;
            }catch (Throwable t){
                hoveLogo();
                wait(400);
            }
        }
    }

    public void clickVisible(String xpath){
        clickVisible(By.xpath(xpath));
    }

    public void clickVisible(WebDriver driver1, String xpath){
        clickVisible(driver1, By.xpath(xpath));
    }

    public void clickVisibleDisappear(String xpath){
        clickVisibleDisappear(By.xpath(xpath));
    }

    public void clickVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public void clickVisibleDisappear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void clickVisibleDelay(By element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        wait(200);
        driver.findElement(element).click();
    }

    public void clickVisible(By element, int delay){
        wait = new WebDriverWait(driver, delay);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
        wait = new WebDriverWait(driver, globalDelay);
    }

    public void clickVisible(String xpath, int delay){
        clickVisible(By.xpath(xpath),delay);
    }

    public void clickVisible(WebElement element, int delay){
        wait = new WebDriverWait(driver, delay);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        wait = new WebDriverWait(driver, globalDelay);
    }

    public int clickRandomListElement(By element){
        waitElementAmountToBeMoreThan(element,0);
        wait(300);
        List<WebElement>list = driver.findElements(element);
        int index = rnd.nextInt(list.size());
        clickVisible(list.get(index));
        return index;
    }

    public int clickRandomListElement(String xpath){
        return clickRandomListElement(By.xpath(xpath));
    }

    public void hoverAndClickRandomElement(String xpath){
        waitElementAmountToBeMoreThan(xpath,0);
        wait(200);
        List<WebElement>list = driver.findElements(By.xpath(xpath));
        int index = rnd.nextInt(list.size());
        webElement = list.get(index);
        hoverAndClickOnElement(webElement);
    }


    public void waitForElementToBeClickable(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeClickable(String xpath) {
        waitForElementToBeClickable(By.xpath(xpath));
    }

    public void waitForElementToBeClickable(By element, int delay) {
        wait = new WebDriverWait(driver, delay);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait = new WebDriverWait(driver, globalDelay);
    }

    public boolean waitForElementToBeVisible(By element) {
        try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementToBeVisible(WebDriver driver1, By element) {
        try {
            WebDriverWait wait1 = new WebDriverWait(driver1, globalDelay);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(element));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementToBeVisible(WebDriver driver1, String xpath) {
        try {
            WebDriverWait wait1 = new WebDriverWait(driver1, globalDelay);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementToBeVisible(String xpath) {
        return waitForElementToBeVisible(By.xpath(xpath));
    }

     public boolean waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return true;
    }

    public boolean waitForElementToBeVisible(By element, int delay) {
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            return true;
        }catch (Throwable t) {
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForElementToBeVisible(String xpath, int delay) {
        return waitForElementToBeVisible(By.xpath(xpath),delay);
    }

    public boolean waitForElementToDisappear(By element){
        try {
            wait.until((ExpectedConditions.invisibilityOfElementLocated(element)));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementStaleness(WebElement element){
        try {
            wait.until((ExpectedConditions.stalenessOf(element)));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementStaleness(WebElement element, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until((ExpectedConditions.stalenessOf(element)));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForElementToDisappear(String xpath){
       return waitForElementToDisappear(By.xpath(xpath));
    }

    public boolean waitForElementToDisappear(WebElement element){
        try {
            wait.until((ExpectedConditions.invisibilityOf(element)));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForElementToDisappear(By element, int delay){
        try {
            wait = new WebDriverWait(driver, delay);
            wait.until((ExpectedConditions.invisibilityOfElementLocated(element)));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForElementToDisappear(String xpath, int delay){
        return waitForElementToDisappear(By.xpath(xpath),delay);
    }

    public boolean waitForTextInElement(By element, String text){
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(element, text));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForTextInElement(String xpath, String text){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath),text));
        return true;
    }

    public boolean waitForTextInElements(String xpath, String text){
        WebDriverWait wait1 = new WebDriverWait(driver, 1);
        int counter =0;
        while (counter<20) {
            List<WebElement>list = getElementsList(xpath);
            for (WebElement element : list) {
                try {
                    wait1.until(ExpectedConditions.textToBePresentInElement(element, text));
                    return true;
                } catch (Throwable t) {
                    counter++;
                }
            }
        }
        return false;
    }


    public boolean waitForTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));
        return true;
    }


    public boolean validateTextInElements(By element, String text){
        waitElementAmountToBeMoreThan(element,0);
        wait(300);
        List<WebElement> list = driver.findElements(element);
        for (WebElement el:list){
            if(el.getText().contains(text)){
                return true;
            }
        }
        return false;
    }

    public boolean validateTextInElements(String xpath, String text){
        return validateTextInElements(By.xpath(xpath),text);
    }

    public boolean waitForElementPresence(By element){
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        return true;
    }

    public boolean waitForElementPresence(String xpath){
        return waitForElementPresence(By.xpath(xpath));
    }

    public boolean waitForElementPresence(By element, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(element));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForElementPresence(String xpath, int delay){
        return  waitForElementPresence(By.xpath(xpath),delay);
    }

    public void sendTextToElement(By element, String text) {
        waitForElementPresence(element);
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    public void sendTextToElement(String xpath, String text) {
       sendTextToElement(By.xpath(xpath),text);
    }

    public void sendTextToVisibleElement(By element, String text) {
        waitForElementToBeVisible(element);
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    public void sendTextToVisibleElement(WebDriver driver1, By element, String text) {
        waitForElementToBeVisible(driver1,element);
        driver1.findElement(element).clear();
        driver1.findElement(element).sendKeys(text);
    }

    public void sendTextToVisibleElement(WebDriver driver1, String xpath, String text) {
        waitForElementToBeVisible(driver1,xpath);
        driver1.findElement(By.xpath(xpath)).clear();
        driver1.findElement(By.xpath(xpath)).sendKeys(text);
    }


    public void justSendTextToVisibleElement(By element, String text) {
        waitForElementToBeVisible(element);
        driver.findElement(element).sendKeys(text);
    }

    public void justSendTextToVisibleElement(String xpath, String text) {
        justSendTextToVisibleElement(By.xpath(xpath),text);
    }

    public void justSendTextToElement(By element, String text) {
        driver.findElement(element).sendKeys(text);
    }

    public void justSendTextToElement(String xpath, String text) {
        justSendTextToElement(By.xpath(xpath),text);
    }

    public void sendTextToVisibleElement(String xpath, String text) {
       sendTextToVisibleElement(By.xpath(xpath),text);
    }

    public String getVisibleElementText(By element){
        waitForElementToBeVisible(element);
        wait(50);
        return driver.findElement(element).getText();
    }

    public String getVisibleElementText(String xpath){
        return getVisibleElementText(By.xpath(xpath));
    }

    public String getElementText(WebElement element){
        return element.getText();
    }

    public String getElementText(By element){
        return driver.findElement(element).getText();
    }

    public String getVisibleElementTextStrip(By element){
        waitForElementToBeVisible(element);
        wait(50);
        return driver.findElement(element).getText().strip();
    }

    public String getVisibleElementTextStrip(String xpath){
        return getVisibleElementTextStrip(By.xpath(xpath));
    }

    public String getElementText(String xpath){
        return getElementText(By.xpath(xpath));
    }

    public String getElementTextStrip(String xpath){
        return getElementTextStrip(By.xpath(xpath));
    }

    public String getElementTextStrip(By locator){
        return getElementText(locator).strip();
    }

    public String getElementTextStrip(WebElement element){
        return getElementText(element).strip();
    }

    public String getElementAttribute(By element, String attribute){
        return driver.findElement(element).getAttribute(attribute);
    }

    public String getElementAttribute(String xpath, String attribute){
        return getElementAttribute(By.xpath(xpath),attribute);
    }

    public String getElementAttribute(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }

    public String getElementValue(By element){
        return driver.findElement(element).getAttribute("value");
    }

    public String getElementValue(String xpath){
        return driver.findElement(By.xpath(xpath)).getAttribute("value");
    }

    public String getBackGroundColor(By element){
        return driver.findElement(element).getCssValue("background-color");
    }

    public String getElementBorderValue(By element){
        return driver.findElement(element).getCssValue("border");
    }

    public String getFontType(By element){
        return driver.findElement(element).getCssValue("font-family");
    }

    public void hoverOverElement(By element){
        action = new Actions(driver);
        webElement = driver.findElement(element);
        action.moveToElement(webElement).build().perform();
    }

    public void hoverOverElement(String xpath){
       hoverOverElement(By.xpath(xpath));
    }

    public void hoverAndClickOnElement(By element1, By element2){
        action = new Actions(driver);
        waitForElementToBeVisible(element1);
        webElement1 = driver.findElement(element1);
        action.moveToElement(webElement1).build().perform();
        clickVisible(element2);
    }

    public void hoverAndClickOnElement(String xpath1, String xpath2){
        hoverAndClickOnElement(By.xpath(xpath1), By.xpath(xpath2));
    }

    public void hoverAndClickOnElement(By element){
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        webElement = driver.findElement(element);
        action = new Actions(driver);
        action.moveToElement(webElement).click(webElement).build().perform();
    }

    public void hoverAndClickOnElement(String xpath){
       hoverAndClickOnElement(By.xpath(xpath));
    }

    public void hoverAndClickOnElement(WebElement element){
        action = new Actions(driver);
        action.moveToElement(element).click(element).build().perform();
    }

    public void wait(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int extractIntFromElement(String element){
        return Integer.parseInt(getElementText(element).replaceAll("[^0-9]", ""));
    }

    public int getElementsListAmount(By element){
        return driver.findElements(element).size();
    }

    public int getElementsListAmount(String xpath){
        return getElementsListAmount(By.xpath(xpath));
    }

    public void scrollElementIntoView(By element){
        waitForElementPresence(element);
        webElement = driver.findElement(element);
        scrollElementIntoView(webElement);
    }

    public void scrollOnElement(String css){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        String script = stringFormat("document.querySelector('%s').scrollDown += 250",css);
        jsExec.executeScript(script);
    }

    public void scrollOnElementUntilVisible(String divCss, String target){
        while (!waitForElementToBeVisible(target)) {
            JavascriptExecutor jsExec = (JavascriptExecutor) driver;
            String script = stringFormat("document.querySelector('%s').scrollDown += 250", divCss);
            jsExec.executeScript(script);
        }
    }

    public void scrollIntoViewLoop(By locator){
        int counter = 0;
        while (counter<10 && !waitForElementPresence(locator,10)){
            scrollX(250);
            counter++;
        }
        scrollElementIntoView(locator);
    }

    public void scrollIntoViewLoop(String xpath){
        scrollIntoViewLoop(By.xpath(xpath));
    }

    public void scrollElementIntoView(String xpath){
        scrollElementIntoView(By.xpath(xpath));
    }

    public void scrollElementIntoView(WebElement element){
        waitForElementToBeVisible(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait(300);
    }

    public void scrollElementUp(String cssSelector){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String script = stringFormat("document.querySelector('%s').scrollTop= 450",cssSelector);
        js.executeScript(script);
    }

    public int countElements(By element){
        return driver.findElements(element).size();
    }

    public int countElements(String xpath){
        return countElements(By.xpath(xpath));
    }

    public boolean waitElementAmountToBe(By element, int expectedElementsAmount){
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(element, expectedElementsAmount));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean waitElementAmountToBe(By element, int expectedElementsAmount, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(element, expectedElementsAmount));
            return true;
        }catch (Exception e){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitElementAmountToBe(String xpath, int expectedElementsAmount){
        return waitElementAmountToBe(By.xpath(xpath),expectedElementsAmount);
    }

    public boolean waitElementAmountToBe(String xpath, int expectedElementsAmount, int delay){
        return waitElementAmountToBe(By.xpath(xpath),expectedElementsAmount,delay);
    }

    public boolean waitElementAmountToBeMoreThan(By element, int expectedElementsAmount){
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(element, expectedElementsAmount));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitElementAmountToBeMoreThan(String xpath, int expectedElementsAmount){
        return waitElementAmountToBeMoreThan(By.xpath(xpath),expectedElementsAmount);
    }

    public boolean waitElementAmountToBeMoreThan(By element, int expectedElementsAmount, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(element, expectedElementsAmount));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitElementAmountToBeMoreThan(String xpath, int expectedElementsAmount, int delay){
        return waitElementAmountToBeMoreThan(By.xpath(xpath),expectedElementsAmount,delay);
    }


    public boolean waitElementValueToBecome(By element, String expectedValueText){
        wait.until(ExpectedConditions.textToBePresentInElementValue(element,expectedValueText));
        return true;
    }

    public boolean waitElementValueToBecome(String xpath, String expectedValueText){
        return waitElementValueToBecome(By.xpath(xpath),expectedValueText);
    }

    public void clearElement(By element){
        driver.findElement(element).clear();
    }

    public void clearElement(String xpath){
        clearElement(By.xpath(xpath));
    }

    public List<WebElement> getElementsList(By element){
        return driver.findElements(element);
    }

    public List<WebElement> getElementsList(String xpath){
        return driver.findElements(By.xpath(xpath));
    }

    public List<String> getElementsAttribute(By element, String attribute){
        List<WebElement> elements = getElementsList(element);
        List<String> values = new ArrayList<>();
        for(WebElement el : elements){
            String value = el.getAttribute(attribute);
            values.add(value);
        }
        return values;
    }

    public List<String> getElementsAttribute(String xpath, String attribute){
        return getElementsAttribute(By.xpath(xpath),attribute);
    }

    public void clickAllElements(By element){
        wait(1500);
        List<WebElement> list = driver.findElements(element);
        for (WebElement el:list){
            clickVisible(el);
            wait(300);
        }
    }

    public void doubleClickAllElements(By element){
        wait(1500);
        List<WebElement> list = driver.findElements(element);
        for (WebElement el:list){
            clickVisible(el);
            wait(200);
            try {
                clickVisible(el,100);
                wait(200);
            }catch (Throwable t){

            }
        }
    }


    public void clickAction(By element){
        action = new Actions(driver);
        webElement = driver.findElement(element);
        action.moveToElement(webElement).click(webElement).build().perform();
    }

    public void clickAction(String xpath){
        clickAction(By.xpath(xpath));
    }

    public void justClickAction(By element){
        action = new Actions(driver);
        webElement = driver.findElement(element);
        action.click(webElement).build().perform();
    }

    public void justClickAction(String xpath){
        justClickAction(By.xpath(xpath));
    }

    public boolean clickVisibleLoop(By element){
        waitForElementToBeVisible(element);
        int counter = 0;
        while (counter<10) {
            try {
                WebElement el = driver.findElement(element);
                el.click();
                waitForElementStaleness(el,4);
                return true;
            } catch (Throwable throwable) {
                wait(400);
                counter++;
            }
        }
        return false;
    }

    public boolean clickVisibleAppearsLoop(String xpath, String xpath1){
        return clickVisibleAppearsLoop(By.xpath(xpath),By.xpath(xpath1));
    }

    public boolean clickVisibleAppearsLoop(By element, By element1){
        waitForElementToBeVisible(element);
        int counter = 0;
        while (counter<10) {
            try {
                clickOnElement(element);
                waitForElementToBeVisible(element1,4);
                return true;
            } catch (Throwable throwable) {
                wait(400);
                counter++;
            }
        }
        return false;
    }

    public boolean clickVisibleRefresh(String xpath){
        return clickVisibleRefresh(By.xpath(xpath));
    }

    public boolean clickVisibleRefresh(By element){
        for( int counter=0; counter<10; counter++) {
            if(waitForElementToBeVisible(element,3)){
                clickVisible(element);
                waitForElementToDisappear(element);
                return true;
            }else {
                refreshScreen();
            }
        }
        return false;
    }

    public boolean clickVisibleLoop(String xpath){
        return clickVisibleLoop(By.xpath(xpath));
    }

    public boolean clickIfPresented(By element){
        wait(300);
        if(isElementFound(element)){
            clickOnElement(element);
            return true;
        }
        return false;
    }

    public boolean clickIfPresented(String xpath){
        return clickIfPresented(By.xpath(xpath));
    }


    public static void robotDoEnter()
    {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(300);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(300);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public void hoverOverVisibleElement(String locator){
        hoverOverVisibleElement(By.xpath(locator));
    }

    public void hoverOverVisibleElement(By element){
        waitForElementToBeVisible(element,4);
        scrollElementIntoView(element);
        hoverOverElement(element);
    }

    public void hoverOverVisibleElement(By element, int delay){
        waitForElementToBeVisible(element,delay);
        scrollElementIntoView(element);
        hoverOverElement(element);
    }

    public void hoverOverVisibleElement(String xpath, int delay){
        hoverOverVisibleElement(By.xpath(xpath),delay);
    }

    public void hoverOverVisibleCSS(By element){
        waitForElementToBeVisible(element);
        scrollElementIntoView(element);
        hoverOverElement(element);
    }

    public void hoverAndClickVisibleElement(By element){
        waitForElementToBeVisible(element);
        scrollElementIntoView(element);
        hoverOverElement(element);
        clickVisible(element);
    }

    public void hoverAndClickVisibleElement(String locator){
        hoverAndClickVisibleElement(By.xpath(locator));
    }

    public void deletePreviouslyDownloadedFiles(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        if(dirContents == null){
            return;
        }

        for (File dirContent : dirContents) {
            if (dirContent.getName().contains(fileName)) {
                // File has been found, it can now be deleted:
                dirContent.delete();
            }
        }
    }

    public void scrollX(int x){
        JavascriptExecutor jsx = (JavascriptExecutor)driver;
        String script = stringFormat("window.scrollBy(0,%d)", x);
        jsx.executeScript(script, "");
    }

    public void scrollPageDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollPageUp(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    public void pageDown(){
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
    }

    public boolean validateFileDownloaded(String downloadPath, String fileName) {

        for (int k = 0; k< 60; k++) {
            File dir = new File(downloadPath);
            File[] dirContents = dir.listFiles();
            if(dirContents == null){
                continue;
            }
            for (File dirContent : dirContents) {
                if (dirContent.getName().contains(fileName)) {
                    return true;
                }
            }
            wait(500);
        }
        return false;
    }

    public int getRandomElementIndex(By element){
        List<WebElement> list = driver.findElements(element);
        return rnd.nextInt(list.size());
    }

    public int getRandomElementIndex(String xpath){
        return getRandomElementIndex(By.xpath(xpath));
    }

    public WebElement getRandomElement(By element){
        List<WebElement> list = driver.findElements(element);
        int index = rnd.nextInt(list.size());
        return list.get(index);
    }

    public WebElement getRandomElement(String xpath){
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        int index = rnd.nextInt(list.size());
        return list.get(index);
    }

    public List<String> getElementsListTexts(By element){
        List<String> texts = new ArrayList<>();
        List<WebElement> list = driver.findElements(element);
        for (WebElement el : list ){
            texts.add(el.getText().split("\n")[0]);
        }
        return texts;
    }

    public List<String> getElementsListTexts(String xpath){
        return getElementsListTexts(By.xpath(xpath));
    }

    public boolean validateTextsListInElements(List<String> texts, By elements){
        List<String> actualTexts = getElementsListTexts(elements);
        return  actualTexts.containsAll(texts);
    }

    public boolean isElementFound(By element){
        wait(300);
         return !driver.findElements(element).isEmpty();
    }

    public boolean isElementFound(String xpath){
        return isElementFound(By.xpath(xpath));
    }

    public void switchToNewWindow(){
        wait(500);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));
    }

    public void switchToFirstWindow(){
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void switchToSecondWindow(){
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void switchToPreviousWindow(){
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-2));
    }

    public By waitForOneOfTwoElements(By element1, By element2, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(element1),
                    ExpectedConditions.visibilityOfElementLocated(element2)));
            if(waitForElementToBeVisible(element1,delay)){
                return element1;
            }else if(waitForElementToBeVisible(element2,delay)){
                return element2;
            }
        }catch (Throwable t){
            return null;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
        return null;
    }

    public String waitForOneOfTwoElements(String xpath1, String xpath2, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath1)),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath2))));
            if(waitForElementToBeVisible(xpath1,delay)){
                return xpath1;
            }else if(waitForElementToBeVisible(xpath2,delay)){
                return xpath2;
            }
        }catch (Throwable t){
            return null;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
        return null;
    }

    public boolean waitForOneOfTwoTextsInElement(By element, String text1, String text2){
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(element,text1),
                ExpectedConditions.textToBePresentInElementLocated(element,text2)));
        return true;
    }

    public boolean waitForOneOfTwoTextsInElement(String xpath, String text1, String text2){
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath),text1),
                ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath),text2)));
        return true;
    }

    public boolean waitForOneOfTwoTextsInElementAttribute(String xpath, String attribute, String text1, String text2){
        wait.until(ExpectedConditions.or(
                ExpectedConditions.attributeContains(By.xpath(xpath),attribute,text1),
                ExpectedConditions.attributeContains(By.xpath(xpath),attribute,text2)));
        return true;
    }

    public boolean waitForOneOfTwoTextsInElementAttribute(By element, String attribute, String text1, String text2){
        wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(element, attribute, text1),
                    ExpectedConditions.attributeContains(element, attribute, text2)));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForAttributeToContain(By element, String attribute, String expectedValue, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public boolean waitForAttributeToContain(String xpath, String attribute, String expectedValue, int delay){
        return waitForAttributeToContain(By.xpath(xpath),attribute,expectedValue, delay);
    }

    public boolean waitForAttributeToContain(By element, String attribute, String expectedValue){
        try {
            wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
            return true;
        }catch (Throwable t){
            return false;
        }
    }

    public boolean waitForAttributeToContainRefresh(String xpath, String attribute, String expectedValue) {
        return waitForAttributeToContainRefresh(By.xpath(xpath), attribute,expectedValue);
    }

    public boolean waitForAttributeToContainRefresh(By element, String attribute, String expectedValue){
        wait = new WebDriverWait(driver, 8);
        for( int counter=0; counter<5; counter++) {
            try {
                wait.until(ExpectedConditions.attributeContains(element, attribute, expectedValue));
                return true;
            }catch (Throwable t)
            {
                refreshScreen();
            }
        }
        return false;
    }

    public boolean waitForElementToBeVisibleRefresh(String xpath){
        return waitForElementToBeVisibleRefresh(By.xpath(xpath));
    }

    public boolean waitForElementToBeVisibleRefresh(By element){
        wait = new WebDriverWait(driver, 8);
        for( int counter=0; counter<5; counter++) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(element));
                return true;
            }catch (Throwable t)
            {
                refreshScreen();
            }
        }
        return false;
    }

    public boolean waitForAttributeToContain(String xpath, String attribute, String expectedValue){
        return waitForAttributeToContain(By.xpath(xpath),attribute,expectedValue);
    }

    public String stringFormat(String template, String parameter){
        return String.format(template,parameter);
    }

    public String stringFormat(String template, Integer parameter){
        return String.format(template,parameter);
    }

    public String stringFormat(String template, String parameter1, String parameter2){
        return String.format(template,parameter1,parameter2);
    }

    public boolean waitForElementContainingText(String txt){
        waitForElementToBeVisible(stringFormat(textElement,txt));
        return true;
    }

    public boolean waitForUrlContains(String url){
        wait.until(ExpectedConditions.urlContains(url));
        return true;
    }

    public boolean waitForUrlContains(String url, int delay){
        wait = new WebDriverWait(driver, delay);
        try {
            wait.until(ExpectedConditions.urlContains(url));
            return true;
        }catch (Throwable t){
            return false;
        }finally {
            wait = new WebDriverWait(driver, globalDelay);
        }
    }

    public void uploadFileRobot(By uploadBtn, String filePath){
        clickVisible(uploadBtn);
        sendTextAction(filePath);
//        typeRobot(filePath);
        robotDoEnter();
    }

    public String getPseudoElementValue(String css, boolean after, String attribute){
        String template = "return window.getComputedStyle(document.querySelector('%s'),':%s').getPropertyValue('%s')";
        String pseudo = (after)?"after":"before";
        String script = String.format(template,css,pseudo,attribute);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String content = (String) js.executeScript(script);
        return content;
    }

    public void sendTextAction(By locator, String text){
        action = new Actions(driver);
        clickVisible(locator);
        action.sendKeys(text).build().perform();
        action.sendKeys(Keys.ENTER).build().perform();
    }
    public void sendTextAction(String text){
        action = new Actions(driver);
        action.sendKeys(text).build().perform();
        action.sendKeys(Keys.ENTER).build().perform();
    }

    public void sendEscapeKey(){
        clickOnElement("//body");
        action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void jsClick(WebElement element){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void jsClick(By locator){
        WebElement element = driver.findElement(locator);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void jsClick(String xpath){
        jsClick(By.xpath(xpath));
    }

    public void jsClickIfPresented(By locator){
        if(isElementFound(locator)) {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        }
    }

    public void jsClickIfPresented(String xpath){
        jsClickIfPresented(By.xpath(xpath));
    }

    public boolean isSelected(By locator){
        String className = getElementAttribute(locator,"class");
        return className.contains("selected");
    }

    public boolean isSelected(String xpath){
        return isSelected(By.xpath(xpath));
    }

    public String toggleSelectors(String xpath1, String xpath2){
        String xpath = (isSelected(xpath1)) ? xpath2 : xpath1;
        clickVisible(xpath);
        return xpath;
    }

    public void switchToIframe(By locator){
        waitForElementPresence(locator);
        wait(100);
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void switchToIframe(String xpath){
        switchToIframe(By.xpath(xpath));
    }

    public void switchToDefaultContent(){
        driver.switchTo().defaultContent();
    }








}
