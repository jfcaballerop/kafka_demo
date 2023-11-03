package com.mrknight.pocs.demoproducer.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mrknight.pocs.demoproducer.model.UserDAO;
import com.mrknight.pocs.demoproducer.model.UserDTO;

@Service
public class SendMsgSvc {
  @Value(value = "${kafka.topic.name}")
  private String topic;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private KafkaTemplate<String, UserDAO> userKafkaTemplate;

  // Style 1. Callback ASYNC Completable Future
  public void sendMessageAsync(String msg) {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
    future.whenComplete((result, exc) -> {
      if (exc == null) {
        System.out.println("Sent message=[" + msg + "] with offset: " + result.getRecordMetadata().offset());
      } else {
        System.out.println("Unable sent message=[" + msg + "] due to: " + exc.getMessage());
        // en caso de error hacer un tratamiento de errores ASYNC
        // Ex: insert en cola de err, o insert en BD, etc ...
      }

    });
  }

  // Style 2. Callback ASYNC Completable Future with Service SYNC CALL
  @Async("asyncExecutor")
  public CompletableFuture<SendResult<String, String>> sendKafkaExecutor(String msg) throws InterruptedException {
    System.out.println("Sending msg [" + msg + "] in topic:: " + topic);
    return kafkaTemplate.send(topic, msg);
  }

  public String sendMessageSync(String msg) throws InterruptedException {
    CompletableFuture<SendResult<String, String>> futureSendMsg = sendKafkaExecutor(msg);
    String res = "Sending ...";

    // WAIT for all CompletableFuture
    CompletableFuture.allOf(futureSendMsg).join();
    try {
      res = "Sent message=[" + msg + "] with offset: " + futureSendMsg.get().getRecordMetadata().offset();
      System.out.println(res);

    } catch (ExecutionException e) {
      res = "Unable Sent message=[" + msg + "] with err: " + e.getMessage();
      System.out.println(res);
    }
    return res;
  }

  public void sendUserMessageAsync(UserDTO user) {
    UserDAO userdao = new UserDAO(user.getNombre(), user.getApellidos(), user.getDni());
    CompletableFuture<SendResult<String, UserDAO>> future = userKafkaTemplate.send(topic, userdao);
    future.whenComplete((result, exc) -> {
      if (exc == null) {
        System.out.println("Sent message=[" + user + "] with offset: " + result.getRecordMetadata().offset());
      } else {
        System.out.println("Unable sent message=[" + user + "] due to: " + exc.getMessage());
        // en caso de error hacer un tratamiento de errores ASYNC
        // Ex: insert en cola de err, o insert en BD, etc ...
      }

    });
  }

}
