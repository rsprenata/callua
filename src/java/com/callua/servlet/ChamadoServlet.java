/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Chamado;
import com.callua.bean.Endereco;
import com.callua.bean.Estado;
import com.callua.facade.ChamadoFacade;
import com.callua.facade.CidadeFacade;
import com.callua.facade.EstadoFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.callua.util.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author renata
 */
@WebServlet(name = "ChamadoServlet", urlPatterns = {"/Chamado"})
@MultipartConfig(location = "/",fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)
public class ChamadoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String op = request.getParameter("op");
        RequestDispatcher rd = null;
        Mensagem mensagem = null;
        HttpSession session = request.getSession(false);
        
        Login logado = null;
        if (session != null)
            logado = (Login)session.getAttribute("logado");
        
        if (logado == null || (logado.getUsuario() == null || !logado.getUsuario().isAdministrador()) && (logado.getCliente() == null)) {
            mensagem = new Mensagem("Acesso não autorizado !!!");
            mensagem.setTipo("error");
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            rd = getServletContext().getRequestDispatcher("/Login?op=dashboard");
            rd.forward(request, response);
        } else {
            switch(op) {
                case "abrirForm":
                    List<Estado> estados = EstadoFacade.buscarTodos();

                    request.setAttribute("estados", estados);
                    rd = getServletContext().getRequestDispatcher("/view/abrirchamado.jsp");
                    rd.forward(request, response);
                    break;
                case "abrir":
                    Chamado chamado = carregarChamado(request);
                    mensagem = formValido(request, chamado);
                    if (mensagem == null) {
                        ChamadoFacade.abrirUm(chamado, getServletContext().getInitParameter("upload.location"));
                        mensagem = new Mensagem("Chamado aberto com sucesso !!!");
                        mensagem.setTipo("success");
                        session.setAttribute("mensagem", mensagem);
                        response.sendRedirect("Login?op=dashboard");
                    } else {
                        mensagem.setTipo("error");
                        session.setAttribute("mensagem", mensagem);
                        request.setAttribute("chamado", chamado);
                        rd = getServletContext().getRequestDispatcher("/Chamado?op=abrirForm");
                        rd.forward(request, response);
                    }
                    break;
            }
        }
    }
    
    private Chamado carregarChamado(HttpServletRequest request) {
        Chamado chamado = new Chamado();
        
        chamado.setTitulo(request.getParameter("titulo"));
        chamado.setDescricao(request.getParameter("descricao"));
        Endereco endereco = new Endereco();
        endereco.setEndereco(request.getParameter("endereco"));
        endereco.setCep(request.getParameter("cep"));
        if (endereco.getCep() != null) endereco.setCep(endereco.getCep().replaceAll("\\W", ""));
        try {
            endereco.setCidade(CidadeFacade.carregarUma(Integer.parseInt(request.getParameter("cidade"))));
            if (endereco.getCidade() != null)
                endereco.getCidade().setEstado(EstadoFacade.carregarUm(Integer.parseInt(request.getParameter("uf"))));
        } catch (NumberFormatException e) {
            endereco.setCidade(null);
        }
        chamado.setEndereco(endereco);
        
        try {
            chamado.setPartArquivos(request.getParts().stream().filter(part -> "arquivos".equals(part.getName()) && !"".equals(part.getSubmittedFileName())).collect(Collectors.toList()));
        } catch (Exception ex) {
            chamado.setPartArquivos(null);
            Logger.getLogger(ChamadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        return chamado;
    }
    
    private Mensagem formValido(HttpServletRequest request, Chamado chamado) {
        Mensagem mensagem = Validator.validarTitulo(chamado.getTitulo());
        if (mensagem == null) {
            mensagem = Validator.validarDescricao(chamado.getDescricao());
            if (mensagem == null) {
                mensagem = Validator.validarEndereco(chamado.getEndereco());
            }
        }
        
        return mensagem;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
