package com.javatodev.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BookLendRequest {
    @NotEmpty
    private List<String> bookIds;
    @NotBlank
    private String memberId;
}
