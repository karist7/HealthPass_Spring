package com.HealthPass.Service;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Data.Repository.AccountRepository;
import com.HealthPass.Data.Repository.ReservationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.HealthPass.Data.Entity.Account.toAccountDto;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDto register(AccountDto accountDto) {
        AccountDto dto = new AccountDto();
        dto.setEmail(accountDto.getEmail());
        dto.setName(accountDto.getName());
        dto.setPhone(accountDto.getPhone());
        dto.setPassword(accountDto.getPassword());
        Account account = AccountDto.toAccount(dto);
        if(accountRepository.findById(dto.getEmail()).isPresent()){
            dto.setMessage("중복된 아이디");
            dto.setStatus(202);
        }
        else{
            dto.setMessage("회원가입 완료");
            accountRepository.save(account);
            dto.setStatus(201);
        }
        return dto;
    }
    public AccountDto login(AccountDto accountDto) throws IOException {

        String email = accountDto.getEmail();
        String pwd = accountDto.getPassword();
        if(accountRepository.findById(email).isPresent()){
            if(accountRepository.findPwd(email).equals(pwd)){
                Account act = accountRepository.findInfo(email);
                accountDto = toAccountDto(act);
                accountDto.setMessage("로그인 성공");
                accountDto.setStatus(201);
            }
            else {
                accountDto.setMessage("비밀번호 오류");
                accountDto.setStatus(202);
            }
        }
        else{
            accountDto.setMessage("아이디가 존재하지 않음");
            accountDto.setStatus(203);

        }
        return accountDto;

    }

}
