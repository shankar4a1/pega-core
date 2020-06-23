// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.browser;

import java.util.Properties;
import com.pega.config.BrowserConfig;

public class HtmlUnitConfig extends BrowserConfig
{
    private String htmlunitBrowserVersion;
    
    public HtmlUnitConfig(final Properties prop) {
        super(prop);
        if (System.getenv("htmlunit.browserversion") == null) {
            this.htmlunitBrowserVersion = prop.getProperty("htmlunit.browserversion", "chrome").trim();
        }
        else {
            this.htmlunitBrowserVersion = System.getenv("htmlunit.browserversion").trim();
        }
    }
    
    public String getHtmlUnitBrowserType() {
        return this.htmlunitBrowserVersion;
    }
}
