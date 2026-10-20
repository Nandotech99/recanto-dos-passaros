package com.example.RecantoDosPassaros.Pck_Main.Service;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure.ItemProcedure;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemService {
    private final ItemProcedure itemProcedure;

    public void putItem(int codVenda,int codProd, int qtd){
        itemProcedure.post(codVenda,codProd,qtd);
    }

    public List<Item> getItem(int codVenta) {
        return itemProcedure.get(codVenta);

    }
}
