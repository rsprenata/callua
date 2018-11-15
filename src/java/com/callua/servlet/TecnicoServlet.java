/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Chamado;
import com.callua.bean.Cliente;
import com.callua.bean.Endereco;
import com.callua.bean.Estado;
import com.callua.bean.StatusChamado;
import com.callua.bean.Usuario;
import com.callua.facade.ChamadoFacade;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.EstadoFacade;
import com.callua.facade.UsuarioFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.callua.util.Validator;
import com.google.gson.Gson;
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
@WebServlet(name = "TecnicoServlet", urlPatterns = {"/Tecnico"})
@MultipartConfig(location = "/",fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)
public class TecnicoServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        
        Login logado = null;
        if (session != null)
            logado = (Login)session.getAttribute("logado");
        
        if ("criarSenhaForm".equals(op) || "criarSenha".equals(op) ||
                logado != null && logado.getUsuario() != null) {
            switch(op) {
                case "cadastrarForm":
                    if (logado.getUsuario().isAdministrador())
                        cadastrarForm(request, response);
                    break;
                case "cadastrar":
                    if (logado.getUsuario().isAdministrador())
                    cadastrar(request, response);
                    break;
                case "criarSenhaForm":
                    criarSenhaForm(request, response);
                    break;
                case "criarSenha":
                    criarSenha(request, response);
                    break;
            }
        } else {
            Mensagem mensagem = new Mensagem("Acesso não autorizado !!!");
            mensagem.setTipo("error");
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login?op=dashboard");
            rd.forward(request, response);
        }
    }
    
    public void cadastrarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/cadastrartecnico.jsp");
        rd.forward(request, response);
    }
    
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario tecnico = carregarTecnico(request);
        Mensagem mensagem = formValido(request, tecnico);
        if (mensagem == null) {
            String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            UsuarioFacade.adicionarUmTecnico(tecnico, url);
            mensagem = new Mensagem("Cadastrado com sucesso !!! O técnico recebeu um email para criação da senha.");
            mensagem.setTipo("success");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Login?op=dashboard");
        } else {
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("tecnico", tecnico);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Tecnico?op=cadastrarForm");
            rd.forward(request, response);
        }
    }
    
    public void criarSenhaForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        Integer idTecnico = Integer.parseInt(request.getParameter("idTecnico"));
        if (UsuarioFacade.tokenValidoCriarSenhaTecnico(token, idTecnico)) {
            Usuario tecnico = UsuarioFacade.carregarUm(idTecnico);
            
            request.setAttribute("tecnico", tecnico);
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/tecnico/criarsenha.jsp");
            rd.forward(request, response);
        } else {
            Mensagem mensagem = new Mensagem("Token de validação inválido.");
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/public/index.jsp");
            rd.forward(request, response);
        }
    }
    
    public void criarSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario tecnico = new Usuario();
        Integer idTecnico = Integer.parseInt(request.getParameter("idTecnico"));
        tecnico.setId(idTecnico);
        tecnico.setSenha(request.getParameter("senha"));
        Mensagem mensagem = Validator.validarSenha(tecnico.getSenha(), request.getParameter("confirmacaoSenha"));
        if (mensagem == null) {
            UsuarioFacade.criarSenhaTecnico(tecnico);
            mensagem = new Mensagem("Cadastrado com sucesso !!!");
            mensagem.setTipo("success");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("view/public/index.jsp");
        } else {
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("tecnico", tecnico);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Tecnico?op=criarSenhaForm&token="
                                                                                + request.getParameter("token") 
                                                                                + "&idTecnico="
                                                                                + tecnico.getId());
            rd.forward(request, response);
        }
    }
    
    public Usuario carregarTecnico(HttpServletRequest request) {
        Usuario tecnico = new Usuario();
        
        tecnico.setNome(request.getParameter("nome"));
        tecnico.setCpfCnpj(request.getParameter("cpfCnpj"));
        if (tecnico.getCpfCnpj() != null) tecnico.setCpfCnpj(tecnico.getCpfCnpj().replaceAll("\\W", ""));
        tecnico.setTelefoneCelular(request.getParameter("telefoneCelular"));
        if (tecnico.getTelefoneCelular() != null) tecnico.setTelefoneCelular(tecnico.getTelefoneCelular().replaceAll("\\W", ""));
        tecnico.setEmail(request.getParameter("email"));
        if(request.getParameter("administrador") == null) {
            tecnico.setAdministrador(false);
        } else {
            tecnico.setAdministrador(true);
        }
           
        return tecnico;
    }
    
    public Mensagem formValido(HttpServletRequest request, Usuario tecnico) {
        Mensagem mensagem = Validator.validarNome(tecnico.getNome());
        if (mensagem == null) {
            mensagem = Validator.validarCpfCnpj(tecnico.getCpfCnpj());
            if (mensagem == null) {
                mensagem = Validator.validarTelefoneCelular(tecnico.getTelefoneCelular());
                if (mensagem == null) {
                    mensagem = Validator.validarEmail(tecnico.getEmail());
                }
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
