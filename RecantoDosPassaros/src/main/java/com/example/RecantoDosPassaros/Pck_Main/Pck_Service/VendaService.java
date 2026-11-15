package com.example.RecantoDosPassaros.Pck_Main.Pck_Service;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DTO.ItemDto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure.VendaProcedure;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VendaService {
    private final VendaProcedure vendaProcedure;
    private final ProdutoService produtoService; // Injetado para gerenciar o estoque

    public int postVenda() {
        return vendaProcedure.post();
    }

    public void postItemVenda(Item item) {
        // 1. Adiciona o item na venda via procedure do banco
        vendaProcedure.postItem(item);

        // 2. Realiza a baixa de estoque buscando o produto atual e subtraindo a quantidade vendida
        Produto produto = produtoService.getProdById(item.getCod_produtoFK());
        if (produto != null) {
            int novaQuantidade = produto.getQuantidade() - item.getQuantidadeFK();
            if (novaQuantidade < 0) {
                novaQuantidade = 0; // Previne estoque negativo
            }

            // Atualiza o estoque usando a procedure upt_quantidade do ProdutoService
            Produto produtoAtualizado = new Produto();
            produtoAtualizado.setQuantidade(novaQuantidade);
            produtoService.putProd(item.getCod_produtoFK(), produtoAtualizado);
        }
    }

    public List<Venda> getVenda() {
        return vendaProcedure.get();
    }

    public List<ItemDto> getItensPorVenda(int codVenda) {
        return vendaProcedure.getItensPorVenda(codVenda);
    }

    public void alterarStatusVenda(int codVenda, String novoStatus) {
        vendaProcedure.atualizarStatus(codVenda, novoStatus);
    }
}