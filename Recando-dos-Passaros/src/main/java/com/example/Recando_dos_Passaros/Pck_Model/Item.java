package com.example.Recando_dos_Passaros.Pck_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Item {
    //TABELA - ITEM
    private int cod_item;
    private int cod_vendaFK;
    private int cod_produtoFK;
    private String produtoFK;
    private double precoFK;
    private int quantidadeFK;

}