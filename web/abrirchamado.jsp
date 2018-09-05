<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novo chamado</title>
    </head>
    <body>
        <h1>Novo chamado</h1>
        <form action="">
            Título:</br>
            <input type="text" name="titulo"><br>
            Descrição:</br> 
            <input type="text" name="descricao"><br>
            Endereço do serviço: 
            <form action="">
                <input type="checkbox" name="endcadastrado" value="endcadastrado">Endereço cadastrado<br>
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
            <input type="submit" value="Abrir chamado">
        </form>
    </body>
</html>
