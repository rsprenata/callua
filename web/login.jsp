<%-- 
    Document   : login
    Created on : 05/09/2018, 13:52:58
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
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
                                <a class="btn btn-default btn-light" href="${pageContext.request.contextPath}/Cliente?op=cadastrarForm" style="background-color: #e68f39;" data-toggle="tooltip" data-placement="right" title="Clique para se cadastrar">Sou novo aqui</a>
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
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script> 
            $(function(){
                $('[data-toggle="tooltip"]').tooltip();
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Login');
                }, 100);
            });

            $(document).ready(function(){
                <c:if test="${not empty mensagem}">
                    swal({
                        title: '${mensagemTipo == "error" ? "Erro" : "Sucesso"}!',
                        text: '${mensagem}',
                        type: '${mensagemTipo}',
                        confirmButtonText: 'Ok'
                    });
                    getCidades();
                </c:if>
            });
        </script>
    </body>
</html>
