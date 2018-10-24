package com.callua.dao;

import com.callua.facade.ClienteFacade;
import com.callua.facade.UsuarioFacade;
import com.callua.util.Login;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDao {
	public Login carregarLogin(String cpfCnpj, String senha) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Login login = null;
        
        try {
            connection.setAutoCommit(false);
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes(), 0, senha.length());
            senha = new BigInteger(1, md.digest()).toString(16);
            
            //VERIFICA SE É CLIENTE
            stmt = connection.prepareStatement("SELECT * FROM Cliente "
                                                + "WHERE cpfCnpj = ? AND senha = ?");
            stmt.setString(1, cpfCnpj);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                login = new Login();
                login.setCliente(ClienteFacade.carregarUm(rs.getInt("id")));
            }
            
            //VERIFICA SE É USUÁRIO
            stmt = connection.prepareStatement("SELECT * FROM Usuario "
                                                + "WHERE cpfCnpj = ? AND senha = ?");
            stmt.setString(1, cpfCnpj);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                if (login == null)
                    login = new Login();
                login.setUsuario(UsuarioFacade.carregarUm(rs.getInt("id")));
            }

            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        
        return login;
    }
}
