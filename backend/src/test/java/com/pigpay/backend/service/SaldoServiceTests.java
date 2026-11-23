package com.pigpay.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.model.Renda;
import com.pigpay.backend.model.TipoPagamento;
import com.pigpay.backend.repository.GastoRepository;
import com.pigpay.backend.repository.RendaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaldoServiceTests {

    @Autowired
    private SaldoService saldoService;

    @Autowired
    private RendaRepository rendaRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @BeforeEach
    void limparBancoDeTeste() {
        gastoRepository.deleteAll();
        rendaRepository.deleteAll();
    }

    @Test
    void saldoDescontaSoGastosNoDebito() {
        salvarRenda("3000.00");
        salvarGasto("200.00", TipoPagamento.DEBITO);
        salvarGasto("500.00", TipoPagamento.CREDITO);

        assertEquals(0, new BigDecimal("2800.00").compareTo(saldoService.calcularSaldo()));
    }

    @Test
    void saldoSemNadaCadastradoEZero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(saldoService.calcularSaldo()));
    }

    private void salvarRenda(String valor) {
        Renda renda = new Renda();
        renda.setDescricao("Salário");
        renda.setValor(new BigDecimal(valor));
        renda.setDataRecebimento(LocalDate.of(2026, 11, 5));
        rendaRepository.save(renda);
    }

    private void salvarGasto(String valor, TipoPagamento tipo) {
        Gasto gasto = new Gasto();
        gasto.setDescricao("Teste");
        gasto.setValor(new BigDecimal(valor));
        gasto.setDataGasto(LocalDate.of(2026, 11, 6));
        gasto.setTipo(tipo);
        gastoRepository.save(gasto);
    }
}
