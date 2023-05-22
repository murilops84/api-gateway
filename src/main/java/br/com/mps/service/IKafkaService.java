package br.com.mps.service;

import br.com.mps.controller.record.RequestRecord;
import br.com.mps.controller.record.ResponseRecord;

public interface IKafkaService {

    ResponseRecord postMessage(RequestRecord request);

    void postResponse(String responseId, RequestRecord request);

}
