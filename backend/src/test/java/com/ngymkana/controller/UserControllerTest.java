package com.ngymkana.controller;

import com.ngymkana.pojo.Result;
import com.ngymkana.pojo.User;
import com.ngymkana.service.UserService;
import com.ngymkana.utils.Md5Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("login succeeds when username exists and password matches")
    void loginSuccessReturnsToken() {
        String username = "testUser";
        String rawPassword = "p4ssw0rd";

        User storedUser = new User();
        storedUser.setId(1);
        storedUser.setUsername(username);
        storedUser.setPassword(Md5Util.getMD5String(rawPassword));

        when(userService.findByUserName(username)).thenReturn(storedUser);

        Result<String> result = userController.login(username, rawPassword);

        assertThat(result.getCode()).isZero();
        assertThat(result.getData())
                .as("login should return a non-empty JWT token")
                .isNotBlank();
    }

    @Test
    @DisplayName("login fails when username does not exist")
    void loginFailsForUnknownUser() {
        String username = "missingUser";
        String password = "somePass";

        when(userService.findByUserName(username)).thenReturn(null);

        Result<String> result = userController.login(username, password);

        assertThat(result.getCode()).isEqualTo(1);
        assertThat(result.getMessage()).isEqualTo("Invalid username");
    }

    @Test
    @DisplayName("login fails when password does not match stored hash")
    void loginFailsForWrongPassword() {
        String username = "testUser";
        String correctPassword = "p4ssw0rd";
        String badPassword = "wrongPass";

        User storedUser = new User();
        storedUser.setId(1);
        storedUser.setUsername(username);
        storedUser.setPassword(Md5Util.getMD5String(correctPassword));

        when(userService.findByUserName(username)).thenReturn(storedUser);

        Result<String> result = userController.login(username, badPassword);

        assertThat(result.getCode()).isEqualTo(1);
        assertThat(result.getMessage()).isEqualTo("Invalid password");
    }
}

