package com.callua.dao;

import com.callua.bean.Chamado;
import com.callua.bean.MensagemChamado;
import com.callua.bean.TabelaPessoa;
import com.callua.facade.ClienteFacade;
import com.callua.facade.UsuarioFacade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MensagemChamadoDao {
    public List<MensagemChamado> carregar(Chamado chamado) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<MensagemChamado> mensagensChamado = new ArrayList<MensagemChamado>();
        
        try { 
            stmt = connection.prepareStatement("SELECT * FROM MensagemChamado WHERE idChamado = ? ORDER BY data");
            stmt.setInt(1, chamado.getId());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                MensagemChamado mensagemChamado = new MensagemChamado();
                
                mensagemChamado.setId(rs.getInt("id"));
                mensagemChamado.setTabelaPessoa(TabelaPessoa.valueOf(rs.getString("tabelaPessoa")));
                if (mensagemChamado.getTabelaPessoa() == TabelaPessoa.CLIENTE) 
                    mensagemChamado.setCliente(ClienteFacade.carregarUm(rs.getInt("idPessoa")));
                else
                    mensagemChamado.setUsuario(UsuarioFacade.carregarUm(rs.getInt("idPessoa")));
                mensagemChamado.setChamado(chamado);
                mensagemChamado.setMensagem(rs.getString("mensagem"));
                mensagemChamado.setData(rs.getTimestamp("data"));
                
                mensagensChamado.add(mensagemChamado);
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
        
        return mensagensChamado;
    }
    public void enviar(Chamado chamado, MensagemChamado mensagem) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try { 
            stmt = connection.prepareStatement("INSERT INTO MensagemChamado (idPessoa, "
                    + "idChamado, mensagem, data, tabelaPessoa)"
                    + "VALUES (?, ?, ?, ?, ?::TabelaPessoa)");
            if (mensagem.getTabelaPessoa() == TabelaPessoa.CLIENTE) {
                stmt.setInt(1, mensagem.getCliente().getId());
                stmt.setString(5, "CLIENTE");
            } else {
                stmt.setInt(1, mensagem.getUsuario().getId());
                stmt.setString(5, "USUARIO");
            }
            stmt.setInt(2, chamado.getId());
            stmt.setString(3, mensagem.getMensagem());
            stmt.setTimestamp(4, new Timestamp(mensagem.getData().getTime()));
            stmt.executeUpdate();
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
}
