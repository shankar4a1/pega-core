// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework;

import com.pega.TestEnvironment;

public class WindowImpl extends PegaWebDriverImpl implements Window
{
    private static final String VERSION = "$Id: WindowImpl.java 121818 2015-01-26 07:18:23Z SachinVellanki $";
    
    public WindowImpl(final TestEnvironment testEnv) {
        super(testEnv);
    }
    
    @Override
    public String getWindowID() {
        return null;
    }
}
