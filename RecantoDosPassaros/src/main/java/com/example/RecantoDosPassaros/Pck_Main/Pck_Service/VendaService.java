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
    public int postVenda(){
        return vendaProcedure.post();
    }
    public ArrayList<Venda> get(){
        return vendaProcedure.get();
    }
}
