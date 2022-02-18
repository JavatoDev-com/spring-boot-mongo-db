package com.javatodev.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookCreationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String isbn;
    @NotBlank
    private String authorId;
}