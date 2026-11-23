package com.pigpay.backend.controller;

import com.pigpay.backend.model.Fatura;
import com.pigpay.backend.service.FaturaService;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/faturas")
public class FaturaController {

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @GetMapping
    public Fatura consultar(@RequestParam(required = false) String mes) {
        try {
            YearMonth yearMonth = (mes == null || mes.isBlank()) ? YearMonth.now() : YearMonth.parse(mes);
            return faturaService.calcularFatura(yearMonth);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Use o formato AAAA-MM, ex.: 2026-12");
        }
    }
}
