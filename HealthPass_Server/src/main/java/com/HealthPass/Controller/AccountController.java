package com.HealthPass.Controller;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Service.AccountService;
import com.HealthPass.config.MessageData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@Data
public class AccountController {
    private final AccountService accountService;
    @Operation(summary = "회원 가입 확인", description = "회원 가입 테스트.", tags = { "Register Controller" })
    @PostMapping(value ="/register/")
    @ResponseBody
    public ResponseEntity<AccountDto> registerPage(@RequestBody AccountDto accountDto) throws IOException {
        AccountDto dto = accountService.register(accountDto);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto,header, HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(dto,header, HttpStatus.ACCEPTED);
        }
    }

    @Operation(summary = "로그인 확인", description = "로그인 테스트.", tags = { "Login Controller" })
    @PostMapping(value ="/login/")
    @ResponseBody
    public ResponseEntity<AccountDto> loginPage(@RequestBody AccountDto accountDto)throws IOException{
        AccountDto dto = accountService.login(accountDto);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto, header, HttpStatus.CREATED);
        }
        else if(dto.getStatus()==202){
            return new ResponseEntity<>(dto, header, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(dto, header, 203);
        }
    }

}
