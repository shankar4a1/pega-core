// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.HasInputDevices;
import com.pega.TestEnvironment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class KeyboardImpl implements Keyboard
{
    private static final String VERSION = "$Id: KeyboardImpl.java 121818 2015-01-26 07:18:23Z SachinVellanki $";
    private Actions actions;
    private WebDriver driver;
    private org.openqa.selenium.interactions.Keyboard keyboard;
    
    public KeyboardImpl(final TestEnvironment testEnv) {
        this.driver = testEnv.getPegaDriver().getDriver();
        this.keyboard = ((HasInputDevices)this.driver).getKeyboard();
        this.actions = testEnv.getDriverActions();
    }
    
    @Override
    public void keyPress(final Keys key) {
        this.keyboard.pressKey((CharSequence)key);
    }
    
    @Override
    public void keyRelease(final Keys key) {
        this.keyboard.releaseKey((CharSequence)key);
    }
    
    @Override
    public void keyPressAndRelease(final Keys key) {
        this.keyPress(key);
        this.keyRelease(key);
    }
    
    @Override
    public void sendKeys(final Keys key) {
        this.keyboard.sendKeys(new CharSequence[] { (CharSequence)key });
    }
    
    @Override
    public void typeString(final String text) {
        this.actions.sendKeys(new CharSequence[] { text }).build().perform();
    }
    
    @Override
    public void shiftPress(final Keys key) {
        this.actions.keyDown((CharSequence)Keys.SHIFT).build().perform();
        this.keyPressAndRelease(key);
        this.actions.keyUp((CharSequence)Keys.SHIFT).build().perform();
    }
    
    @Override
    public void ctrlPress(final Keys key) {
        this.ctrlKeyDown();
        this.keyPressAndRelease(key);
        this.ctrlKeyUp();
    }
    
    @Override
    public void ctrlKeyDown() {
        this.actions.keyDown((CharSequence)Keys.CONTROL).build().perform();
    }
    
    @Override
    public void ctrlKeyUp() {
        this.actions.keyUp((CharSequence)Keys.CONTROL).build().perform();
    }
    
    @Override
    public void shiftKeyDown() {
        this.actions.keyDown((CharSequence)Keys.SHIFT).build().perform();
    }
    
    @Override
    public void shiftKeyUp() {
        this.actions.keyUp((CharSequence)Keys.SHIFT).build().perform();
    }
    
    @Override
    public void ctrlPress(final String s) {
        this.actions.keyDown((CharSequence)Keys.CONTROL).sendKeys(new CharSequence[] { s }).keyUp((CharSequence)Keys.CONTROL).build().perform();
    }
    
    @Override
    public void shiftPress(final String s) {
        this.actions.keyDown((CharSequence)Keys.SHIFT).sendKeys(new CharSequence[] { s }).keyUp((CharSequence)Keys.SHIFT).build().perform();
    }
}
