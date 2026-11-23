package com.pigpay.backend.model;

import java.math.BigDecimal;
import java.util.List;

public record Fatura(String mes, BigDecimal total, List<Gasto> gastos) {
}
