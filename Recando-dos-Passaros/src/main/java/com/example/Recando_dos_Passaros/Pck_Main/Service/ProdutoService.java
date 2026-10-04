package com.example.Recando_dos_Passaros.Pck_Main.Service;

import com.example.Recando_dos_Passaros.Pck_Main.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Pck_Main.Pck_Procedure.ProdutoProcedure;
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

    public void deleteProd(int id){
        produtoProcedure.delete(id);
    }

    public void putProd(int id,Produto produto){
         produtoProcedure.Uptade(id ,produto);
    }
    public void putProdPreco(int id, Produto produto){
        produtoProcedure.AtualizarPreco(id,produto);
    }

}


