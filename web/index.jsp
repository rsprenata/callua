<%-- 
    Document   : inicial
    Created on : 05/09/2018, 13:44:55
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
                        <div class="col-md-12 order-md-1">
                            <h4 class="mb-3">Serviços oferecidos:</h4>
                            <div class="row menu-options">
                                <button type="button" class="btn btn-light"><i class="fas fa-3x fa-mobile-alt"></i>Troca de tela de celular</button>
                            </div>
                            <div class="row menu-options">
                                <button type="button" class="btn btn-light"><i class="fab fa-3x fa-apple"></i>Assistencia Apple</button>
                            </div>
                            <div class="row menu-options abrir-chamado">
                                <button type="button" class="btn btn-secondary" id="abrir" name="abrir">Abrir chamado</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"></div>
    </body>
</html>
