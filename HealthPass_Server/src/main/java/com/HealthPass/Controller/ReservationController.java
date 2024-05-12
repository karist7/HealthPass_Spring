package com.HealthPass.Controller;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Data.Entity.Reservation;
import com.HealthPass.Service.ReservationService;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@Data
public class ReservationController {

    private final ReservationService reservationService;

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
    public ResponseEntity<ReservationDto> reserved(@RequestBody ReservationDto reservationDto) throws  IOException{
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

}
