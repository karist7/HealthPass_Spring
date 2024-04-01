package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDto {
    private int id;

    private String ex_name;
    private String seat;

    private Date date;

    private Account account;

    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setDate(reservationDto.getDate());
        reservation.setAccount(reservationDto.getAccount());
        return reservation;
    }
}
