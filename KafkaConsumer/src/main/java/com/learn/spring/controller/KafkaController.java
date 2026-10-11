package com.learn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.spring.service.KafkaService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;
    @GetMapping("/test")
    public ResponseEntity<String> testKafka() {
        return new ResponseEntity<>("Kafka endpoint is working!", HttpStatus.OK);
    }

    @GetMapping("/consume")
    public ResponseEntity<String> consumeKafka() {
        kafkaService.consumeMessage();
        return new ResponseEntity<>("Kafka message consumed successfully!", HttpStatus.OK);
    }
}
