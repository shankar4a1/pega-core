// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.framework.http;

import org.apache.http.client.methods.HttpUriRequest;

public interface PegaHttpUriRequest
{
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: PegaHttpUriRequest.java 142258 2015-06-30 11:44:42Z SachinVellanki $";
    
    HttpUriRequest getHttpUriRequest();
}
