package com.mrknight.pocs.demoproducer.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrknight.pocs.demoproducer.model.UserDTO;
import com.mrknight.pocs.demoproducer.services.SendMsgSvc;

@RestController
@RequestMapping("producer")
public class ProducerCtrl {

  @Autowired
  private SendMsgSvc sendSvc;

  @PostMapping("/hello-world")
  public String sendHelloWorld() {
    sendSvc.sendMessageAsync("Hello World");

    return "Sended ...";

  }

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(@RequestBody String msg) {
    String resp = "";
    try {
      resp = sendSvc.sendMessageSync(msg);
      return ResponseEntity.ok(resp);
      // TODO: Falta capturar la exception de envío y diferenciarlo.

    } catch (InterruptedException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }

  }

  @PostMapping("/user/send")
  public ResponseEntity<String> sendUserMessage(@RequestBody UserDTO user) {
    String resp = "";

    sendSvc.sendUserMessageAsync(user);
    return ResponseEntity.ok(resp);
  }

}
