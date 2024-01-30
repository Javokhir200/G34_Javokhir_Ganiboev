package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class User {
    private static int seq = 0;
    {
        seq++;
    }
    private Integer userId = seq;
    private String fullName;
    private String password;
    private LocalDate createdDate = LocalDate.now();
    private String email;
    private String userName;

    public User(String fullName, String password,String email, String userName) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }
}
