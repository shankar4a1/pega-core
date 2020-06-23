// 
// Decompiled by Procyon v0.5.36
// 

package com.pega;

import com.pega.framework.elmt.Frame;
import com.pega.page.Portal;
import com.pega.util.XPathUtil;

public interface Browser
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: Browser.java 201415 2016-06-30 11:34:15Z AnilBattinapati $";
    public static final String BROWSER_IMPL_LOGIN_ID = "txtUserID";
    public static final String BROWSER_IMPL_PASSWORD_ID = "txtPassword";
    public static final String BROWSER_IMPL_LOGIN_BUTTON_NAME = "pyActivity=Code-Security.Login";
    public static final String BROWSER_IMPL_OPERATOR_MENU_XPATH = "//i[@data-test-id='px-opr-image-ctrl' and contains(@class,'icons avatar name')]|//a[@data-test-id='201504060444150304189882']|//a[@data-test-id='2014110318003603997207']";
    public static final String BROWSER_IMPL_LOG_OFF_XPATH = "//span[text()='Log off']|//td[text()='Log off']|//span[text()='Log Off']|" + XPathUtil.getMenuItemXPath("Log Off");
    public static final String LOGIN_ERROR_MESSAGE_XPATH = "//div[@id='error' and contains(text(),'The information you entered was not recognized')]";
    public static final String BROWSER_TENANT_IDENTIFIER_XPATH = "//footer/p/span[1]";
    public static final String BROWSER_PRPC_VERSION_XPATH = "//span[contains(text(),'Pega')]";
    public static final String BROWSER_PRPC_FEATURE_XPATH = "//a[@title='Select a default feature']";
    public static final String CUR_PASSWORD_FOR_PASSWORD_CHANGE_XPATH = "//input[contains(@name,'pyPwdOldText')]";
    public static final String NEW_PASSWORD_FOR_PASSWORD_CHANGE_XPATH = "//input[contains(@name,'pyPwdNew')]";
    public static final String CONFIRM_PASSWORD_FOR_PASSWORD_CHANGE_XPATH = "//input[contains(@name,'pyPwdVerifyText')]";
    public static final String CHANGE_PASSWORD_BUTTON_XPATH = XPathUtil.getButtonStrongPzhcPzBtnXPath("Change password");
    public static final String OPENED_TABS_ICON_CSS = "a[aria-label='Currently open']";
    public static final String CLOSE_ALL_XPATH = XPathUtil.getTdMenuItemXPath("Close All");
    
    void setLoggedInUser(final String p0);
    
    String getLoggedInUser();
    
    void setNonClonedUser(final String p0);
    
    String getUnClonedLoggedInUser();
    
    @Deprecated
    void login();
    
    void login(final String p0, final String p1);
    
    void loginAndChangePassword(final String p0, final String p1, final String p2);
    
    void logout();
    
    void open();
    
    void open(final String p0);
    
    void close();
    
    Object executeJavaScript(final String p0);
    
     <T extends Portal> T getPortal(final Class<T> p0);
    
     <T extends Portal> T getPortal(final Class<T> p0, final Frame p1);
    
    void closeCurrentPopUpAndGoBackToDefaultWindow();
    
    String getWindow(final int p0);
    
    boolean switchToWindow(final int p0);
    
    boolean switchToWindow(final int p0, final boolean p1);
    
    boolean switchToWindow(final String p0);
    
    String getTenantIdentifier();
    
    String getPRPCVersion();
    
    String getDownloadDir();
    
    boolean isIE9();
    
    boolean isIE10();
    
    boolean isIE11();
    
    boolean isFirefox();
    
    void refresh();
}
