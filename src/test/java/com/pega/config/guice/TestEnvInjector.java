// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.guice;

import org.testng.Reporter;
import com.pega.TestEnvironment;
import com.pega.exceptions.PegaWebDriverException;
import com.google.inject.AbstractModule;

public class TestEnvInjector extends AbstractModule
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: TestEnvInjector.java 174698 2016-02-08 08:24:26Z SachinVellanki $";
    
    public TestEnvInjector() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    }
    
    protected void configure() {
        String className = null;
        try {
            className = System.getProperty("baseClass");
            if (className == null) {
                className = System.getenv("baseClass");
            }
            if (className == null) {
                throw new PegaWebDriverException("baseClass param is empty. Base class which extends com.pega.TestEnvironmentImpl is not provided");
            }
            final Class<TestEnvironment> classType = (Class<TestEnvironment>)Class.forName(className, true, this.getClass().getClassLoader());
            this.bind((Class)TestEnvironment.class).to((Class)classType);
            Reporter.log("Implementation class for TestEnvironment interface is: " + className, true);
        }
        catch (ClassNotFoundException e) {
            throw new PegaWebDriverException("Implementation class is not found: " + className);
        }
    }
}
