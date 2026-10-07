package com.example.Recando_dos_Passaros.Pck_Main.Dto;


public record ProdutoDto(
                         String nome,
                         int quantidade,
                         int quantidadeMinima,
                         double preco,
                         String data,
                         String registro) {
}
