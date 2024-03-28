package com.HealthPass.Data.Repository;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    @Query("Select p.password from Account as p where p.email= :email")
    AccountDto findPwd(@Param("email") String email);

}
