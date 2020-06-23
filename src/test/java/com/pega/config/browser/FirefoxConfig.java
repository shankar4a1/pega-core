// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.browser;

import java.util.Properties;
import com.pega.config.BrowserConfig;

public class FirefoxConfig extends BrowserConfig
{
    private String fireFoxAddOnsPath;
    
    public FirefoxConfig(final Properties prop) {
        super(prop);
        if (System.getenv("firefox.addOns.paths") == null) {
            this.fireFoxAddOnsPath = prop.getProperty("firefox.addOns.paths", "").trim();
        }
        else {
            this.fireFoxAddOnsPath = System.getenv("firefox.addOns.paths").trim();
        }
    }
    
    public String getFirefoxAddOnsPath() {
        return this.fireFoxAddOnsPath;
    }
}
