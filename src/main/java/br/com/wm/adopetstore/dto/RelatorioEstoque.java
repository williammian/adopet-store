package br.com.wm.adopetstore.dto;

import java.util.List;

public record RelatorioEstoque (List<ProdutoDTO> produtosAusentes) {
}