package com.pigpay.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pigpay.backend.model.Renda;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RendaRepositoryTests {

    @Autowired
    private RendaRepository repository;

    @Test
    void salvaERecuperaUmaRenda() {
        Renda renda = new Renda();
        renda.setDescricao("Salário");
        renda.setValor(new BigDecimal("3000.00"));
        renda.setDataRecebimento(LocalDate.of(2026, 11, 5));

        Renda salva = repository.save(renda);

        assertNotNull(salva.getId());
        Renda lida = repository.findById(salva.getId()).orElseThrow();
        assertEquals("Salário", lida.getDescricao());
        assertEquals(0, new BigDecimal("3000.00").compareTo(lida.getValor()));
        assertEquals(LocalDate.of(2026, 11, 5), lida.getDataRecebimento());
    }
}
