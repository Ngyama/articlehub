package com.ngymkana.service;

import com.ngymkana.pojo.User;

public interface UserService {
    // Find user by username
    User findByUserName(String username);

    // Register user
    void register(String username, String password);

    // Update user
    void update(User user);

    // Update avatar
    void updateAvatar(String avatarUrl);

    // Update password
    void updatePwd(String newPwd);
}
