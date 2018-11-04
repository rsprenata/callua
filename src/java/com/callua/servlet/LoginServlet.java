/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Cliente;
import com.callua.bean.Estado;
import com.callua.bean.Pessoa;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.EstadoFacade;
import com.callua.facade.LoginFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juniorrek
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

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
        String op = request.getParameter("op");
        HttpSession session = request.getSession(false);
        Mensagem mensagem = null;
        Login logado = null;
        
        switch(op) {
            case "logar":
                mensagem = formValido(request);
                if (mensagem == null) {
                    Login login = LoginFacade.carregarLogin(request.getParameter("cpfCnpj").replaceAll("\\W", "")
                                                            , request.getParameter("senha"));
                    if (login != null && (login.getCliente() != null || login.getUsuario() != null)) {
                        session.setAttribute("logado", login);
                        if (login.getCliente() != null && login.getUsuario() != null) {
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/selecionaacesso.jsp");
                            rd.forward(request, response);
                        } else {
                            response.sendRedirect("Login?op=dashboard");
                        }
                    } else {
                        mensagem = new Mensagem("Usuário não encontrado.");
                    }
                } 
                
                if (mensagem != null) {
                    mensagem.setTipo("error");
                    session.setAttribute("mensagem", mensagem);
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/login.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;
            case "dashboard":
                logado = (Login)session.getAttribute("logado");
                if (logado != null && (logado.getCliente() != null || logado.getUsuario() != null)) {
                    if (logado.getCliente() != null && logado.getUsuario() != null) {
                        //É CLIENTE E USUÁRIO
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/selecionaacesso.jsp");
                        rd.forward(request, response);
                    } else if (logado.getCliente() != null) {
                        //SÓ É CLIENTE
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/dashboardcliente.jsp");
                        rd.forward(request, response);
                    } else {
                        //SÓ É USUÁRIO
                        if (logado.getUsuario().isAdministrador()) {
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/dashboardadmin.jsp");
                            rd.forward(request, response);
                        } else {
                            RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/dashboardtecnico.jsp");
                            rd.forward(request, response);
                        }
                    }
                } else {
                    mensagem = new Mensagem("Usuário deve se autenticar para acessar o sistema");
                    mensagem.setTipo("error");
                    session.setAttribute("mensagem", mensagem);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/login.jsp");
                    rd.forward(request, response);
                }
                break;
            case "selecionaAcesso":
                String acesso = request.getParameter("acesso");
                logado = (Login)session.getAttribute("logado");
                if ("cliente".equals(acesso)) {
                    logado.setUsuario(null);
                } else {
                    logado.setCliente(null);
                }
                response.sendRedirect("Login?op=dashboard");
                break;
            case "logout":
                if (session != null) session.invalidate();
                response.sendRedirect("view/index.jsp");
                break;
        }
    }
    
    private Mensagem formValido(HttpServletRequest request) {
        Mensagem mensagem = null;
        
        if (request.getParameter("cpfCnpj") == null || "".equals(request.getParameter("cpfCnpj"))) {
            mensagem = new Mensagem("Digite um CPF/CNPJ !!!");
        } else if (request.getParameter("senha") == null || "".equals(request.getParameter("senha"))) {
            mensagem = new Mensagem("Digite uma senha !!!");
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
