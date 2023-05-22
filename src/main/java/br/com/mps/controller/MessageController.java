package br.com.mps.controller;

import br.com.mps.controller.record.RequestRecord;
import br.com.mps.controller.record.ResponseRecord;
import br.com.mps.service.IKafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    @Autowired
    IKafkaService kafkaService;

    @PostMapping("/message")
    public ResponseEntity<ResponseRecord> postMessage(@RequestBody RequestRecord request) {
        log.info("Request received");
        return ResponseEntity.ok(kafkaService.postMessage(request));
    }
}
