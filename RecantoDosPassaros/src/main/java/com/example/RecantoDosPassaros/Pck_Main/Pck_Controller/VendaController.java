package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DTO.ItemDto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.ProdutoService;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/recanto/pdv")
public class VendaController {

    private final VendaService vendaService;
    private final ProdutoService produtoService;

    // Exibe a tela de PDV
    @GetMapping
    public String getVendas(Model model) {
        List<Venda> vendas = vendaService.getVenda();
        List<Produto> produtos = produtoService.getProdList();

        model.addAttribute("vendas", vendas != null ? vendas : new ArrayList<>());
        model.addAttribute("produtos", produtos != null ? produtos : new ArrayList<>());
        return "pdv";
    }

    // Recebe a finalização da venda diretamente do carrinho no PDV via POST (/recanto/pdv)
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> finalizarVendaPdv(@RequestBody List<ItemDto> itensCarrinho) {
        if (itensCarrinho == null || itensCarrinho.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O carrinho está vazio."));
        }

        try {
            // 1. Abre a venda no banco e obtém o ID gerado
            int idVendaGerado = vendaService.postVenda();

            // 2. Adiciona cada item do carrinho na venda
            for (ItemDto dto : itensCarrinho) {
                if (dto.getQuantidade() <= 0) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("erro", "Quantidade inválida para o produto " + dto.getCodProduto()));
                }

                Item item = new Item();
                item.setCod_vendaFK(idVendaGerado);
                item.setCod_produtoFK(dto.getCodProduto());
                item.setQuantidadeFK(dto.getQuantidade());

                vendaService.postItemVenda(item);
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensagem", "Venda finalizada com sucesso!", "idVenda", idVendaGerado));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao finalizar a venda: " + e.getMessage()));
        }
    }

    // Abre uma nova venda (caso queira chamar via botão isolado)
    @PostMapping("/abrir")
    @ResponseBody
    public ResponseEntity<Integer> abrirVenda() {
        int idVenda = vendaService.postVenda();
        return ResponseEntity.status(HttpStatus.CREATED).body(idVenda);
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
    @GetMapping("/verificar-vendas")
    public String verificarVendas(Model model) {
        List<Venda> vendas = vendaService.getVenda();
        if (vendas == null) {
            vendas = new ArrayList<>();
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
    }

    // Endpoint para retornar os itens de uma venda em formato JSON para o modal
    @GetMapping("/{id}/itens")
    @ResponseBody
    public ResponseEntity<List<ItemDto>> listarItensDaVenda(@PathVariable("id") int idVenda) {
        try {
            List<ItemDto> itens = vendaService.getItensPorVenda(idVenda);
            return ResponseEntity.ok(itens != null ? itens : new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    // Endpoint para alterar o status da venda (PAGO / PENDENTE)
    @PostMapping("/{id}/status")
    public String alterarStatusVenda(
            @PathVariable("id") int idVenda,
            @RequestParam("status") String novoStatus
    ) {
        try {
            vendaService.alterarStatusVenda(idVenda, novoStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/recanto/pdv/verificar";
    }
}