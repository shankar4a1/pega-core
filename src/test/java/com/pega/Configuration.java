// 
// Decompiled by Procyon v0.5.36
// 

package com.pega;

import java.util.Collection;
import com.pega.config.PlatformDetails;
import com.pega.config.DataBaseConfig;
import com.pega.config.L10NConfig;
import com.pega.config.BrowserConfig;
import com.pega.config.AppiumConfig;
import com.pega.config.MobileConfig;
import com.pega.config.SUTConfig;
import com.pega.config.Credential;
import com.pega.config.DriversConfig;

public interface Configuration
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: Configuration.java 195388 2016-06-01 06:55:22Z PavanBeri $";
    
    DriversConfig getDriversConfig();
    
    Credential getCredential();
    
    SUTConfig getSUTConfig();
    
    MobileConfig getMobileConfig();
    
    AppiumConfig getAppiumConfig();
    
    BrowserConfig getBrowserConfig();
    
    L10NConfig getL10NConfig();
    
    DataBaseConfig getDataBaseConfig();
    
    PlatformDetails getPlatformDetails();
    
    String getProperty(final String p0);
    
    String getCredential(final String p0);
    
    String getApplicationsForTag(final Collection<String> p0);
    
    boolean analyseDataTestId();
    
    boolean isAutoSwitchToDefaultContent();
    
    boolean isCaptureClientPerformanceMetrics();
}
