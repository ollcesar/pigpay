package com.pigpay.backend.service;

import com.pigpay.backend.model.Fatura;
import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.model.TipoPagamento;
import com.pigpay.backend.repository.GastoRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaturaService {

    private final GastoRepository gastoRepository;

    public FaturaService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public Fatura calcularFatura(YearMonth mes) {
        YearMonth mesDasCompras = mes.minusMonths(1);

        List<Gasto> gastos = gastoRepository.findByTipoAndDataGastoBetweenOrderByDataGastoDescIdDesc(
                TipoPagamento.CREDITO, mesDasCompras.atDay(1), mesDasCompras.atEndOfMonth());

        BigDecimal total = gastos.stream()
                .map(Gasto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Fatura(mes.toString(), total, gastos);
    }
}
