package org.example.service.impl;

import org.example.domain.User;
import org.example.service.UserService;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    static UserServiceImpl userService;
    private UserServiceImpl(){

    }
    public static UserServiceImpl getInstance(){
        if (Objects.isNull(userService)){
            userService = new UserServiceImpl();
            return userService;
        }
        return userService;
    }
    @Override
    public User register(User user) {
        if (isExistUser(user.getUserName(),user.getEmail())){
            return null;
        }
        USERS.add(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        for (User user:USERS){
            if (user.getUserName().equalsIgnoreCase(username) &&
            user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public String changePassword(Integer userId, String password) {
        if (password.length()<4)
            return "Enter strong password !!!";
        for (User user:USERS){
            if (user.getUserId().equals(userId)){
                user.setPassword(password);
                return "Password changed successfully !!!";
            }
        }
        return "User is not found !!!";
    }

    @Override
    public void showMyData(Integer userId) {
        for (User user:USERS){
            if (user.getUserId().equals(userId)){
                user.toString();
            }
        }
    }

    @Override
    public String confirm(String email) {

    }

    boolean isExistUser(String username,String email){
        for (User user: USERS){
            if (user.getUserName().equalsIgnoreCase(username) ||
            user.getEmail().equalsIgnoreCase(email))
                return true;
        }
        return false;
    }
}
