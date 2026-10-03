package com.example.Recando_dos_Passaros.Service;

import com.example.Recando_dos_Passaros.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Procedure.ItemProcedure;
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
