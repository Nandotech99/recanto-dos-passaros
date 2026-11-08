package com.example.RecantoDosPassaros.Pck_Main.Pck_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Venda {
    // TABELA - VENDA
    private int cod_venda;
    private double valor_total;
    private String data_venda;
    private String status; // Adicionado para suportar a exibição de Vendas Pagas/Pendentes
}