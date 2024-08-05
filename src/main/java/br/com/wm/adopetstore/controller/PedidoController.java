package br.com.wm.adopetstore.controller;

import br.com.wm.adopetstore.dto.CadastroPedidoDTO;
import br.com.wm.adopetstore.dto.PedidoDTO;
import br.com.wm.adopetstore.email.EmailPedidoRealizado;
import br.com.wm.adopetstore.model.Usuario;
import br.com.wm.adopetstore.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;
    @Autowired
    private EmailPedidoRealizado email;
    @PostMapping
    public ResponseEntity<PedidoDTO> cadastrar(@Valid @RequestBody CadastroPedidoDTO dto, @AuthenticationPrincipal Usuario usuario) {
        var pedido = this.service.cadastrar(dto, usuario);
        System.out.println("Thread do controller: " + Thread.currentThread().getName());
        email.enviar(pedido, usuario);
        return ResponseEntity.ok(pedido);
    }
}
