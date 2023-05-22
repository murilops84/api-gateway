package br.com.mps.service.impl;

import br.com.mps.controller.record.RequestRecord;
import br.com.mps.controller.record.ResponseRecord;
import br.com.mps.service.IKafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService implements IKafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Random rand = new Random();

    @Override
    public ResponseRecord postMessage(RequestRecord request) {
        log.info("Process message {}", request.message());
        kafkaTemplate.send("sender", request.message());
        ResponseRecord response = new ResponseRecord(String.valueOf(rand.nextInt()));
        postResponse(response.idMessage(), request);
        return response;
    }

    @Override
    public void postResponse(String responseId, RequestRecord request) {
        log.info("Response message {} with responseId {}", request.id(), responseId);
        List<Header> headers = List.of(new RecordHeader("origin", request.origin().getBytes()));
        ProducerRecord<String, String> responseRecord = new ProducerRecord<>("response", null,
                request.id().toString(), responseId, headers);
        kafkaTemplate.send(responseRecord);
    }

}
