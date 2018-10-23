package com.callua.dao;

import com.callua.bean.Estado;
import com.callua.facade.CidadeFacade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDao {
    public List<Estado> carregarTodos() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Estado> estados = new ArrayList<Estado>();
        
        try { 
            stmt = connection.prepareStatement("SELECT * FROM Estado");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Estado estado = new Estado();
                
                estado.setId(rs.getInt("id"));
                estado.setUf(rs.getString("uf"));
                
                estados.add(estado);
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
        
        return estados;
    }
    public Estado carregarUm(Integer estadoId) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Estado estado = null;
        
        try { 
            stmt = connection.prepareStatement("SELECT * FROM Estado WHERE id = ?");
            stmt.setInt(1, estadoId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                estado = new Estado();
                
                estado.setId(rs.getInt("id"));
                estado.setUf(rs.getString("uf"));
                estado.setCidades(CidadeFacade.carregarByEstado(estado.getId()));
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
        
        return estado;
    }
}
