package br.com.wm.adopetstore.service;

import br.com.wm.adopetstore.dto.CadastroPedidoDTO;
import br.com.wm.adopetstore.dto.PedidoDTO;
import br.com.wm.adopetstore.exception.ValidacaoException;
import br.com.wm.adopetstore.model.ItemPedido;
import br.com.wm.adopetstore.model.Pedido;
import br.com.wm.adopetstore.model.Usuario;
import br.com.wm.adopetstore.repository.EstoqueRepository;
import br.com.wm.adopetstore.repository.PedidoRepository;
import br.com.wm.adopetstore.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Transactional
    public PedidoDTO cadastrar(CadastroPedidoDTO dto, Usuario usuario) {
        var itens = new ArrayList<ItemPedido>();
        for (var itemDto : dto.itens()) {
            var estoque = estoqueRepository.findByProdutoId(itemDto.produtoId());
            if (estoque.getQuantidade() >= itemDto.quantidade()) {
                var produto = produtoRepository.findById(itemDto.produtoId()).get();
                if (!produto.getAtivo())
                    throw new ValidacaoException("Pedido contém produto excluído: " + produto.getId());
                var itemPedido = new ItemPedido(null, produto, itemDto.quantidade());
                itens.add(itemPedido);
                estoque.diminuir(itemDto.quantidade());
            } else {
                throw new ValidacaoException("Estoque indisponível para o produto: " + itemDto.produtoId());
            }
        }

        var pedido = new Pedido(itens, usuario);
        repository.save(pedido);

        return new PedidoDTO(pedido);

    }
}