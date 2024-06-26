package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp="(?=.*[a-zA-z])(?=.*[0-9]).{6,20}")
    private String password;
    private String name;
    private String phone;

    private int  status;
    private String message;


    private List<Reservation> reservations = new ArrayList<>();

    public static Account toAccount(AccountDto account){
        Account act = new Account();
        act.setEmail(account.getEmail());
        act.setName(account.getName());
        act.setPassword(account.getPassword());
        act.setPhone(account.getPhone());
        act.setReservations(account.getReservations());
        return act;
    }
}
