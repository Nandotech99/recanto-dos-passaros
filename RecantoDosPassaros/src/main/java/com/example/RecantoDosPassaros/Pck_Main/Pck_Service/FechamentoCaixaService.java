package com.example.RecantoDosPassaros.Pck_Main.Pck_Service;

import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.FechamentoCaixa;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure.FechamentoCaixaProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FechamentoCaixaService {

    @Autowired
    private FechamentoCaixaProcedure fechamentoCaixaProcedure;

    // Retorna a lista de todos os fechamentos de caixa cadastrados
    public List<FechamentoCaixa> listarFechamentos() {
        return fechamentoCaixaProcedure.get();
    }

    // Registra um novo fechamento de caixa chamando a procedure
    public void salvarFechamento(FechamentoCaixa fechamento) {
        fechamentoCaixaProcedure.post(fechamento);
    }

    // Calcula o valor total das entradas com status 'FECHADO' (para os cards de KPI)
    public double calcularTotalEntrada() {
        List<FechamentoCaixa> lista = fechamentoCaixaProcedure.get();
        return lista.stream()
                .filter(f -> "FECHADO".equalsIgnoreCase(f.getStatus()))
                .mapToDouble(FechamentoCaixa::getEntrada)
                .sum();
    }

    // Calcula o valor total das entradas com status 'PENDENTE' (para os cards de KPI)
    public double calcularTotalPendentes() {
        List<FechamentoCaixa> lista = fechamentoCaixaProcedure.get();
        return lista.stream()
                .filter(f -> "PENDENTE".equalsIgnoreCase(f.getStatus()))
                .mapToDouble(FechamentoCaixa::getEntrada)
                .sum();
    }
    // Realiza o fechamento de caixa do dia atual
    public void fecharCaixaDiario() {
        fechamentoCaixaProcedure.realizarFechamento();
    }
}