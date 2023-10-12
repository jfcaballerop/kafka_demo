package com.mrknight.pocs.demoproducer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMsgSvc {
  @Value(value = "${message.topic.name}")
  private String topic;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String msg) {
    kafkaTemplate.send(topic, msg);
  }

}
