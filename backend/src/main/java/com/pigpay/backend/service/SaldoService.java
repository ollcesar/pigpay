package com.pigpay.backend.service;

import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.model.Renda;
import com.pigpay.backend.model.TipoPagamento;
import com.pigpay.backend.repository.GastoRepository;
import com.pigpay.backend.repository.RendaRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class SaldoService {

    private final RendaRepository rendaRepository;
    private final GastoRepository gastoRepository;

    public SaldoService(RendaRepository rendaRepository, GastoRepository gastoRepository) {
        this.rendaRepository = rendaRepository;
        this.gastoRepository = gastoRepository;
    }

    public BigDecimal calcularSaldo() {
        BigDecimal rendas = rendaRepository.findAll().stream()
                .map(Renda::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gastosNoDebito = gastoRepository.findByTipo(TipoPagamento.DEBITO).stream()
                .map(Gasto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return rendas.subtract(gastosNoDebito);
    }
}
