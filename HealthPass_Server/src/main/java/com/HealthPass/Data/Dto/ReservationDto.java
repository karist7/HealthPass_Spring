package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private int id;

    private String ex_name;
    private String seat;

    private LocalDate date;

    private int hour;
    private int minute;

    private int status;


    private Account account;

    private String stringAccount;
    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setEx_name(reservationDto.getEx_name());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setDate(reservationDto.getDate());
        reservation.setHour(reservationDto.getHour());
        reservation.setMinute(reservationDto.getMinute());

        reservation.setAccount(reservationDto.getAccount());
        return reservation;
    }
}
