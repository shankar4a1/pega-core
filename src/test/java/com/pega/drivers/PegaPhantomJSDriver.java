// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.drivers;

import org.openqa.selenium.WebDriver;
import java.util.Iterator;
import java.util.Map;
import com.pega.config.BrowserConfig;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.pega.TestEnvironment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PegaPhantomJSDriver extends AbstractDriver
{
    private static final Logger LOGGER;
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)PegaPhantomJSDriver.class);
    }
    
    public PegaPhantomJSDriver(final TestEnvironment testEnv, final String browserName, final DesiredCapabilities caps, final String platformName) {
        super(testEnv, browserName, caps, platformName);
    }
    
    @Override
    public void initializeDriver() {
        final BrowserConfig browserConfig = this.config.getBrowserConfig();
        final DesiredCapabilities capabilities = this.caps;
        System.setProperty("phantomjs.binary.path", this.config.getDriversConfig().getPhantomJSDriverPath());
        if (browserConfig.getCapabilities() != null || !"".equals(browserConfig.getCapabilities())) {
            final Map<String, String> capbs = browserConfig.getCapabilities();
            capabilities.setAcceptInsecureCerts(true);
            for (final String key : capbs.keySet()) {
                capabilities.setCapability(key, (String)capbs.get(key));
            }
            this.driver = (WebDriver)new PhantomJSDriver((Capabilities)capabilities);
        }
        else {
            this.driver = (WebDriver)new PhantomJSDriver();
        }
        PegaPhantomJSDriver.LOGGER.info("PhantomJS Driver launched successfully");
    }
    
    @Override
    public boolean matches() {
        return "phantomjs".equalsIgnoreCase(this.browserName) && !"android".equalsIgnoreCase(this.platformName) && !"iphone".equalsIgnoreCase(this.platformName) && !"ios".equalsIgnoreCase(this.platformName);
    }
}
