package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Model.Item;
import com.example.Recando_dos_Passaros.Pck_Model.Produto;
import com.example.Recando_dos_Passaros.Pck_Model.Venda;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class VendaProcedure {
    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

    public ArrayList<Venda> Venda(){
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
    public void RegistrarVenda(Item item){
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL ins_venda(?,?)");
                oCall.setInt(1, item.getCod_item());
                oCall.setInt(2, item.getQuantidadeFK());

                oCall.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            oConectar.desconectar();
        }
    }

    public int iniciarVenda() {
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
