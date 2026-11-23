package com.pigpay.backend.controller;

import com.pigpay.backend.service.SaldoService;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saldo")
public class SaldoController {

    private final SaldoService saldoService;

    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping
    public Map<String, BigDecimal> consultar() {
        return Map.of("saldo", saldoService.calcularSaldo());
    }
}
