package com.example.RecantoDosPassaros.Pck_Main.Pck_Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FechamentoCaixa {
        /*
        A MODEL CRIA OS SET'S E GET'S PARA MANIPULAR OS DADOS
        SE QUISERMOS DEFINI-LOS(SET) OU PEGA-LAS E USA-LOS(GET)


        SET's - Tem void, ou seja, não retorna o valor
                apenas o guarda na variavel

        GET's - Tem o tipo da variavel, porque
                retornam o valor, ou seja, eles devolvem
                o valor do set
         */

    // TABELA - FECHAMENTO CAIXA
    private int id;
    private String data;
    private double entrada;
    private String status;
}