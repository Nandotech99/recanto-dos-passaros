package com.example.RecantoDosPassaros.Pck_Main.Pck_Service;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure.VendaProcedure;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VendaService {
    private final VendaProcedure vendaProcedure;

    // Abre uma nova venda e retorna o ID gerado
    public int postVenda() {
        return vendaProcedure.post();
    }

    // Adiciona um item à venda (baixa o estoque e atualiza o total via procedure)
    public void postItemVenda(Item item) {
        vendaProcedure.postItem(item);
    }

    // Retorna a lista de todas as vendas cadastradas
    public List<Venda> getVenda() {
        return vendaProcedure.get();
    }

}