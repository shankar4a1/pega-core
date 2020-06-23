// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.test;

import cucumber.runtime.StepDefinitionMatch;

public class ThreadLocalStepDefinitionMatch
{
    private static final ThreadLocal<StepDefinitionMatch> threadStepDefMatch;
    
    static {
        threadStepDefMatch = new InheritableThreadLocal<StepDefinitionMatch>();
    }
    
    private ThreadLocalStepDefinitionMatch() {
    }
    
    public static StepDefinitionMatch get() {
        return ThreadLocalStepDefinitionMatch.threadStepDefMatch.get();
    }
    
    public static void set(final StepDefinitionMatch match) {
        ThreadLocalStepDefinitionMatch.threadStepDefMatch.set(match);
    }
    
    public static void remove() {
        ThreadLocalStepDefinitionMatch.threadStepDefMatch.remove();
    }
}
