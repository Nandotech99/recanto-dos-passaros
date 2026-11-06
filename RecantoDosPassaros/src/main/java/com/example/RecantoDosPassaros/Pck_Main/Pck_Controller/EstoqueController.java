package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/recanto/estoque")
public class EstoqueController {

    private final ProdutoService produtoService;

    @GetMapping
    public String getEstoque(Model model) {
        List<Produto> produtos = produtoService.getProdList();

        double valorTotalEstoque = 0.0;

        for (Produto p : produtos) {
            valorTotalEstoque += (p.getPreco() * p.getQuantidade());
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("valorTotalEstoque", valorTotalEstoque);

        return "estoque"; // Renderiza a página estoque.html
    }

    @PostMapping("/atualizar/{id}")
    public String putEstoqueQuantidade(
            @PathVariable("id") int id,
            @RequestParam("quantidade") int quantidade
    ) {
        Produto produto = new Produto();
        produto.setQuantidade(quantidade);

        produtoService.putProd(id, produto);

        return "redirect:/recanto/estoque";
    }

    @PostMapping("/deletar")
    public String deleteEstoqueItem(@RequestParam("id") int id) {
        produtoService.deleteProd(id);
        return "redirect:/recanto/estoque";
    }
}