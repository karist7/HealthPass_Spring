package com.HealthPass.Data.Entity;

import com.HealthPass.Data.Dto.ReservationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String ex_name;

    @Column(nullable = false)
    private String seat;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String user_phone;

    @Column(nullable = false)
    private Date date;

    public static ReservationDto toReservationDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setEmail(reservation.getEmail());
        reservationDto.setSeat(reservation.getSeat());
        reservationDto.setEx_name(reservation.getEx_name());
        reservationDto.setUser_name(reservation.getUser_name());
        reservationDto.setUser_phone(reservation.getUser_phone());
        reservationDto.setDate(reservation.getDate());
        return reservationDto;
    }
}
