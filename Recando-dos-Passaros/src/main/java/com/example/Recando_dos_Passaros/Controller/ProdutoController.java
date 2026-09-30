package com.example.Recando_dos_Passaros.Controller;

import com.example.Recando_dos_Passaros.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;
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
    public void cadastrar(ProdutoDto produtoDto) {
Produto produto=new Produto();

produto.setNome(produtoDto.nome());
produto.setPreco(produtoDto.preco());
produto.setQuantidade(produtoDto.quantidade());
produto.setQuantidade_min(produtoDto.quantidadeMinima());
produto.setRegistro(produtoDto.registro());

        produtoService.produtoSave();
    }
}