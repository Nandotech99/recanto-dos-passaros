package com.example.Recando_dos_Passaros.Service;

import com.example.Recando_dos_Passaros.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Pck_Procedure.ProdutoProcedure;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

private ProdutoProcedure produtoProcedure;

    public ProdutoService(ProdutoProcedure produtoProcedure) {
        this.produtoProcedure = produtoProcedure;
    }
    public void produtoSave(Produto produto){
        produtoProcedure.Inserir( produto);
    }
}


