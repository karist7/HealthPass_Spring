package com.HealthPass.Data.Entity;

import com.HealthPass.Data.Dto.AccountDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
public class Account {
    @Id
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    @Pattern(regexp="(?=.*[a-z])(?=.*[0-9]).{6,20}")
    private String password;

    @OneToMany(mappedBy = "account")
    private List<Reservation> reservations = new ArrayList<>();

    public static AccountDto toAccountDto(Account account){
        AccountDto dto = new AccountDto();
        dto.setEmail(account.getEmail());
        dto.setPhone(account.getPhone());
        dto.setPassword(account.getPassword());
        dto.setName(account.getName());
        dto.setReservations(account.getReservations());
        return dto;
    }
}
