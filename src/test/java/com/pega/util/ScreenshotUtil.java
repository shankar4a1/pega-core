// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import java.lang.reflect.Method;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import cucumber.api.Scenario;
import com.pega.TestEnvironment;

public class ScreenshotUtil
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: ScreenshotUtil.java 198173 2016-06-16 05:13:57Z PavanBeri $";
    
    public ScreenshotUtil() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    }
    
    public static void captureScreenshot(final TestEnvironment testEnv) {
        try {
            final Class c = Class.forName("com.pega.MyTestEnvironment");
            final Method m = c.getDeclaredMethod("getScenario", (Class[])new Class[0]);
            final Scenario scenario = (Scenario)m.invoke(c.cast(testEnv), new Object[0]);
            try {
                final byte[] screenshot = (byte[])((TakesScreenshot)testEnv.getPegaDriver().getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
            catch (Exception e2) {
                scenario.write("Unable to take screenshot<br/>");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
