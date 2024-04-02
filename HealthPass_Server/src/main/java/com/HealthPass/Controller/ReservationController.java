package com.HealthPass.Controller;

import com.HealthPass.Data.Dto.ReservationDto;
import com.HealthPass.Service.ReservationService;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping("/reservation")
    @ResponseBody
    public ResponseEntity<ReservationDto> reservation (HttpServletRequest request) throws IOException {
        return null;
    }

}
