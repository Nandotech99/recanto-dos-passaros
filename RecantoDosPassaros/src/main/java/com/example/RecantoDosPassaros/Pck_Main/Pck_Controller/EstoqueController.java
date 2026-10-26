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
@RequestMapping("recanto/estoque") 
public class EstoqueController {

    private ProdutoService produtoService;

    @GetMapping
    public String getEstoque(Model model) {
        List<Produto> produtos = produtoService.getProdList();

        int totalItensEstoque = 0;
        int totalCriticos = 0;
        double valorTotalEstoque = 0;

        for (Produto p : produtos) {
            totalItensEstoque += p.getQuantidade();
            valorTotalEstoque += (p.getPreco() * p.getQuantidade());
            
            if (p.getQuantidade() <= p.getQuantidade_min()) {
                totalCriticos++;
            }
        }

        model.addAttribute("estoqueList", produtos);
        model.addAttribute("produtosDisponiveis", produtos);
        model.addAttribute("totalItensEstoque", totalItensEstoque);
        model.addAttribute("totalCriticos", totalCriticos);
        model.addAttribute("valorTotalEstoque", valorTotalEstoque);

        return "estoque";
    }

    @PostMapping("/salvar")
    public String salvarMovimento(
            @RequestParam("codigoProduto") int codigoProduto,
            @RequestParam("tipo") String tipo,
            @RequestParam("quantidade") int quantidade,
            @RequestParam("observacao") String observacao) {

        Produto produto = produtoService.getProdById(codigoProduto);

        if (produto != null) {
            if ("ENTRADA".equalsIgnoreCase(tipo)) {
                produto.setQuantidade(produto.getQuantidade() + quantidade);
            } else if ("SAIDA".equalsIgnoreCase(tipo)) {
                // Garante que o estoque não fique negativo
                int novaQuantidade = Math.max(0, produto.getQuantidade() - quantidade);
                produto.setQuantidade(novaQuantidade);
            }

            produtoService.putProd(codigoProduto, produto);
        }

        return "redirect:/estoque";
    }
}
