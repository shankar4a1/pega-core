// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.drivers;

import org.openqa.selenium.WebDriver;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Proxy;
import java.util.HashMap;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import com.pega.config.util.GetChromeDriver;
import com.pega.config.browser.ChromeConfig;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.pega.TestEnvironment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PegaChromeDriver extends AbstractDriver
{
    private static final Logger LOGGER;
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)PegaChromeDriver.class);
    }
    
    public PegaChromeDriver(final TestEnvironment testEnv, final String browserName, final DesiredCapabilities caps, final String platformName) {
        super(testEnv, browserName, caps, platformName);
    }
    
    @Override
    public void initializeDriver() {
        final ChromeConfig chromeConfig = (ChromeConfig)this.config.getBrowserConfig();
        if (chromeConfig.isChromeAutoDownload()) {
            GetChromeDriver.getChromeDriverForChrome();
        }
        if (this.config.getPlatformDetails().isWindows()) {
            System.setProperty("webdriver.chrome.driver", this.config.getDriversConfig().getChromeDriverPath());
        }
        else if (!this.config.getSUTConfig().isPipelineRun()) {
            PegaChromeDriver.LOGGER.debug("Setting the linux chrome driver path to:" + this.config.getDriversConfig().getChromeDriverLinuxPath() + ". If you want in different location set it using chrome.driver.linux in Global Settings Properties file");
            final File f = new File(this.config.getDriversConfig().getChromeDriverLinuxPath());
            f.setExecutable(true);
            System.setProperty("webdriver.chrome.driver", this.config.getDriversConfig().getChromeDriverLinuxPath());
        }
        final ChromeOptions options = new ChromeOptions();
        if (!this.config.getPlatformDetails().isWindows()) {
            options.addArguments(new String[] { "--no-proxy-server" });
            options.addArguments(new String[] { "--no-sandbox" });
        }
        if (chromeConfig.launchBrowserWithAddOns()) {
            final String chromeExtensions = chromeConfig.getChromeExtensionPath();
            if ("".equals(chromeExtensions)) {
                PegaChromeDriver.LOGGER.debug("No Extensions available for chrome browser in the global properties file..!!");
            }
            else {
                String[] split;
                for (int length = (split = chromeConfig.getChromeExtensionPath().split(",")).length, i = 0; i < length; ++i) {
                    final String addOnFile = split[i];
                    final File addOnPath = new File(addOnFile);
                    try {
                        options.addExtensions(new File[] { addOnPath });
                        PegaChromeDriver.LOGGER.debug(String.valueOf(addOnPath.getName()) + " extension is added..");
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Something goes wrong while adding extensions to browser, at " + addOnPath, e);
                    }
                }
            }
        }
        final Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 2);
        prefs.put("profile.default_content_settings.location", 2);
        prefs.put("profile.default_content_settings.geolocation", 2);
        prefs.put("credentials_enable_service", false);
        options.setExperimentalOption("prefs", (Object)prefs);
        options.setExperimentalOption("useAutomationExtension", (Object)false);
        options.setExperimentalOption("excludeSwitches", (Object)new String[] { "enable-automation" });
        options.addArguments(new String[] { "--test-type" });
        options.addArguments(new String[] { "--window-position=0,0" });
        if (this.config.getSUTConfig().isEnableFullScreenMode()) {
            options.addArguments(new String[] { "--kiosk" });
        }
        if (!((ChromeConfig)this.config.getBrowserConfig()).launchBrowserWithAddOns()) {
            options.addArguments(new String[] { "chrome.switches" });
        }
        if (chromeConfig.getChromeOptionsArguments().size() != 0) {
            final HashSet<String> argumentArray = chromeConfig.getChromeOptionsArguments();
            for (final String arg : argumentArray) {
                options.addArguments(new String[] { arg });
            }
        }
        if (!this.config.getBrowserConfig().getProxyHost().equals("") || !this.config.getBrowserConfig().getProxyPort().equals("")) {
            final String PROXY = String.valueOf(this.config.getBrowserConfig().getProxyHost()) + ":" + this.config.getBrowserConfig().getProxyPort();
            PegaChromeDriver.LOGGER.info("Setting Chrome Proxy : " + PROXY);
            final Proxy proxy = new Proxy();
            proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
            options.setCapability("proxy", (Object)proxy);
        }
        if (this.config.getBrowserConfig().getCapabilities() != null) {
            PegaChromeDriver.LOGGER.debug("Chrome Options Requested:" + this.config.getBrowserConfig().getCapabilities());
            final Map<String, String> capbs = this.config.getBrowserConfig().getCapabilities();
            for (final String key : capbs.keySet()) {
                options.setCapability(key, (String)capbs.get(key));
            }
        }
        this.driver = (WebDriver)new ChromeDriver(options);
        if (this.config.getPlatformDetails().isWindows() && !this.config.getSUTConfig().isEnableFullScreenMode()) {
            this.driver.manage().window().maximize();
        }
        PegaChromeDriver.LOGGER.info("Chrome Driver launched successfully");
    }
    
    @Override
    public boolean matches() {
        return ("chrome".equalsIgnoreCase(this.browserName) || "".equals(this.browserName)) && !"android".equalsIgnoreCase(this.platformName) && !"iphone".equalsIgnoreCase(this.platformName) && !"ios".equalsIgnoreCase(this.platformName);
    }
}
