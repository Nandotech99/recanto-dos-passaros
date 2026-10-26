package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.CaixaService;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.VendaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/recanto/caixa")
public class FechamentoCaixaController {

    private final CaixaService caixaService;
    private final VendaService vendaService;

    @GetMapping
    public String getFechamentoCaixa(Model model) {
        model.addAttribute("saldoInicial", caixaService.getSaldoInicial());
        model.addAttribute("totalEntradas", caixaService.getTotalEntradas());
        model.addAttribute("totalSaidas", caixaService.getTotalSaidas());
        model.addAttribute("saldoAtual", caixaService.getSaldoAtual());
        
        // Passa a lista de vendas/movimentações para a tabela do HTML
        model.addAttribute("movimentacoes", vendaService.getVenda());
        
        return "fechamento-caixa"; // Nome do arquivo HTML correspondente
    }

    @PostMapping("/fechar")
    public String fecharCaixa(
            @RequestParam("dinheiroContado") double dinheiroContado,
            @RequestParam("digitalContado") double digitalContado,
            @RequestParam(value = "observacoes", required = false) String observacoes) {

        caixaService.fecharCaixa(dinheiroContado, digitalContado, observacoes);
        
        return "redirect:/recanto/caixa";
    }
}
