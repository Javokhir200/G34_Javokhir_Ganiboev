package org.example.service;

import org.example.domain.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    List<User> USERS = new ArrayList<>();
    User register(User user);
    User login(String username,String password);
    String changePassword(Integer userId,String password);
    void showMyData(Integer userId);
    String confirm(String email);
}
