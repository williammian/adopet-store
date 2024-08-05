package br.com.wm.adopetstore.email;

import br.com.wm.adopetstore.dto.RelatorioEstoque;
import br.com.wm.adopetstore.dto.RelatorioFaturamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailRelatorioGerado {
    @Autowired
    private EnviadorEmail enviador;

    public void enviar(RelatorioEstoque estoque, RelatorioFaturamento faturamento){
        enviador.enviarEmail(
                "Relatórios gerados",
                "admin@email.com",
                """
                        Olá!
                        
                        Seus relatórios foram gerados e seguem abaixo!
                        
                        %s
                        
                        %s
                        """.formatted(estoque, faturamento)
        );

    }
}