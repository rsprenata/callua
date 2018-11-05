<%-- 
    Document   : cadastrartecnico
    Created on : 05/09/2018, 15:14:07
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua - Novo Cliente</title>
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
                                    <form id="formCadastro" action="${pageContext.request.contextPath}/Tecnico?op=cadastrar" method="POST">
                                        <fieldset>
                                            <div class="form-group">
                                                <label for="nome">Nome</label>
                                                <input type="text" name="nome" class="form-control" placeholder="Nome" value="${tecnico.nome}" autofocus>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label for="cpf">CPF/CNPJ</label>
                                                    <input type="text" id="cpfCnpj" name="cpfCnpj" class="form-control cpfCnpj" value="${tecnico.cpfCnpj}" placeholder="CPF/CNPJ">
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label for="telefone">Telefone/Celular</label>
                                                    <input type="text" name="telefoneCelular" class="form-control telefoneCelular" value="${tecnico.telefoneCelular}" placeholder="Telefone/Celular">
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label for="email">Email</label>
                                                    <input type="email" name="email" class="form-control" value="${tecnico.email}" placeholder="Email">
                                                </div>
                                            </div>
                                                <div class="form-group">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" id="checkadministrador" name="administrador">
                                                    <label class="form-check-label" for="checkadministrador">Administrador</label>
                                                </div>
                                                </div>
                                            <button type="submit" class="btn btn-success float-right" id="cadastrar" name="cadastrar">Salvar</button>
                                        </fieldset>
                                    </form>
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
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Técnico');
                }, 100);
            });
            
            $(document).ready(function(){
                $("#formCadastro").validate({
                    rules: {
                        nome: {
                            required: true,
                            maxlength: 128
                        },
                        cpfCnpj: {
                            required: true,
                            cpfValido: true,
                            cnpjValido: true
                        },
                        telefoneCelular : {
                            required: true,
                            minlength: 16,
                            maxlength: 16
                        },
                        email:  {
                            required: true,
                            email: true,
                            maxlength: 128
                        }
                    },
                    messages: {
                        nome: {
                            required: "Nome é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no nome !!!"
                        },
                        cpfCnpj: {
                            required: "CPF/CNPJ é obrigatório !!!"
                        },
                        telefoneCelular : {
                            required: "Telefone/Celular é obrigatório !!!",
                            minlength: "Telefone/Celular inválido !!!",
                            maxlength: "Telefone/Celular inválido !!!"
                        },
                        email : {
                            required: "Email é obrigatório !!!",
                            email: "Email inválido !!!",
                            maxlength: "No máximo 128 caracteres no email !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
            });
        </script>
    </body>
</html>
