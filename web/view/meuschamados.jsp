<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null || (sessionScope.logado.usuario == null || !sessionScope.logado.usuario.administrador) && (sessionScope.logado.cliente == null)}">
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua - Meus chamados</title>
    </head>
    <body>
        <div id="header"><%@ include file="header.jsp" %></div>

        <main role="main">
            <div class="py-5 bg-light">
            <div class="container">
                <div class="row">
                <div class="col-md-12 order-md-1">
                    <!-- <h4 class="mb-3">Novo Chamado</h4> -->
                    <div class="card">
                        <div class="card-body">
                            <form id="formAbrirChamado" action="${pageContext.request.contextPath}/Chamado?op=abrir" method="POST" enctype="multipart/form-data">
                                <fieldset>
                                    <div class="form-group">
                                        <label for="titulo">Título</label>
                                        <input type="text" id="titulo" name="titulo" class="form-control" placeholder="Título" autofocus value="${chamado.titulo}">
                                    </div>
                                    <div class="form-group">
                                        <label for="descricao">Descrição</label>
                                        <textarea type="text" id="descricao" name="descricao" class="form-control" placeholder="Descrição" rows="3">${chamado.descricao}</textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlFile1">Example file input</label>
                                        <input type="file" name="arquivos" class="form-control-file" id="exampleFormControlFile1" multiple>
                                        <small class="text-info">Segure CTRL e selecione os arquivos</small>
                                    </div>
                                    <div class="form-group">
                                        <label for="endereco">Endereço do serviço</label>
                                        <div class="form-check float-right">
                                            <input class="form-check-input" type="checkbox" id="checkendereco" name="checkendereco">
                                            <label class="form-check-label" for="checkendereco">Endereço cadastrado</label>
                                        </div>
                                        <input type="text" id="endereco" name="endereco" class="form-control" placeholder="Endereço" value="${chamado.endereco.endereco}">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-3">
                                            <label for="cep">CEP</label>
                                            <input type="text" id="cep" name="cep" class="form-control cep" placeholder="CEP" value="${chamado.endereco.cep}">
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label for="uf">UF</label>
                                            <select class="form-control" id="uf" name="uf">
                                                <option value="">UF</option>
                                                <c:forEach items="${estados}" var="estado">
                                                    <option value="${estado.id}" ${estado.id == chamado.endereco.cidade.estado.id ? 'selected' : ''}>${estado.uf}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="cidade">Cidade</label>
                                            <select class="form-control" id="cidade" name="cidade">
                                                <option value="">Cidade</option>
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-success float-right" id="abrir" name="abrir">Abrir chamado</button>
                                </fieldset>
                            </form>
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
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Meus chamados');
                }, 100);
            });
        </script>
    </body>
</html>
