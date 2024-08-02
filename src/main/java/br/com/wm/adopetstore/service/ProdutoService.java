package br.com.wm.adopetstore.service;

import br.com.wm.adopetstore.dto.CadastroProdutoDTO;
import br.com.wm.adopetstore.dto.ProdutoDTO;
import br.com.wm.adopetstore.exception.ValidacaoException;
import br.com.wm.adopetstore.model.Estoque;
import br.com.wm.adopetstore.model.Produto;
import br.com.wm.adopetstore.repository.EstoqueRepository;
import br.com.wm.adopetstore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public ProdutoDTO cadastrar(CadastroProdutoDTO dto){
        var jaCadastrado = repository.existsByNomeIgnoringCase(dto.nome());
        if(jaCadastrado)
            throw new ValidacaoException("Produto já cadastrado!");

        var produto = new Produto(dto);
        repository.save(produto);

        var estoque = new Estoque(dto.estoqueInicial(), produto);
        estoqueRepository.save(estoque);
        return new ProdutoDTO(produto);
    }

    public Page<ProdutoDTO> listar(Pageable paginacao){
        return repository.findAll(paginacao).map(ProdutoDTO::new);
    }

    public void excluir(Long idProduto){
        var produto = repository.findById(idProduto).get();
        produto.desativar();
    }
}
