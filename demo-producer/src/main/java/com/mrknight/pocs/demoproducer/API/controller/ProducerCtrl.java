package com.mrknight.pocs.demoproducer.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrknight.pocs.demoproducer.services.SendMsgSvc;

@RestController
@RequestMapping("producer")
public class ProducerCtrl {

  @Autowired
  private SendMsgSvc sendSvc;

  @PostMapping("/hello-world")
  public String sendHelloWorld() {
    sendSvc.sendMessage("Hello World");

    return "Sended ...";

  }

}
