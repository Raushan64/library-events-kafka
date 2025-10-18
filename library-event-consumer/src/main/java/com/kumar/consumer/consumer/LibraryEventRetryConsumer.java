package com.kumar.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kumar.consumer.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventRetryConsumer {

    @Autowired
    private LibraryEventService libraryEventsService;

    @KafkaListener(topics = {"${topics.retry}"}, autoStartup = "${retryListener.startup:false}", groupId = "retry-listener-group")
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord in Retry Consumer: {} ", consumerRecord );
        libraryEventsService.processLibraryEvent(consumerRecord);

    }
}
