package com.callua.dao;

import com.callua.bean.Chamado;
import com.callua.bean.Cliente;
import com.callua.bean.Endereco;
import com.callua.bean.Produto;
import com.callua.bean.StatusChamado;
import com.callua.bean.Usuario;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.ProdutoFacade;
import com.callua.facade.UsuarioFacade;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

public class ChamadoDao {
    public void abrirUm(Chamado chamado, String applicationPath) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        File uploadFolder = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement("INSERT INTO Chamado (titulo, descricao, endereco, cep, idCidade, status, idCliente, data) VALUES "
                + "(?, ?, ?, ?, ?, ?::StatusChamado, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getEndereco().getEndereco());
            stmt.setString(4, chamado.getEndereco().getCep());
            stmt.setInt(5, chamado.getEndereco().getCidade().getId());
            stmt.setString(6, "ABERTO");
            stmt.setInt(7, chamado.getCliente().getId());
            stmt.setTimestamp(8, new Timestamp(chamado.getData().getTime()));
            
            int affectedRows = stmt.executeUpdate();

            if (chamado.getPartArquivos() != null && chamado.getPartArquivos().size() > 0) {
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chamado.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Falha na abertura do chamado!");
                    }
                }

                // constructs path of the directory to save uploaded file
                String uploadFilePath = applicationPath + File.separator + "uploads" + File.separator + "chamados" + File.separator + chamado.getId();
                // creates upload folder if it does not exists
                uploadFolder = new File(uploadFilePath);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }
                
                stmt = connection.prepareStatement("UPDATE Chamado SET files_path = ? WHERE id = ?");
                stmt.setString(1, uploadFilePath);
                stmt.setInt(2, chamado.getId());
                stmt.executeUpdate();
                
                // write all files in upload folder
                for (Part part : chamado.getPartArquivos()) {
                        if (part != null && part.getSize() > 0) {
                                String fileName = part.getSubmittedFileName();
                                String contentType = part.getContentType();

                                part.write(uploadFilePath + File.separator + fileName);

                        }
                }
            }
            connection.commit();
        } catch (Exception exception) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ChamadoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (uploadFolder != null) {
                uploadFolder.delete();
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
    
    public List<Chamado> buscarTodosByCliente(Cliente cliente) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Chamado WHERE idCliente = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                
                chamado.setId(rs.getInt("id"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setDescricao(rs.getString("descricao"));
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setCep(rs.getString("cep"));
                endereco.setCidade(CidadeFacade.carregarUma(rs.getInt("idCidade")));
                chamado.setEndereco(endereco);
                chamado.setStatus(StatusChamado.valueOf(rs.getString("status")));
                chamado.setCliente(cliente);
                
                chamados.add(chamado);
            }
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
        
        return chamados;
    }
    
    public List<Chamado> buscarTodosByUsuario(Usuario usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Chamado WHERE idUsuario = ?");
            stmt.setInt(1, usuario.getId());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                
                chamado.setId(rs.getInt("id"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setDescricao(rs.getString("descricao"));
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setCep(rs.getString("cep"));
                endereco.setCidade(CidadeFacade.carregarUma(rs.getInt("idCidade")));
                chamado.setEndereco(endereco);
                chamado.setStatus(StatusChamado.valueOf(rs.getString("status")));
                chamado.setCliente(ClienteFacade.carregarUm(rs.getInt("idCliente")));
                chamado.setUsuario(usuario);
                
                chamados.add(chamado);
            }
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
        
        return chamados;
    }
    
    public List<Chamado> buscarTodos() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamados = new ArrayList<Chamado>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Chamado");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                
                chamado.setId(rs.getInt("id"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setDescricao(rs.getString("descricao"));
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setCep(rs.getString("cep"));
                endereco.setCidade(CidadeFacade.carregarUma(rs.getInt("idCidade")));
                chamado.setEndereco(endereco);
                chamado.setStatus(StatusChamado.valueOf(rs.getString("status")));
                chamado.setCliente(ClienteFacade.carregarUm(rs.getInt("idCliente")));
                
                chamados.add(chamado);
            }
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
        
        return chamados;
    }
    
    public Chamado carregarById(Integer id) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Chamado chamado = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM Chamado WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                chamado = new Chamado();
                
                chamado.setId(rs.getInt("id"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setDescricao(rs.getString("descricao"));
                Endereco endereco = new Endereco();
                endereco.setEndereco(rs.getString("endereco"));
                endereco.setCep(rs.getString("cep"));
                endereco.setCidade(CidadeFacade.carregarUma(rs.getInt("idCidade")));
                chamado.setEndereco(endereco);
                chamado.setStatus(StatusChamado.valueOf(rs.getString("status")));
                chamado.setCliente(ClienteFacade.carregarUm(rs.getInt("idCliente")));
                chamado.setProdutos(ProdutoFacade.carregarByChamado(chamado.getId()));
                chamado.setUsuario(UsuarioFacade.carregarUm(rs.getInt("idUsuario")));
            }
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
        
        return chamado;
    }
    
    public void fecharUm(Chamado chamado) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Chamado SET status = ?::StatusChamado WHERE id = ?");
            
            stmt.setString(1, "RESOLVIDO");
            stmt.setInt(2, chamado.getId());
            
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
    
    public void adicionarProduto(Chamado chamado, Produto produto) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("INSERT INTO RelChamadoProduto (idChamado, idProduto) VALUES (?, ?)");
            stmt.setInt(1, chamado.getId());
            stmt.setInt(2, produto.getId());
            
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
    
    public void removerProduto(Chamado chamado, Produto produto) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("DELETE FROM RelChamadoProduto WHERE idChamado = ? AND idProduto = ?");
            stmt.setInt(1, chamado.getId());
            stmt.setInt(2, produto.getId());
            
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
    
    public void atribuirUsuario(Chamado chamado, Usuario usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement("UPDATE Chamado SET idUsuario = ? WHERE id = ?");
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, chamado.getId());
            
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
