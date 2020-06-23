// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config;

import java.util.Properties;

public class Credential
{
    private Properties usersProperties;
    
    public Credential(final Properties prop) {
        this.usersProperties = prop;
    }
    
    public String getUserID(final String propertyName) {
        return this.usersProperties.getProperty(propertyName);
    }
    
    public String getPassword(final String propertyName) {
        return this.usersProperties.getProperty(propertyName);
    }
}
