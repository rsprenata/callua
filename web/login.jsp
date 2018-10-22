<%-- 
    Document   : login
    Created on : 05/09/2018, 13:52:58
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.js"></script>
        <script> 
            $(function(){
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Login');
                }, 100);
            });
        </script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-grid.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-reboot.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <title>Callua System</title>
    </head>
    <body>
        <div id="header"></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <fieldset class="login">
                            <div class="form-group text-center">
                                <a type="submit" class="btn btn-default btn-light" id="novo" name="novo" href="novocliente.jsp" style="background-color: #e68f39;">Sou novo aqui</a>
                            </div>
                            <div class="form-group">
                                <label for="login">CPF/CNPJ</label>
                                <input type="text" id="login" name="login" class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="senha">Senha</label>
                                <input type="password" id="senha" name="senha" class="form-control">
                            </div>
                            <div class="form-group text-center">
                                <button type="submit" class="btn btn-success" id="entrar" name="entrar">Entrar</button>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"></div>
    </body>
</html>
