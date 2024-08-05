package br.com.wm.adopetstore.email;

import br.com.wm.adopetstore.dto.PedidoDTO;
import br.com.wm.adopetstore.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailPedidoRealizado {
    @Autowired
    private EnviadorEmail enviador;

    @Async("asyncExecutor")
    public void enviar(PedidoDTO dto, Usuario usuario){
        enviador.enviarEmail(
                "Pedido efetuado com sucesso na Adopet Store",
                usuario.getEmail(),
                "Olá! " + "!\n\nSeu pedido foi registrado. Itens: \n" + dto.itens());
        System.out.println("Thread do email: " + Thread.currentThread().getName());
    }
}
