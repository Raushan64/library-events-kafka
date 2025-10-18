package com.kumar.consumer.repository;

import com.kumar.consumer.entity.FailureRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FailureRecordRepository extends JpaRepository<FailureRecord,Integer> {
    List<FailureRecord> findAllByStatus(String status);
}
