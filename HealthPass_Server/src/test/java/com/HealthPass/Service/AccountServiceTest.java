package com.HealthPass.Service;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import static org.mockito.Mockito.verify;


@SpringBootTest

public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;




    Account account = new Account();
    @BeforeEach
    @Transactional
    void beforeEach(){

        account.setEmail("test@naver.com");
        account.setName("test");
        account.setPassword("a12345");
        account.setPhone("01000000000");

    }
    @AfterEach
    void afterEach(){
        accountRepository.deleteById(account.getEmail());
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registerTest() throws IOException {

    }


}
