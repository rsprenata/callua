package com.callua.dao;

import com.callua.bean.Chamado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChamadoDao {
    public void abrirUm(Chamado chamado) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Chamado (titulo, descricao, endereco, cep, idCidade) VALUES "
                + "(?, ?, ?, ?, ?)");
            
            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getEndereco().getEndereco());
            stmt.setString(4, chamado.getEndereco().getCep());
            stmt.setInt(5, chamado.getEndereco().getCidade().getId());
            
            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
}
