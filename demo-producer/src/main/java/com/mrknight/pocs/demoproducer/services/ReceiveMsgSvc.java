package com.mrknight.pocs.demoproducer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMsgSvc {

  @Value(value = "${kafka.topic.name}")
  private String topic;

  @Value(value = "${spring.kafka.consumer.group-id}")
  private String groupId;

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
  public void listener(@Payload String message) {
    System.out
        .println("Received Message from Topic [" + topic + "] in group: " + groupId + " msg: [ " + message + " ]");
  }

}
