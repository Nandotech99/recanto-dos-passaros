package com.example.Recando_dos_Passaros.Controller;

import com.example.Recando_dos_Passaros.Dto.ItemDto;
import com.example.Recando_dos_Passaros.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Model.Venda;
import com.example.Recando_dos_Passaros.Service.ItemService;
import com.example.Recando_dos_Passaros.Service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/recanto/venda")
public class VendaController {
    private  final VendaService vendaService;
    private final ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Venda> getVenda(){

       return vendaService.getVenda();
    }

    @PostMapping
    public int abrirVenda(){
       return vendaService.postVenda();
    }

    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody ItemDto itemDto){
            Item item = new Item();

            item.setCod_produtoFK(itemDto.codProd());
            item.setCod_vendaFK(itemDto.codVenda());
            item.setQuantidadeFK(itemDto.qtd());

            vendaService.postItem(item);

    }

    @GetMapping("/itens")
    public List<Item> getItem(@RequestBody ItemDto itemDto){
        return   itemService.getItem(itemDto.codVenda());
    }

}
