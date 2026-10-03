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
    private Integer cod_item;
    private Integer cod_vendaFK;
    private Integer cod_produtoFK;
    private String produtoFK;
    private double precoFK;
    private Integer quantidadeFK;

}