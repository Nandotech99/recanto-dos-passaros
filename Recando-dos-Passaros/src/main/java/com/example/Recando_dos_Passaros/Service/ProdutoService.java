package com.example.Recando_dos_Passaros.Service;

import com.example.Recando_dos_Passaros.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Pck_Procedure.ProdutoProcedure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

private ProdutoProcedure produtoProcedure;

    public ProdutoService(ProdutoProcedure produtoProcedure) {
        this.produtoProcedure = produtoProcedure;
    }
    public void produtoSave(Produto produto){
        produtoProcedure.inserir( produto);
    }

    public List<Produto> getProdList(){
        return produtoProcedure.consulta();
    }

    public void deleteProd( Produto produto){
        produtoProcedure.delete(produto);
    }

    public void putProd(Produto produto){
         produtoProcedure.Uptade(produto);
    }

}


