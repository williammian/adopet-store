package br.com.wm.adopetstore.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "estoques")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @OneToOne
    private Produto produto;

    @Version
    private Integer versao;

    public Estoque(){}

    public Estoque(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getVersao() {
        return versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(id, estoque.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void adicionar(Integer quantidade) {
        this.quantidade += quantidade;
    }
    public void diminuir(Integer quantidade) {
        this.quantidade -= quantidade;
    }
}
