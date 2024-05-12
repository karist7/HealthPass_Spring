package com.HealthPass.Data.Entity;

import com.HealthPass.Data.Dto.ReservationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String user_phone;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public static ReservationDto toReservationDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setSeat(reservation.getSeat());
        reservationDto.setEx_name(reservation.getEx_name());
        reservationDto.setDate(reservation.getDate());
        reservationDto.setHour(reservation.getHour());
        reservationDto.setMinute(reservation.getMinute());
        reservationDto.setEmail(reservation.getEmail());
        reservationDto.setUser_name(reservation.getUser_name());
        reservationDto.setUser_phone(reservation.getUser_phone());
        return reservationDto;
    }
}
