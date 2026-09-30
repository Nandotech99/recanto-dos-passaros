package com.example.Recando_dos_Passaros.Controller;
import com.example.Recando_dos_Passaros.Pck_Model.Model;
import com.example.Recando_dos_Passaros.Pck_Procedure.Procedure;

    /*
    O CONTROLER RECEBE O PEDIDO DA VIEW
    E ORGANIZA COM OS DADOS COM SUA DETERMINADAS
    TIPAGENS (FEITAS NO MODEL) OS SET'S
    DEPOIS CHAMA A PROCEDURE PARA EXECUTAR DETERMINADA
    AÇÃO
    */

public class Controler {
    Model model = new Model();
    Procedure procedure=new Procedure();

    public void Inserir(int codigo,String nome,int quantidade,int quantidadeMin,double preco,String data,String registro){
        model.setCodigo(codigo);
        model.setNome(nome);
        model.setQuantidade(quantidade);
        model.setQuantidade_min(quantidadeMin);
        model.setPreco(preco);
        model.setData(data);
        model.setRegistro(registro);

        procedure.Inserir(model);
    }

    public void Update (int codigo,int quantidade,String data,String registro){
        model.setCodigo(codigo);
        model.setQuantidade(quantidade);
        model.setData(data);
        model.setRegistro(registro);

        procedure.Uptade(model);
    }
    public void Delete (int codigo) {
        model.setCodigo(codigo);
        procedure.Delete(model);
    }

    public  void RegistrarVenda(int codigo,int quantidade){
        model.setCod_item(codigo);
        model.setQuantidadeFK(quantidade);

        procedure.RegistrarVenda(model);
    }

    public int abrirNovaVenda() {
        return procedure.iniciarVenda();
    }

    public void adicionarItemVenda(int codVenda, int codProd, int qtd) {
        procedure.adicionarItem(codVenda, codProd, qtd);
    }

    public void AtualizarPreco(int codigo, double preco, String data, String registro) {
        model.setCodigo(codigo);
        model.setPreco(preco);
        model.setData(data);
        model.setRegistro(registro);

        procedure.AtualizarPreco(model);
    }

}
