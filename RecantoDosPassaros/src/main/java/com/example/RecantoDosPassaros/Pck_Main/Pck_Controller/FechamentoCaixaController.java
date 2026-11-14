package com.example.RecantoDosPassaros.Pck_Main.Pck_Controller;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.FechamentoCaixa;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Service.FechamentoCaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recanto")
public class FechamentoCaixaController {

    @Autowired
    private FechamentoCaixaService fechamentoCaixaService;

    // Rota para exibir a página de Fechamento de Caixa com os dados e KPIs
    @GetMapping("/caixa")
    public String exibirFechamentoCaixa(Model model) {
        List<FechamentoCaixa> fechamentos = fechamentoCaixaService.listarFechamentos();
        double totalEntrada = fechamentoCaixaService.calcularTotalEntrada();
        double totalPendentes = fechamentoCaixaService.calcularTotalPendentes();

        model.addAttribute("fechamentos", fechamentos);
        model.addAttribute("totalEntrada", totalEntrada);
        model.addAttribute("totalPendentes", totalPendentes);
        model.addAttribute("novoFechamento", new FechamentoCaixa());

        // Retorna o nome do arquivo HTML (ex: fechaCaixa.html ou fechamentoCaixa.html)
        return "fechaCaixa";
    }

    // Rota para salvar um novo fechamento de caixa via formulário (POST)
    // Rota para salvar um novo fechamento de caixa via formulário (POST)
    @PostMapping("/caixa/salvar")
    public String salvarFechamento(@ModelAttribute FechamentoCaixa fechamentoCaixa) {
        // Define o status padrão como pendente caso venha vazio ou nulo
        if (fechamentoCaixa.getStatus() == null || fechamentoCaixa.getStatus().isEmpty()) {
            fechamentoCaixa.setStatus("PENDENTE");
        }

        fechamentoCaixaService.salvarFechamento(fechamentoCaixa);

        // Redireciona de volta para a página de caixa após salvar
        return "redirect:/recanto/caixa";
    }
    // Rota para executar o fechamento de caixa ao clicar no botão
    @PostMapping("/caixa/fechar-dia")
    public String fecharCaixaDiario() {
        try {
            fechamentoCaixaService.fecharCaixaDiario();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/recanto/caixa";
    }
}