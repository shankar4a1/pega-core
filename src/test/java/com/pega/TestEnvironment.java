// 
// Decompiled by Procyon v0.5.36
// 

package com.pega;

import com.pega.config.ObjectBean;
import com.pega.util.ClientPerformanceUtil;
import com.pega.framework.TouchActions;
import io.appium.java_client.AppiumDriver;
import com.pega.framework.http.PegaHttpClient;
import java.io.File;
import com.pega.framework.PegaWebDriver;
import com.pega.framework.ScriptExecutor;
import org.openqa.selenium.interactions.Actions;
import com.pega.framework.Mouse;
import com.pega.framework.RobotKeyboard;
import com.pega.framework.Keyboard;

public interface TestEnvironment
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: TestEnvironment.java 170459 2016-01-08 12:18:51Z ChanukyaVempati $";
    
    void terminate();
    
    Browser getBrowser();
    
    Keyboard getKeyboard();
    
    RobotKeyboard getRobotKeyboard();
    
    Mouse getMouse();
    
    Actions getDriverActions();
    
    ScriptExecutor getScriptExecutor();
    
    PegaWebDriver getPegaDriver();
    
    Configuration getConfiguration();
    
    File getConfigFile();
    
    PegaHttpClient getPegaClient();
    
    AppiumDriver getAppiumDriver();
    
    TouchActions getTouchActions();
    
    ClientPerformanceUtil getClientPerformance();
    
     <T extends ObjectBean> T getObjectsBean();
}
