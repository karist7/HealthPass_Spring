package com.HealthPass.Service;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Repository.AccountRepository;
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

    public AccountDto register(HttpServletRequest request) throws IOException {

        AccountDto dto = setAccount(request);
        Account account = dto.accountObject(dto);


        if(accountRepository.findById(dto.getEmail()).isPresent()){
            dto.setMessage("중복된 아이디");
            dto.setStatus(202);
            return dto;
        }


        else{
            dto.setMessage("회원가입 완료");
            accountRepository.save(account);
            dto.setStatus(201);
            return dto;
        }
    }
    public AccountDto login(HttpServletRequest request) throws IOException {
        AccountDto accountDto = new AccountDto();

        String email = request.getParameter("email");
        String pwd =  request.getParameter("password");
        if(accountRepository.findById(email).isPresent()){
            if(accountRepository.findPwd(email).equals(pwd)){
                Account act = accountRepository.findInfo(email);

                accountDto = toAccountDto(act);
                System.out.println(accountDto.getName());
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
    private AccountDto setAccount(HttpServletRequest request){
        AccountDto account = new AccountDto();
        account.setEmail(request.getParameter("email"));
        account.setName(request.getParameter("name"));
        account.setPhone(request.getParameter("phone"));
        account.setPassword(request.getParameter("password"));
        return account;
    }
}
