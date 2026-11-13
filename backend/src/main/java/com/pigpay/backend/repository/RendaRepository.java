package com.pigpay.backend.repository;

import com.pigpay.backend.model.Renda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Conversa com o banco. Só de estender JpaRepository, já ganhamos
 * save(), findAll(), findById(), deleteById() etc. sem escrever SQL.
 */
public interface RendaRepository extends JpaRepository<Renda, Long> {
}
