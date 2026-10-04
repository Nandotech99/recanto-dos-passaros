package com.example.Recando_dos_Passaros.Pck_Main.Controller;

import com.example.Recando_dos_Passaros.Pck_Main.Dto.ProdutoDto;
import com.example.Recando_dos_Passaros.Pck_Main.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Pck_Main.Service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/recanto/produtos")
public class ProdutoController {
    private ProdutoService produtoService;

    @GetMapping
    public String getProds(Model model) {
        List<Produto> produtos = produtoService.getProdList();

        int totalItens = produtos.size();
        double valorTotalEstoque = 0;
        int totalProdutos = 0;

        for (Produto p : produtos) {
            valorTotalEstoque += (p.getPreco() * p.getQuantidade());
            totalProdutos++;
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("totalItens", totalItens);
        model.addAttribute("valorTotalEstoque", valorTotalEstoque);
        model.addAttribute("totalProdutos", totalProdutos);
        model.addAttribute("produtoDto", new ProdutoDto(0, "", 0, 0, 0.0, "", ""));
        return "produtos";
    }

    @PostMapping
    public String postProd(
            @RequestParam("nome") String nome,
            @RequestParam("quantidade") int quantidade,
            @RequestParam("quantidadeMinima") int quantidadeMinima,
            @RequestParam("preco") double preco,
            @RequestParam("data") String data) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidade(quantidade);
        produto.setQuantidade_min(quantidadeMinima);
        produto.setPreco(preco);
        produto.setData(data);

        produtoService.produtoSave(produto);

        return "redirect:/recanto/produtos";
    }

    @PostMapping("/deletar")
    public String deleteProd(@RequestParam("id") int id) {
        produtoService.deleteProd(id);
        return "redirect:/recanto/produtos";
    }

    @PostMapping("/atualizar/{id}")
    public String putProdEstoque(
            @PathVariable("id") int id,
            @RequestParam("quantidade") int quantidade

    ) {
        Produto produto = new Produto();
        produto.setQuantidade(quantidade);

        produtoService.putProd(id, produto);

        return "redirect:/recanto/produtos";
    }

    @PostMapping("/atualizar-preco/{id}")
    public String putProdPreco(
            @PathVariable("id") int id,
            @RequestParam("preco") double preco
    ) {
        Produto produto = new Produto();
        produto.setPreco(preco);

        produtoService.putProdPreco(id, produto);

        return "redirect:/recanto/produtos";
    }
}