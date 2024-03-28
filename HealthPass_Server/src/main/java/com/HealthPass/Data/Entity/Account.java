package com.HealthPass.Data.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)

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
}
