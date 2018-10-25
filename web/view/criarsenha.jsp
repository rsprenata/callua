<%-- 
    Document   : criarsenha
    Created on : 05/09/2018, 15:20:53
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Callua - Confirmar cadastro</title>
    </head>
    <!-- essa pagina eh enviada por email, um link eh gerado na hora
    que o admin cadastra o tecnico-->
    <body>
        <h1>Confirmar cadastro</h1>
        <p>CPF: 00000000000</p>
        <p>Telefone/Celular: 41 00000000</p>
        <p>E-mail: fulaninho@callua.com</p>
        <form>
            Senha:
            <input type="text" name="senha"><br>
            Confirme:
            <input type="text" name="confirmesenha"><br>
            <input type="submit" value="Salvar">
        </form>
    </body>
</html>
