// 
// Decompiled by Procyon v0.5.36
// 

package com.pega;

import com.pega.config.ObjectBean;
import io.appium.java_client.MobileDriver;
import com.pega.framework.TouchActions;
import io.appium.java_client.AppiumDriver;
import com.pega.framework.RobotKeyboardImpl;
import com.pega.framework.http.PegaHttpClient;
import com.pega.framework.ScriptExecutorImpl;
import com.pega.util.DataUtil;
import com.pega.framework.MouseImpl;
import com.pega.framework.KeyboardImpl;
import java.util.Iterator;
import java.util.List;
import com.pega.drivers.PegaPhantomJSDriver;
import com.pega.drivers.PegaIOSDriver;
import com.pega.drivers.PegaIEDriver;
import com.pega.drivers.PegaHtmlUnitDriver;
import com.pega.drivers.PegaFirefoxProxyDriver;
import com.pega.drivers.PegaFirefoxDriver;
import com.pega.drivers.PegaEdgeDriver;
import com.pega.drivers.PegaChromeDriver;
import com.pega.drivers.PegaAndriodDriver;
import com.pega.drivers.PegaRemoteWebDriver;
import com.pega.drivers.AbstractDriver;
import java.util.ArrayList;
import ch.qos.logback.classic.Level;
import com.pega.util.GlobalConstants;
import com.pega.framework.PegaWebDriverImpl;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.LoggerFactory;
import com.pega.config.PegaObjectBean;
import com.pega.util.ClientPerformanceUtil;
import com.pega.framework.TouchActionsImpl;
import java.io.File;
import com.pega.framework.Mouse;
import com.pega.framework.RobotKeyboard;
import com.pega.framework.Keyboard;
import org.openqa.selenium.interactions.Actions;
import com.pega.framework.ScriptExecutor;
import org.openqa.selenium.WebDriver;
import com.pega.framework.PegaWebDriver;
import org.slf4j.Logger;

public class TestEnvironmentImpl implements TestEnvironment
{
    private static final String VERSION = "$Id: TestEnvironmentImpl.java 209038 2016-09-22 09:24:46Z SachinVellanki $";
    private static final Logger LOGGER;
    private PegaWebDriver pegaDriver;
    private WebDriver driver;
    protected Browser browser;
    private Configuration config;
    private ScriptExecutor executor;
    private Actions actions;
    private Keyboard keyboard;
    private RobotKeyboard robotKeyboard;
    private Mouse mouse;
    private File configFile;
    private TouchActionsImpl touchActions;
    private ClientPerformanceUtil clientPerformance;
    private PegaObjectBean pegaObjectBean;
    
    static {
        LOGGER = LoggerFactory.getLogger(TestEnvironmentImpl.class.getName());
    }
    
    public TestEnvironmentImpl() {
        this.pegaDriver = null;
        this.driver = null;
        this.browser = null;
        this.config = null;
        this.executor = null;
        this.actions = null;
        this.keyboard = null;
        this.robotKeyboard = null;
        this.mouse = null;
        this.configFile = null;
        final String browserName = this.getConfiguration().getBrowserConfig().getBrowserName();
        this.setLoggerLevel();
        this.initializeEnvironment(browserName);
    }
    
    public TestEnvironmentImpl(final String browserName) {
        this.pegaDriver = null;
        this.driver = null;
        this.browser = null;
        this.config = null;
        this.executor = null;
        this.actions = null;
        this.keyboard = null;
        this.robotKeyboard = null;
        this.mouse = null;
        this.configFile = null;
        this.getConfiguration();
        this.setLoggerLevel();
        this.initializeEnvironment(browserName);
    }
    
    public TestEnvironmentImpl(final String browserName, final String platform) {
        this.pegaDriver = null;
        this.driver = null;
        this.browser = null;
        this.config = null;
        this.executor = null;
        this.actions = null;
        this.keyboard = null;
        this.robotKeyboard = null;
        this.mouse = null;
        this.configFile = null;
        this.getConfiguration();
        this.setLoggerLevel();
        this.initializeEnvironment(browserName, platform);
    }
    
