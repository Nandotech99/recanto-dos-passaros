package com.example.Recando_dos_Passaros.Pck_DAO;
import java.sql.*;

public class DAO {
    private String sDriver;
    private String sServidor;
    private String connStr;
    private Connection conn;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/cafeteria_bd";// trocar a porta
    private static final String login = "nando";
    private static final String senha ="12";

    public DAO()
    {
        this.sDriver="com.mysql.cj.jdbc.Driver";
        this.sServidor="localhost";
        this.connStr="jdbc:mysql://127.0.0.1:3306/cafeteria_bd";

    }
    public Connection getConexao(String login,String senha)throws SQLException{
        conn=null;
        try {
            Class.forName(sDriver);
            conn=DriverManager.getConnection(connStr,login,senha);
            System.out.println("Conexão executada com sucesso!");
            return conn;
        }
        catch (ClassNotFoundException e){
            String erroMsg="Driver não encontrado";
            throw new SQLException(erroMsg,e);

        }catch (SQLException e){
            String erroMsg="Erro ao obter conexão!";
            throw new SQLException(erroMsg,e);
        }
    }
    public void desconectar(){
        try{
            if(conn !=null){
                conn.close();
                System.out.println("Desconectado passou aqui");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}