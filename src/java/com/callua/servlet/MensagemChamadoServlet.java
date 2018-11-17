/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Chamado;
import com.callua.bean.MensagemChamado;
import com.callua.bean.TabelaPessoa;
import com.callua.facade.ChamadoFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.MensagemChamadoFacade;
import com.callua.facade.UsuarioFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
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
@WebServlet(name = "MensagemChamadoServlet", urlPatterns = {"/MensagemChamado"})
@MultipartConfig(location = "/",fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)
public class MensagemChamadoServlet extends HttpServlet {

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
        
        if (logado != null && (logado.getCliente() != null || logado.getUsuario() != null)) {
            switch(op) {
                case "carregarViaAjax":
                    carregarViaAjax(request, response);
                    break;
                case "enviarViaAjax":
                    enviarViaAjax(request, response);
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
    
    public void carregarViaAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String chamadoId = request.getParameter("chamadoId");
        
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(chamadoId));
        
        List<MensagemChamado> mensagensChamado = MensagemChamadoFacade.buscar(chamado);

        // transforma o MAP em JSON
        String json = new Gson().toJson(mensagensChamado);   

        // retorna o JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
    public void enviarViaAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String chamadoId = request.getParameter("chamadoId");
        String logadoPessoa = request.getParameter("logadoPessoa");
        Integer logadoId = Integer.parseInt(request.getParameter("logadoId"));
        MensagemChamado mensagem = new MensagemChamado();
        mensagem.setMensagem(request.getParameter("mensagem"));
        mensagem.setData(new Date());
        if ("CLIENTE".equals(logadoPessoa)) {
            mensagem.setCliente(ClienteFacade.carregarUm(logadoId));
            mensagem.setTabelaPessoa(TabelaPessoa.CLIENTE);
        } else {
            mensagem.setUsuario(UsuarioFacade.carregarUm(logadoId));
            mensagem.setTabelaPessoa(TabelaPessoa.USUARIO);
        }
        
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(chamadoId));
        
        MensagemChamadoFacade.enviar(chamado, mensagem);
        
        // retorna o JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\": true}");
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
