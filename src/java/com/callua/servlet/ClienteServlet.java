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
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
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
        HttpSession session = request.getSession(false);
        
        Login logado = null;
        if (session != null)
            logado = (Login)session.getAttribute("logado");
        
        //Em cima são os links que são publicos, sem validação de login
        if ("cadastrarForm".equals(op) || "cadastrar".equals(op) ||
            logado != null) {
            switch(op) {
                case "cadastrarForm":
                    cadastrarForm(request, response);
                    break;
                case "cadastrar":
                    cadastrar(request, response);
                    break;
                case "dadosForm":
                    dadosForm(request, response);
                    break;
                case "editarDados":
                    editarDados(request, response);
                    break;
                case "carregarAjax":
                    carregarAjax(request, response);
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
        List<Estado> estados = EstadoFacade.buscarTodos();

        request.setAttribute("estados", estados);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/public/novocliente.jsp");
        rd.forward(request, response);
    }
    
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = carregarCliente(request);
        Mensagem mensagem = formValido(request, cliente);
        mensagem = Validator.validarSenha(cliente.getSenha(), request.getParameter("confirmacaoSenha"));
        if (mensagem == null) {
            ClienteFacade.adicionarUm(cliente);
            mensagem = new Mensagem("Cadastrado com sucesso !!!");
            mensagem.setTipo("success");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("view/public/login.jsp");
        } else {
            mensagem.setTipo("error");
            HttpSession session = request.getSession();
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("cliente", cliente);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cliente?op=cadastrarForm");
            rd.forward(request, response);
        }
    }
    
    public void dadosForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Login logado = (Login)session.getAttribute("logado");
        
        List<Estado> estados = EstadoFacade.buscarTodos();
        logado.setCliente(ClienteFacade.carregarUm(logado.getCliente().getId()));//Atualiza o cliente pro mais atual
        session = request.getSession();
        session.setAttribute("logado", logado);

        request.setAttribute("estados", estados);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/cliente/dadoscliente.jsp");
        rd.forward(request, response);
    }
    
    public void editarDados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = carregarCliente(request);
        Mensagem mensagem = formValido(request, cliente);
        HttpSession session = request.getSession();
        Login logado = (Login)session.getAttribute("logado");
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
            session.setAttribute("mensagem", mensagem);
            response.sendRedirect("Cliente?op=dadosForm");
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("cliente", cliente);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cliente?op=dadosForm");
            rd.forward(request, response);
        }
    }
    
    public void carregarAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clienteId = request.getParameter("clienteId");
        
        // Vai no BD buscar todas as cidades deste estado, em uma lista
        Cliente cliente = ClienteFacade.carregarUm(Integer.parseInt(clienteId));
       
        // transforma o MAP em JSON
        String json = new Gson().toJson(cliente);   

        // retorna o JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
    public Cliente carregarCliente(HttpServletRequest request) {
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
    
    public Mensagem formValido(HttpServletRequest request, Cliente cliente) {
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
