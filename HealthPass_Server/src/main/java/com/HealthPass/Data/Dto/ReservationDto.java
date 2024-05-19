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

    private int status;
    private AccountDto account;
    private String classString;
    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setEx_name(reservationDto.getEx_name());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setDate(reservationDto.getDate());
        reservation.setHour(reservationDto.getHour());
        reservation.setMinute(reservationDto.getMinute());
        AccountDto toAccount = reservationDto.getAccount();
        Account info = AccountDto.toAccount(toAccount);
        reservation.setAccount(info);
        return reservation;
    }
}
