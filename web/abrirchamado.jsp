<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
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
                    <form>
                        <fieldset>
                            <div class="form-group">
                                <label for="titulo">Título</label>
                                <input type="text" id="titulo" name="titulo" class="form-control" placeholder="Título">
                            </div>
                            <div class="form-group">
                                <label for="descricao">Descrição</label>
                                <input type="text" id="descricao" name="descricao" class="form-control" placeholder="Descrição">
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
        <script> 
            $(function(){
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Chamado');
                }, 100);
            });
        </script>
    </body>
</html>
