// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.testng;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TestInfo {
    public static final String COPYRIGHT = "Copyright (c) 2014  Pegasystems Inc.";
    public static final String VERSION = "$Id: TestInfo.java 121818 2015-01-26 07:18:23Z SachinVellanki $";
    
    String tcid() default "";
    
    String dataFileName() default "";
    
    boolean recordTest() default false;
    
    String description() default "";
}
