package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp="(?=.*[a-zA-z])(?=.*[0-9]).{6,20}")
    private String password;
    private String name;
    private String phone;

    private int  status;
    private String message;

    public Account accountObject(){
        Account account = new Account();
        account.setEmail(email);
        account.setName(name);
        account.setPassword(password);
        account.setPhone(phone);
        return account;
    }
}
