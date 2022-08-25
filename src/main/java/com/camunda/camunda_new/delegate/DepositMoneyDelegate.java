package com.camunda.camunda_new.delegate;

import com.camunda.camunda_new.model.BankAccount;
import org.camunda.bpm.engine.HistoryService;
import org.json.JSONObject;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Random;

@Component("deposit_money")
public class DepositMoneyDelegate implements JavaDelegate {
    @Autowired
    private StreamBridge streamBridge;

    @Override
    @Transactional
  //  @Scheduled(cron = "*/2 * * * * *")
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("DepositMoneyDelegate");

        Message<Object> message = wrapperForMessage(
                generateBankAccount());
        System.out.println(message);
        streamBridge.send("saveToAccountStorage-in-0", message);
    }

    public BankAccount generateBankAccount() {
        Random rnd = new Random();
        long i = 0;
        int j = 0;
        i = rnd.nextInt(1_000_000_000)
                + (rnd.nextInt(90) + 10) * 1_000_000_000L;
        j = rnd.nextInt(5);
        return new BankAccount(i, j, OffsetDateTime.now());
    }

    private Message<Object> wrapperForMessage(Object payload) {
        return MessageBuilder.withPayload(payload).build();
    }
}

//        HistoryService historyService = delegateExecution.getProcessEngineServices().getHistoryService();
//        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().
//                processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();
//        RestTemplate restTemplate = new RestTemplate();
//
//        JSONObject processVariables = new JSONObject();
//        processVariables.put("value", "true");
//        processVariables.put("type", "Boolean");
//        JSONObject map = new JSONObject();
//        map.put("messageName", "catch_event");
//        map.put("processInstanceId", historicProcessInstance.getRootProcessInstanceId());
//        map.put("processVariables", String.valueOf(processVariables));
//        System.out.println(processVariables);
//        System.out.println(map);
//        boolean resp = restTemplate.postForEntity(
//                "http://localhost:8081/engine-rest/message",
//                map,
//                Void.class).getStatusCode().is2xxSuccessful();
//    }
//            }
