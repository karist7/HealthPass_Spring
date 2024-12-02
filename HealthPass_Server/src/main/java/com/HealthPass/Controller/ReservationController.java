package com.HealthPass.Controller;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@Data
public class ReservationController {

    private final ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @PostMapping(value="/reserveTime/")
    @ResponseBody
    public ResponseEntity<ReservationDto> reserveTime (@RequestBody ReservationDto reservation) throws IOException {
        ReservationDto dto = reservationService.reservedTime(reservation);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto,header, HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(dto,header, HttpStatus.ACCEPTED);
        }

    }
    @PostMapping(value="/machine/", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ReservationDto> reserveMachine (@RequestBody ReservationDto reservation) throws IOException{
        ReservationDto dto = reservationService.reservedMachine(reservation);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto,header, HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(dto,header, HttpStatus.ACCEPTED);
        }
    }
    @PostMapping(value="/reservation/", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ReservationDto> reserved(  @RequestBody ReservationDto reservationDto) throws  IOException{
        ReservationDto dto = reservationService.reservation(reservationDto);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto,header, HttpStatus.CREATED);

        }
        else if(dto.getStatus()==202){
            return new ResponseEntity<>(dto,header, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(dto,header, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
    @PostMapping(value="/info/", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservedInfo(@RequestBody ReservationDto reservationDto) throws JsonProcessingException {
        List<Reservation> reservations = reservationService.findInfo(reservationDto);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(reservations,header,HttpStatus.CREATED);
    }
    @DeleteMapping(value="/info/", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<ReservationDto> cancel(@RequestBody ReservationDto reservationDto){
        ReservationDto dto = reservationService.deleteReservation(reservationDto);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        logger.debug("errorMessage {}",dto.getStatus());
        if(dto.getStatus()==201)
            return new ResponseEntity<>(dto, header, HttpStatus.CREATED);
        else{
            return new ResponseEntity<>(dto, header, HttpStatus.BAD_REQUEST);
        }

    }
}
