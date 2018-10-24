package com.callua.dao;

import com.callua.bean.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {
    public Usuario carregarUm(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
                
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpfCnpj(rs.getString("cpfCnpj"));
                usuario.setTelefoneCelular(rs.getString("telefoneCelular"));
                usuario.setEmail(rs.getString("email"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (rs != null)
                try { rs.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar rs. Ex="+exception.getMessage()); }
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
        
        return usuario;
    }
}
