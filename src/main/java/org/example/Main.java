package org.example;

import org.example.domain.User;
import org.example.service.ApiService;
import org.example.service.UserService;
import org.example.service.impl.ApiServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner scStr = new Scanner(System.in);
    static Scanner scNum = new Scanner(System.in);
    static UserService userService = UserServiceImpl.getInstance();
    static ApiService apiService = ApiServiceImpl.getApiService();
    public static void main(String[] args) {
        boolean onProcess = true;
        while (onProcess){
            start();
            System.out.print("Enter the number: ");
            int startNum = scNum.nextInt();
            switch (startNum){
                case 0 -> onProcess = false;
                case 1 -> login();
                case 2 -> register();
                default -> System.out.println("Enter number between 0 to 2 !!!");
            }
        }
    }

    private static void register() {
        int chance = 5;
        System.out.print("Enter your email: ");
        String email = scStr.nextLine();
        String confirm = userService.confirm(email);
        while (chance>0){
            System.out.print("Enter the code we have sent to your email: ");
            String code = scStr.nextLine();
            if (code.equals(confirm)){
                System.out.print("Enter your fullName: ");
                String fullName = scStr.nextLine();
                System.out.print("Enter valid userName: ");
                String userName = scStr.nextLine();
                System.out.print("Enter valid password: ");
                String password = scStr.nextLine();
                User loggedUser = userService.register(new User(fullName, password, email, userName));
                if (Objects.isNull(loggedUser)){
                    System.out.println("User already exist !!!");
                }else{
                    UserMenu(loggedUser);
                    chance=0;
                }
            }else{
                System.out.println("Code is incorrect !!!");
                chance--;
                System.out.println("chance = " + chance);
            }
        }
    }


    private static void login() {
        System.out.print("Enter yor userName: ");
        String userName = scStr.nextLine();
        System.out.print("Enter yor password: ");
        String password = scStr.nextLine();
        User loggedUser = userService.login(userName, password);
        if (Objects.isNull(loggedUser)){
            System.out.println("User is not found !!!");
        }else{
            UserMenu(loggedUser);
        }

    }

    private static void start() {
        System.out.println("""
                1.Login
                2.Register
                
                0.Exit
                """);
    }
    private static void UserMenu(User loggedUser) {
        boolean onProcess = true;
        while (onProcess){
            int chance = 5;
            showUserMenu();
            System.out.print("Enter the number: ");
            int userMenuNum = scNum.nextInt();
            switch (userMenuNum){
                case 0 -> onProcess = false;
                case 1 -> {
                    System.out.print("Enter new password: ");
                    String password = scStr.nextLine();
                    String confirm = userService.confirm(loggedUser.getEmail());
                    while (chance>0){
                        System.out.print("Enter the code we have sent to your email: ");
                        String code = scStr.nextLine();
                        if (code.equals( confirm)){
                            System.out.println(userService.changePassword(loggedUser.getUserId(), password));
                            chance = 0;
                        }else{
                            System.out.println("Code is incorrect !!!");
                            chance--;
                            System.out.println("chance = " + chance);
                        }
                    }
                }case 2->{
                    System.out.print("Enter the month's number: ");
                    Integer monthNum = scNum.nextInt();
                    System.out.print("Enter the day's number: ");
                    Integer dayNum = scNum.nextInt();
                    System.out.println(apiService.sendFact(monthNum, dayNum));
                }case 3->{
                    userService.showMyData(loggedUser.getUserId());
                }
                default -> System.out.println("Please enter the number between 0 to 2 !!!");
            }
        }
    }

    private static void showUserMenu() {
        System.out.println("""
                1.Change password
                2.Facts about date
                3.Show my info
                
                0.Exit
                """);
    }
}