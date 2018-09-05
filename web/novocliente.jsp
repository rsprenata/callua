<%-- 
    Document   : novocliente
    Created on : 05/09/2018, 14:00:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Callua - Cadastre-se</title>
    </head>
    <body>
        <h1>Novo Cliente</h1>
        <form action="">
            Nome:</br>
            <input type="text" name="nome"><br>
            CPF/CNPJ:</br> 
            <input type="text" name="cpf"><br>
            Telefone/Celular:</br>
            <input type="text" name="phone"><br>
            E-mail:</br> 
            <input type="text" name="email"><br>
            Senha:</br>
            <input type="text" name="senha"><br>
            Endereço:</br> 
            <input type="text" name="endereco"><br>
            CEP:</br>
            <input type="text" name="cep"><br>
            <select>
                <option value="pr">PR</option>
                <option value="sp">PR</option>
                <option value="pa">PA</option>
            </select>
            <!-- Esses combobox tem que ser feito de outra forma... pegar os estados e cidades do banco -->
            <select>
                <option value="curitiba">Curitiba</option>
                <option value="pinhais">Pinhais</option>
                <option value="piraquara">Piraquara</option>
            </select>
            </br>
            <input type="submit" value="Cadastrar">
        </form>
    </body>
</html>
