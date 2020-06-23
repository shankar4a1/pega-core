// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework.elmt;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.pega.framework.PegaWebElement;
import com.pega.framework.AnyPicker;
import com.pega.framework.PegaWebElementImpl;

public class AnyPickerImpl extends PegaWebElementImpl implements AnyPicker
{
    private PegaWebElement elmt;
    private String eleXpath;
    
    public AnyPickerImpl(final WebElement elmt) {
        super(elmt);
        this.elmt = null;
    }
    
    @Override
    public void selectValue(final String recordType, final String subCategory) {
        this.eleXpath = this.getAnyPickerHeader();
        this.findElement(By.xpath(this.eleXpath)).click();
        this.pegaDriver.switchToActiveFrame();
        this.findElement(By.xpath("//input[@id='anypicker-input']")).sendKeys("z" + Keys.BACK_SPACE);
        this.findElement(By.xpath("//span[text()='" + recordType + "']/following-sibling::span//i")).click();
        this.pegaDriver.switchToActiveFrame();
        this.findElement(By.xpath("//li[@role='treeitem']//span[text()='" + subCategory + "']")).click();
    }
    
    @Override
    public void selectValue(final String ruleLabel) {
        this.eleXpath = this.getAnyPickerHeader();
        this.findElement(By.xpath(this.eleXpath)).click();
        this.findElement(By.xpath("//input[@id='anypicker-input']")).sendKeys(ruleLabel);
        this.findElement(By.xpath("//li[@role='treeitem']//span[text()='" + ruleLabel + "']")).click();
    }
    
    @Override
    public List<List<String>> getTopLevelContents() {
        this.eleXpath = this.getAnyPickerHeader();
        this.findElement(By.xpath(this.eleXpath)).click();
        this.pegaDriver.switchToActiveFrame();
        return this.getContents();
    }
    
    @Override
    public List<List<String>> getRuleTypeContents(final String recordType) {
        this.eleXpath = this.getAnyPickerHeader();
        this.findElement(By.xpath(this.eleXpath)).click();
        this.pegaDriver.switchToActiveFrame();
        this.findElement(By.xpath("//input[@id='anypicker-input']")).sendKeys("z" + Keys.BACK_SPACE);
        this.findElement(By.xpath("//span[text()='" + recordType + "']/following-sibling::span//i")).click();
        this.pegaDriver.switchToActiveFrame();
        final List<List<String>> rawList = this.getContents();
        rawList.remove(0);
        rawList.remove(0);
        return rawList;
    }
    
    @Override
    public void deleteValue(final String fieldTobeDeleted) {
        this.findElement(By.xpath("//span[text()='" + fieldTobeDeleted + "']/following-sibling::i[@id='anypicker-clearIcon']")).click();
    }
    
    private String getAnyPickerHeader() {
        return this.getBy().toString().replace("By.xpath:", "").trim().concat("//i[@class='pi pi-caret-down']");
    }
    
    private List<List<String>> getContents() {
        final List<WebElement> properties = this.findElements(By.xpath("//li[@role='treeitem']//span"));
        final List<List<String>> contentsArray = new ArrayList<List<String>>();
        for (int i = 0; i < properties.size(); ++i) {
            final ArrayList<String> entry = new ArrayList<String>();
            final String curName = properties.get(i).getText();
            if (!curName.isEmpty()) {
                entry.add(curName);
                if (i < properties.size() - 1) {
                    final String nextName = properties.get(i + 1).getText();
                    if (nextName.isEmpty()) {
                        entry.add("Category");
                        ++i;
                    }
                    else {
                        entry.add("Property");
                    }
                }
                else {
                    entry.add("Property");
                }
            }
            contentsArray.add(entry);
        }
        return contentsArray;
    }
    
    private String mapRuleTypeToCategory(final String ruleType) {
        switch (ruleType) {
            case "When": {
                return "When Conditions";
            }
            default:
                break;
        }
        return ruleType;
    }
}
