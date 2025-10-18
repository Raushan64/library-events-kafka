package com.kumar.consumer.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class FailureRecord {
    @Id
    @GeneratedValue
    private Integer bookId;
    private String topic;

    @Column(name = "record_key")
    private Integer key;

    private String errorRecord;
    @Column(name = "partition_id")
    private Integer partition;
    private Long offset_value;
    private String exception;
    private String status;
}
