// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework;

import java.util.LinkedHashSet;
import io.appium.java_client.MobileElement;
import java.awt.Point;
import com.pega.framework.elmt.DropDown;
import com.pega.TestEnvironment;
import org.openqa.selenium.By;
import com.pega.ri.Instance;
import org.openqa.selenium.WebElement;

public interface PegaWebElement extends WebElement, Instance
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: PegaWebElement.java 174698 2016-02-08 08:24:26Z SachinVellanki $";
    public static final String AUTO_SELECTBOX_ID = "ISnsSelect";
    
    String getId();
    
    String getStyle(final String p0);
    
    String getDOMPointer();
    
    By getBy();
    
    @Deprecated
    void _setDOMPointer(final String p0);
    
    @Deprecated
    void _initialize(final TestEnvironment p0, final By p1);
    
    void _setEnvironment(final TestEnvironment p0, final By p1, final String p2);
    
    void _setEnvironment(final TestEnvironment p0, final String p1);
    
    void _setInnerFrameEnv(final TestEnvironment p0, final String p1, final String p2);
    
    PegaWebElement findElement(final By p0);
    
    DropDown findSelectBox(final By p0);
    
    String getTagName();
    
    void mouseOver();
    
    void moveMouseToThis();
    
    void moveMouseToThis(final int p0, final int p1);
    
    void doClickWithMouse();
    
    void doDoubleClickWithMouse();
    
    void doRightClickWithMouse();
    
    void doRightClickWithMouse(final Point p0);
    
    void doDoubleClickWithMouse(final Point p0);
    
    void doClickWithMouse(final Point p0);
    
    void click();
    
    void click(final boolean p0);
    
    void click(final CheckLocalized p0);
    
    void click(final boolean p0, final CheckLocalized p1);
    
    void rightClick();
    
    String getParentDocument();
    
    String getAttribute(final String p0);
    
    void scrollIntoView();
    
    void doubleClick();
    
    void doubleClick(final boolean p0);
    
    void focus();
    
    void blur();
    
    void dragAndDrop(final PegaWebElement p0);
    
    void dragAndDrop(final int p0, final int p1);
    
    void dragAndDrop(final int p0, final int p1, final boolean p2);
    
    void dragAndDrop(final PegaWebElement p0, final int p1, final int p2);
    
    void dragAndDrop(final PegaWebElement p0, final int p1, final int p2, final boolean p3);
    
    boolean isEnabled();
    
    boolean isReadOnly();
    
    int getElementLeft();
    
    int getElementTop();
    
    int getElementWidth();
    
    int getElementHeight();
    
    void check();
    
    void sendKeys(final CharSequence... p0);
    
    AutoComplete findAutoComplete(final By p0);
    
    void clear();
    
    WebElement getWebElement();
    
    MobileElement getMobileElement();
    
    boolean isVisible();
    
    boolean verifyElement(final By p0);
    
    void dragAndDrop(final PegaWebElement p0, final int p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void _setEnvironment(final TestEnvironment p0, final By p1, final String p2, final LinkedHashSet<By> p3);
    
    LinkedHashSet<By> getFramesSet();
    
    public enum CheckLocalized
    {
        TRUE("TRUE", 0), 
        FALSE("FALSE", 1);
        
        private CheckLocalized(final String name, final int ordinal) {
        }
    }
}
