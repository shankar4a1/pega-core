// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public interface WaitHandler
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: WaitHandler.java 190774 2016-05-04 09:14:20Z ShakkariSakethkumar $";
    public static final String PORTAL_LOADING_XPATH = "//*[text()='Loading...']";
    public static final By ALL_OPENED_TABS = By.cssSelector("ul[class*='Temporary_top_tabsList'] li[role='tab']");
    public static final By ALL_AVAILABLE_FRAMES = By.xpath("//iframe[contains(@id,'PegaGadget')]");
    
    void waitForPageLoaded();
    
    void waitForElementVisibility(final By p0);
    
    void waitForElementClickable(final By p0);
    
    void waitForAlert();
    
    void waitForElementVisibility(final WebElement p0);
    
    void waitForTextPresence(final By p0, final String p1);
    
    void waitTillElementIsEnabled(final By p0);
    
    void waitForElementNotVisible(final By p0);
    
    @Deprecated
    void waitForNewFrameAndSwitchToIt();
    
    void waitForElementPresence(final By p0);
    
    void waitForElementPresence(final By p0, final int p1);
    
    void waitForCondition(final String p0);
    
    void sleep(final long p0);
    
    void waitForNoThrobber();
    
    @Deprecated
    void clickAndWait(final By p0, final By p1, final boolean p2, final boolean p3);
    
    @Deprecated
    void clickAndWait(final By p0, final By p1, final boolean p2, final boolean p3, final PegaWebElement.CheckLocalized p4);
    
    @Deprecated
    PegaWebElement waitAndGetElmt(final By p0, final boolean p1);
    
    void waitForDocStateReady();
    
    @Deprecated
    void waitForDocStateReady(final boolean p0);
    
    void waitForDocStateReady(final int p0);
    
    void waitTillTitleContains(final String p0);
    
    void waitForWindowPresence(final Integer p0);
    
    void waitForFramesToBeLoaded();
    
    void waitForElementNotPresence(final By p0);
    
    void verifyAndWaitIfThrobberPresent();
    
    void waitForTextPresence(final By p0, final String p1, final long p2);
    
    void waitForElementVisibility(final WebElement p0, final int p1);
    
    void waitForElementNotPresence(final By p0, final int p1);
    
    void getPortalReady();
    
    void waitForFileDownload(final String p0, final int p1);
}
