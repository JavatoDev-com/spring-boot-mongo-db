package com.javatodev.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class Lend {

    @Id
    private String id;
    private LendStatus status;
    private Instant startOn;
    private Instant dueOn;
    @DBRef
    private Book book;
    @DBRef
    private Member member;

}
