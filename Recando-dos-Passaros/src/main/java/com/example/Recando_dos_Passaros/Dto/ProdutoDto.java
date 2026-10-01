package com.example.Recando_dos_Passaros.Dto;

import java.time.LocalDate;

public record ProdutoDto(int id,
                         String nome,
                         int quantidade,
                         int quantidadeMinima,
                         double preco,
                         String data,
                         String registro) {
}
