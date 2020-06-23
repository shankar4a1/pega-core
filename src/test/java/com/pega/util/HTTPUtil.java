// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.pega.TestEnvironment;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import com.pega.exceptions.PegaWebDriverException;
import javax.net.ssl.KeyManager;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManager;
import java.net.MalformedURLException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.testng.Reporter;
import com.sun.jersey.api.client.Client;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;

public class HTTPUtil
{
    String COPYRIGHT;
    private static final String VERSION = "$Id: HTTPUtil.java 209087 2016-09-23 09:15:55Z SachinVellanki $";
    private static String URL;
    private static String cookie;
    
    static {
        HTTPUtil.URL = null;
        HTTPUtil.cookie = null;
    }
    
    public HTTPUtil(final String url) {
        this.COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
        HTTPUtil.URL = url;
    }
    
    public static String getResponseText(final String url, final String cookie) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.79 Safari/535.11");
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestMethod("GET");
        final InputStream is = connection.getInputStream();
        return getStringFromInputStream(is);
    }
    
    public static String getStringFromInputStream(final InputStream is) throws IOException {
        BufferedReader br = null;
        final StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return sb.toString();
        }
        finally {
            if (br != null) {
                br.close();
            }
        }
        if (br != null) {
            br.close();
        }
        return sb.toString();
    }
    
    public String getPostRequestResponse(final String url, final String[][] params) throws MalformedURLException, IOException {
        handleSSLCertificates();
        final Client client = Client.create();
        final WebResource webResource = client.resource(url);
        Reporter.log("URL" + url, true);
        final MultivaluedMapImpl form = new MultivaluedMapImpl();
        for (int rows = params.length, i = 0; i < rows; ++i) {
            Reporter.log(params[i][0] + "--" + params[i][1], true);
            form.add(params[i][0], params[i][1]);
        }
        final ClientResponse response = (ClientResponse)webResource.type("application/x-www-form-urlencoded").post((Class)ClientResponse.class, (Object)form);
        final String output = (String)response.getEntity((Class)String.class);
        Reporter.log("Output from Server .... \n" + output);
        return output;
    }
    
    public static void handleSSLCertificates() {
        Reporter.log("All SSL Certificates will be accepted...", true);
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                }
            } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
        }
        catch (Exception e) {
            throw new PegaWebDriverException("Unable to install all trusting trust manager for accepting all SSL certificates...");
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        final HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
    
    public static void appendPropertyFileWithUTF8Format(final String oldPropFilePath, final String newPropFilePath, final String mergedPropFilePath, final String comment) throws IOException {
        final File oldPropFile = new File(oldPropFilePath);
        final File newPropFile = new File(newPropFilePath);
        final File mergedPropFile = new File(mergedPropFilePath);
        InputStream is1 = null;
        FileInputStream is2 = null;
        FileOutputStream os = null;
        Label_0171: {
            Label_0127: {
                Label_0083: {
                    try {
                        is1 = new FileInputStream(oldPropFile);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                        break Label_0083;
                    }
                    finally {
                        is1.close();
                    }
                    is1.close();
                    try {
                        is2 = new FileInputStream(newPropFile);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                        break Label_0127;
                    }
                    finally {
                        is2.close();
                    }
                }
                is2.close();
                try {
                    os = new FileOutputStream(mergedPropFile);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    break Label_0171;
                }
                finally {
                    os.close();
                }
            }
            os.close();
        }
        final Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(is1, "UTF-8"));
            prop.load(new InputStreamReader(is2, "UTF-8"));
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            prop.store(new OutputStreamWriter(os, "UTF-8"), comment);
        }
        catch (IOException e2) {
            e2.printStackTrace();
            try {
                is2.close();
                is1.close();
                os.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
            return;
        }
        finally {
            try {
                is2.close();
                is1.close();
                os.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        try {
            is2.close();
            is1.close();
            os.close();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
    
    public static int getURLAvailabilityResponseCode(final String url) throws MalformedURLException, IOException {
        Reporter.log("All SSL Certificates will be accepted...", true);
        final TrustManager[] trustAllCerts = { new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                }
                
                @Override
                public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                }
            } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
        }
        catch (Exception e) {
            throw new PegaWebDriverException("Unable to install all trusting trust manager for accepting all SSL certificates...");
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        final HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        Reporter.log("Checking availability of URL: " + url, true);
        final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setConnectTimeout(180000);
        connection.setReadTimeout(180000);
        connection.setRequestMethod("HEAD");
        final int responseCode = connection.getResponseCode();
        return responseCode;
    }
    
    public static void mergeTestFlowBranch(final TestEnvironment testEnv) {
        String prpcUrl = testEnv.getConfiguration().getSUTConfig().getURL();
        String filePath = "data" + File.separator + "users.properties";
        if (FileUtil.isFileExists("Data" + File.separator + "users.properties")) {
            filePath = "Data" + File.separator + "users.properties";
        }
        final Properties props = FileUtil.loadPropertiesFile(filePath);
        if (prpcUrl.contains("prweb")) {
            final String str = prpcUrl.split("prweb")[0];
            prpcUrl = String.valueOf(str) + "prweb/";
        }
        final String restUrl = String.valueOf(prpcUrl) + "PRRestService/BuildSmoke/Application/Merge?UserIdentifier=NewBuildSmokeDeveloper&Password=cGVnYQ%3D%3D&branchlist=testBranch";
        Reporter.log("Url for branch to merge is " + restUrl, true);
        final Response res = (Response)RestAssured.given().auth().preemptive().basic(props.getProperty("BUILD_SMOKE_USER_ID"), props.getProperty("BUILD_SMOKE_PASSWORD")).header("cache-control", (Object)"no-cache", new Object[0]).header("content-type", (Object)"application/x-www-form-urlencoded", new Object[0]).get(restUrl, new Object[0]);
        Reporter.log(String.valueOf(res.statusCode()), true);
        Reporter.log(res.body().asString(), true);
        Reporter.log("Successfully merged testbranchflow.", true);
    }
    
    public static void mergeTestFlowBranch(final String url, final String username, final String password) {
        String prpcUrl = url;
        if (prpcUrl.contains("prweb")) {
            final String str = prpcUrl.split("prweb")[0];
            prpcUrl = String.valueOf(str) + "prweb/";
        }
        final String restUrl = String.valueOf(prpcUrl) + "PRRestService/BuildSmoke/Application/Merge?UserIdentifier=NewBuildSmokeDeveloper&Password=cGVnYQ%3D%3D&branchlist=testBranch";
        Reporter.log("Url for branch to merge is " + restUrl, true);
        final Response res = (Response)RestAssured.given().auth().preemptive().basic(username, password).header("cache-control", (Object)"no-cache", new Object[0]).header("content-type", (Object)"application/x-www-form-urlencoded", new Object[0]).get(restUrl, new Object[0]);
        Reporter.log(String.valueOf(res.statusCode()), true);
        Reporter.log(res.body().asString(), true);
        Reporter.log("Successfully merged testbranchflow.", true);
    }
    
    public enum TransaltionLang
    {
        ENGLISH("ENGLISH", 0, "en"), 
        HINDI("HINDI", 1, "hi"), 
        TELUGU("TELUGU", 2, "te"), 
        CHINESE_SIMPLIFIED("CHINESE_SIMPLIFIED", 3, "zh-CN"), 
        CHINESE_TRADITIONAL("CHINESE_TRADITIONAL", 4, "zh-TW"), 
        JAPANESE("JAPANESE", 5, "ja"), 
        FRENCH("FRENCH", 6, "fr"), 
        SPANISH("SPANISH", 7, "es"), 
        GERMAN("GERMAN", 8, "de"), 
        POLISH("POLISH", 9, "pl");
        
        private String lang;
        
        private TransaltionLang(final String name, final int ordinal, final String lang) {
            this.lang = lang;
        }
        
        public String getLang() {
            return this.lang;
        }
    }
}
