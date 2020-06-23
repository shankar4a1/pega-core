// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework.http;

import java.io.File;
import org.apache.http.HttpResponse;

public interface PegaHttpResponse
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: PegaHttpResponse.java 142258 2015-06-30 11:44:42Z SachinVellanki $";
    
    HttpResponse getHttpResponse();
    
    String getResponseAsString();
    
    int getResponseCode();
    
    void consumeResponseQuietly();
    
    void writeResponseToFile(final File p0, final String p1);
}
