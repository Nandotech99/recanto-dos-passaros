package com.example.RecantoDosPassaros.Pck_Main.Pck_Service;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CaixaService {

    private final VendaService vendaService;

    public double getSaldoInicial() {
        // Valor padrão de abertura de caixa (troco inicial)
        return 150.00;
    }

    public double getTotalEntradas() {
        List<Venda> vendas = vendaService.getVenda();
        double total = 0.0;
        if (vendas != null) {
            for (Venda v : vendas) {
                total += v.getValor_total(); // Utiliza o atributo real da classe Venda
            }
        }
        return total;
    }

    public double getTotalSaidas() {
        // Retorna o somatório de sangrias/saídas do dia (pode ser ajustado conforme sua regra de negócio)
        return 50.00;
    }

    public double getSaldoAtual() {
        return getSaldoInicial() + getTotalEntradas() - getTotalSaidas();
    }

    public void fecharCaixa(double dinheiroContado, double digitalContado, String observacoes) {
        // Lógica para registrar o fechamento de caixa
    }
}
