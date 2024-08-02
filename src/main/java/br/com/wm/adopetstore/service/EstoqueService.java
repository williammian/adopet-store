package br.com.wm.adopetstore.service;

import br.com.wm.adopetstore.dto.AtualizaEstoqueDTO;
import br.com.wm.adopetstore.dto.EstoqueDTO;
import br.com.wm.adopetstore.exception.ValidacaoException;
import br.com.wm.adopetstore.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {
    @Autowired
    private EstoqueRepository repository;

    public List<EstoqueDTO> listar(){
        return repository.findAll()
                .stream().map(EstoqueDTO::new).collect(Collectors.toList());
    }

    public void atualizarEstoque(AtualizaEstoqueDTO dto){
        var estoque = repository.findByProdutoId(dto.idProduto());
        if(!estoque.getProduto().getAtivo())
            throw new ValidacaoException("Produto excluído!");
        estoque.adicionar(dto.quantidade());
    }
}
