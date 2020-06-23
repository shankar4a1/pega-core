// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.guice;

import com.google.inject.Guice;
import cucumber.api.guice.CucumberModules;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.Injector;
import cucumber.runtime.java.guice.InjectorSource;

public class GuiceInjector implements InjectorSource
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: GuiceInjector.java 174698 2016-02-08 08:24:26Z SachinVellanki $";
    
    public GuiceInjector() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    }
    
    public Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, new Module[] { CucumberModules.SCENARIO, (Module)new TestEnvInjector() });
    }
}
