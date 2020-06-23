// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.drivers;

import org.openqa.selenium.WebDriver;
import java.util.Iterator;
import java.util.Map;
import com.pega.config.BrowserConfig;
import com.pega.config.browser.HtmlUnitConfig;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.pega.TestEnvironment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PegaHtmlUnitDriver extends AbstractDriver
{
    private static final Logger LOGGER;
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)PegaHtmlUnitDriver.class);
    }
    
    public PegaHtmlUnitDriver(final TestEnvironment testEnv, final String browserName, final DesiredCapabilities caps, final String platformName) {
        super(testEnv, browserName, caps, platformName);
    }
    
    @Override
    public void initializeDriver() {
        final BrowserConfig browserConfig = this.config.getBrowserConfig();
        DesiredCapabilities capabilities = this.caps;
        if (browserConfig.getCapabilities() != null || !browserConfig.getCapabilities().equals("")) {
            final Map<String, String> capbs = browserConfig.getCapabilities();
            capabilities = DesiredCapabilities.htmlUnit();
            capabilities.setAcceptInsecureCerts(true);
            for (final String key : capbs.keySet()) {
                capabilities.setCapability(key, (String)capbs.get(key));
            }
            this.driver = (WebDriver)new HtmlUnitDriver((Capabilities)capabilities);
        }
        else {
            BrowserVersion version = BrowserVersion.CHROME;
            final HtmlUnitConfig htmlUnitConfig = (HtmlUnitConfig)this.config.getBrowserConfig();
            if (htmlUnitConfig.getHtmlUnitBrowserType().equalsIgnoreCase("chrome")) {
                version = BrowserVersion.CHROME;
            }
            else if (htmlUnitConfig.getHtmlUnitBrowserType().equalsIgnoreCase("ie") || htmlUnitConfig.getHtmlUnitBrowserType().equalsIgnoreCase("iexplore") || htmlUnitConfig.getHtmlUnitBrowserType().equalsIgnoreCase("internetexplorer")) {
                version = BrowserVersion.INTERNET_EXPLORER;
            }
            else if (htmlUnitConfig.getHtmlUnitBrowserType().equalsIgnoreCase("firefox")) {
                version = BrowserVersion.FIREFOX_60;
            }
            this.driver = (WebDriver)new HtmlUnitDriver(version, true);
        }
        this.driver.manage().window().maximize();
        PegaHtmlUnitDriver.LOGGER.info("HtmlUnit Driver launched successfully");
    }
    
    @Override
    public boolean matches() {
        return "htmlunit".equalsIgnoreCase(this.browserName) && !"android".equalsIgnoreCase(this.platformName) && !"iphone".equalsIgnoreCase(this.platformName) && !"ios".equalsIgnoreCase(this.platformName);
    }
}
