package com.example.Recando_dos_Passaros.Service;

import com.example.Recando_dos_Passaros.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Model.Venda;
import com.example.Recando_dos_Passaros.Pck_Procedure.VendaProcedure;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VendaService {
    private final VendaProcedure vendaProcedure;
    public int postVenda(){
        return vendaProcedure.post();
    }
    public List<Venda> getVenda(){
        return vendaProcedure.get();
    }
    public void postItem(Item item){
        vendaProcedure.postItem(item);
    }
}
