package br.com.wm.adopetstore.dto;

import br.com.wm.adopetstore.model.Produto;

import java.math.BigDecimal;

public record ProdutoDTO(Long id, String nome, String descricao, BigDecimal preco) {
    public ProdutoDTO(Produto produto){
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco());
    }
}
