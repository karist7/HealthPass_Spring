package com.HealthPass.Service;

import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Repository.AccountRepository;
import com.HealthPass.Data.Repository.ReservationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    public ReservationDto reservedTime(HttpServletRequest request) throws UnsupportedEncodingException {
        logger.debug(request.getParameter("day"));
        
        logger.debug(request.getParameter("minute"));
        logger.debug(request.getParameter("hour"));
        ReservationDto reservationDto = setReservation(request);
        String email = reservationDto.getEmail();
        if(reservationRepository.findDate(email).isPresent()){
            reservationDto.setStatus(202);
        }
        else{
            reservationDto.setStatus(201);
        }

        return reservationDto;
    }

    public ReservationDto setReservation(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        ReservationDto reservationDto = new ReservationDto();
        String dateString = request.getParameter("day");
        LocalDate date = LocalDate.parse(dateString);
        reservationDto.setDate(date);
        reservationDto.setHour(Integer.parseInt(request.getParameter("hour")));
        reservationDto.setMinute(Integer.parseInt(request.getParameter("minute")));
        reservationDto.setEmail(request.getParameter("email"));
        return reservationDto;

    }
}
