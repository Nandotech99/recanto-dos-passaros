package com.example.RecantoDosPassaros.Pck_Main.Pck_Procedure;

import com.example.RecantoDosPassaros.Pck_Main.Pck_DAO.DAO;
import com.example.RecantoDosPassaros.Pck_Main.Pck_Model.FechamentoCaixa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FechamentoCaixaProcedure {

    private final DAO oConectar = new DAO();
    private static final String login = "nando";
    private static final String senha = "12";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall callExibirFechamento;

    public FechamentoCaixaProcedure(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Mapeia a procedure exibir_fechamento_caixa do MySQL
        this.callExibirFechamento = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("exibir_fechamento_caixa")
                .returningResultSet("fechamentos", new RowMapper<FechamentoCaixa>() {
                    @Override
                    public FechamentoCaixa mapRow(ResultSet rs, int rowNum) throws SQLException {
                        FechamentoCaixa fechamento = new FechamentoCaixa();
                        fechamento.setId(rs.getInt("CODIGO"));
                        fechamento.setData(rs.getString("DATA"));
                        fechamento.setEntrada(rs.getDouble("ENTRADA"));
                        fechamento.setStatus(rs.getString("STATUS"));
                        return fechamento;
                    }
                });
    }

    // Método get() usando JdbcTemplate direto na procedure (para o Service)
    public List<FechamentoCaixa> get() {
        String sql = "CALL exibir_fechamento_caixa(?)";

        return jdbcTemplate.query(sql, new RowMapper<FechamentoCaixa>() {
            @Override
            public FechamentoCaixa mapRow(ResultSet rs, int rowNum) throws SQLException {
                FechamentoCaixa fechamento = new FechamentoCaixa();
                fechamento.setId(rs.getInt("CODIGO"));
                fechamento.setData(rs.getString("DATA"));
                fechamento.setEntrada(rs.getDouble("ENTRADA"));
                fechamento.setStatus(rs.getString("STATUS"));
                return fechamento;
            }
        }, new Object[]{null});
    }

    // Método alternativo de consulta via DAO / CallableStatement
    public ArrayList<FechamentoCaixa> get2() {
        ArrayList<FechamentoCaixa> lista = new ArrayList<>();
        CallableStatement oCall = null;
        ResultSet rs = null;

        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL exibir_fechamento_caixa(?)}");
            oCall.setNull(1, Types.INTEGER);
            rs = oCall.executeQuery();

            while (rs.next()) {
                FechamentoCaixa fechamento = new FechamentoCaixa();
                fechamento.setId(rs.getInt("CODIGO"));
                fechamento.setData(rs.getString("DATA"));
                fechamento.setEntrada(rs.getDouble("ENTRADA"));
                fechamento.setStatus(rs.getString("STATUS"));
                lista.add(fechamento);
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

    // Método para registrar um novo fechamento de caixa
    public void post(FechamentoCaixa fechamento) {
        CallableStatement oCall = null;
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL registrar_fechamento_caixa(?,?,?)}");

            oCall.setDate(1, Date.valueOf(fechamento.getData())); // Espera formato yyyy-MM-dd
            oCall.setDouble(2, fechamento.getEntrada());
            oCall.setString(3, fechamento.getStatus());

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
    // Método para disparar a procedure de fechamento de caixa do dia
    public void realizarFechamento() {
        CallableStatement oCall = null;
        try {
            oCall = oConectar.getConexao(login, senha).prepareCall("{CALL realizar_fechamento_caixa()}");
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
}