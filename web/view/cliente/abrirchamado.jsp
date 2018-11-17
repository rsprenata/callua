<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/select2-4.0.5/dist/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua - Novo chamado</title>
    </head>
    <body>
        <div id="header"><%@ include file="../public/header.jsp" %></div>

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
                                        <label for="exampleFormControlFile1">Arquivos</label>
                                        <input type="file" name="arquivos" class="form-control-file" id="exampleFormControlFile1" multiple>
                                        <small class="text-info">Segure CTRL e selecione os arquivos</small>
                                    </div>
                                    <c:if test="${not empty logado.usuario}">
                                    <div class="form-group">
                                        <label for="">Cliente</label>
                                        <select class="form-control select2" style="width: 100%;" id="cliente" name="cliente">
                                            <option value="">Selecione o cliente...</option>
                                            <c:forEach items="${clientes}" var="cliente">
                                                <option value="${cliente.id}">${cliente.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    </c:if>
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
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/formUfCidades.js"></script>
        <script src="${pageContext.request.contextPath}/resources/select2-4.0.5/dist/js/select2.min.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Chamado');
                }, 100);
                
                $('#cliente').select2();
            });
            
            getCidades('${not empty chamado.endereco.cidade.id ? chamado.endereco.cidade.id: 0}');
            
            $(document).ready(function(){
                <c:if test="${not empty logado.usuario}">
                    $('#checkendereco').change(function() {
                        if ($(this).is(':checked')) {
                            var clienteId = $("#cliente").val();
                            if (clienteId === "") {
                                swal({
                                    title: 'Erro!',
                                    text: 'Selecione um cliente',
                                    type: 'error',
                                    confirmButtonText: 'Ok'
                                });
                                $('#checkendereco').prop('checked', false);
                            } else {
                                $.ajax({
                                    url : '${pageContext.request.contextPath}/Cliente?op=carregarAjax', // URL da sua Servlet
                                    data : {
                                        clienteId : clienteId
                                    }, // Parâmetro passado para a Servlet
                                    dataType : 'json',
                                    async: true,
                                    success : function(cliente) {
                                        $("#endereco").val(cliente.endereco.endereco);
                                        $("#cep").val(cliente.endereco.cep).trigger('input');
                                        $("#uf").val(cliente.endereco.cidade.estado.id);
                                        getCidades(cliente.endereco.cidade.id);
                                    },
                                    error : function(request, textStatus, errorThrown) {
                                        alert(request.status + ', Error: ' + request.statusText);
                                         // Erro
                                    }
                                });
                                $('#endereco').attr('readonly', true);
                                $('#cep').attr('readonly', true);
                                $('#uf').attr('readonly', true);
                                $('#cidade').attr('readonly', true);
                            }
                        } else {
                            $('#endereco').attr('readonly', false);
                            $('#cep').attr('readonly', false);
                            $('#uf').attr('readonly', false);
                            $('#cidade').attr('readonly', false);
                        }
                    });
                </c:if>
                <c:if test="${empty logado.usuario}">
                    $('#checkendereco').change(function() {
                        if ($(this).is(':checked')) {
                            $("#endereco").val('${logado.cliente.endereco.endereco}');
                            $("#cep").val('${logado.cliente.endereco.cep}').trigger('input');
                            $("#uf").val('${logado.cliente.endereco.cidade.estado.id}');
                            getCidades('${logado.cliente.endereco.cidade.id}');
                            /*setTimeout(() => {
                                $("#cidade").val('${logado.cliente.endereco.cidade.id}');
                            }, 100);*/
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
                </c:if>
                
                $("#formAbrirChamado").validate({
                    rules: {
                        titulo: {
                            required: true,
                            maxlength: 128
                        },
                        descricao: {
                            required: true,
                            maxlength: 1024
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
                        titulo: {
                            required: "Título é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no título !!!"
                        },
                        descricao: {
                            required: "Descrição é obrigatória !!!",
                            maxlength: "No máximo 1024 caracteres na descrição !!!"
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
