package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class ItemProcedure {
    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

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
