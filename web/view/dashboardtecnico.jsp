<%-- 
    Document   : dashboardadmin
    Created on : 05/09/2018, 14:20:59
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null || sessionScope.logado.usuario == null}">
    <jsp:useBean id="mensagem" class="com.callua.util.Mensagem">
        <jsp:setProperty name="mensagem" property="texto" value="Acesso não autorizado"/>
        <jsp:setProperty name="mensagem" property="tipo" value="error"/>
    </jsp:useBean>
    <c:set var="mensagem" value="${mensagem}" scope="session" />
    <jsp:forward page="Login?op=dashboard" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Callua - Dashboard</title>
    </head>
    <body>
        <h1>Olá, técnico!</h1>
        <!--os dados dessa tabela tem que vir do banco 
        e ao clicar em cada chamado deve abrir edicao
        -->
        <table>
            <tr>
              <th>Chamados em aberto</th>
              <th>Chamados resolvidos</th>
            </tr>
            <tr>
              <td>Bla bla bla</td>
              <td>Bla bla bla</td>
            </tr>
            <tr>
              <td>Bla bla bla</td>
              <td>Bla bla bla</td>
            </tr>
            <tr>
              <td>Bla bla bla</td>
            </tr>
         </table>
    </body>
</html>
