package com.HealthPass.Service;

import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Data.Repository.AccountRepository;
import com.HealthPass.Data.Repository.ReservationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    ReservationDto dto = new ReservationDto();
    public ReservationDto reservedTime(HttpServletRequest request) throws UnsupportedEncodingException {

        setReservation(request);
        String email = dto.getEmail();
        if(reservationRepository.findDate(email).isPresent()){
            dto.setStatus(202);
        }
        else {
            dto.setStatus(201);
        }

        return dto;
    }
    public ReservationDto reservedMachine(ReservationDto reservation) throws UnsupportedEncodingException {

        setMachine(reservation);
        dto.setStatus(0);

        LocalDate date = dto.getDate();
        int hour = dto.getHour();
        int minute = dto.getMinute();
        String seat = dto.getSeat();
        String ex_name = dto.getEx_name();
        //예약 내역이 존재할 경우
        if(reservationRepository.findResv(date,hour,minute,seat,ex_name).isPresent()){
            dto.setStatus(202);
        }
        else{
            dto.setStatus(201);
        }
        return dto;
    }

    public void setReservation(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String dateString = request.getParameter("day");
        LocalDate date = LocalDate.parse(dateString);
        dto.setDate(date);
        dto.setHour(Integer.parseInt(request.getParameter("hour")));
        dto.setMinute(Integer.parseInt(request.getParameter("minute")));
        dto.setEmail(request.getParameter("email"));


    }
    public void setMachine(ReservationDto reservation){

        dto.setEx_name(reservation.getEx_name());
        dto.setSeat(reservation.getSeat());
        logger.debug(dto.getEx_name());

    }
}
