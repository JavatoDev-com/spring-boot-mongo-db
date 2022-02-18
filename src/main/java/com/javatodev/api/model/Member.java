package com.javatodev.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Member {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private MemberStatus status;

}
