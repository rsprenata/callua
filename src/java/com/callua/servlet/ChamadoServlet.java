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
import com.callua.bean.MensagemChamado;
import com.callua.bean.Produto;
import com.callua.bean.StatusChamado;
import com.callua.bean.TabelaPessoa;
import com.callua.bean.Usuario;
import com.callua.facade.ChamadoFacade;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.EstadoFacade;
import com.callua.facade.MensagemChamadoFacade;
import com.callua.facade.ProdutoFacade;
import com.callua.facade.UsuarioFacade;
import com.callua.util.Login;
import com.callua.util.Mensagem;
import com.callua.util.Validator;
import com.callua.webclient.ProdutoClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
        HttpSession session = request.getSession(false);
        
        Login logado = null;
        if (session != null)
            logado = (Login)session.getAttribute("logado");
        
        if (logado != null && (logado.getCliente() != null || logado.getUsuario() != null)) {
            switch(op) {
                case "abrirForm":
                    abrirForm(request, response);
                    break;
                case "abrir":
                    abrir(request, response);
                    break;
                case "meus":
                    meus(request, response);
                    break;
                case "visualizar":
                    visualizar(request, response);
                    break;
                case "fechar":
                    if (logado.getUsuario() != null)
                        fechar(request, response);
                    break;
                case "adicionarProduto":
                    adicionarProduto(request, response);
                    break;
                case "removerProduto":
                    removerProduto(request, response);
                    break;
                case "atribuirUsuario":
                    atribuirUsuario(request, response);
                    break;
                case "downloadArquivo":
                    downloadArquivo(request, response);
                    break;
                case "removerArquivo":
                    removerArquivo(request, response);
                    break;
                case "anexarArquivos":
                    anexarArquivos(request, response);
                    break;
                case "enviarMensagem":
                    enviarMensagem(request, response);
                    break;
                case "reabrir":
                    reabrir(request, response);
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
    
    public void abrirForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Estado> estados = EstadoFacade.buscarTodos();
        List<Cliente> clientes = ClienteFacade.carregar();

        request.setAttribute("estados", estados);
        request.setAttribute("clientes", clientes);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/cliente/abrirchamado.jsp");
        rd.forward(request, response);
    }
    
    public void abrir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Chamado chamado = carregarChamado(request);
        Mensagem mensagem = formValido(request, chamado);
        HttpSession session = request.getSession(false);
        Login logado = (Login)session.getAttribute("logado");
        if (mensagem == null) {
            if (logado.getCliente() == null) {
                Integer clienteId = Integer.parseInt(request.getParameter("cliente"));
                chamado.setCliente(ClienteFacade.carregarUm(clienteId));
            } else chamado.setCliente(logado.getCliente());
            chamado.setData(new Date());
            ChamadoFacade.abrirUm(chamado, getServletContext().getInitParameter("upload.location"));
            mensagem = new Mensagem("Chamado aberto com sucesso !!!");
            mensagem.setTipo("success");
            session.setAttribute("mensagem", mensagem);
            if (logado.getCliente() == null) {
                response.sendRedirect("Login?op=dashboard");
            } else {
                response.sendRedirect("Chamado?op=meus");
            }
        } else {
            mensagem.setTipo("error");
            session.setAttribute("mensagem", mensagem);
            request.setAttribute("chamado", chamado);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Chamado?op=abrirForm");
            rd.forward(request, response);
        }
    }
    
    public void meus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Login logado = (Login)session.getAttribute("logado");
        List<Chamado> chamados = ChamadoFacade.buscarTodosByCliente(logado.getCliente());

        request.setAttribute("chamadosAbertos", chamados.stream().filter(c -> c.getStatus() == StatusChamado.ABERTO).collect(Collectors.toList()));
        request.setAttribute("chamadosResolvidos", chamados.stream().filter(c -> c.getStatus() == StatusChamado.RESOLVIDO).collect(Collectors.toList()));
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/cliente/meuschamados.jsp");
        rd.forward(request, response);
    }
    
    public void visualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idChamado = request.getParameter("idChamado");
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(idChamado));
        List<Produto> produtos = ProdutoFacade.carregarTodos();
        List<Usuario> usuarios = UsuarioFacade.carregar();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("chamado", chamado);
        request.setAttribute("produtos", produtos);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/view/administrador/chamado.jsp");
        rd.forward(request, response);
    }
    
    public void fechar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String idChamado = request.getParameter("idChamado");
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(idChamado));
        ChamadoFacade.fecharUm(chamado);
        
        Mensagem mensagem = new Mensagem("Chamado fechado com sucesso !!!");
        mensagem.setTipo("success");
        session.setAttribute("mensagem", mensagem);
        
        response.sendRedirect("Login?op=dashboard");
    }
    
    public void adicionarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String idChamado = request.getParameter("idChamado");
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(idChamado));
        String idProduto = request.getParameter("produto");
        Produto produto = ProdutoFacade.carregarUm(Integer.parseInt(idProduto));
        Integer quantidade = Integer.parseInt(request.getParameter("quantidade"));
        produto.setQuantidade(quantidade);
        Mensagem mensagem = Validator.validarQuantidade(quantidade);
        try {
            if (mensagem != null)  throw new Exception(mensagem.getTexto());
            ChamadoFacade.adicionarProduto(chamado, produto);
        
            mensagem = new Mensagem("Produto adicionado com sucesso !!!");
            mensagem.setTipo("success");
        } catch (Exception ex) {
            ex.printStackTrace();
            mensagem = new Mensagem(ex.getMessage());
            mensagem.setTipo("error");
        }
        session.setAttribute("mensagem", mensagem);

        response.sendRedirect("Chamado?op=visualizar&idChamado=" + chamado.getId());
    }
    
    public void removerProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Chamado chamado = new Chamado();
        chamado.setId(Integer.parseInt(request.getParameter("idChamado")));
        Produto produto = ProdutoClient.getProduto(Integer.parseInt(request.getParameter("idProduto")));
        Integer quantidade = Integer.parseInt(request.getParameter("quantidade"));
        produto.setQuantidade(quantidade);
        Mensagem mensagem = Validator.validarQuantidade(quantidade);
        try {
            if (mensagem != null)  throw new Exception(mensagem.getTexto());
            ChamadoFacade.removerProduto(chamado, produto);
            mensagem = new Mensagem("Produto removido com sucesso !!!");
            mensagem.setTipo("success");
        } catch (Exception ex) {
            ex.printStackTrace();
            mensagem = new Mensagem(ex.getMessage());
            mensagem.setTipo("error");
        }
        
        session.setAttribute("mensagem", mensagem);
        
        response.sendRedirect("Chamado?op=visualizar&idChamado=" + chamado.getId());
    }
    
    public void atribuirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String idChamado = request.getParameter("idChamado");
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(idChamado));
        String idUsuario = request.getParameter("usuario");
        Usuario usuario = UsuarioFacade.carregarUm(Integer.parseInt(idUsuario));
        ChamadoFacade.atribuirUsuario(chamado, usuario);
        
        Mensagem mensagem = new Mensagem("Usuario atribuido com sucesso !!!");
        mensagem.setTipo("success");
        session.setAttribute("mensagem", mensagem);
        
        response.sendRedirect("Chamado?op=visualizar&idChamado=" + chamado.getId());
    }
    
    public void downloadArquivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream is = null;
        String filePath = request.getParameter("filePath");
        try {
            // get your file as InputStream
            File file = new File(filePath);
            is = new FileInputStream(file);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(ChamadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ChamadoServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
    
    public void removerArquivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String absolutePath = request.getParameter("absolutePath");
        java.io.File dest = new java.io.File(absolutePath);
        dest.delete();
        
        response.sendRedirect("Chamado?op=visualizar&idChamado=" + request.getParameter("chamadoId"));
    }
    
    public void anexarArquivos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadLocation = getServletContext().getInitParameter("upload.location");
        Integer chamadoId = Integer.parseInt(request.getParameter("chamadoId"));
        Chamado chamado = ChamadoFacade.carregarById(chamadoId);
        List<Part> partArquivos = request.getParts().stream().filter(part -> "arquivos".equals(part.getName()) && !"".equals(part.getSubmittedFileName())).collect(Collectors.toList());
        
        ChamadoFacade.anexarArquivos(chamado, partArquivos, uploadLocation);
        
        response.sendRedirect("Chamado?op=visualizar&idChamado=" + request.getParameter("chamadoId"));
    }
    
    public void enviarMensagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Login logado = (Login)session.getAttribute("logado");
        String chamadoId = request.getParameter("chamadoId");
        MensagemChamado mensagem = new MensagemChamado();
        mensagem.setMensagem(request.getParameter("mensagem"));
        mensagem.setData(new Date());
        if (logado.getCliente() != null) {
            mensagem.setCliente(logado.getCliente());
            mensagem.setTabelaPessoa(TabelaPessoa.CLIENTE);
        } else {
            mensagem.setUsuario(logado.getUsuario());
            mensagem.setTabelaPessoa(TabelaPessoa.USUARIO);
        }
        
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(chamadoId));
        
        MensagemChamadoFacade.enviar(chamado, mensagem);
        
        response.sendRedirect("Chamado?op=visualizar&idChamado=" + chamadoId);
    }
    
    public void reabrir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String idChamado = request.getParameter("idChamado");
        Chamado chamado = ChamadoFacade.carregarById(Integer.parseInt(idChamado));
        ChamadoFacade.reabrir(chamado);
        
        Mensagem mensagem = new Mensagem("Chamado reaberto com sucesso !!!");
        mensagem.setTipo("success");
        session.setAttribute("mensagem", mensagem);
        
        response.sendRedirect("Login?op=dashboard");
    }
    
    public Chamado carregarChamado(HttpServletRequest request) {
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
    
    public Mensagem formValido(HttpServletRequest request, Chamado chamado) {
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
