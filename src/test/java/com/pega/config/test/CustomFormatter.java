// 
// Decompiled by Procyon v0.5.36
// 

package com.pega.config.test;

import cucumber.runtime.StepDefinitionMatch;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Match;
import gherkin.formatter.Reporter;

public class CustomFormatter implements Reporter
{
    public void after(final Match arg0, final Result arg1) {
    }
    
    public void before(final Match arg0, final Result arg1) {
    }
    
    public void embedding(final String arg0, final byte[] arg1) {
    }
    
    public void match(final Match match) {
        ThreadLocalStepDefinitionMatch.set((StepDefinitionMatch)match);
    }
    
    public void result(final Result arg0) {
    }
    
    public void write(final String arg0) {
    }
}
