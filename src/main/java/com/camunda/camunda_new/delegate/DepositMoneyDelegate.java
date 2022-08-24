package com.camunda.camunda_new.delegate;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("deposit_money")
public class DepositMoneyDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("DepositMoneyDelegate");

        HistoryService historyService = delegateExecution.getProcessEngineServices().getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().
                processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
        RestTemplate restTemplate = new RestTemplate();

        Map processVariables = new HashMap<String, String>();
        processVariables.put("value", "true");
        processVariables.put("type", "Boolean");
        Map map = new HashMap<String, String>();
        map.put("messageName", "catch_event");
        map.put("processInstanceId", historicProcessInstance);
        map.put("processVariables", processVariables);
        ResponseEntity<String> resp = restTemplate.postForEntity(
                "http://localhost:8081/engine-rest/message",
                map,
                String.class);
        System.out.println(resp);
    }
}
