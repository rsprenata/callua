<%-- 
    Document   : dashboardadmin
    Created on : 05/09/2018, 14:20:59
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null || sessionScope.logado.usuario == null}">
    <jsp:useBean id="mensagem" class="com.callua.util.Mensagem">
        <jsp:setProperty name="mensagem" property="texto" value="Acesso nÃ£o autorizado"/>
        <jsp:setProperty name="mensagem" property="tipo" value="error"/>
    </jsp:useBean>
    <c:set var="mensagem" value="${mensagem}" scope="session" />
    <jsp:forward page="/Login?op=dashboard" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua</title>
    </head>
    <body>
        <div id="header"><%@ include file="../public/header.jsp" %></div>

        <main role="main">
            <div class="py-5 bg-light">
            <div class="container">
                <div class="row">
                <div class="col-md-12 order-md-1">
                    <div class="card">
                        <div class="card-body">
                            <div class="row text-center">
                                <div class="col-md-5">
                                    <h5 class="card-title">Chamados em aberto</h5>
                                    <div id="chamadosAbertos">
                                        <input class="search form-group" placeholder="Filtrar" />
                                        <ul class="list">
                                        <c:forEach items="${chamadosAbertos}" var="chamado">
                                            <li>
                                            <a href="${pageContext.request.contextPath}/Chamado?op=visualizar&idChamado=${chamado.id}" class="btn btn-light btn-block btn-lg" data-toggle="tooltip" data-placement="right" title="Clique no chamado para abrir e ter mais opções">
                                                <h5 class="card-title id">Chamado ${chamado.id}</h5>
                                                <h6 class="card-subtitle mb-2 text-muted titulo">${chamado.titulo}</h6>
                                                <h6 class="card-text clienteNome">Cliente: ${chamado.cliente.nome}</h6>
                                            </a>
                                            </li>
                                        </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="offset-md-2 col-md-5">
                                    <h5 class="card-title">Chamado resolvidos</h5>
                                    <div id="chamadosResolvidos">
                                        <input class="search form-group" placeholder="Filtrar" />
                                        <ul class="list">
                                        <c:forEach items="${chamadosResolvidos}" var="chamado">
                                            <a href="${pageContext.request.contextPath}/Chamado?op=visualizar&idChamado=${chamado.id}" class="btn btn-light btn-block btn-lg" data-toggle="tooltip" data-placement="right" title="Clique no chamado para abrir e ter mais opções">
                                                <h5 class="card-title id">Chamado ${chamado.id}</h5>
                                                <h6 class="card-subtitle mb-2 text-muted titulo">${chamado.titulo}</h6>
                                                <h6 class="card-text clienteNome">Cliente: ${chamado.cliente.nome}</h6>
                                            </a>
                                        </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
            </div>
        </main>
                    
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/list.js/list.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Olá, Técnico ${logado.usuario.nome}');
                }, 100);
                
                var options = {
                    valueNames: ['id', 'titulo', 'clienteNome']
                };
                new List('chamadosAbertos', options);
                new List('chamadosResolvidos', options);
            });
        </script>
    </body>
</html>
