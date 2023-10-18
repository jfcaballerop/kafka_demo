package com.mrknight.pocs.demoproducer.config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class ContainerFactoryConfigurer {

  ContainerFactoryConfigurer(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
    factory.getContainerProperties().setMissingTopicsFatal(false);
  }

}
