// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.objectrepo;

public class GlobalRepo
{
    private static final String VERSION = "$Id: GlobalRepository.java 205797 2016-07-28 14:44:02Z SachinVellanki $";
    private static final String ACTIVE_FRAME_XPATH = "//div[contains(@id, 'PegaWebGadget') and @style='display: block;']/iframe|//div[contains(@id,'module') and contains(@style,'block')]/div[contains(@id,'PegaWebGadget')]/iframe[contains(@id,'PegaGadget')]|//div[contains(@data-pega-gadgetname,'PegaGadget')]/iframe[contains(@id,'PegaGadget')]";
    public static final String TABLESS_ACTIVE_FRAME_XPATH = "(//iframe[contains(@id,'PegaGadget')])[last()]";
    public static final String THROBBER__XPATH = "//*[@class='throbber']";
    public static final String DOC_STATE_XPATH = "//div[@class='document-statetracker']";
    public static final String TAB_CONTAINING_PEGA_GADGET_FRAME_XPATH = "//div[@id='workarea']//li[@role='tab'][@tabgroupname!='']";
    public static final String BODY_TAG_NAME = "body";
    
    public static String getActiveFrameXpath() {
        return "//div[contains(@id, 'PegaWebGadget') and @style='display: block;']/iframe|//div[contains(@id,'module') and contains(@style,'block')]/div[contains(@id,'PegaWebGadget')]/iframe[contains(@id,'PegaGadget')]|//div[contains(@data-pega-gadgetname,'PegaGadget')]/iframe[contains(@id,'PegaGadget')]";
    }
}
