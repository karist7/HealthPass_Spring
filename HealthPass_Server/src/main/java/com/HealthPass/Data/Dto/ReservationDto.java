package com.HealthPass.Data.Dto;

import com.HealthPass.Data.Entity.Reservation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDto {
    private int id;
    @Email
    private String email;
    private String ex_name;
    private String seat;
    private String user_name;
    private String user_phone;
    private Date date;

    public static Reservation toReservation(ReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setEmail(reservationDto.getEmail());
        reservation.setSeat(reservationDto.getSeat());
        reservation.setUser_name(reservationDto.getUser_name());
        reservation.setUser_phone(reservationDto.getUser_phone());
        reservation.setDate(reservationDto.getDate());
        return reservation;
    }
}
