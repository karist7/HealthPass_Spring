package com.HealthPass.Service;

import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Entity.Account;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Data.Repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationDto reservedTime(ReservationDto reservationDto) throws UnsupportedEncodingException, JsonProcessingException {

        ReservationDto dto =setReservation(reservationDto);
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
    public ReservationDto reservedMachine(ReservationDto reservationDto) throws UnsupportedEncodingException {
        ReservationDto dto = setMachine(reservationDto);
        LocalDate date =dto.getDate();
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
    public ReservationDto reservation(ReservationDto reservationDto ) throws JsonProcessingException {
        ReservationDto dto = new ReservationDto();
        dto.setStatus(0);


        int hour = reservationDto.getHour();
        int minute = reservationDto.getMinute();
        String stringAccount = reservationDto.getStringAccount();
        Account account = Account.fromJson(stringAccount);
        dto.setAccount(account);
        String email = dto.getAccount().getEmail();

        LocalDate date =reservationDto.getDate();
        dto.setDate(date);
        dto.setHour(hour);
        dto.setMinute(minute);
        dto.setEx_name(reservationDto.getEx_name());
        dto.setSeat(reservationDto.getSeat());


        Reservation resv = ReservationDto.toReservation(dto);
   

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
    public ReservationDto setReservation(ReservationDto reservationDto) throws JsonProcessingException {
        ReservationDto dto = new ReservationDto();
        String dateString = reservationDto.getDate().toString();
        LocalDate date = LocalDate.parse(dateString);
        // Reservation 객체에서 AccountDto를 가져옴
        String stringAccount = reservationDto.getStringAccount();
        Account account = Account.fromJson(stringAccount);
        dto.setAccount(account);
        logger.debug(dto.getAccount().getEmail());
        dto.setDate(date);
        dto.setHour(reservationDto.getHour());
        dto.setMinute(reservationDto.getMinute());
        return dto;

    }
    public ReservationDto setMachine(ReservationDto reservationDto){
        ReservationDto dto = new ReservationDto();
        dto.setEx_name(reservationDto.getEx_name());
        dto.setSeat(reservationDto.getSeat());
        dto.setStatus(0);
        LocalDate date = dto.getDate();
        int hour = dto.getHour();
        int minute = dto.getMinute();


        dto.setHour(hour);
        dto.setMinute(minute);
        return dto;

    }
    public List<Reservation> findInfo(ReservationDto reservationDto) throws JsonProcessingException {
        String stringAccount = reservationDto.getStringAccount();

        Account account = Account.fromJson(stringAccount);
        String email = account.getEmail();
        return reservationRepository.info(email);
    }
    public ReservationDto deleteReservation(ReservationDto reservationDto){
        ReservationDto dto = new ReservationDto();
        dto.setStatus(0);
        int minute = reservationDto.getMinute();
        LocalDate date = reservationDto.getDate();
        String ex_name = reservationDto.getEx_name();
        String seat = reservationDto.getSeat();
        int hour = reservationDto.getHour();



        Optional<Reservation> resv = reservationRepository.findResv(date,hour,minute,seat,ex_name);
        if(resv.isPresent()){

            Reservation reservation = resv.get();

            reservationDto.setStatus(201);

            reservationRepository.delete(reservation);
        }
        else{
            reservationDto.setStatus(400);
        }


        return reservationDto;
    }
}
