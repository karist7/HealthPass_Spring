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
    //사용자 이름
    private String user_name;
    //사용자 전화번호
    private String user_phone;
    private int status;

    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setEx_name(reservationDto.getEx_name());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setDate(reservationDto.getDate());
        reservation.setHour(reservationDto.getHour());
        reservation.setMinute(reservationDto.getMinute());
        reservation.setEmail(reservationDto.getEmail());
        reservation.setUser_name(reservationDto.getUser_name());
        reservation.setUser_phone(reservationDto.getUser_phone());
        return reservation;
    }
}
