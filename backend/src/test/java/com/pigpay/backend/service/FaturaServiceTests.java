package com.pigpay.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pigpay.backend.model.Fatura;
import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.model.TipoPagamento;
import com.pigpay.backend.repository.GastoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FaturaServiceTests {

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private GastoRepository gastoRepository;

    @BeforeEach
    void limparBancoDeTeste() {
        gastoRepository.deleteAll();
    }

    @Test
    void compraNoCreditoEntraNaFaturaDoMesSeguinte() {
        salvarGasto("100.00", TipoPagamento.CREDITO, LocalDate.of(2026, 11, 15));
        salvarGasto("50.50", TipoPagamento.CREDITO, LocalDate.of(2026, 11, 30));

        Fatura dezembro = faturaService.calcularFatura(YearMonth.of(2026, 12));

        assertEquals(0, new BigDecimal("150.50").compareTo(dezembro.total()));
        assertEquals(2, dezembro.gastos().size());
    }

    @Test
    void compraNoCreditoNaoEntraNaFaturaDoMesDaCompra() {
        salvarGasto("100.00", TipoPagamento.CREDITO, LocalDate.of(2026, 11, 15));

        Fatura novembro = faturaService.calcularFatura(YearMonth.of(2026, 11));

        assertEquals(0, BigDecimal.ZERO.compareTo(novembro.total()));
        assertEquals(0, novembro.gastos().size());
    }

    @Test
    void gastoNoDebitoNaoEntraNaFatura() {
        salvarGasto("80.00", TipoPagamento.DEBITO, LocalDate.of(2026, 11, 15));

        Fatura dezembro = faturaService.calcularFatura(YearMonth.of(2026, 12));

        assertEquals(0, BigDecimal.ZERO.compareTo(dezembro.total()));
    }

    @Test
    void compraDeDezembroEntraNaFaturaDeJaneiroDoAnoSeguinte() {
        salvarGasto("200.00", TipoPagamento.CREDITO, LocalDate.of(2026, 12, 20));

        Fatura janeiro = faturaService.calcularFatura(YearMonth.of(2027, 1));

        assertEquals(0, new BigDecimal("200.00").compareTo(janeiro.total()));
    }

    private void salvarGasto(String valor, TipoPagamento tipo, LocalDate data) {
        Gasto gasto = new Gasto();
        gasto.setDescricao("Teste");
        gasto.setValor(new BigDecimal(valor));
        gasto.setDataGasto(data);
        gasto.setTipo(tipo);
        gastoRepository.save(gasto);
    }
}
