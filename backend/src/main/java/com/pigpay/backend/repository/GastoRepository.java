package com.pigpay.backend.repository;

import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.model.TipoPagamento;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<Gasto, Long> {

    List<Gasto> findByTipo(TipoPagamento tipo);

    List<Gasto> findByTipoAndDataGastoBetweenOrderByDataGastoDescIdDesc(
            TipoPagamento tipo, LocalDate inicio, LocalDate fim);
}
