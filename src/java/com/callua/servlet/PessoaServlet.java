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
import com.callua.facade.PessoaFacade;
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

/**
 *
 * @author juniorrek
 */
@WebServlet(name = "PessoaServlet", urlPatterns = {"/Pessoa"})
public class PessoaServlet extends HttpServlet {

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
        
        switch(op) {
            case "logar":
                String mensagem = formValido(request);
                if (mensagem == null) {
                    Pessoa pessoa = PessoaFacade.validaLogin(request.getParameter("cpfCnpj"), request.getParameter("senha"));
                    if (pessoa != null && (pessoa.isCliente() || pessoa.isUsuario())) {
                        if (pessoa.isCliente() && pessoa.isUsuario()) {
                            //É CLIENTE E USUÁRIO
                        } else if (pessoa.isCliente()) {
                            //SÓ É CLIENTE
                            response.sendRedirect("dashboardcliente.jsp");
                        } else {
                            //SÓ É USUÁRIO
                        }
                    } else {
                        mensagem = "Usuário não encontrado.";
                    }
                } 
                
                if (mensagem != null) {
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("mensagemTipo", "error");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;
        }
    }
    
    private String formValido(HttpServletRequest request) {
        String mensagem = null;
        
        if (request.getParameter("cpfCnpj") == null || "".equals(request.getParameter("cpfCnpj"))) {
            mensagem = "Digite um CPF/CNPJ !!!";
        } else if (request.getParameter("senha") == null || "".equals(request.getParameter("senha"))) {
            mensagem = "Digite uma senha !!!";
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
