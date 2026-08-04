package com.opensourceapi.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequest {

    @NotBlank
    private String title;

    private String description;

    private boolean completed;
}
