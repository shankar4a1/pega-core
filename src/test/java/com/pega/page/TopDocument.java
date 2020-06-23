// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.page;

import org.openqa.selenium.WebElement;
import java.util.List;
import com.pega.TestEnvironment;
import com.pega.framework.AutoComplete;
import com.pega.framework.elmt.DropDown;
import com.pega.ri.Wizard;
import com.pega.framework.elmt.Frame;
import com.pega.framework.PegaWebElement;
import org.openqa.selenium.By;

public interface TopDocument
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: PegaWebDriver.java 174698 2016-02-08 08:24:26Z SachinVellanki $";
    
    PegaWebElement findElement(final By p0);
    
    PegaWebElement findElement(final By p0, final boolean p1);
    
    @Deprecated
    Frame findFrame(final String p0);
    
    Wizard findWizard(final String p0);
    
    DropDown findSelectBox(final By p0);
    
    AutoComplete findAutoComplete(final By p0);
    
    String getActiveFrameId(final boolean p0);
    
    boolean verifyElement(final PegaWebElement p0, final By p1);
    
    boolean verifyElement(final By p0);
    
    boolean verifyElementVisible(final By p0);
    
    TestEnvironment getTestEnv();
    
    Frame findFrame(final PegaWebElement p0);
    
    Wizard findWizard(final PegaWebElement p0);
    
    List<WebElement> findElements(final By p0);
}
