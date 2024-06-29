package com.HealthPass.Data.Entity;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Dto.ReservationDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String ex_name;

    @Column(nullable = false)
    private String seat;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int hour;

    @Column(nullable = false)
    private int minute;

    @ManyToOne
    @JoinColumn(name = "account_email")
    @JsonBackReference
    private Account account;

    public static ReservationDto toReservationDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setSeat(reservation.getSeat());
        reservationDto.setEx_name(reservation.getEx_name());
        reservationDto.setDate(reservation.getDate());
        reservationDto.setHour(reservation.getHour());
        reservationDto.setMinute(reservation.getMinute());
        reservationDto.setAccount(reservation.getAccount());
        return reservationDto;
    }
}
