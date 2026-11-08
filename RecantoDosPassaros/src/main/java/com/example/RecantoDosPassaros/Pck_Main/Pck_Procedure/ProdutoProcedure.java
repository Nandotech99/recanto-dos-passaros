package com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DAO.DAO;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Produto;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class ProdutoProcedure {

    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login = "nando";
    private static final String senha = "12";

    public void inserir(Produto produto) {
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL ins_produto(?,?,?,?,?)");
            oCall.setString(1, produto.getNome());
            oCall.setDouble(2, produto.getPreco());
            oCall.setString(3, produto.getCategoria());
            oCall.setInt(4, produto.getQuantidade());
            oCall.setInt(5, produto.getQuantidade_min());
            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
    }

    public ArrayList<Produto> consulta() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL exibir_produto()");
            ResultSet rs = oCall.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setCodigo(rs.getInt("CODIGO"));
                produto.setNome(rs.getString("PRODUTO"));
                produto.setPreco(rs.getDouble("PRECO"));
                produto.setCategoria(rs.getString("CATEGORIA"));
                produto.setQuantidade(rs.getInt("QUANTIDADE"));
                produto.setQuantidade_min(rs.getInt("QUANTIDADE_MIN")); // Padronizado

                lista.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }

    public Produto getById(int id) {
        Produto produto = null;
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL exibir_produto_por_id(?)");
            oCall.setInt(1, id);
            ResultSet rs = oCall.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setCodigo(rs.getInt("CODIGO"));
                produto.setNome(rs.getString("PRODUTO"));
                produto.setPreco(rs.getDouble("PRECO"));
                produto.setCategoria(rs.getString("CATEGORIA"));
                produto.setQuantidade(rs.getInt("QUANTIDADE"));
                produto.setQuantidade_min(rs.getInt("QUANTIDADE_MIN")); // Padronizado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
        return produto;
    }

    public void Uptade(int id, Produto produto) {
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_quantidade(?,?)");
            oCall.setInt(1, id);
            oCall.setInt(2, produto.getQuantidade());
            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
    }

    public void delete(int id) {
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL del_produto(?)");
            oCall.setInt(1, id);
            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
    }

    public void AtualizarPreco(int id, Produto produto) {
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_preco(?,?)");
            oCall.setInt(1, id);
            oCall.setDouble(2, produto.getPreco());
            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
    }
}