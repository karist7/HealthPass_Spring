package com.HealthPass.Data.Repository;

import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {


    @Query("SELECT p FROM Reservation p WHERE p.date = :date AND p.hour = :hour AND p.minute = :minute AND p.seat = :seat AND p.ex_name = :ex_name")
    Optional<Reservation> findResv(@Param("date") LocalDate date, @Param("hour") int hour, @Param("minute") int minute,
                                   @Param("seat") String seat, @Param("ex_name") String ex_name);
    @Query("SELECT r FROM Reservation r JOIN r.account a WHERE a.email = :email AND r.date = :date AND r.hour = :hour AND r.minute = :minute")
    Optional<Reservation> duplicateMachine(@Param("email") String email, @Param("date") LocalDate date, @Param("hour") int hour, @Param("minute") int minute);

    @Query("SELECT p from Reservation p Where p.date = :date AND p.hour = :hour AND p.minute =:minute")
    Optional<Reservation> duplicateTime(@Param("date")LocalDate date, @Param("hour") int hour, @Param("minute") int minute);


}
