package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoProcedure {

    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

    public void inserir(Produto model){
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




    public ArrayList<Produto> consulta() {
        ArrayList<Produto> lista = new ArrayList<>(); // Lista para guardar os produtos encontrados
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL exibir_produto()");
                ResultSet rs = oCall.executeQuery();
                while (rs.next()) {
                    Produto produto = new Produto();
                    // Pega os dados das colunas do banco e coloca no objeto Model
                    produto.setCodigo(rs.getInt("CODIGO"));
                    produto.setNome(rs.getString("PRODUTO"));
                    produto.setQuantidade(rs.getInt("QUANTIDADE"));
                    produto.setQuantidade_min(rs.getInt("QTD_MINIMA"));
                    produto.setPreco((rs.getDouble("PRECO")));
                    produto.setData(rs.getString("DATA"));
                    produto.setRegistro(rs.getString("DESCRICAO"));

                    lista.add(produto); // Adiciona o produto na lista
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }

    public void Uptade(Produto produto) {
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_quantidade(?,?,?,?)");
                oCall.setInt(1, produto.getCodigo());
                oCall.setInt(2, produto.getQuantidade());
                oCall.setString(3, produto.getData());
                oCall.setString(4, produto.getRegistro());

                oCall.execute();
                System.out.println("Estoque do codigo:" + produto.getCodigo() + " atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }

    public void delete(Produto produto) {
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL del_produto(?)");
                oCall.setInt(1,produto.getCodigo());
                oCall.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }
    public void AtualizarPreco(Produto produto) {
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_preco(?,?,?,?)");
                oCall.setInt(1, produto.getCodigo());
                oCall.setDouble(2, produto.getPreco());
                oCall.setString(3, produto.getData());
                oCall.setString(4, produto.getRegistro());

                oCall.execute();
                System.out.println("Preço do codigo:" + produto.getCodigo() + " atualizado com sucesso!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }
}
