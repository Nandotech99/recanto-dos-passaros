package com.example.RecantoDosPassaros.Pck_Main.Dto;


public record ProdutoDto(
                         String nome,
                         int quantidade,
                         int quantidadeMinima,
                         double preco,
                         String data,
                         String registro) {
}
