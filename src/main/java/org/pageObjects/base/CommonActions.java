package org.pageObjects.base;

import com.microsoft.playwright.Page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonActions {
    protected Page page;
//    protected Logger log = Logger.getLogger(this.getClass());

    protected final Logger log = LogManager.getLogger(this.getClass());

    public CommonActions(Page page) {
        this.page = page;
    }

    public CommonActions openPage(String url) {
        try {
            page.navigate(url);
            log.warn("Page was opened. Url " + url);
        } catch (Exception e) {
            stopTestAndPrintMessage("Can't open page " + url);
        }
        return this;
    }

    //enter text
    public CommonActions enterText(String locator, String text) {
        try {
            page.fill(locator, text);
            log.warn("Text was entered. Locator " + locator + " Text " + text);
        } catch (Exception e) {
            stopTestAndPrintMessage("Can't enter text " + text + " in locator " + locator);
        }
        return this;
    }

    public CommonActions waitTime(int time) {
        try {
            page.waitForTimeout(time);
            log.warn("Waited for " + time + " milliseconds");
        } catch (Exception e) {
            stopTestAndPrintMessage("Can't wait for " + time + " milliseconds");
        }
        return this;
    }

    public CommonActions click(String locator) {
        try {
            page.click(locator);
            log.warn("Element was clicked. Locator " + locator);
        } catch (Exception e) {
            stopTestAndPrintMessage("Can't click on element " + locator);
        }
        return this;
    }

    public boolean isElementDisplayed(String locator){
        try {
            boolean isDisplayed = page.isVisible(locator);
            log.warn("Element visibility checked. Locator " + locator + " Is Displayed " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            log.warn("Element visibility checked. Locator " + locator + " Is Displayed " + false);
            return false;
        }
    }

    public CommonActions checkIsElementDisplayed(String locator){
        log.info("Trying to find element by locator ");
        assertThat(page.locator(locator)).isVisible();
        log.warn("As expected: Element is displayed " + locator);
        return this;
    }

    public CommonActions checkIsElementNotDisplayed(String locator){
        log.info("Trying to find element by locator ");
        assertThat(page.locator(locator)).not().isVisible();
        log.warn("As expected: Element is not displayed " + locator);
        return this;
    }


    //List of elements

    public CommonActions checkNumberOfElements(int expectedNumber, String locator){
        try {
            int actualNumber = page.locator(locator).count();
            log.warn("Number of elements checked. Locator " + locator + " Expected number " + expectedNumber + " Actual number " + actualNumber);
            Assert.assertEquals("Number of elements " + locator, expectedNumber, actualNumber);
        } catch (Exception e) {
            stopTestAndPrintMessage("Can't check number of elements " + locator);
        }
        return this;
    }

    private void stopTestAndPrintMessage(String message) {
        log.error(message);
        Assert.fail(message);
    }
}
