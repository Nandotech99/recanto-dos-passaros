package com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DAO.DAO;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Item;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.Venda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class VendaProcedure {

    private final DAO oConectar = new DAO();
    private static final String login = "nando";
    private static final String senha = "12";

    public ArrayList<Venda> get2() {
        ArrayList<Venda> lista = new ArrayList<>();
        CallableStatement oCall = null;
        ResultSet rs = null;

        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL exibir_venda(?)}");
            oCall.setNull(1, Types.INTEGER);
            rs = oCall.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setCod_venda(rs.getInt("CODIGO"));
                venda.setValor_total(rs.getDouble("VALOR_TOTAL"));
                lista.add(venda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (oCall != null) oCall.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            oConectar.desconectar();
        }

        return lista;
    }

    public void postItem(Item item) {
        CallableStatement oCall = null;
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL add_item_venda(?,?,?)}");

            oCall.setInt(1, item.getCod_vendaFK());
            oCall.setInt(2, item.getCod_produtoFK());
            oCall.setInt(3, item.getQuantidadeFK());

            oCall.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oCall != null) oCall.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            oConectar.desconectar();
        }
    }

    public int post() {
        int idGerado = -1;
        CallableStatement oCall = null;

        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL abrir_venda(?)}");
            oCall.registerOutParameter(1, Types.INTEGER);
            oCall.execute();

            idGerado = oCall.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oCall != null) oCall.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            oConectar.desconectar();
        }

        return idGerado;
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall callExibirVenda;

    public VendaProcedure(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Mapeia a procedure exibir_venda do MySQL
        this.callExibirVenda = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("exibir_venda")
                .returningResultSet("vendas", new RowMapper<Venda>() {
                    @Override
                    public Venda mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Venda venda = new Venda();
                        venda.setCod_venda(rs.getInt("CODIGO"));
                        venda.setValor_total(rs.getDouble("VALOR_TOTAL"));
                        venda.setData_venda(rs.getString("DATA_VENDA"));
                        venda.setStatus(rs.getString("STATUS"));
                        return venda;
                    }
                });
    }
   public List<Venda> get() {
        String sql = "CALL exibir_venda(?)";

        return jdbcTemplate.query(sql, new RowMapper<Venda>() {
            @Override
            public Venda mapRow(ResultSet rs, int rowNum) throws SQLException {
                Venda venda = new Venda();
                venda.setCod_venda(rs.getInt("CODIGO"));
                venda.setValor_total(rs.getDouble("VALOR_TOTAL"));
                venda.setData_venda(rs.getString("DATA_VENDA"));
                venda.setStatus(rs.getString("STATUS"));
                return venda;
            }
        }, new Object[]{null}); // Passa null para o parâmetro p_cod_venda
    }
}
