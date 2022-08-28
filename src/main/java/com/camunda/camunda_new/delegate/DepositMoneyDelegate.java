package com.camunda.camunda_new.delegate;

import com.camunda.camunda_new.model.BankAccount;
import com.camunda.camunda_new.model.dto.BankAccountProcessDto;
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
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Random;

@Component("deposit_money")
public class DepositMoneyDelegate implements JavaDelegate {
    @Autowired
    private StreamBridge streamBridge;

    @Override
    @Transactional
    // @Scheduled(cron = "*/2 * * * * *")
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("DepositMoneyDelegate");
        BankAccountProcessDto bankAccountProcessDto = generateBankAccount();
        bankAccountProcessDto.setProcessInstanceId(delegateExecution.getProcessInstanceId());
        Message<Object> message = wrapperForMessage(
                bankAccountProcessDto);
        Thread.sleep(5000);
        streamBridge.send("takeFromBankAccountQueue-in-0", message);
        System.out.println("message is sent:"+message);
//        Thread.sleep(5000);
//        BankAccountProcessDto bankAccountProcessDto1 = generateBankAccount();
//        bankAccountProcessDto1.setProcessInstanceId(delegateExecution.getProcessInstanceId());
//        Message<Object> message1 = wrapperForMessage(
//                bankAccountProcessDto1);
//        streamBridge.send("takeFromBankAccountQueue-in-0", message1);
//        System.out.println("message is sent:"+message1);
//        Thread.sleep(5000);
//        BankAccountProcessDto bankAccountProcessDto2 = generateBankAccount();
//        bankAccountProcessDto2.setProcessInstanceId(delegateExecution.getProcessInstanceId());
//        Message<Object> message2 = wrapperForMessage(
//                bankAccountProcessDto2);
//
//      streamBridge.send("takeFromBankAccountQueue-in-0", message2);
//       System.out.println("message is sent:"+message2);
    }

    public BankAccountProcessDto generateBankAccount() {
        Random rnd = new Random();
        long i = 0;
        int j = 0;
        i = rnd.nextInt(1_000_000_000)
                + (rnd.nextInt(90) + 10) * 1_000_000_000L;
        j = rnd.nextInt(5);
        return new BankAccountProcessDto(i, j, OffsetDateTime.now(), "", false);
    }

    private Message<Object> wrapperForMessage(Object payload) {
        return MessageBuilder.withPayload(payload).build();
    }
}

