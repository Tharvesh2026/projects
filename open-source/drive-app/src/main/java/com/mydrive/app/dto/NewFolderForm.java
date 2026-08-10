package com.mydrive.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewFolderForm {

    @NotBlank
    private String name;

    private Long parentId; // null = create at root
}
