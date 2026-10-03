package com.example.Recando_dos_Passaros.Pck_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
        /*
        A MODEL CRIA OS SET'S E GET'S PARA MANIPULAR OS DADOS
        SE QUISERMOS DEFINI-LOS(SET) OU PEGA-LAS E USA-LOS(GET)


        SET's - Tem void, ou seja, não retorna o valor
                apenas o guarda na variavel

        GET's - Tem o tipo da variavel, porque
                retornam o valor, ou seja, eles devolvem
                o valor do set
         */
        // TABELA - PRODUTO
        private int codigo;
    private String nome;
    private int quantidade;
    private int quantidade_min;
    private double preco;
    private String data;
    private String registro;
}