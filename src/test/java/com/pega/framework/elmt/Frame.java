// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework.elmt;

import com.pega.framework.AnyPicker;
import java.util.LinkedHashSet;
import java.util.List;
import com.pega.framework.AutoComplete;
import org.openqa.selenium.WebElement;
import com.pega.framework.PegaWebElement;
import org.openqa.selenium.By;
import com.pega.TestEnvironment;

public interface Frame
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: Frame.java 188865 2016-04-22 10:38:15Z VenkatasrikarVadlamudi $";
    
    TestEnvironment getTestEnvironment();
    
    Frame findFrame(final String p0);
    
    PegaWebElement findElement(final By p0);
    
    PegaWebElement findElement(final String p0, final String p1);
    
    WebElement getWebElement();
    
    void _setInnerFrameEnv(final TestEnvironment p0, final String p1, final String p2);
    
    String getDOMPointer();
    
    Frame findFrame(final String p0, final boolean p1);
    
    PegaWebElement findElement(final By p0, final boolean p1);
    
    DropDown findSelectBox(final By p0);
    
    AutoComplete findAutoComplete(final By p0);
    
    String getFrameDocument();
    
    boolean verifyElement(final By p0);
    
    String getFrameDocument(final boolean p0);
    
    Frame findFrameByName(final String p0);
    
    String getId();
    
    List<WebElement> findElements(final By p0);
    
    @Deprecated
    void _initialize(final TestEnvironment p0, final By p1);
    
    @Deprecated
    void _setDOMPointer(final String p0);
    
    void _setFrames(final LinkedHashSet<By> p0, final By p1);
    
    Frame findFrame(final PegaWebElement p0);
    
    LinkedHashSet<By> getFramesSet();
    
    String getActiveFrameId(final boolean p0);
    
    String getActiveFrameIdWithInThisFrame();
    
    void switchToParentFrame();
    
    boolean verifyElementVisible(final By p0);
    
    boolean verifyElement(final PegaWebElement p0, final By p1);
    
    AnyPicker findAnyPicker(final String p0);
    
    ConditionBuilder findConditionBuilder(final By p0);
}