    public TestEnvironmentImpl(final File file) {
        this.pegaDriver = null;
        this.driver = null;
        this.browser = null;
        this.config = null;
        this.executor = null;
        this.actions = null;
        this.keyboard = null;
        this.robotKeyboard = null;
        this.mouse = null;
        this.configFile = null;
        this.configFile = file;
        final String browserName = this.getConfiguration().getBrowserConfig().getBrowserName();
        this.setLoggerLevel();
        this.initializeEnvironment(browserName);
    }
    
    public TestEnvironmentImpl(final boolean introduceFlakinessForIE, final boolean requireWindowFocusForIE) {
        this.pegaDriver = null;
        this.driver = null;
        this.browser = null;
        this.config = null;
        this.executor = null;
        this.actions = null;
        this.keyboard = null;
        this.robotKeyboard = null;
        this.mouse = null;
        this.configFile = null;
        final DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("ignoreProtectedModeSettings", introduceFlakinessForIE);
        capabilities.setCapability("requireWindowFocus", requireWindowFocusForIE);
        capabilities.setCapability("enablePersistentHover", false);
        capabilities.setCapability("nativeEvents", true);
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("acceptSslCerts", true);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability("unexpectedAlertBehaviour", (Object)UnexpectedAlertBehaviour.IGNORE);
        this.getConfiguration();
        final String browserName = this.getConfiguration().getBrowserConfig().getBrowserName();
        this.setLoggerLevel();
        this.initializeEnvironment(browserName, capabilities, null);
    }
    
    private void initializeEnvironment(final String browserName) {
        TestEnvironmentImpl.LOGGER.debug("Initialising the driver...");
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("acceptSslCerts", true);
        this.initializeEnvironment(browserName, capabilities, null);
        TestEnvironmentImpl.LOGGER.info("Driver is initialised::" + this.driver);
    }
    
    private void initializeEnvironment(final String browserName, final String platform) {
        TestEnvironmentImpl.LOGGER.debug("Initialising the driver::" + this.driver);
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("acceptSslCerts", true);
        this.initializeEnvironment(browserName, capabilities, platform);
        TestEnvironmentImpl.LOGGER.info("Driver is initialised::" + this.driver);
    }
    
    private void initializeEnvironment(final String browserName, final DesiredCapabilities caps, final String platformName) {
        String platform = platformName;
        if (platform == null) {
            platform = this.config.getPlatformDetails().getPlatform();
        }
        else {
            this.config.getPlatformDetails().setPlatform(platform);
            this.config.getBrowserConfig().setBrowserName(browserName);
        }
        this.driver = this.createDriverInstance(browserName, caps, platform);
        this.pegaDriver = new PegaWebDriverImpl(this.driver, this);
        this.initializeMouse();
        if (System.getenv("timeout") != null) {
            TestEnvironmentImpl.LOGGER.debug("timeout=" + System.getenv("timeout"));
            try {
                GlobalConstants.setGLOBAL_TIMEOUT(Integer.parseInt(System.getenv("timeout").trim()));
            }
            catch (Exception e) {
                TestEnvironmentImpl.LOGGER.debug("invalid time out value, using the default value");
            }
        }
        if (this.config.getSUTConfig().isDebugMode() && System.getenv("JENKINS_URL") == null) {
            GlobalConstants.setGLOBAL_TIMEOUT(60);
        }
        String timeout = null;
        if ((timeout = this.config.getProperty("global.timeout")) != null) {
            GlobalConstants.setGLOBAL_TIMEOUT(Integer.parseInt(timeout));
        }
        System.setProperty("l10n.language", this.getConfiguration().getL10NConfig().getL10NLanguage());
        System.setProperty("l10n.bundle.path", this.getConfiguration().getL10NConfig().getL10NLangBundlePath());
    }
    
