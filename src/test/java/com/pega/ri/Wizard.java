// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.ri;

import com.pega.util.LocalizationUtil;
import com.pega.util.XPathUtil;
import com.pega.framework.elmt.Frame;

public interface Wizard extends Frame
{
    public static final String VERSION = "$Id: Wizard.java 208871 2016-09-15 13:06:30Z VenkatasrikarVadlamudi $";
    public static final String REFRESH_BTN_XPATH = XPathUtil.getMenuItemXPath("Refresh");
    public static final String ACTION_BUTTON_XPATH = String.valueOf(XPathUtil.getButtonpzButtonXpath(LocalizationUtil.getLocalizedWord("Actions"))) + "|" + XPathUtil.getButtonPzBtnMidXPath("Actions");
    public static final String HEADER_AGENTS_DIV_XPATH = "//*[text()='Agents']";
    public static final String HEADER_TITLE_SPAN_XPATH = "//span[@class='workarea_header_titles']";
    public static final String NEXTBUTTON_XPATH = "//button[not(contains(@data-click,'disabled'))][div//div[@class='pzbtn-mid' and contains(text(),'Next')]]";
    public static final String FINISHBUTTON_XPATH = "//button[not(contains(@data-click,'disabled'))][div//div[@class='pzbtn-mid' and contains(text(),'Finish')]]";
    public static final String DONE_BTN_XPATH = XPathUtil.getButtonpzButtonXpath("Done");
    public static final String CLOSE_BUTTON_XPATH = "//i[contains(@class,'pi-close')]|" + XPathUtil.getButtonPzBtnMidXPath(LocalizationUtil.getLocalizedWord("Close"));
    public static final String LAST_TAB_NAME_LABEL_XPATH = "//div[@id='workarea']//ol[@title='Currently open']/following-sibling::div//ul[@role='tablist']//li[last()-1]//span[contains(@style,'display')]";
    public static final String NEWOP_BTN_XPATH = XPathUtil.getButtonTdBtnXpath("New");
    public static final String SHORTDESC_INPUT = "//input[contains(@name,'pyLabel')]";
    public static final String OPERATORID_INPUT = "//input[contains(@name,'pyUserIdentifier')]";
    public static final String CREATEANDOPEN_BUTTON = XPathUtil.getButtonPzBtnMidXPath("Create and open");
    public static final String ACESSGRPUPRADIO_XPATH = "//*[@id='DefaultAG']";
    public static final String ACESSGRPUP_INPUT_XPATH = "//input[contains(@name,'pyLabel')]";
    public static final String SAVE_BUTTON1 = "//button[contains(text(),'Save') and not(@data-test-id='Save_and_compare_button')]";
    public static final String UPDATE_BUTTON_XPATH = XPathUtil.getButtonPzBtnMidXPath("Update");
    public static final String UPDATEORG_BTN_XPATH = XPathUtil.getButtonTdBtnXpath("Update");
    public static final String RULECHECKOUT_XPATH = "//*[contains(@name,'pyAllowRuleCheckOut') and @type='checkbox']";
    public static final String SAVE_AND_RUN_BTN_XPATH = XPathUtil.getButtonPzhcBtnXPath("Save and run");
    
    void close();
    
    void next();
    
    void finish();
    
    void switchTab(final String p0);
    
    String getTitle();
    
    void refresh();
    
    String getHeaderTitle();
    
    void done();
    
    void newOperator(final String p0, final String p1);
    
    void updateOrganization(final String p0);
    
    void ruleCheckOut();
    
    void save();
    
    void saveAndRun();
    
    Wizard findWizard(final String p0);
}
