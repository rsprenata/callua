<%-- 
    Document   : cadastrartecnico
    Created on : 05/09/2018, 15:14:07
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null || sessionScope.logado.usuario == null || !sessionScope.logado.usuario.administrador}">
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <title>Callua - Novo Técnico</title>
    </head>
    <body>
        <div id="header"></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 order-md-1">
                            <form>
                                <fieldset>
                                    <div class="form-group">
                                        <label for="nome">Nome</label>
                                        <input type="text" id="nome" name="nome" class="form-control" placeholder="Nome">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label for="cpf">CPF/CNPJ</label>
                                            <input type="text" id="cpf" name="cpf" class="form-control" placeholder="CPF/CNPJ">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="telefone">Telefone/Celular</label>
                                            <input type="text" id="telefone" name="telefone" class="form-control" placeholder="Telefone/Celular">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="text" id="email" name="email" class="form-control" placeholder="Email">
                                    </div>
                                    <div class="form-group">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="checkendereco" name="checkendereco">
                                            <label class="form-check-label" for="checkendereco">Endereço cadastrado</label>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-success float-right" id="cadastrar" name="cadastrar">Cadastrar</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script> 
            $(function(){
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Técnico');
                }, 100);
            });
        </script>
    </body>
</html>

