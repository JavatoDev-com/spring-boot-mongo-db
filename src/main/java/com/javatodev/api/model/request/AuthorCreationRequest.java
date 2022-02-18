package com.javatodev.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthorCreationRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
