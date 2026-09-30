package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Model.Model;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class ProdutoProcedure {

    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

    public void Inserir(Model model){
        try {
            try {
                oCall=oConectar.getConexao(login,senha).prepareCall("CAll ins_produto(?,?,?,?,?,?,?)");
                oCall.setInt(1,model.getCodigo());
                oCall.setString(2,model.getNome());
                oCall.setInt(3,model.getQuantidade());
                oCall.setInt(4,model.getQuantidade_min());
                oCall.setDouble(5,model.getPreco());
                oCall.setString(6,model.getData());
                oCall.setString(7, model.getRegistro());

                oCall.execute();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }

    public ArrayList<Model> Venda(){
        ArrayList<Model> lista = new ArrayList<>();
        try {
            try {
                oCall=oConectar.getConexao(login,senha).prepareCall("CALL exibir_venda(?)");
                oCall.setNull(1, Types.INTEGER);
                ResultSet rs= oCall.executeQuery();
                while (rs.next()){
                    Model model = new Model();
                    model.setCod_venda(rs.getInt("CODIGO"));
                    model.setValor_total(rs.getDouble("VALOR_TOTAL"));
                    lista.add(model);
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }

    public ArrayList<Model> Item(int codVenda){
        ArrayList<Model> lista = new ArrayList<>();
        try {
            try {
                oCall = oConectar.getConexao(login,senha).prepareCall("CALL exibir_item(?)");
                oCall.setInt(1,codVenda);

                ResultSet rs=oCall.executeQuery();
                while (rs.next()){
                    Model model= new Model();

                    model.setCod_item(rs.getInt("ITEM"));
                    model.setCod_produtoFK(rs.getInt("COD_PROD"));
                    model.setProdutoFK(rs.getString("PRODUTO"));
                    model.setPrecoFK(rs.getDouble("PRECO"));
                    model.setQuantidadeFK(rs.getInt("QUANTIDADE"));
                    lista.add(model);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }

    public ArrayList<Model> Consulta() {
        ArrayList<Model> lista = new ArrayList<>(); // Lista para guardar os produtos encontrados
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL exibir_produto()");
                ResultSet rs = oCall.executeQuery();
                while (rs.next()) {
                    Model model = new Model();
                    // Pega os dados das colunas do banco e coloca no objeto Model
                    model.setCodigo(rs.getInt("CODIGO"));
                    model.setNome(rs.getString("PRODUTO"));
                    model.setQuantidade(rs.getInt("QUANTIDADE"));
                    model.setQuantidade_min(rs.getInt("QTD_MINIMA"));
                    model.setPreco((rs.getDouble("PRECO")));
                    model.setData(rs.getString("DATA"));
                    model.setRegistro(rs.getString("DESCRICAO"));

                    lista.add(model); // Adiciona o produto na lista
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }

    public void Uptade(Model model) {
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_quantidade(?,?,?,?)");
                oCall.setInt(1, model.getCodigo());
                oCall.setInt(2, model.getQuantidade());
                oCall.setString(3, model.getData());
                oCall.setString(4, model.getRegistro());

                oCall.execute();
                System.out.println("Estoque do codigo:" + model.getCodigo() + " atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }

    public void AtualizarPreco(Model model) {
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_preco(?,?,?,?)");
                oCall.setInt(1, model.getCodigo());
                oCall.setDouble(2, model.getPreco());
                oCall.setString(3, model.getData());
                oCall.setString(4, model.getRegistro());

                oCall.execute();
                System.out.println("Preço do codigo:" + model.getCodigo() + " atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }
}
