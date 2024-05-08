package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ReservationDto {
    private int id;

    private String ex_name;
    private String seat;

    private LocalDate date;

    private int hour;
    private int minute;
    private String email;

    private int status;

    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setDate(reservationDto.getDate());
        reservation.setHour(reservationDto.getHour());
        reservation.setMinute(reservationDto.getMinute());
        reservation.setEmail(reservationDto.getEmail());
        return reservation;
    }
}
