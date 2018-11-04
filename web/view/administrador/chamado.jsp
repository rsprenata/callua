<%-- 
    Document   : chamado
    Created on : 05/09/2018, 14:32:37
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Callua - Chamado</title>
    </head>
    <body>
        <h1>Chamado 430</h1>
        <p>Cliente: Receita Federal</p>
        <p>Título: Trocar tonner</p>
        <p>Endereço: Rua fulana de tal</p>
        <p>Descrição: bla bla bla bla bla</p>
        <form action="">
            <input type="text" name="comentar"><br>
            <input type="submit" value="Comentar">
        </form>
        <button type="button">Atribuir</button>
        <button type="button">Atender</button>
        <button type="button">Fechar</button>
        <p>Materiais utilizados:</p>
        <p>Tonner bla bla</p>
        <button type="button">Adicionar material</button>
    </body>
</html>
