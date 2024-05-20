package com.HealthPass.Service;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Data.Repository.AccountRepository;
import com.HealthPass.Data.Repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    ReservationDto dto = new ReservationDto();
    public ReservationDto reservedTime(ReservationDto reservation) throws UnsupportedEncodingException, JsonProcessingException {

        setReservation(reservation);
        Account account = dto.getAccount();
        String email = account.getEmail();

        LocalDate date = dto.getDate();
        int hour = dto.getHour();
        int minute = dto.getMinute();
        if(reservationRepository.duplicateMachine(email,date,hour,minute).isPresent()){
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
        logger.info(dto.getEx_name());
        //예약 내역이 존재할 경우
        if(reservationRepository.findResv(date,hour,minute,seat,ex_name).isPresent()){
            dto.setStatus(202);
        }
        else{
            dto.setStatus(201);
        }
        return dto;
    }
    public ReservationDto reservation(ReservationDto reservationDto){
        dto.setStatus(0);
        logger.trace(dto.getEx_name());

        Reservation resv = ReservationDto.toReservation(dto);
        LocalDate date =dto.getDate();
        int hour = dto.getHour();
        int minute = dto.getMinute();
        String email = dto.getAccount().getEmail();

        if(reservationRepository.duplicateMachine(email,date, hour, minute).isPresent()){
            dto.setStatus(202);
        }
        else if(reservationRepository.duplicateTime(date,hour,minute).isPresent()){
            dto.setStatus(203);
        }
        else{
            dto.setStatus(201);
            reservationRepository.save(resv);
        }
        return dto;
    }
    public void setReservation(ReservationDto reservation) throws JsonProcessingException {

        String dateString = reservation.getDate().toString();
        LocalDate date = LocalDate.parse(dateString);
        // Reservation 객체에서 AccountDto를 가져옴
        String stringAccount = reservation.getStringAccount();


        Account account = Account.fromJson(stringAccount);
        dto.setAccount(account);
        dto.setDate(date);
        dto.setHour(reservation.getHour());
        dto.setMinute(reservation.getMinute());




    }
    public void setMachine(ReservationDto reservation){

        dto.setEx_name(reservation.getEx_name());
        dto.setSeat(reservation.getSeat());
    }

}
