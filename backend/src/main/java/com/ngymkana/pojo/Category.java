package com.ngymkana.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id; // Primary key
    @NotEmpty
    private String categoryName; // Category name
    @NotEmpty
    private String categoryAlias; // Category alias
    private Integer createUser; // Creator ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // Creation time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // Update time

    // Validation groups for different operations


    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
