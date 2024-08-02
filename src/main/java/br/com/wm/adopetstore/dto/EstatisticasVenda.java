package br.com.wm.adopetstore.dto;

import br.com.wm.adopetstore.model.Categoria;

import java.math.BigDecimal;

public record EstatisticasVenda(Categoria categoria, Long quantidadesVenda, BigDecimal faturamento) {
}