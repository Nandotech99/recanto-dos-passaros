package com.example.Recando_dos_Passaros.Pck_Main.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_Main.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Main.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Main.Pck_Model.Venda;
import org.springframework.stereotype.Repository;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
@Repository
public class VendaProcedure {
    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="nando";
    private static final String senha ="12";

    public ArrayList<Venda> get(){
        ArrayList<Venda> lista = new ArrayList<>();
        try {
            try {
                oCall=oConectar.getConexao(login,senha).prepareCall("CALL exibir_venda(?)");
                oCall.setNull(1, Types.INTEGER);
                ResultSet rs= oCall.executeQuery();
                while (rs.next()){
                    Venda venda = new Venda();
                    venda.setCod_venda(rs.getInt("CODIGO"));
                    venda.setValor_total(rs.getDouble("VALOR_TOTAL"));
                    lista.add(venda);
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
        return lista;
    }
    public void postItem(Item item){
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL add_item_venda(?,?,?)");
                oCall.setInt(1,item.getCod_vendaFK());
                oCall.setInt(2, item.getCod_produtoFK());
                oCall.setInt(3, item.getQuantidadeFK());

                oCall.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }

    public int post() {
        int idGerado = -1;
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL abrir_venda(?)}");
            oCall.registerOutParameter(1, java.sql.Types.INTEGER);
            oCall.execute();
            idGerado = oCall.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oConectar.desconectar();
        }
        return idGerado;
    }
}
