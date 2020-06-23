// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.page;

import com.pega.framework.AutoComplete;
import com.pega.ri.Wizard;
import com.pega.framework.elmt.DropDown;
import org.openqa.selenium.WebElement;
import java.util.List;
import com.pega.framework.elmt.Frame;
import com.pega.framework.PegaWebElement;
import org.openqa.selenium.By;
import com.pega.framework.WaitHandler;
import org.openqa.selenium.interactions.Actions;
import com.pega.framework.ScriptExecutor;
import com.pega.TestEnvironment;
import com.pega.framework.PegaWebDriver;

public class TopDocumentImpl implements TopDocument
{
    private static final String VERSION = "$Id: PegaWebDriverImpl.java 195418 2016-06-01 08:59:34Z AnilBattinapati $";
    protected PegaWebDriver pegaDriver;
    protected TestEnvironment testEnv;
    protected ScriptExecutor scriptExecutor;
    protected Actions actions;
    protected WaitHandler waitHandler;
    
    public TopDocumentImpl(final TestEnvironment testEnv) {
        this.testEnv = testEnv;
        this.pegaDriver = testEnv.getPegaDriver();
        this.actions = testEnv.getDriverActions();
        this.scriptExecutor = testEnv.getScriptExecutor();
        this.waitHandler = this.pegaDriver.handleWaits();
    }
    
    @Override
    public PegaWebElement findElement(final By by, final boolean switchToDefaultContent) {
        return this.pegaDriver.findElement(by, switchToDefaultContent);
    }
    
    @Override
    public PegaWebElement findElement(final By by) {
        return this.findElement(by, this.testEnv.getConfiguration().isAutoSwitchToDefaultContent());
    }
    
    @Override
    public Frame findFrame(final String frameId) {
        return this.pegaDriver.findFrame(frameId);
    }
    
    @Override
    public List<WebElement> findElements(final By by) {
        return (List<WebElement>)this.pegaDriver.findElements(by);
    }
    
    @Override
    public boolean verifyElement(final PegaWebElement elmt, final By locator) {
        return this.pegaDriver.verifyElement(elmt, locator);
    }
    
    @Override
    public boolean verifyElement(final By locator) {
        return this.pegaDriver.verifyElement(locator);
    }
    
    @Override
    public String getActiveFrameId(final boolean switchToActiveFrame) {
        return this.pegaDriver.getActiveFrameId(switchToActiveFrame);
    }
    
    @Override
    public DropDown findSelectBox(final By by) {
        return this.pegaDriver.findSelectBox(by);
    }
    
    @Override
    public Wizard findWizard(final String frameId) {
        return this.pegaDriver.findWizard(frameId);
    }
    
    @Override
    public boolean verifyElementVisible(final By locator) {
        return this.pegaDriver.verifyElementVisible(locator);
    }
    
    @Override
    public AutoComplete findAutoComplete(final By by) {
        return this.pegaDriver.findAutoComplete(by);
    }
    
    @Override
    public TestEnvironment getTestEnv() {
        return this.testEnv;
    }
    
    @Override
    public Frame findFrame(final PegaWebElement element) {
        return this.pegaDriver.findFrame(element);
    }
    
    @Override
    public Wizard findWizard(final PegaWebElement element) {
        return this.pegaDriver.findWizard(element);
    }
}
