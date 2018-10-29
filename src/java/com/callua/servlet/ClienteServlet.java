/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Cliente;
import com.callua.bean.Endereco;
import com.callua.bean.Estado;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.EstadoFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.callua.util.Validator;
import java.io.IOException;
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
@WebServlet(name = "ClienteServlet", urlPatterns = {"/Cliente"})
public class ClienteServlet extends HttpServlet {

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
        List<Estado> estados = null;
        Mensagem mensagem = null;
        HttpSession session = request.getSession(false);
        Cliente cliente = null;
        
        Login logado = null;
        if (session != null)
            logado = (Login)session.getAttribute("logado");
        
        //Em cima são os links que são publicos, sem validação de login
        if ("cadastrarForm".equals(op) || "cadastrar".equals(op) ||
            logado != null && logado.getCliente() != null) {
            switch(op) {
                case "cadastrarForm":
                    estados = EstadoFacade.buscarTodos();

                    request.setAttribute("estados", estados);
                    rd = getServletContext().getRequestDispatcher("/view/novocliente.jsp");
                    rd.forward(request, response);
                    break;
                case "cadastrar":
                    cliente = carregarCliente(request);
                    mensagem = formValido(request, cliente);
                    mensagem = Validator.validarSenha(cliente.getSenha(), request.getParameter("confirmacaoSenha"));
                    if (mensagem == null) {
                        ClienteFacade.adicionarUm(cliente);
                        mensagem = new Mensagem("Cadastrado com sucesso !!!");
                        mensagem.setTipo("success");
                        session = request.getSession();
                        session.setAttribute("mensagem", mensagem);
                        response.sendRedirect("view/login.jsp");
                    } else {
                        mensagem.setTipo("error");
                        session = request.getSession();
                        session.setAttribute("mensagem", mensagem);
                        request.setAttribute("cliente", cliente);
                        rd = getServletContext().getRequestDispatcher("/Cliente?op=cadastrarForm");
                        rd.forward(request, response);
                    }
                    break;
                case "dadosForm":
                    estados = EstadoFacade.buscarTodos();
                    logado.setCliente(ClienteFacade.carregarUm(logado.getCliente().getId()));//Atualiza o cliente pro mais atual
                    session = request.getSession();
                    session.setAttribute("logado", logado);

                    request.setAttribute("estados", estados);
                    rd = getServletContext().getRequestDispatcher("/view/dadoscliente.jsp");
                    rd.forward(request, response);
                    break;
                case "editarDados":
                    cliente = carregarCliente(request);
                    mensagem = formValido(request, cliente);
                    if (request.getParameter("senhaAtual") != null && request.getParameter("senhaAtual").length() >= 1) {//Se preencheu senha atual, então quer alterar senha
                        mensagem = Validator.validarSenhaAtual(logado.getCliente(), request.getParameter("senhaAtual"));
                        if (mensagem == null)
                            mensagem = Validator.validarSenha(cliente.getSenha(), request.getParameter("confirmacaoSenha"));
                    } else cliente.setSenha("");//Seta senha vazia, pra não cair no update de senha la na frente, ou seja, só vai editar senha se passar pelo teste de senha atual acima.
                    if (mensagem == null) {
                        cliente.setId(logado.getCliente().getId());
                        ClienteFacade.editarUm(cliente);
                        mensagem = new Mensagem("Editado com sucesso !!!");
                        mensagem.setTipo("success");
                        session = request.getSession();
                        session.setAttribute("mensagem", mensagem);
                        response.sendRedirect("Cliente?op=dadosForm");
                    } else {
                        mensagem.setTipo("error");
                        session = request.getSession();
                        session.setAttribute("mensagem", mensagem);
                        request.setAttribute("cliente", cliente);
                        rd = getServletContext().getRequestDispatcher("/Cliente?op=dadosForm");
                        rd.forward(request, response);
                    }
                    break;
            }
        } else {
            mensagem = new Mensagem("Acesso não autorizado !!!");
            mensagem.setTipo("error");
            session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            rd = getServletContext().getRequestDispatcher("/Login?op=dashboard");
            rd.forward(request, response);
        }
    }
    
    private Cliente carregarCliente(HttpServletRequest request) {
        Cliente cliente = new Cliente();
        
        cliente.setNome(request.getParameter("nome"));
        cliente.setCpfCnpj(request.getParameter("cpfCnpj"));
        if (cliente.getCpfCnpj() != null) cliente.setCpfCnpj(cliente.getCpfCnpj().replaceAll("\\W", ""));
        cliente.setTelefoneCelular(request.getParameter("telefoneCelular"));
        if (cliente.getTelefoneCelular() != null) cliente.setTelefoneCelular(cliente.getTelefoneCelular().replaceAll("\\W", ""));
        cliente.setEmail(request.getParameter("email"));
        cliente.setSenha(request.getParameter("senha"));
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
        cliente.setEndereco(endereco);
           
        return cliente;
    }
    
    private Mensagem formValido(HttpServletRequest request, Cliente cliente) {
        Mensagem mensagem = Validator.validarNome(cliente.getNome());
        if (mensagem == null) {
            mensagem = Validator.validarCpfCnpj(cliente.getCpfCnpj());
            if (mensagem == null) {
                mensagem = Validator.validarTelefoneCelular(cliente.getTelefoneCelular());
                if (mensagem == null) {
                    mensagem = Validator.validarEmail(cliente.getEmail());
                    if (mensagem == null) {
                        mensagem = Validator.validarEndereco(cliente.getEndereco());
                    }
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
