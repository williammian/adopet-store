package br.com.wm.adopetstore.service;

import br.com.wm.adopetstore.dto.ProdutoDTO;
import br.com.wm.adopetstore.dto.RelatorioEstoque;
import br.com.wm.adopetstore.dto.RelatorioFaturamento;
import br.com.wm.adopetstore.repository.EstoqueRepository;
import br.com.wm.adopetstore.repository.PedidoRepository;
import br.com.wm.adopetstore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class RelatorioService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Async
    public CompletableFuture<RelatorioEstoque> infoEstoque(){
        var produtosSemEstoque = estoqueRepository.produtosComEstoqueZerado()
                .stream().map(ProdutoDTO::new)
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(new RelatorioEstoque(produtosSemEstoque));
    }

    @Async
    public CompletableFuture<RelatorioFaturamento> faturamentoObtido() {
        var dataOntem = LocalDate.now().minusDays(1);
        var faturamentoTotal = pedidoRepository.faturamentoTotalDoDia(dataOntem);

        var estatisticas = pedidoRepository.faturamentoTotalDoDiaPorCategoria(dataOntem);

        return CompletableFuture.completedFuture(new RelatorioFaturamento(faturamentoTotal, estatisticas));
    }
}