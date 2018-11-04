<%-- 
    Document   : header
    Created on : Oct 22, 2018, 4:36:27 PM
    Author     : renata
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="navbar navbar-expand-sm navbar-dark bg-secondary shadow-sm">
    <div class="container d-flex justify-content-between">
        <a href="${sessionScope.logado == null ? pageContext.request.contextPath.concat('/view/public/index.jsp') : 'Login?op=dashboard'}" class="navbar-brand d-flex align-items-center">
            <strong>Callua System</strong>
        </a>
        <span class="navbar-text titulo-header"></span>
        <c:choose>
            <c:when test="${sessionScope.logado == null}">
                <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/view/public/login.jsp">Entrar</a>
            </c:when>
            <c:when test="${sessionScope.logado.cliente != null}">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="logadoDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${logado.cliente.nome}</a>
                        <div class="dropdown-menu" aria-labelledby="logadoDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Chamado?op=abrirForm">Abrir chamado</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Cliente?op=dadosForm">Dados cadastrais</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Chamado?op=meus">Meus chamados</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Login?op=logout">Sair</a>
                        </div>
                    </li>
                </ul>
            </c:when>
        </c:choose>
    </div>
    </div>
    
</header>