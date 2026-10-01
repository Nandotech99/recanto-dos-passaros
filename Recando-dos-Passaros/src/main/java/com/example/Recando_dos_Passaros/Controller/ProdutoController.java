package com.example.Recando_dos_Passaros.Controller;

import com.example.Recando_dos_Passaros.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/recanto/produtos")
public class ProdutoController {
    private ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postProd(ProdutoDto produtoDto) {

        Produto produto=new Produto();

        produto.setCodigo(produtoDto.id());
        produto.setNome(produtoDto.nome());
        produto.setPreco(produtoDto.preco());
        produto.setQuantidade(produtoDto.quantidade());
        produto.setQuantidade_min(produtoDto.quantidadeMinima());
        produto.setData(produtoDto.data());
        produto.setRegistro(produtoDto.registro());

        produtoService.produtoSave(produto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getProds(){
        return produtoService.getProdList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProd(@PathVariable int id,ProdutoDto produtoDto){

        Produto produto=new Produto();
        produto.setCodigo(produtoDto.id());

        produtoService.deleteProd(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void putProdEstoque(@PathVariable int id, ProdutoDto produtoDto){
        Produto produto=new Produto();

        produto.setCodigo(produtoDto.id());
        produto.setQuantidade(produtoDto.quantidade());
        produto.setData(produtoDto.data());
        produto.setRegistro(produtoDto.registro());

        produtoService.putProd(produto);

    }
}