    private void setLoggerLevel() {
        final ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("ROOT");
        final String loggerLevel = this.config.getSUTConfig().getLoggerLevel().toUpperCase();
        TestEnvironmentImpl.LOGGER.info("Logger level is set to : " + loggerLevel);
        final String s;
        switch (s = loggerLevel) {
            case "ALL": {
                root.setLevel(Level.ALL);
                return;
            }
            case "OFF": {
                root.setLevel(Level.OFF);
                return;
            }
            case "INFO": {
                root.setLevel(Level.INFO);
                return;
            }
            case "WARN": {
                root.setLevel(Level.WARN);
                return;
            }
            case "DEBUG": {
                root.setLevel(Level.DEBUG);
                return;
            }
            case "ERROR": {
                root.setLevel(Level.ERROR);
                return;
            }
            case "TRACE": {
                root.setLevel(Level.TRACE);
                return;
            }
            default:
                break;
        }
        root.setLevel(Level.INFO);
    }
    
    private WebDriver createDriverInstance(final String browserName, final DesiredCapabilities caps, final String platform) {
        WebDriver driver = null;
        final List<AbstractDriver> allDrivers = new ArrayList<AbstractDriver>();
        allDrivers.add(new PegaRemoteWebDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaAndriodDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaChromeDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaEdgeDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaFirefoxDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaFirefoxProxyDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaHtmlUnitDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaIEDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaIOSDriver(this, browserName, caps, platform));
        allDrivers.add(new PegaPhantomJSDriver(this, browserName, caps, platform));
        for (final AbstractDriver currDriver : allDrivers) {
            if (currDriver.matches()) {
                currDriver.initializeDriver();
                driver = currDriver.getDriver();
                break;
            }
        }
        return driver;
    }
    
    private void initializeMouse() {
        this.getMouse().moveTo(540, 540);
    }
    
    @Override
    public void terminate() {
        try {
            this.driver.quit();
        }
        catch (Exception e) {
            TestEnvironmentImpl.LOGGER.error("Selenium is unable to kill the chrome driver process..!!");
        }
    }
    
    @Override
    public Browser getBrowser() {
        if (this.browser == null) {
            this.browser = new BrowserImpl(this);
        }
        return this.browser;
    }
    
    @Override
    public Keyboard getKeyboard() {
        if (this.keyboard == null) {
            this.keyboard = new KeyboardImpl(this);
        }
        return this.keyboard;
    }
    
    @Override
    public Mouse getMouse() {
        if (this.mouse == null) {
            this.mouse = new MouseImpl(this);
        }
        return this.mouse;
    }
    
    @Override
    public Configuration getConfiguration() {
        if (this.config == null) {
            final File f = DataUtil.getGlobalSettingsFile();
            this.config = new ConfigurationImpl(f);
        }
        return this.config;
    }
    
    @Override
    public PegaWebDriver getPegaDriver() {
        return this.pegaDriver;
    }
    
    @Override
    public ScriptExecutor getScriptExecutor() {
        if (this.executor == null) {
            this.executor = new ScriptExecutorImpl(this);
        }
        return this.executor;
    }
    
    @Override
    public Actions getDriverActions() {
        if (this.actions == null) {
            this.actions = new Actions(this.driver);
        }
        return this.actions;
    }
    
    @Override
    public File getConfigFile() {
        return this.configFile;
    }
    
    @Override
    public PegaHttpClient getPegaClient() {
        return null;
    }
    
    @Override
    public RobotKeyboard getRobotKeyboard() {
        if (this.robotKeyboard == null) {
            this.robotKeyboard = new RobotKeyboardImpl();
        }
        return this.robotKeyboard;
    }
    
    @Override
    public AppiumDriver getAppiumDriver() {
        return (AppiumDriver)this.pegaDriver.getDriver();
    }
    
    @Override
    public TouchActions getTouchActions() {
        if (this.touchActions == null) {
            this.touchActions = new TouchActionsImpl((MobileDriver)this.pegaDriver.getDriver());
        }
        return this.touchActions;
    }
    
    @Override
    public ClientPerformanceUtil getClientPerformance() {
        if (this.clientPerformance == null) {
            this.clientPerformance = new ClientPerformanceUtil(this);
        }
        return this.clientPerformance;
    }
    
    @Override
    public <T extends ObjectBean> T getObjectsBean() {
        if (this.pegaObjectBean == null) {
            this.pegaObjectBean = new PegaObjectBean();
        }
        return (T)this.pegaObjectBean;
    }
}
