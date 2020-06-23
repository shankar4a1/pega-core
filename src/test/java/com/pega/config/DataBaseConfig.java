// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config;

import java.util.Properties;

public class DataBaseConfig
{
    private String dataBaseURL;
    private String dataBaseUserName;
    private String dataBasePassword;
    
    public DataBaseConfig(final Properties prop) {
        this.dataBaseURL = prop.getProperty("db.url", "").trim();
        this.dataBaseUserName = prop.getProperty("db.user", "").trim();
        this.dataBasePassword = prop.getProperty("db.pwd", "").trim();
    }
    
    public String getDataBaseURL() {
        return this.dataBaseURL;
    }
    
    public String getDataBasUserName() {
        return this.dataBaseUserName;
    }
    
    public String getDataBasePassword() {
        return this.dataBasePassword;
    }
}
