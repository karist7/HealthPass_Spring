package com.HealthPass.Controller;

import com.HealthPass.Data.Dto.AccountDto;
import com.HealthPass.Data.Dto.ReservationDto;
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
    public ResponseEntity<ReservationDto> reservation (HttpServletRequest request) throws IOException {
        ReservationDto dto = reservationService.reservedTime(request);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(dto.getStatus()==201){
            return new ResponseEntity<>(dto,header, HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(dto,header, HttpStatus.ACCEPTED);
        }

    }

}
