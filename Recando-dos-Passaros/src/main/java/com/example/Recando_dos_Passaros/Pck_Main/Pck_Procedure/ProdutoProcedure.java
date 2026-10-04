package com.example.Recando_dos_Passaros.Pck_Main.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_Main.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Main.Pck_Model.Produto;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
@Repository
public class ProdutoProcedure {

    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="nando";
    private static final String senha ="12";

    public void inserir(Produto produto){
        try {
            try {
                oCall=oConectar.getConexao(login,senha).prepareCall("CAll ins_produto(?,?,?,?,?,?)");
                oCall.setString(1,produto.getNome());
                oCall.setInt(2,produto.getQuantidade());
                oCall.setInt(3,produto.getQuantidade_min());
                oCall.setDouble(4,produto.getPreco());
                oCall.setString(5,produto.getData());
                oCall.setString(6, produto.getRegistro());

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
                    produto.setQuantidade_min(rs.getInt("QUANTIDADE MINIMA"));
                    produto.setPreco((rs.getDouble("PREÇO")));
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

    public void Uptade(int id,Produto produto) {
        try {
                try {

                        oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_quantidade(?,?)");
                        oCall.setInt(1, id);
                        oCall.setInt(2, produto.getQuantidade());
                        oCall.execute();
                        System.out.println("Estoque do codigo:" + produto.getCodigo() + " atualizado com sucesso!");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } finally {
                oConectar.desconectar();
            }
    }

    public void delete(int id) {
        try {
            try {
                    oCall = oConectar.getConexao(login, senha).prepareCall("CALL del_produto(?)");
                    oCall.setInt(1, id);
                    oCall.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }

        }
    public void AtualizarPreco(int id,Produto produto) {
        try {
            try {
                    oCall = oConectar.getConexao(login, senha).prepareCall("CALL upt_preco(?,?,?,?)");
                    oCall.setInt(1, id);
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
