package com.pigpay.backend.controller;

import com.pigpay.backend.model.Gasto;
import com.pigpay.backend.repository.GastoRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final GastoRepository repository;

    public GastoController(GastoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Gasto cadastrar(@Valid @RequestBody Gasto gasto) {
        gasto.setId(null);
        return repository.save(gasto);
    }

    @GetMapping
    public List<Gasto> listar() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataGasto", "id"));
    }
}
