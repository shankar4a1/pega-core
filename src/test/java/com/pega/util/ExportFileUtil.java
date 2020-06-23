// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.util;

import java.util.Iterator;
import java.util.Set;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import java.util.regex.Matcher;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import java.io.FileOutputStream;
import java.io.File;
import java.util.regex.Pattern;
import org.apache.http.util.EntityUtils;
import com.pega.exceptions.PegaWebDriverException;
import org.testng.Reporter;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import com.pega.TestEnvironment;

public class ExportFileUtil
{
    private static void exportForIE(String url, final TestEnvironment testEnv, final String filePath) throws ClientProtocolException, IOException {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        FileOutputStream fileOutputStream = null;
        DefaultHttpClient client = null;
        try {
            client = new DefaultHttpClient();
            client.setCookieStore((CookieStore)cookieStore);
            String baseUrl = testEnv.getConfiguration().getSUTConfig().getURL().trim();
            final HttpGet get = new HttpGet(baseUrl);
            HttpResponse response = (HttpResponse)client.execute((HttpUriRequest)get);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                Reporter.log("Download cannot be done as given URL is down: " + baseUrl, true);
                throw new PegaWebDriverException("Download cannot be done as given URL is down: " + baseUrl);
            }
            InputStream inputStream = entity.getContent();
            final String content = EntityUtils.toString(entity);
            Reporter.log("Downloded  bytes. " + entity.getContentType(), true);
            final Pattern pattern = Pattern.compile("action=\"(.+?)\"");
            final Matcher m = pattern.matcher(content);
            m.find();
            final String action = m.group(1);
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf(47));
            }
            String completeUrl = "";
            if (baseUrl.endsWith("/prweb")) {
                completeUrl = baseUrl.replace("/prweb", action);
            }
            else {
                completeUrl = baseUrl.replace("/prweb/PRServlet", action);
            }
            url = url.replace(url.substring(0, url.lastIndexOf(42)), completeUrl.substring(0, completeUrl.lastIndexOf(42)));
            final HttpGet httpGet = new HttpGet(url);
            Reporter.log("Downloding file form: " + url, true);
            response = (HttpResponse)client.execute((HttpUriRequest)httpGet);
            entity = response.getEntity();
            if (entity == null) {
                Reporter.log("Download failed!", true);
                throw new PegaWebDriverException("Download failed!");
            }
            final File outputFile = new File(filePath);
            inputStream = entity.getContent();
            fileOutputStream = new FileOutputStream(outputFile);
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            Reporter.log("Downloded " + outputFile.length() + " bytes. " + entity.getContentType(), true);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally {
            fileOutputStream.close();
            client.getConnectionManager().shutdown();
            client.close();
        }
        fileOutputStream.close();
        client.getConnectionManager().shutdown();
        client.close();
    }
    
    public static void exportFile(final String url, final String filePath, final TestEnvironment testEnv) throws IOException {
        if (testEnv.getConfiguration().getBrowserConfig().getBrowserName().toLowerCase().trim().startsWith("i")) {
            try {
                exportForIE(url, testEnv, filePath);
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        DefaultHttpClient httpclient = null;
        FileOutputStream fileOutputStream = null;
        try {
            httpclient = new DefaultHttpClient();
            final CookieStore cookieStore = seleniumCookiesToCookieStore(testEnv);
            httpclient.setCookieStore(cookieStore);
            httpclient = new DefaultHttpClient();
            final HttpGet httpGet = new HttpGet(url);
            Reporter.log("Downloding file from: " + url, true);
            final HttpResponse response = (HttpResponse)httpclient.execute((HttpUriRequest)httpGet);
            Thread.sleep(20000L);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                final String[] strArray = filePath.split("\\.");
                final String fileExtn = (strArray.length == 2) ? strArray[1] : "";
                Reporter.log("ENTITY CONTENT TYPE:" + entity.getContentType(), true);
                if (fileExtn.equalsIgnoreCase("jar") && !entity.getContentType().toString().contains("application/java-archive")) {
                    throw new RuntimeException("URL content type mismatched. Expected is jar. Acrual is " + entity.getContentType());
                }
                final File outputFile = new File(filePath);
                final InputStream inputStream = entity.getContent();
                fileOutputStream = new FileOutputStream(outputFile);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, read);
                }
                Reporter.log("Downloded " + outputFile.length() + " bytes. " + entity.getContentType(), true);
            }
            else {
                Reporter.log("Download failed!", true);
            }
        }
        catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
        finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            httpclient.getConnectionManager().shutdown();
            httpclient.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        httpclient.getConnectionManager().shutdown();
        httpclient.close();
    }
    
    private static CookieStore seleniumCookiesToCookieStore(final TestEnvironment testEnv) {
        final Set<Cookie> seleniumCookies = (Set<Cookie>)testEnv.getPegaDriver().manage().getCookies();
        final CookieStore cookieStore = (CookieStore)new BasicCookieStore();
        for (final Cookie seleniumCookie : seleniumCookies) {
            final BasicClientCookie basicClientCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie((org.apache.http.cookie.Cookie)basicClientCookie);
        }
        return cookieStore;
    }
}
