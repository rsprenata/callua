package com.callua.dao;

import com.callua.bean.Cidade;
import com.callua.facade.EstadoFacade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDao {
    public List<Cidade> carregarByEstado(Integer estadoId) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cidade> cidades = new ArrayList<Cidade>();
        
        try { 
            stmt = connection.prepareStatement("SELECT * FROM Cidade WHERE idEstado = ?");
            stmt.setInt(1, estadoId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cidade cidade = new Cidade();
                
                cidade.setId(rs.getInt("id"));
                cidade.setNome(rs.getString("nome"));
                
                cidades.add(cidade);
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
        
        return cidades;
    }
    
    public Cidade carregarUma(Integer cidadeId) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cidade cidade = null;
        
        try { 
            stmt = connection.prepareStatement("SELECT * FROM Cidade WHERE id = ?");
            stmt.setInt(1, cidadeId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cidade = new Cidade();
                
                cidade.setId(rs.getInt("id"));
                cidade.setNome(rs.getString("nome"));
                cidade.setEstado(EstadoFacade.carregarUm(rs.getInt("idEstado")));
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
        
        return cidade;
    }
}
