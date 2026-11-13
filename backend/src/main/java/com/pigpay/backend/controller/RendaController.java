package com.pigpay.backend.controller;

import com.pigpay.backend.model.Renda;
import com.pigpay.backend.repository.RendaRepository;
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

/**
 * Porta de entrada da API de rendas: recebe os pedidos da tela e responde em JSON.
 * POST /api/rendas  -> cadastra uma renda
 * GET  /api/rendas  -> lista as rendas, da mais recente para a mais antiga
 */
@RestController
@RequestMapping("/api/rendas")
public class RendaController {

    private final RendaRepository repository;

    public RendaController(RendaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Renda cadastrar(@Valid @RequestBody Renda renda) {
        renda.setId(null); // garante que sempre será uma renda nova
        return repository.save(renda);
    }

    @GetMapping
    public List<Renda> listar() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "dataRecebimento", "id"));
    }
}
