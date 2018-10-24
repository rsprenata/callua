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
        <title>Callua - Novo chamado</title>
    </head>
    <body>
        <div id="header"></div>

        <main role="main">
            <div class="py-5 bg-light">
            <div class="container">
                <div class="row">
                <div class="col-md-12 order-md-1">
                    <!-- <h4 class="mb-3">Novo Chamado</h4> -->
                    <form id="formAbrirChamado" action="${pageContext.request.contextPath}/Chamado?op=abrir" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="titulo">Título</label>
                                <input type="text" id="titulo" name="titulo" class="form-control" placeholder="Título">
                            </div>
                            <div class="form-group">
                                <label for="descricao">Descrição</label>
                                <textarea type="text" id="descricao" name="descricao" class="form-control" placeholder="Descrição" rows="3"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="endereco">Endereço do serviço</label>
                                <div class="form-check float-right">
                                    <input class="form-check-input" type="checkbox" id="checkendereco" name="checkendereco">
                                    <label class="form-check-label" for="checkendereco">Endereço cadastrado</label>
                                </div>
                                <input type="text" id="endereco" class="form-control" placeholder="Endereço">
                            </div>
                            <div class="row">
                                <div class="form-group col-md-3">
                                    <label for="cep">CEP</label>
                                    <input type="text" id="cep" name="cep" class="form-control cep" placeholder="CEP">
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="uf">UF</label>
                                    <select class="form-control" id="uf" name="uf">
                                        <option value="">UF</option>
                                        <c:forEach items="${estados}" var="estado">
                                            <option value="${estado.id}" ${estado.id == idEstado ? 'selected' : ''}>${estado.uf}</option>
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

        </main>
        <div id="footer"></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/formUfCidades.js"></script>
        <%@ include file="initializeJS.jsp" %>
        <script> 
            $(function(){
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Chamado');
                }, 100);
            });
            
            $(document).ready(function(){
                $('#checkendereco').change(function() {
                    if ($(this).is(':checked')) {
                        $("#endereco").val('${logado.cliente.endereco.endereco}');
                        $("#cep").val('${logado.cliente.endereco.cep}').trigger('input');
                        $("#uf").val('${logado.cliente.endereco.cidade.estado.id}');
                        getCidades();
                        setTimeout(() => {
                            $("#cidade").val('${logado.cliente.endereco.cidade.id}');
                        }, 100);
                        $('#endereco').attr('readonly', true);
                        $('#cep').attr('readonly', true);
                        $('#uf').attr('readonly', true);
                        $('#cidade').attr('readonly', true);
                    } else {
                        $('#endereco').attr('readonly', false);
                        $('#cep').attr('readonly', false);
                        $('#uf').attr('readonly', false);
                        $('#cidade').attr('readonly', false);
                    }
                });
                
                /*$("#formAbrirChamado").validate({
                    rules: {
                        titulo: {
                            required: true,
                            maxlength: 128
                        },
                        descricao: {
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
                        },
                        senha: {
                            required: true,
                            maxlength: 128
                        },
                        confirmacaoSenha: {
                            equalTo: "#senha",
                            required: true,
                            maxlength: 128
                        },
                        endereco: {
                            required: true,
                            maxlength: 128
                        },
                        cep: {
                            required: true,
                            minlength: 9,
                            maxlength: 9
                        },
                        uf: "required",
                        cidade: "required"
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
                        },
                        senha: {
                            required: "Senha é obrigatória !!!",
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        },
                        confirmacaoSenha: {
                            equalTo: "Senha e confirmação diferentes !!!",
                            required: "Confirmação de senha é obrigatória !!!",
                            maxlength: "No máximo 128 caracteres na confirmação da senha !!!"
                        },
                        endereco: {
                            required: "Endereço é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no endereço !!!"
                        },
                        cep: {
                            required: "CEP é obrigatório !!!",
                            minlength: "CEP inválido !!!",
                            maxlength: "CEP inválido !!!"
                        },
                        uf: "UF é obrigatório !!!",
                        cidade: "Cidade é obrigatória !!!"
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });*/
            });
        </script>
    </body>
</html>
