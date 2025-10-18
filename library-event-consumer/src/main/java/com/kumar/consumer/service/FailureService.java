package com.kumar.consumer.service;

import com.kumar.consumer.entity.FailureRecord;
import com.kumar.consumer.repository.FailureRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FailureService {

    @Autowired
    private FailureRecordRepository failureRecordRepository;

    public void saveFailedRecord(ConsumerRecord<Integer, String> record, Exception exception, String recordStatus){
        var failureRecord = new FailureRecord(null,record.topic(), record.key(),  record.value(), record.partition(),record.offset(),
                exception.getCause().getMessage(),
                recordStatus);
        log.info("save fail record: {}", failureRecord);
        failureRecordRepository.save(failureRecord);

    }
}
