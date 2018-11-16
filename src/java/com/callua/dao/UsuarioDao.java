package com.callua.dao;

import com.callua.bean.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class UsuarioDao {
    public List<Usuario> carregar() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario");
            rs = stmt.executeQuery();
                
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpfCnpj(rs.getString("cpfCnpj"));
                usuario.setTelefoneCelular(rs.getString("telefoneCelular"));
                usuario.setEmail(rs.getString("email"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
                
                usuarios.add(usuario);
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
        
        return usuarios;
    }
    
    
    public List<Usuario> carregarMenosUm(Usuario u) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id != ?");
            stmt.setInt(1, u.getId());
            rs = stmt.executeQuery();
                
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpfCnpj(rs.getString("cpfCnpj"));
                usuario.setTelefoneCelular(rs.getString("telefoneCelular"));
                usuario.setEmail(rs.getString("email"));
                usuario.setAdministrador(rs.getBoolean("administrador"));
                
                usuarios.add(usuario);
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
        
        return usuarios;
    }
    
    public Usuario carregarUm(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
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
    
    public void adicionarUmTecnico(Usuario tecnico, String url) {
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            connection.setAutoCommit(false);
            
            //ADICIONA O TÉCNICO
            adicionarUm(tecnico, connection);
            
            //COLOCAR O TOKEN PARA CRIAÇÃO DE SENHA - SEGURANÇA
            stmt = connection.prepareStatement("INSERT INTO TecnicoCriarSenhaToken (idTecnico, token) "
                                                + "VALUES (?, ?)");
            stmt.setInt(1, tecnico.getId());
            stmt.setString(2, token);
            stmt.executeUpdate();
            
            //ENVIAR O EMAIL PARA O TÉCNICO
            //https://stackoverflow.com/questions/27721408/authentication-error-in-java-mail-program-on-openshift
            Email e = new SimpleEmail();
            e.setDebug(true);
            e.setHostName("smtp.googlemail.com");
            e.setSmtpPort(465);
            e.setAuthenticator(new DefaultAuthenticator("calluasystems@gmail.com", "callua@)!*"));
            e.setSSLOnConnect(true);
            e.setFrom("calluasystems@gmail.com");
            e.setSubject("Callua - Cadastro de técnico - Criação de senha");
            e.setMsg(url + "/callua/Tecnico?op=criarSenhaForm&token=" + token + "&idTecnico=" + tecnico.getId());
            e.addTo(tecnico.getEmail());
            e.send();
            
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
    
    public void adicionarUm(Usuario usuario, Connection connection) throws SQLException {
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO Usuario (nome, cpfCnpj, telefoneCelular, email, administrador) VALUES "
                + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpfCnpj());
            stmt.setString(3, usuario.getTelefoneCelular());
            stmt.setString(4, usuario.getEmail());
            stmt.setBoolean(5, usuario.isAdministrador());
            
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Falha no cadastro do técnico!");
                }
            }
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (stmt != null)
                try { stmt.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar stmt. Ex="+exception.getMessage()); }
        }
    }
    
    public Boolean tokenValidoCriarSenhaTecnico(String token, Integer idTecnico) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM TecnicoCriarSenhaToken WHERE token = ? AND idTecnico = ?");
            stmt.setString(1, token);
            stmt.setInt(2, idTecnico);
            rs = stmt.executeQuery();
                
            return rs.next();
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
    }
    
    public void criarSenhaTecnico(Usuario tecnico) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            connection.setAutoCommit(false);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(tecnico.getSenha().getBytes(), 0, tecnico.getSenha().length());
            tecnico.setSenha(new BigInteger(1, md.digest()).toString(16));
            
            stmt = connection.prepareStatement("UPDATE Usuario SET senha = ? WHERE id = ?");
            stmt.setString(1, tecnico.getSenha());
            stmt.setInt(2, tecnico.getId());
            
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement("DELETE FROM TecnicoCriarSenhaToken WHERE idTecnico = ?");
            stmt.setInt(1, tecnico.getId());
            
            stmt.executeUpdate();
            
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    public void editar(Usuario usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Usuario SET nome = ?, cpfCnpj = ?,"
                    + "telefoneCelular = ?, email = ?, administrador = ? WHERE id = ?");
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpfCnpj());
            stmt.setString(3, usuario.getTelefoneCelular());
            stmt.setString(4, usuario.getEmail());
            stmt.setBoolean(5, usuario.isAdministrador());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
    
    public void remover(Integer usuarioId) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("DELETE FROM Usuario WHERE id = ?");
            stmt.setInt(1, usuarioId);
            stmt.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException("Erro. Origem="+exception.getMessage());
        } finally {
            if (connection != null)
                try { connection.close(); }
                catch (SQLException exception) { System.out.println("Erro ao fechar conexão. Ex="+exception.getMessage()); }
        }
    }
}
