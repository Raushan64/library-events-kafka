package com.kumar.consumer.sheduler;

import com.kumar.consumer.config.LibraryEventConsumerConfig;
import com.kumar.consumer.entity.FailureRecord;
import com.kumar.consumer.repository.FailureRecordRepository;
import com.kumar.consumer.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetrySheduler {

    @Autowired
    LibraryEventService libraryEventsService;

    @Autowired
    FailureRecordRepository failureRecordRepository;

    //once app start then every 10s invoke this method
    @Scheduled(fixedRate = 10000 )
    public void retryFailedRecords(){

        log.info("Retrying Failed Records Started!");
        var status = LibraryEventConsumerConfig.RETRY;
        failureRecordRepository.findAllByStatus(status)
                .forEach(failureRecord -> {
                    log.info("Retrying Failed Records : {}", failureRecord );
                    var consumerRecord= buildConsumerRecord(failureRecord);
                    try {
                        libraryEventsService.processLibraryEvent(consumerRecord);
                        failureRecord.setStatus(LibraryEventConsumerConfig.SUCCESS);
                    } catch (Exception e){
                        log.error("Exception in retryFailedRecords :{} ", e.getMessage());
                    }

                });
        log.info("Retrying Failed Records completed!");
    }

    private ConsumerRecord<Integer, String> buildConsumerRecord(FailureRecord failureRecord) {
        return new ConsumerRecord<>(
                failureRecord.getTopic(),
                failureRecord.getPartition(),
                failureRecord.getOffset_value(),
                failureRecord.getKey(),
                failureRecord.getErrorRecord());
    }
}
