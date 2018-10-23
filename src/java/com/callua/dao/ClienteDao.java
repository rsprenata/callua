package com.callua.dao;

import com.callua.bean.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDao {
	public void adicionarUm(Cliente cliente) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement("INSERT INTO Pessoa (nome, cpfCnpj, telefoneCelular, email, senha) VALUES "
                    + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefoneCelular());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
	  	int idPessoa = rs.getInt(1);
			  	
	  	stmt = connection.prepareStatement("INSERT INTO Cliente (idPessoa, endereco, cep, idCidade) VALUES "
                    + "(?, ?, ?, ?)");
              	stmt.setInt(1, idPessoa);
	        stmt.setString(2, cliente.getEndereco());
	        stmt.setString(3, cliente.getCep());
	        stmt.setInt(4, cliente.getCidade().getId());
	        stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
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
    }
}
