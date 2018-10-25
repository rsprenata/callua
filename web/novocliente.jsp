<%-- 
    Document   : novocliente
    Created on : 05/09/2018, 14:00:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div id="header"></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 order-md-1">
                            <form id="formCadastro" action="${pageContext.request.contextPath}/Cliente?op=cadastrar" method="POST">
                                <fieldset>
                                    <div class="form-group">
                                        <label for="nome">Nome</label>
                                        <input type="text" name="nome" class="form-control" placeholder="Nome" value="${cliente.nome}" autofocus>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-4">
                                            <label for="cpf">CPF/CNPJ</label>
                                            <input type="text" id="cpfCnpj" name="cpfCnpj" class="form-control cpfCnpj" value="${cliente.cpfCnpj}" placeholder="CPF/CNPJ">
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label for="telefone">Telefone/Celular</label>
                                            <input type="text" name="telefoneCelular" class="form-control telefoneCelular" value="${cliente.telefoneCelular}" placeholder="Telefone/Celular">
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label for="email">Email</label>
                                            <input type="email" name="email" class="form-control" value="${cliente.email}" placeholder="Email">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label for="senha">Senha</label>
                                            <input type="password" id="senha" name="senha" class="form-control" placeholder="Senha">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="senha">Confirmação de senha</label>
                                            <input type="password" name="confirmacaoSenha" class="form-control" placeholder="Confirmação de senha">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="endereco">Endereço</label>
                                        <input type="text" id="endereco" name="endereco" class="form-control" value="${cliente.endereco.endereco}" placeholder="Endereço">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-3">
                                            <label for="cep">CEP</label>
                                            <input type="text" name="cep" class="form-control cep" value="${cliente.endereco.cep}" placeholder="CEP">
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label for="selectUf">UF</label>
                                            <select class="form-control" id="uf" name="uf">
                                                <option value="">UF</option>
                                                <c:forEach items="${estados}" var="estado">
                                                    <option value="${estado.id}" ${estado.id == cliente.endereco.cidade.estado.id ? 'selected' : ''}>${estado.uf}</option>
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
                    $('header .titulo-header').text('Novo Cliente');
                }, 100);
            });
            
            getCidades('${not empty cliente.endereco.cidade.id ? cliente.endereco.cidade.id: 0}');
            
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
                });
            });
        </script>
    </body>
</html>
