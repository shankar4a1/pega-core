// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import java.util.Iterator;
import com.pega.exceptions.GroupAssertException;
import com.pega.TestEnvironment;
import org.testng.Reporter;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class GroupAsserts
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: GroupAsserts.java 198173 2016-06-16 05:13:57Z PavanBeri $";
    private static List<String> assertionFailures;
    
    static {
        GroupAsserts.assertionFailures = new ArrayList<String>();
    }
    
    public GroupAsserts() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    }
    
    public static void assertTrue(final boolean condition, final String message) {
        try {
            Assert.assertTrue(condition, message);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertTrue statement executed", true);
    }
    
    public static void assertTrue(final boolean condition) {
        try {
            Assert.assertTrue(condition);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertTrue statement executed", true);
    }
    
    public static void assertEquals(final String actual, final String expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertEquals statement executed for Expected: " + expected + " Actual: " + actual, true);
    }
    
    public static void assertEquals(final String actual, final String expected) {
        try {
            Assert.assertEquals(actual, expected);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertEquals statement executed for Expected: " + expected + " Actual: " + actual, true);
    }
    
    public static void assertEquals(final int actual, final int expected, final String message) {
        try {
            Assert.assertEquals(actual, expected, message);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertEquals statement executed for Expected: " + expected + " Actual: " + actual, true);
    }
    
    public static void assertEquals(final int actual, final int expected) {
        try {
            Assert.assertEquals(actual, expected);
        }
        catch (AssertionError e) {
            GroupAsserts.assertionFailures.add(e.getMessage());
        }
        Reporter.log("Group assertEquals statement executed for Expected: " + expected + " Actual: " + actual, true);
    }
    
    public static void throwAssertFailures(final TestEnvironment testEnv) throws GroupAssertException {
        String message = "";
        if (GroupAsserts.assertionFailures.size() > 0) {
            final Iterator iterator = GroupAsserts.assertionFailures.iterator();
            while (iterator.hasNext()) {
                message = String.valueOf(message) + iterator.next() + "\n";
            }
            GroupAsserts.assertionFailures.clear();
            ScreenshotUtil.captureScreenshot(testEnv);
        }
        if (!message.equals("")) {
            throw new GroupAssertException(message);
        }
    }
}
