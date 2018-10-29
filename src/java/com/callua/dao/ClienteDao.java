package com.callua.dao;

import com.callua.bean.Cliente;
import com.callua.bean.Endereco;
import com.callua.facade.CidadeFacade;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDao {
    public void adicionarUm(Cliente cliente) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(cliente.getSenha().getBytes(), 0, cliente.getSenha().length());
            cliente.setSenha(new BigInteger(1, md.digest()).toString(16));
        
        
            stmt = connection.prepareStatement("INSERT INTO Cliente (nome, cpfCnpj, telefoneCelular, email, senha, endereco, cep, idCidade) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefoneCelular());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            stmt.setString(6, cliente.getEndereco().getEndereco());
            stmt.setString(7, cliente.getEndereco().getCep());
            stmt.setInt(8, cliente.getEndereco().getCidade().getId());
            stmt.executeUpdate();
        } catch (Exception exception) {
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
    
    public Cliente carregarUm(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        
        try {stmt = connection.prepareStatement("SELECT * FROM Cliente WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
                
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpfCnpj(rs.getString("cpfCnpj"));
                cliente.setTelefoneCelular(rs.getString("telefoneCelular"));
                cliente.setEmail(rs.getString("email"));
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setCep(rs.getString("cep"));
                endereco.setCidade(CidadeFacade.carregarUma(rs.getInt("idCidade")));
                cliente.setEndereco(endereco);
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
        
        return cliente;
    }
    
    public boolean senhaAtualValida(Cliente cliente, String senhaAtual) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senhaAtual.getBytes(), 0, senhaAtual.length());
            senhaAtual= new BigInteger(1, md.digest()).toString(16);
            
            stmt = connection.prepareStatement("SELECT * FROM Cliente WHERE id = ? AND senha = ?");
            stmt.setInt(1, cliente.getId());
            stmt.setString(2, senhaAtual);
            rs = stmt.executeQuery();
                
            return rs.next();
        } catch (Exception exception) {
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
    public void editarUm(Cliente cliente) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            if (!"".equals(cliente.getSenha())) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(cliente.getSenha().getBytes(), 0, cliente.getSenha().length());
                cliente.setSenha(new BigInteger(1, md.digest()).toString(16));
                stmt = connection.prepareStatement("UPDATE Cliente SET nome = ?, cpfCnpj = ?, telefoneCelular = ?, email = ?, senha = ?, endereco = ?, cep = ?, idCidade = ? WHERE id = ?");
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpfCnpj());
                stmt.setString(3, cliente.getTelefoneCelular());
                stmt.setString(4, cliente.getEmail());
                stmt.setString(5, cliente.getSenha());
                stmt.setString(6, cliente.getEndereco().getEndereco());
                stmt.setString(7, cliente.getEndereco().getCep());
                stmt.setInt(8, cliente.getEndereco().getCidade().getId());
                stmt.setInt(9, cliente.getId());
            } else {
                stmt = connection.prepareStatement("UPDATE Cliente SET nome = ?, cpfCnpj = ?, telefoneCelular = ?, email = ?, endereco = ?, cep = ?, idCidade = ? WHERE id = ?");
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpfCnpj());
                stmt.setString(3, cliente.getTelefoneCelular());
                stmt.setString(4, cliente.getEmail());
                stmt.setString(5, cliente.getEndereco().getEndereco());
                stmt.setString(6, cliente.getEndereco().getCep());
                stmt.setInt(7, cliente.getEndereco().getCidade().getId());
                stmt.setInt(8, cliente.getId());
            }
            
            stmt.executeUpdate();
        } catch (Exception exception) {
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
