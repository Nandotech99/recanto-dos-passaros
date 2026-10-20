package com.example.RecantoDosPassaros.Pck_Main.Pck_DTO;


public record ProdutoDto(
                         String nome,
                         int quantidade,
                         int quantidadeMinima,
                         double preco,
                         String data,
                         String registro) {
}
