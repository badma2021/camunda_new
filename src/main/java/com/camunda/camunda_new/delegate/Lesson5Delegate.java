package com.camunda.camunda_new.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("ДелегатИзУрока5")
public class Lesson5Delegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delEx) throws Exception {
        
        final String procVar1 = (String) delEx.getVariable("параметр1");
        System.out.println(String.format("Lesson5Delegate.execute, procVar1='%s'", procVar1));
    }
}
