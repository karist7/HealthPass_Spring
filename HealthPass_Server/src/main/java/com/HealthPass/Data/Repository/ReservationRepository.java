package com.HealthPass.Data.Repository;

import com.HealthPass.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Query("Select p.date from Reservation as p where p.email= :email")
    Date findDate(@Param("email") String email);
}
