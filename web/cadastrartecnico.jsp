<%-- 
    Document   : cadastrartecnico
    Created on : 05/09/2018, 15:14:07
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar técnico</title>
    </head>
    <body>
        <h1>Novo técnico</h1>
        <form>
            Nome:</br>
            <input type="text" name="nome"><br>
            CPF:</br>
            <input type="text" name="cpf"><br>
            Telefone/Celular:</br>
            <input type="text" name="phone"><br>
            E-mail:</br>
            <input type="text" name="email"><br>
            <input type="checkbox" name="admin" value="admin">Administrador<br>
            <input type="submit" value="Salvar">
        </form>
    </body>
</html>
