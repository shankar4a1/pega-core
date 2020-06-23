// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.drivers;

import org.openqa.selenium.WebDriver;
import com.pega.config.BrowserConfig;
import java.util.Iterator;
import java.util.Map;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.io.File;
import com.pega.config.browser.RemoteDriverConfig;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.pega.TestEnvironment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PegaRemoteWebDriver extends AbstractDriver
{
    private static final Logger LOGGER;
    
    static {
        LOGGER = LoggerFactory.getLogger((Class)PegaRemoteWebDriver.class);
    }
    
    public PegaRemoteWebDriver(final TestEnvironment testEnv, final String browserName, final DesiredCapabilities caps, final String platformName) {
        super(testEnv, browserName, caps, platformName);
    }
    
    @Override
    public void initializeDriver() {
        final RemoteDriverConfig remoteDriverConfig = (RemoteDriverConfig)this.config.getBrowserConfig();
        final DesiredCapabilities capabilities = this.caps;
        capabilities.setBrowserName(this.browserName);
        capabilities.setVersion(remoteDriverConfig.getBrowserVersion());
        final Map<String, String> hubCapabilities = remoteDriverConfig.getCapabilities();
        PegaRemoteWebDriver.LOGGER.debug("Capabilities Requested:" + hubCapabilities);
        for (final String key : hubCapabilities.keySet()) {
            capabilities.setCapability(key, (String)hubCapabilities.get(key));
        }
        if (!remoteDriverConfig.getTunnelCommand().equals("")) {
            try {
                PegaRemoteWebDriver.LOGGER.debug("Tunnel Command is \n" + System.getProperty("user.dir") + File.separator + remoteDriverConfig.getTunnelCommand());
                Runtime.getRuntime().exec(String.valueOf(System.getProperty("user.dir")) + File.separator + remoteDriverConfig.getTunnelCommand());
                Thread.sleep(10000L);
                PegaRemoteWebDriver.LOGGER.debug("Tunnelling established");
            }
            catch (Exception e) {
                PegaRemoteWebDriver.LOGGER.error("Unable to tunnel with the tunneling command provided");
                e.printStackTrace();
            }
        }
        if (remoteDriverConfig.isBrowserStackCapblty()) {
            final String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
            final String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
            PegaRemoteWebDriver.LOGGER.info("BROWSERSTACK_LOCAL : " + browserstackLocal);
            PegaRemoteWebDriver.LOGGER.info("BROWSERSTACK_LOCAL_IDENTIFIER : " + browserstackLocalIdentifier);
            capabilities.setCapability("browserstack.local", browserstackLocal);
            capabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
        }
        try {
            this.driver = (WebDriver)new RemoteWebDriver(new URL(remoteDriverConfig.getHubUrl()), (Capabilities)capabilities);
            ((RemoteWebDriver)this.driver).setFileDetector((FileDetector)new LocalFileDetector());
            if (this.config.getPlatformDetails().isWindows() && !this.config.getSUTConfig().isEnableFullScreenMode()) {
                this.driver.manage().window().maximize();
            }
        }
        catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public boolean matches() {
        final BrowserConfig browserConfig = this.config.getBrowserConfig();
        return browserConfig instanceof RemoteDriverConfig;
    }
}
