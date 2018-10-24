/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.servlet;

import com.callua.bean.Cliente;
import com.callua.bean.Estado;
import com.callua.facade.CidadeFacade;
import com.callua.facade.ClienteFacade;
import com.callua.facade.EstadoFacade;
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
        
        
        String op = request.getParameter("op");
        
        switch(op) {
            case "cadastrarForm":
                List<Estado> estados = EstadoFacade.buscarTodos();
                
                request.setAttribute("estados", estados);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/novocliente.jsp");
                rd.forward(request, response);
                break;
            case "cadastrar":
                //String mensagem = formValido(request);
                Mensagem mensagem = null;
                Cliente cliente = carregarCliente(request);
                if (mensagem == null) {
                    //ClienteFacade.adicionarUm(cliente);
                    request.setAttribute("mensagem", "Cadastrado com sucesso !!!");
                    request.setAttribute("mensagemTipo", "success");
                    HttpSession session = request.getSession(false);
                    session.setAttribute("mensagem", "Hello world");
                    response.sendRedirect("/login.jsp");
                } else {
                    mensagem.setTipo("error");
                    request.setAttribute("mensagem", mensagem);
                    request.setAttribute("cliente", cliente);
                    request.setAttribute("idEstado", request.getParameter("uf"));
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/Cliente?op=cadastrarForm");
                    requestDispatcher.forward(request, response);
                }
                break;
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
        cliente.setEndereco(request.getParameter("endereco"));
        cliente.setCep(request.getParameter("cep"));
        if (cliente.getCep() != null) cliente.setCep(cliente.getCep().replaceAll("\\W", ""));
        try {
            cliente.setCidade(CidadeFacade.carregarUma(Integer.parseInt(request.getParameter("cidade"))));
        } catch (NumberFormatException e) {
            cliente.setCidade(null);
        }
           
        return cliente;
    }
    
    private Mensagem formValido(HttpServletRequest request) {
        Cliente cliente = carregarCliente(request);
        Mensagem mensagem = null;
        
        if (cliente.getNome() == null || "".equals(cliente.getNome())) {
            mensagem = new Mensagem("Nome é obrigatório !!!");
        } else if (cliente.getNome().length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no nome !!!");
        } else if (cliente.getCpfCnpj() == null || "".equals(cliente.getCpfCnpj())) {
            mensagem = new Mensagem("CPF/CNPJ é obrigatório !!!");
        } else if (cliente.getCpfCnpj().length() <= 11 && !cpfValido(cliente.getCpfCnpj())) {
            mensagem = new Mensagem("CPF inválido !!!");
        } else if (cliente.getCpfCnpj().length() > 11 && !cnpjValido(cliente.getCpfCnpj())) {
            mensagem = new Mensagem("CNPJ inválido !!!");
        } else if (cliente.getTelefoneCelular()== null || "".equals(cliente.getTelefoneCelular())) {
            mensagem = new Mensagem("Telefone/Celular é obrigatório !!!");
        } else if (cliente.getTelefoneCelular().length() != 11  || !ehInteiro(cliente.getCep())) {
            mensagem = new Mensagem("Telefone/Celular inválido !!!");
        } else if (cliente.getEmail()== null || "".equals(cliente.getEmail())) {
            mensagem = new Mensagem("Email é obrigatório !!!");
        } else if (cliente.getEmail().length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no email !!!");
        } else if (!emailValido(cliente.getEmail())) {
            mensagem = new Mensagem("Email inválido !!!");
        } else if (cliente.getSenha()== null || "".equals(cliente.getSenha())) {
            mensagem = new Mensagem("Senha é obrigatória !!!");
        } else if (cliente.getSenha().length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na senha !!!");
        } else if (request.getParameter("confirmacaoSenha") == null || "".equals(request.getParameter("confirmacaoSenha"))) {
            mensagem = new Mensagem("Confirmação de senha é obrigatória !!!");
        } else if (request.getParameter("confirmacaoSenha").length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na confirmação da senha !!!");
        } else if (!cliente.getSenha().equals(request.getParameter("confirmacaoSenha"))) {
            mensagem = new Mensagem("Senha e confirmação diferentes !!!");
        } else if (cliente.getEndereco()== null || "".equals(cliente.getEndereco())) {
            mensagem = new Mensagem("Endereço é obrigatório !!!");
        } else if (cliente.getEndereco().length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no endereço !!!");
        } else if (cliente.getCep()== null || "".equals(cliente.getCep())) {
            mensagem = new Mensagem("CEP é obrigatório !!!");
        } else if (cliente.getCep().length() != 8 || !ehInteiro(cliente.getCep())) {
            mensagem = new Mensagem("CEP inválido !!!");
        } else if (request.getParameter("uf") == null || "".equals(request.getParameter("uf"))) {
            mensagem = new Mensagem("UF é obrigatório !!!");
        } else if (cliente.getCidade() == null) {
            mensagem = new Mensagem("Cidade é obrigatória !!!");
        }
        
        return mensagem;
    }
    
    public boolean cpfValido(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {              
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0         
        // (48 eh a posicao de '0' na tabela ASCII)         
            num = (int)(CPF.charAt(i) - 48); 
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
    
    public static boolean cnpjValido(String CNPJ) {
    // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
            CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
            CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
            CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
            CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
           (CNPJ.length() != 14))
           return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

    // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
    // Calculo do 1o. Digito Verificador
          sm = 0;
          peso = 2;
          for (i=11; i>=0; i--) {
    // converte o i-ésimo caractere do CNPJ em um número:
    // por exemplo, transforma o caractere '0' no inteiro 0
    // (48 eh a posição de '0' na tabela ASCII)
            num = (int)(CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig13 = '0';
          else dig13 = (char)((11-r) + 48);

    // Calculo do 2o. Digito Verificador
          sm = 0;
          peso = 2;
          for (i=12; i>=0; i--) {
            num = (int)(CNPJ.charAt(i)- 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig14 = '0';
          else dig14 = (char)((11-r) + 48);

    // Verifica se os dígitos calculados conferem com os dígitos informados.
          if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
             return(true);
          else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
      }
    
    public static boolean emailValido(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
    public boolean ehInteiro( String s ) {
        // cria um array de char
        char[] c = s.toCharArray();
        boolean d = true;
        for ( int i = 0; i < c.length; i++ ) {
            // verifica se o char não é um dígito
            if ( !Character.isDigit( c[ i ] ) ) {
                d = false;
                break;
            }
        }
        return d;
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
