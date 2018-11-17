<%-- 
    Document   : inicial
    Created on : 05/09/2018, 13:44:55
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
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
        <div id="header"><%@ include file="header.jsp" %></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 order-md-1">
                            <h4 class="mb-3">Serviços oferecidos:</h4>
                            <div class="row">
                                <div class="col-sm-6" style="text-align: -webkit-center;">
                                    <div class="card" style="width: 18rem;">
                                        <div style="text-align: center; margin-top: 50px;">
                                            <i class="fas fa-8x fa-mobile-alt"></i>    
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title">Troca de tela de celular</h5>
                                            <p class="card-text">Substituição de tela quebrada causada por qualquer tipo de acidente com o seu celular.</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6" style="text-align: -webkit-center;">
                                    <div class="card" style="width: 18rem;">
                                        <div style="text-align: center; margin-top: 50px;">
                                            <i class="fab fa-8x fa-apple"></i>    
                                        </div>
                                        <div class="card-body">
                                            <h5 class="card-title">Assistencia Apple</h5>
                                            <p class="card-text" style="margin-bottom: 23px;">Assistência Técnica Especializada em Reparos de Smartphones, Tablets e outros produtos Apple.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"><%@ include file="footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <%@ include file="initializeJS.jsp" %>
    </body>
</html>
