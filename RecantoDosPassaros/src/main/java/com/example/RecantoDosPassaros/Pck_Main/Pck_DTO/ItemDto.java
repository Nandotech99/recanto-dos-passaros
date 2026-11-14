package com.example.RecantoDosPassaros.Pck_Main.Pck_DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Integer codProduto; // Mudou de int para Integer
    private Integer quantidade; // Mudou de int para Integer
    private Double precoUnitario;  // Mudou de double para Double
    private String nomeProduto;
}