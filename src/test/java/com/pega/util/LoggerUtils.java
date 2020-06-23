// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import org.slf4j.MDC;

public class LoggerUtils
{
    public static final String TEST_NAME = "testname";
    
    public static void startTestLogging(final String name) {
        MDC.put("testname", name);
    }
    
    public static String stopTestLogging() {
        final String name = MDC.get("testname");
        MDC.remove("testname");
        return name;
    }
}
