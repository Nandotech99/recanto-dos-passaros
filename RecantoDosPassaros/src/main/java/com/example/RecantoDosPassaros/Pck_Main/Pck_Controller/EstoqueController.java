package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DTO.ProdutoDto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
    @Controller
    @RequestMapping("/recanto/estoque")
    public class EstoqueController {
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
    model.addAttribute("produtoDto", new ProdutoDto("", 0, 0, 0.0, "", ""));
    return "estoque";
}
    }
