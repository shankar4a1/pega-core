// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.util;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;
import java.net.HttpURLConnection;

public class FetchPRPCLogs
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: FetchPRPCLogs.java 174698 2016-02-08 08:24:26Z SachinVellanki $";
    private final String USER_AGENT = "Mozilla/5.0";
    private HttpURLConnection con;
    String url;
    String setCookie;
    List<String> cookies;
    int currentResponseCode;
    StringBuffer currentResponseMessage;
    private boolean repeat;
    
    public FetchPRPCLogs() {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
        this.url = "http://vengwindb192:9892/prweb/PRServlet";
        this.currentResponseMessage = null;
    }
    
    public static void main(final String[] args) throws Exception {
        final FetchPRPCLogs http = new FetchPRPCLogs();
        http.generateLogs(http.url, "LogOutput.log");
    }
    
    public void generateLogs(final String url, final String filePath) throws Exception {
        String newUrl = this.openPRPCUrl(url);
        newUrl = this.login(newUrl, "bwatcher", "pega");
        final String logOutput = this.getLogs(newUrl);
        final File file = new File(filePath);
        final FileWriter fw = new FileWriter(file.getAbsoluteFile());
        final BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(logOutput);
        }
        catch (Exception e) {
            System.out.println("Exception is  " + e);
            return;
        }
        finally {
            bw.close();
        }
        bw.close();
    }
    
    private String getRegExpPattern(final String regeExpPattern, final String actualString) {
        final Pattern p = Pattern.compile(regeExpPattern);
        final Matcher m = p.matcher(actualString);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    private StringBuffer getResponseString() throws IOException {
        StringBuffer response = null;
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(this.con.getInputStream()));
            response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }
    
    private HttpURLConnection postRequest(final String url, final String cookie, final String postParameters) throws IOException {
        try {
            PrintWriter pw1 = null;
            (this.con = (HttpURLConnection)new URL(url).openConnection()).setDoOutput(true);
            this.con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.79 Safari/535.11");
            this.con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.con.setRequestProperty("Content-Length", String.valueOf(postParameters.length()));
            for (final String cookie2 : this.cookies) {
                this.con.addRequestProperty("Cookie", cookie2.split(";", 1)[0]);
            }
            this.con.setRequestMethod("POST");
            this.con.setInstanceFollowRedirects(false);
            (pw1 = new PrintWriter(new OutputStreamWriter(this.con.getOutputStream()), true)).print(postParameters);
            pw1.flush();
            pw1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.currentResponseCode = this.con.getResponseCode();
        this.currentResponseMessage = this.getResponseString();
        return this.con;
    }
    
    private HttpURLConnection getRequest(final String url, final String cookie) throws IOException {
        try {
            (this.con = (HttpURLConnection)new URL(url).openConnection()).setDoOutput(false);
            this.con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.79 Safari/535.11");
            if (this.cookies != null) {
                for (final String cookie2 : this.cookies) {
                    this.con.addRequestProperty("Cookie", cookie2.split(";", 1)[0]);
                }
            }
            else {
                this.con.setRequestProperty("Cookie", "");
            }
            this.con.setRequestMethod("GET");
            this.con.setInstanceFollowRedirects(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.currentResponseCode = this.con.getResponseCode();
        this.currentResponseMessage = this.getResponseString();
        return this.con;
    }
    
    private String getHeaderContent(final HttpURLConnection connection, final String name) {
        String headerContent = null;
        int i = 0;
        while (true) {
            final String headerName = connection.getHeaderFieldKey(i);
            final String headerValue = connection.getHeaderField(i);
            if (name.equals(headerName)) {
                headerContent = ((headerContent == null) ? (String.valueOf(headerValue) + ";") : (String.valueOf(headerContent) + headerValue + ";"));
            }
            if (headerName == null && headerValue == null) {
                break;
            }
            ++i;
        }
        return headerContent;
    }
    
    private String openPRPCUrl(final String url) throws IOException {
        this.getRequest(url, "");
        final String response = this.currentResponseMessage.toString();
        final String newUrl = String.valueOf(url) + "/" + this.getRegExpPattern("/prweb/PRServlet/(.+?)!STANDARD", response) + "!STANDARD";
        this.setCookie = this.getHeaderContent(this.con, "Set-Cookie");
        this.cookies = this.con.getHeaderFields().get("Set-Cookie");
        return newUrl;
    }
    
    private String login(String newUrl, final String userName, final String password) throws UnsupportedEncodingException, IOException {
        final String urlParameters = "pzAuth=guest&UserIdentifier=" + URLEncoder.encode(userName, "UTF-8") + "&Password=" + URLEncoder.encode(password, "UTF-8") + "&pyActivity%3DCode-Security.Login=";
        this.postRequest(newUrl, this.setCookie, urlParameters);
        this.setCookie = this.getHeaderContent(this.con, "Set-Cookie");
        this.cookies = this.con.getHeaderFields().get("Set-Cookie");
        this.con = this.getRequest(String.valueOf(newUrl) + "?pzPostData=-569061234", this.setCookie);
        final String loginResponse = this.currentResponseMessage.toString();
        newUrl = String.valueOf(this.url) + "/" + this.getRegExpPattern("/prweb/PRServlet/(.+?)!STANDARD", loginResponse) + "!STANDARD";
        this.con = this.getRequest(String.valueOf(newUrl) + "?pzPostData=-569061234", this.setCookie);
        this.setCookie = this.getHeaderContent(this.con, "Set-Cookie");
        this.cookies = this.con.getHeaderFields().get("Set-Cookie");
        final String homePage = this.currentResponseMessage.toString();
        final String harnessID = this.getRegExpPattern("id='pzHarnessID' value='(.+?)'", homePage);
        final String queryString = "?pyActivity=Data-Portal.pyPegaTaskSetParams&pageName=Work&pzHarnessID=" + harnessID;
        this.getRequest(String.valueOf(newUrl) + queryString, this.setCookie);
        return newUrl.replaceAll("!STANDARD", "");
    }
    
    private String getLogs(final String url) throws IOException {
        StringBuffer logOutput = new StringBuffer();
        String queryString = "!STANDARD?pyActivity=pzGetMenu&pzTransactionId=&pzFromFrame=&pzPrimaryPageName=pyDisplayHarness.pyLandingMenu&navName=pzMainMenu&pzKeepPageMessages=true&removePage=false&ContextPage=pyDisplayHarness.pyLandingMenu&showmenucall=true&navPageName=pyNavigation1451456703957&pzHarnessID=HID22AE956CC60B3392726B86FB388B2C01&inStandardsMode=true&AJAXTrackID=1&HeaderButtonSectionName=";
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add((NameValuePair)new BasicNameValuePair("$PDeclare_pyDisplay$ppyDCDisplayState$ppyActiveDocumentType", "HOME"));
        paramList.add((NameValuePair)new BasicNameValuePair("$OCompositeAPIInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$ODesktopWrapperInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OEvalDOMScripts_Include", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OHarnessInlineScripts", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OLaunchFlowScriptInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OListViewIncludes", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OMenuBar", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OSpecCheckerScript", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OWorkformStyles", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OdocumentInfo_bundle", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$Ogridincludes", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OmenubarInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpxWorkAreaTabsContainer", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpyWorkFormStandard", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzControlMenuScripts", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzDynamicContainerScript", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzHarnessStaticScripts", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzMobileAppNotification", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzPegaCompositeGadget_PortalInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OpzTextInput", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OxmlDocumentInclude", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$OselectActivityTab", ""));
        paramList.add((NameValuePair)new BasicNameValuePair("$PpxRequestor$ppyLatitude", "17.4378202"));
        paramList.add((NameValuePair)new BasicNameValuePair("$PpxRequestor$ppyLongitude", "78.3833574"));
        String urlParameters = "";
        urlParameters = String.valueOf(urlParameters) + "$PDeclare_pyDisplay$ppyDCDisplayState$ppyActiveDocumentType=" + URLEncoder.encode("HOME", "UTF-8");
        urlParameters = String.valueOf(urlParameters) + "&$OCompositeAPIInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$ODesktopWrapperInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$OEvalDOMScripts_Include=";
        urlParameters = String.valueOf(urlParameters) + "&$OHarnessInlineScripts=";
        urlParameters = String.valueOf(urlParameters) + "&$OLaunchFlowScriptInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$OListViewIncludes=";
        urlParameters = String.valueOf(urlParameters) + "&$OMenuBar=";
        urlParameters = String.valueOf(urlParameters) + "&$OSpecCheckerScript=";
        urlParameters = String.valueOf(urlParameters) + "&$OWorkformStyles=";
        urlParameters = String.valueOf(urlParameters) + "&$OdocumentInfo_bundle=";
        urlParameters = String.valueOf(urlParameters) + "&$Ogridincludes=";
        urlParameters = String.valueOf(urlParameters) + "&$OmenubarInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$OpxWorkAreaTabsContainer=";
        urlParameters = String.valueOf(urlParameters) + "&$OpyWorkFormStandard=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzControlMenuScripts=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzDynamicContainerScript=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzHarnessStaticScripts=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzMobileAppNotification=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzPegaCompositeGadget_PortalInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$OpzTextInput=";
        urlParameters = String.valueOf(urlParameters) + "&$OxmlDocumentInclude=";
        urlParameters = String.valueOf(urlParameters) + "&$OselectActivityTab=";
        urlParameters = String.valueOf(urlParameters) + "&$PpxRequestor$ppyLatitude=" + URLEncoder.encode("17.4378202", "UTF-8");
        urlParameters = String.valueOf(urlParameters) + "&$PpxRequestor$ppyLongitude=" + URLEncoder.encode("78.3833574", "UTF-8");
        this.postRequest(String.valueOf(url) + queryString, this.setCookie, urlParameters);
        paramList = null;
        queryString = "!TABTHREAD0?pyActivity=%40baseclass.doUIAction&landingAction=openlanding&harnessName=pzLPSystemManagement&className=Pega-Landing-System-SMA&readOnly=false&action=Display&levelC=Logs&label=System%3A%20Operations&contentID=a1c133ed-1424-4733-8f69-dc0420742692&dynamicContainerID=de12ecd7-b2b3-4849-bff1-ee2797014513&tabIndex=2&portalThreadName=STANDARD&portalName=Developer&pzHarnessID=HID22AE956CC60B3392726B86FB388B2C01";
        this.getRequest(String.valueOf(url) + queryString, this.setCookie);
        final String harnessID1 = this.getRegExpPattern("id='pzHarnessID' value='(.*?)'", this.currentResponseMessage.toString());
        queryString = "!TABTHREAD0?pyActivity=showStream&pyTargetStream=LogFileDownload&pyTargetFrame=&pyBasePage=&pyApplyTo=&target=popup&pzHarnessID=" + harnessID1;
        this.getRequest(String.valueOf(url) + queryString, this.setCookie);
        queryString = "!TABTHREAD0?pyStream=LogViewer&initDisplay=true&logType=PEGA";
        this.getRequest(String.valueOf(url) + queryString, this.setCookie);
        queryString = "!TABTHREAD0?pyStream=LogViewer";
        int selectedPage = 0;
        int currentChapter = 0;
        final int pagesPerChapter = 10;
        int prevLinesPerPage = 25;
        final int linesPerPage = 2500;
        do {
            urlParameters = "";
            urlParameters = String.valueOf(urlParameters) + "linesPerPage=" + URLEncoder.encode(new StringBuilder(String.valueOf(linesPerPage)).toString(), "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&pagesPerChapter=" + URLEncoder.encode(new StringBuilder(String.valueOf(pagesPerChapter)).toString(), "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&filterString=";
            urlParameters = String.valueOf(urlParameters) + "&selectedPage=" + URLEncoder.encode(new StringBuilder(String.valueOf(selectedPage++)).toString(), "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&currentChapter=" + URLEncoder.encode(new StringBuilder(String.valueOf(currentChapter)).toString(), "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&prevLinesPerPage=" + URLEncoder.encode(new StringBuilder(String.valueOf(prevLinesPerPage)).toString(), "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&prevPagesPerChapter=" + URLEncoder.encode("10", "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&logType=" + URLEncoder.encode("PEGA", "UTF-8");
            urlParameters = String.valueOf(urlParameters) + "&initDisplay=" + URLEncoder.encode("false", "UTF-8");
            this.postRequest(String.valueOf(url) + queryString, this.setCookie, urlParameters);
            String currentLog = this.currentResponseMessage.toString();
            if (currentLog.contains("next&gt;</a>")) {
                this.repeat = true;
                prevLinesPerPage = linesPerPage;
            }
            else {
                this.repeat = false;
                currentLog = currentLog.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "");
            }
            if (selectedPage % pagesPerChapter == 0) {
                ++currentChapter;
                selectedPage = 0;
            }
            logOutput = logOutput.append(this.getRegExpPattern("<pre>(.+)</pre>", currentLog));
        } while (this.repeat);
        return logOutput.toString().replaceAll("&nbsp;", "\n");
    }
}
