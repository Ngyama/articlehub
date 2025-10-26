package com.ngymkana.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
// Auto-generate getter/setter/toString methods
@Data
public class User {
    @NotNull
    private Integer id; // Primary key
    private String username; // Username
    @JsonIgnore // Ignore password in JSON serialization
    private String password; // Password

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname; // Nickname

    @NotEmpty
    @Email
    private String email; // Email
    private String userPic; // Avatar URL
    private LocalDateTime createTime; // Creation time
    private LocalDateTime updateTime; // Update time
}
