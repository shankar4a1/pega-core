// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.pega.TestEnvironment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PegaFirefoxProxyDriver extends AbstractDriver
{
    private static final Logger LOGGER;
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)PegaFirefoxProxyDriver.class);
    }
    
    public PegaFirefoxProxyDriver(final TestEnvironment testEnv, final String browserName, final DesiredCapabilities caps, final String platformName) {
        super(testEnv, browserName, caps, platformName);
    }
    
    @Override
    public void initializeDriver() {
        final String PROXY = String.valueOf(this.config.getBrowserConfig().getProxyHost()) + ":" + this.config.getBrowserConfig().getProxyPort();
        PegaFirefoxProxyDriver.LOGGER.info("Proxy : " + PROXY);
        final Proxy proxy = new Proxy();
        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
        final DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("proxy", (Object)proxy);
        if (this.config.getPlatformDetails().isWindows()) {
            System.setProperty("webdriver.gecko.driver", this.config.getDriversConfig().getFirefoxDriverPath());
        }
        else if (!this.config.getSUTConfig().isPipelineRun()) {
            PegaFirefoxProxyDriver.LOGGER.debug("Setting the linux chrome driver path to:" + this.config.getDriversConfig().getFirefoxDriverLinuxPath() + ". If you want in different location set it using chrome.driver.linux in Global Settings Properties file");
            final File f = new File(this.config.getDriversConfig().getFirefoxDriverLinuxPath());
            f.setExecutable(true);
            System.setProperty("webdriver.gecko.driver", this.config.getDriversConfig().getFirefoxDriverLinuxPath());
        }
        this.driver = (WebDriver)new FirefoxDriver((Capabilities)cap);
        this.driver.manage().window().maximize();
    }
    
    @Override
    public boolean matches() {
        return "proxy".equalsIgnoreCase(this.browserName) && !"android".equalsIgnoreCase(this.platformName) && !"iphone".equalsIgnoreCase(this.platformName) && !"ios".equalsIgnoreCase(this.platformName);
    }
}
