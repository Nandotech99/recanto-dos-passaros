package com.example.Recando_dos_Passaros.Controller;

import com.example.Recando_dos_Passaros.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Model.Produto;
import com.example.Recando_dos_Passaros.Service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/recanto/produtos")
public class ProdutoController {
    private ProdutoService produtoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProd(@RequestBody ProdutoDto produtoDto){
        return produtoService.saveProd(produtoDto);
    }
}
