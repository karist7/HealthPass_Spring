package com.HealthPass.Data.Entity;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Service.ReservationService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Account {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Id
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    //@Pattern(regexp="(?=.*[a-z])(?=.*[0-9]).{6,20}")
    private String password;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    @JsonManagedReference
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
    public static Account fromJson(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Account.class);
    }
}
