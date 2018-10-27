package com.callua.dao;

import com.callua.bean.Chamado;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            stmt = connection.prepareStatement("INSERT INTO Chamado (titulo, descricao, endereco, cep, idCidade) VALUES "
                + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, chamado.getTitulo());
            stmt.setString(2, chamado.getDescricao());
            stmt.setString(3, chamado.getEndereco().getEndereco());
            stmt.setString(4, chamado.getEndereco().getCep());
            stmt.setInt(5, chamado.getEndereco().getCidade().getId());
            
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
}
