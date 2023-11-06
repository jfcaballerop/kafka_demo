package com.mrknight.pocs.demoproducer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.mrknight.pocs.demoproducer.model.UserDAO;

@Component
public class ReceiveMsgSvc {

  @Value(value = "${kafka.topic.name}")
  private String topic;

  @Value(value = "${spring.kafka.consumer.group-id}")
  private String groupId;

  @Value(value = "${spring.kafka.consumer.group-id2}")
  private String groupId2;

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
  public void listener(@Payload String message) {
    System.out
        .println("Received Message from Topic [" + topic + "] in group: " + groupId +
            " msg: [ " + message + " ]");
  }

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id2}", containerFactory = "filterKafkaListenerContainerFactory")
  public void listenWithFilter(String message) {
    System.out.println("Received Message in filtered listener: " + message);
  }

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id3}", containerFactory = "kafkaUserListenerContainerFactory")
  public void listenerCustom(UserDAO message) {
    System.out.println("Received USER Message in listener: " + message);
  }

}
