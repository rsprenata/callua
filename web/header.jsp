<%-- 
    Document   : header
    Created on : Oct 22, 2018, 4:36:27 PM
    Author     : renata
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="navbar navbar-dark bg-secondary shadow-sm">
    <div class="container d-flex justify-content-between">
        <a href="index.jsp" class="navbar-brand d-flex align-items-center">
            <strong>Callua System</strong>
        </a>
        <span class="navbar-text titulo-header"></span>
        <c:if test="${sessionScope.logado == null}">
            <a class="btn btn-outline-light" href="login.jsp">Entrar</a>
        </c:if>
    </div>
    </div>
</header>