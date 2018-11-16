/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Usuario;
import com.callua.facade.UsuarioFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.callua.util.Validator;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                case "listar":
                    listar(request, response);
                    break;
                case "editarForm":
                    if (logado.getUsuario().isAdministrador())
                        editarForm(request, response);
                    break;
                case "editar":
                    if (logado.getUsuario().isAdministrador())
                        editar(request, response);
                    break;
                case "remover":
                    if (logado.getUsuario().isAdministrador())
                        remover(request, response);
                    break;
                case "dadosForm":
                    dadosForm(request, response);
                    break;
                case "editarDados":
                    editarDados(request, response);
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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/tecnicoForm.jsp");
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
            response.sendRedirect("Tecnico?op=listar");
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
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/public/login.jsp");
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
    
    public void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Login logado = (Login)session.getAttribute("logado");
        List<Usuario> usuarios = UsuarioFacade.carregarMenosUm(logado.getUsuario());
        
        request.setAttribute("usuarios", usuarios);
        
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/usuariosListar.jsp");
        rd.forward(request, response);
    }
    
    public void editarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        Usuario usuario = UsuarioFacade.carregarUm(usuarioId);
        
        request.setAttribute("tecnico", usuario);
        request.setAttribute("form", "editar");
        
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/tecnicoForm.jsp");
        rd.forward(request, response);
    }
    
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario tecnico = carregarTecnico(request);
        Mensagem mensagem = formValido(request, tecnico);
        Integer usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        tecnico.setId(usuarioId);
        if (mensagem == null) {
            UsuarioFacade.editar(tecnico);
            mensagem = new Mensagem("Editado com sucesso !!!");
            mensagem.setTipo("success");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Tecnico?op=listar");
        } else {
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("tecnico", tecnico);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Tecnico?op=cadastrarForm");
            rd.forward(request, response);
        }
    }
    
    public void remover(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
        UsuarioFacade.remover(usuarioId);
        Mensagem mensagem = new Mensagem("Removido com sucesso !!!");
        mensagem.setTipo("success");
        HttpSession session = request.getSession();
        session.setAttribute("mensagem", mensagem);
        response.sendRedirect("Tecnico?op=listar");
    }
    
    public void dadosForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Login logado = (Login)session.getAttribute("logado");
        
        logado.setUsuario(UsuarioFacade.carregarUm(logado.getUsuario().getId()));//Atualiza o usuario pro mais atual
        session = request.getSession();
        session.setAttribute("logado", logado);
        
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/tecnico/dadostecnico.jsp");
        rd.forward(request, response);
    }
    
    public void editarDados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = carregarTecnico(request);
        usuario.setSenha(request.getParameter("senha"));
        Mensagem mensagem = formValido(request, usuario);
        HttpSession session = request.getSession();
        Login logado = (Login)session.getAttribute("logado");
        if (request.getParameter("senhaAtual") != null && request.getParameter("senhaAtual").length() >= 1) {//Se preencheu senha atual, então quer alterar senha
            mensagem = Validator.validarSenhaAtual(logado.getUsuario(), request.getParameter("senhaAtual"));
            if (mensagem == null)
                mensagem = Validator.validarSenha(usuario.getSenha(), request.getParameter("confirmacaoSenha"));
        } else usuario.setSenha("");//Seta senha vazia, pra não cair no update de senha la na frente, ou seja, só vai editar senha se passar pelo teste de senha atual acima.
        if (mensagem == null) {
            usuario.setId(logado.getUsuario().getId());
            UsuarioFacade.editarDados(usuario);
            mensagem = new Mensagem("Editado com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Tecnico?op=dadosForm");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("usuario", usuario);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Tecnico?op=dadosForm");
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
