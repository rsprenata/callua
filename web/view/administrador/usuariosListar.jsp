<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt-BR" />  
<c:if test="${sessionScope.logado == null || sessionScope.logado.usuario == null || !sessionScope.logado.usuario.administrador}">
    <jsp:useBean id="mensagem" class="com.callua.util.Mensagem">
        <jsp:setProperty name="mensagem" property="texto" value="Acesso não autorizado"/>
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/DataTables/datatables.min.css">
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
                     <h4 class="mb-3">Técnicos</h4>
                    <div class="form-group">
                        <a class="btn btn-success" href="${pageContext.request.contextPath}/Tecnico?op=cadastrarForm">Novo Técnico</a>
                    </div>
                    <table id="tableUsuarios" class="table">
                        <thead>
                            <tr>
                                <th scope="col">Nome</th>
                                <th scope="col">CPF/CNPJ</th>
                                <th scope="col">Telefone/Celular</th>
                                <th scope="col">Email</th>
                                <th scope="col">Administrador</th>
                                <th scope="col">Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios}" var="usuario">
                            <tr>
                                <td scope="row">${usuario.nome}</td>
                                <td scope="row" class="cpfCnpj">${usuario.cpfCnpj}</td>
                                <td scope="row" class="telefoneCelular">${usuario.telefoneCelular}</td>
                                <td scope="row">${usuario.email}</td>
                                <td scope="row">${usuario.administrador ? 'Sim' : 'Não'}</td>
                                <td scope="row">    
                                    <a href="Tecnico?op=editarForm&usuarioId=${usuario.id}" class="btn btn-link" style="color: #0056b3;"><i class="fas fa-edit"></i></a>
                                    <button class="btn btn-link btnRemover" onclick="confirmarRemover('${usuario.id}')"><i class="fas fa-trash"></i></button>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
            </div>

        </main>
            
        <div class="modal fade" id="modalConfirmarRemover" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Confirmar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Deseja realmente remover este cliente?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <a id="linkRemover" class="btn btn-primary">Confirmar</a>
                    </div>
                </div>
            </div>
        </div>
                    
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/DataTables/datatables.min.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script>
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Técnicos');
                }, 100);
            });
            
            $(document).ready( function () {
                var table = $('#tableUsuarios').DataTable({
                    "language": {
                        "url": "${pageContext.request.contextPath}/resources/DataTables/Portuguese-Brasil.json"
                    }
                });
            } );
            
            function confirmarRemover(usuarioId) {
                $('#modalConfirmarRemover #linkRemover').attr("href", "Tecnico?op=remover&usuarioId=" + usuarioId);
                $('#modalConfirmarRemover').modal("show");
            }
        </script>
    </body>
</html>
