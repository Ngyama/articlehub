package com.ngymkana.pojo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import com.ngymkana.anno.State;

import java.time.LocalDateTime;
@Data
public class Article {
    private Integer id; // Primary key
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title; // Article title
    @NotEmpty
    private String content; // Article content
    @NotEmpty
    @URL
    private String coverImg; // Cover image

    @State
    private String state; // Publish status: published|draft
    @NotNull
    private Integer categoryId; // Category ID
    private Integer createUser; // Creator ID
    private LocalDateTime createTime; // Creation time
    private LocalDateTime updateTime; // Update time
}
