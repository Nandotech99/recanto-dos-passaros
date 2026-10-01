package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Service.ItemService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemProcedure {
    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

    public ArrayList<Item> Item(int codVenda){
        ArrayList<Item> lista = new ArrayList<>();
        try {
            try {
                oCall = oConectar.getConexao(login,senha).prepareCall("CALL exibir_item(?)");
                oCall.setInt(1,codVenda);

                ResultSet rs=oCall.executeQuery();
                while (rs.next()){
                    Item item= new Item();

                    item.setCod_item(rs.getInt("ITEM"));
                    item.setCod_produtoFK(rs.getInt("COD_PROD"));
                    item.setProdutoFK(rs.getString("PRODUTO"));
                    item.setPrecoFK(rs.getDouble("PRECO"));
                    item.setQuantidadeFK(rs.getInt("QUANTIDADE"));
                    lista.add(item);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }
    public void adicionarItem(int codVenda, int codProd, int qtd) {
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("CALL add_item_venda(?,?,?)");
            oCall.setInt(1, codVenda);
            oCall.setInt(2, codProd);
            oCall.setInt(3, qtd);
            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
    }
}
