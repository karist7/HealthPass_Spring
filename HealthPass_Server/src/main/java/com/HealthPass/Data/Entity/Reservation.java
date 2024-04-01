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
    private String ex_name;

    @Column(nullable = false)
    private String seat;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_email", nullable = false)
    private Account account;

    public static ReservationDto toReservationDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setSeat(reservation.getSeat());
        reservationDto.setEx_name(reservation.getEx_name());
        reservationDto.setDate(reservation.getDate());
        reservationDto.setAccount(reservation.getAccount());
        return reservationDto;
    }
}
