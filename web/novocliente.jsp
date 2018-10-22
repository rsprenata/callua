<%-- 
    Document   : novocliente
    Created on : 05/09/2018, 14:00:30
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
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Cliente');
                }, 100);
            });
        </script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-grid.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-reboot.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <title>Callua - Novo Cliente</title>
    </head>
    <body>
        <div id="header"></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 order-md-1">
                            <form>
                                <fieldset>
                                    <div class="form-group">
                                        <label for="nome">Nome</label>
                                        <input type="text" id="nome" name="nome" class="form-control" placeholder="Nome">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-4">
                                            <label for="cpf">CPF/CNPJ</label>
                                            <input type="text" id="cpf" name="cpf" class="form-control" placeholder="CPF/CNPJ">
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label for="telefone">Telefone/Celular</label>
                                            <input type="text" id="telefone" name="telefone" class="form-control" placeholder="Telefone/Celular">
                                        </div>
                                        <div class="form-group col-md-4">
                                            <label for="email">Email</label>
                                            <input type="text" id="email" name="email" class="form-control" placeholder="Email">
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label for="senha">Senha</label>
                                            <input type="password" id="senha" name="senha" class="form-control" placeholder="Senha">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="senha">Confirmação de senha</label>
                                            <input type="password" id="senha" name="senha" class="form-control" placeholder="Senha">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="endereco">Endereço</label>
                                        <input type="text" id="endereco" class="form-control" placeholder="Endereço">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-3">
                                            <label for="cep">CEP</label>
                                            <input type="text" id="cep" name="cep" class="form-control" placeholder="CEP">
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label for="uf">UF</label>
                                            <select id="uf" class="form-control">
                                                <option>UF</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="cidade">Cidade</label>
                                            <select id="cidade" class="form-control">
                                                <option>Cidade</option>
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
    </body>
</html>
