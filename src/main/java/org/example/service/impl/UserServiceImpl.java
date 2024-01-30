package org.example.service.impl;

import org.example.domain.User;
import org.example.service.UserService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

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
                System.out.println(user);
            }
        }
    }

    @Override
    public String confirm(String email) {
        ResourceBundle bundle = ResourceBundle.getBundle("data");
        String password = bundle.getString("password");
        String from = bundle.getString("email");

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        Random random = new Random();
        Integer code = random.nextInt(100_000,999_999);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Confirmation");
            message.setText(String.format("This is your code: %d",code));
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return String.valueOf(code);
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
