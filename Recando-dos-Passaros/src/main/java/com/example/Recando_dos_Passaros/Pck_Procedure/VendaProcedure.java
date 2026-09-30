package com.example.Recando_dos_Passaros.Pck_Procedure;

import com.example.Recando_dos_Passaros.Pck_DAO.DAO;
import com.example.Recando_dos_Passaros.Pck_Model.Model;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class VendaProcedure {
    CallableStatement oCall;
    DAO oConectar = new DAO();
    private static final String login ="root";
    private static final String senha ="01";

    public void RegistrarVenda(Model model){
        try {
            try {
                oCall = oConectar.getConexao(login, senha).prepareCall("CALL ins_venda(?,?)");
                oCall.setInt(1, model.getCod_item());
                oCall.setInt(2, model.getQuantidadeFK());

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
