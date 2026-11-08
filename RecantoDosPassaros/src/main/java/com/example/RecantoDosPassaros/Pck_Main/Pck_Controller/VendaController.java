package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DTO.ItemDto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.ProdutoService;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/recanto/pdv")
public class VendaController {

    private final VendaService vendaService;
    private final ProdutoService produtoService;

    // Exibe a tela de PDV
    @GetMapping
    public String getVendas(Model model) {
        try {
            List<Venda> vendas = vendaService.getVenda();
            List<Produto> produtos = produtoService.getProdList();

            model.addAttribute("vendas", vendas);
            model.addAttribute("produtos", produtos);
            return "pdv";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Recebe a finalização da venda diretamente do carrinho no PDV via POST (/recanto/pdv)
    @PostMapping
    public String finalizarVendaPdv(@RequestBody List<ItemDto> itensCarrinho) {
        try {
            if (itensCarrinho == null || itensCarrinho.isEmpty()) {
                return "redirect:/recanto/pdv";
            }

            // 1. Abre a venda no banco e obtém o ID gerado (via procedure abrir_venda)
            int idVendaGerado = vendaService.postVenda();

            // 2. Adiciona cada item do carrinho na venda (dispara add_item_venda e baixa o estoque)
            for (ItemDto dto : itensCarrinho) {
                Item item = new Item();
                item.setCod_vendaFK(idVendaGerado);
                item.setCod_produtoFK(dto.getCodProduto());
                item.setQuantidadeFK(dto.getQuantidade());

                vendaService.postItemVenda(item);
            }

            return "redirect:/recanto/pdv";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Abre uma nova venda (caso queira chamar via botão isolado)
    @PostMapping("/abrir")
    @ResponseBody
    public int abrirVenda() {
        return vendaService.postVenda();
    }

    // Adiciona um item de forma isolada se necessário
    @PostMapping("/item")
    public String adicionarItem(
            @RequestParam("codVenda") int codVenda,
            @RequestParam("codProduto") int codProduto,
            @RequestParam("quantidade") int quantidade
    ) {
        Item item = new Item();
        item.setCod_vendaFK(codVenda);
        item.setCod_produtoFK(codProduto);
        item.setQuantidadeFK(quantidade);

        vendaService.postItemVenda(item);

        return "redirect:/recanto/pdv";
    }

    // Exibe a tela de listagem e verificação de vendas
    @GetMapping("/verificar")
    public String verificarVendas(Model model) {
        try {
            List<Venda> vendas = vendaService.getVenda();

            // Proteção caso venha nulo do banco
            if (vendas == null) {
                vendas = new java.util.ArrayList<>();
            }

            double totalPagas = vendas.stream()
                    .filter(v -> "PAGO".equalsIgnoreCase(v.getStatus()))
                    .mapToDouble(Venda::getValor_total)
                    .sum();

            double totalPendentes = vendas.stream()
                    .filter(v -> "PENDENTE".equalsIgnoreCase(v.getStatus()))
                    .mapToDouble(Venda::getValor_total)
                    .sum();

            model.addAttribute("vendas", vendas);
            model.addAttribute("totalVendasPagas", totalPagas);
            model.addAttribute("totalVendasPendentes", totalPendentes);

            return "verificar-vendas";
        } catch (Exception e) {
            System.err.println("ERRO DETALHADO EM /verificar: " + e.getMessage());
            e.printStackTrace(); // Imprime o erro completo no console
            throw e;
        }
    }
}