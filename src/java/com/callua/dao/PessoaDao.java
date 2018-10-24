package com.callua.dao;

import com.callua.bean.Cliente;
import com.callua.bean.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaDao {
	public Pessoa validaLogin(String cpfCnpj, String senha) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa pessoa = null;
        
        try {
            connection.setAutoCommit(false);
            
            //VERIFICA SE É PESSOA
            stmt = connection.prepareStatement("SELECT * FROM Pessoa "
                                                + "WHERE cpfCnpj = ? AND senha = ?");
            stmt.setString(1, cpfCnpj);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                pessoa = new Pessoa();
	  	pessoa.setId(rs.getInt(("id")));
	  	pessoa.setNome(rs.getString(("nome")));
	  	pessoa.setCpfCnpj(rs.getString(("cpfCnpj")));
	  	pessoa.setTelefoneCelular(rs.getString(("telefoneCelular")));
	  	pessoa.setEmail(rs.getString(("email")));
                
                //VERIFICA SE É CLIENTE
                stmt = connection.prepareStatement("SELECT * FROM Pessoa p "
                                                    + "JOIN Cliente c ON c.idPessoa = p.id "
                                                    + "WHERE p.id = ?");
                stmt.setInt(1, pessoa.getId());
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    pessoa.setCliente(true);
                } else {
                    pessoa.setCliente(false);
                }
                
                //VERIFICA SE É CLIENTE
                stmt = connection.prepareStatement("SELECT * FROM Pessoa p "
                                                    + "JOIN Usuario u ON u.idPessoa = p.id "
                                                    + "WHERE p.id = ?");
                stmt.setInt(1, pessoa.getId());
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    pessoa.setUsuario(true);
                } else {
                    pessoa.setUsuario(false);
                }
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
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
        
        return pessoa;
    }
}
