package com.mrknight.pocs.demoproducer.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class SendMsgSvc {
  @Value(value = "${kafka.topic.name}")
  private String topic;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  // Style 1. Callback Completable Future
  public void sendMessage(String msg) {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
    future.whenComplete((result, exc) -> {
      if (exc == null) {
        System.out.println("Sent message=[" + msg + "] with offset: " + result.getRecordMetadata().offset());
      } else {
        System.out.println("Unable sent message=[" + msg + "] due to: " + exc.getMessage());
      }

    });
  }

}
