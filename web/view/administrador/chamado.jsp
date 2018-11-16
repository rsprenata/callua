<%-- 
    Document   : chamado
    Created on : 05/09/2018, 14:32:37
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null}">
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
        <title>Callua</title>
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
                            <div class="row">
                            <div class="col-md-7">
                                <div class="row">
                                    <div class="col-md-6">
                                        <b>Cliente: </b><p class="d-inline">${chamado.cliente.nome}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <b>Situação: </b><p class="d-inline">${chamado.status}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class=" col-md-6">
                                        <b>Título: </b><p class="d-inline">${chamado.titulo}</p>
                                    </div>
                                    <c:if test="${not empty chamado.usuario}">
                                    <div class="col-md-6">
                                        <b>Usuário: </b><p class="d-inline">${chamado.usuario.nome}</p>
                                    </div>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class=" col-md-12">
                                        <b>Endereço: </b><p class="d-inline">${chamado.endereco.endereco}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <c:if test="${not empty logado.usuario}">
                                <c:if test="${logado.usuario.administrador == true}">
                                <div class="row">
                                    <div class="col-md-4 offset-md-8">
                                        <div class="form-group">
                                            <button type="button" class="btn btn-warning btn-block" data-toggle="modal" data-target="#modalAtribuirUsuario">Atribuir</button>
                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                <div class="row">
                                    <div class="col-md-4 offset-md-8">
                                        <div class="form-group">
                                            <button type="button" class="btn btn-info btn-block">Atender</button>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${chamado.status != 'RESOLVIDO'}">
                                <div class="row">
                                    <div class="col-md-4 offset-md-8">
                                        <div class="form-group">
                                            <button type="button" class="btn btn-success btn-block" data-toggle="modal" data-target="#modalFecharChamado">Fechar</button>
                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                </c:if>
                            </div>
                            </div>
                            <div class="row">
                            <div class="col-md-7">
                                <div class="row">
                                    <div class=" col-md-12">
                                        <div class="form-group">
                                            <label for="descricao"><b>Descrição:</b></label>
                                            <textarea class="form-control" id="descricao" rows="3" readonly>${chamado.descricao}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class=" col-md-12">
                                        <div class="form-group">
                                            <div class="messaging">
                                                <div class="inbox_msg">
                                                    <div class="mesgs">
                                                        <div class="msg_history" id="chatChamado">
                                                            
                                                        </div>
                                                        <div class="type_msg">
                                                            <div class="input_msg_write">
                                                                <input type="text" id="msg-chat" class="write_msg" placeholder="Escreva o comentário" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <br/>
                                            <div class="col-md-4 offset-md-8">
                                                <div class="form-group">
                                                    <button type="button" class="btn btn-primary btn-block" id="btn-chat">Comentar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">Materiais utilizados</h5>
                                                <c:if test="${not empty logado.usuario}">
                                                <div class="row text-center">
                                                    <div class="col-md-12">
                                                        <button type="button" class="card-link btn btn-primary" data-toggle="modal" data-target="#modalAdicionarProduto">Adicionar material</button>
                                                    </div>
                                                </div>
                                                </c:if>
                                                <br/>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <ul class="list-group">
                                                            <c:forEach items="${chamado.produtos}" var="produto">
                                                                <li class="list-group-item">${produto.descricao} <c:if test="${not empty logado.usuario}"><button class="btn btn-link float-right" onclick="confirmarRemoverProduto('${produto.id}')"><i class="fas fa-trash"></i></button></c:if></li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">Arquivos anexados</h5>
                                                <c:if test="${not empty logado.cliente && chamado.status != 'RESOLVIDO'}">
                                                <div class="row text-center">
                                                    <div class="col-md-12">
                                                        <button type="button" class="card-link btn btn-primary" data-toggle="modal" data-target="#modalAnexarArquivos">Anexar arquivo</button>
                                                    </div>
                                                </div>
                                                </c:if>
                                                <br/>
                                                <c:if test="${chamado.arquivos.size() > 0}">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <ul class="list-group">
                                                            <c:forEach items="${chamado.arquivos}" var="arquivo">
                                                                <li class="list-group-item">${arquivo.name} 
                                                                <c:if test="${not empty logado.cliente && chamado.status != 'RESOLVIDO'}">
                                                                    <button class="btn btn-link float-right" onclick="confirmarRemoverArquivo('${arquivo.absolutePath}')"><i class="fas fa-trash"></i></button>
                                                                </c:if>
                                                                <a target="_blank" href="${pageContext.request.contextPath}/Chamado?op=downloadArquivo&filePath=${arquivo.absolutePath}" class="btn btn-link float-right" style="color: #0056b3;"><i class="fas fa-download"></i></a>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
            </div>

        </main>
               
        <form action="${pageContext.request.contextPath}/Chamado?op=fechar" method="POST">
            <div class="modal fade" id="modalFecharChamado" tabindex="-1" role="dialog" aria-labelledby="modalFecharChamadoLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalFecharChamadoLabel">Confirmação fechar chamado</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Tem certeza que deseja fechar o chamado?
                            <input type="hidden" name="idChamado" value="${chamado.id}"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-success">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                        
        <form action="${pageContext.request.contextPath}/Chamado?op=adicionarProduto" method="POST">
            <div class="modal fade" id="modalAdicionarProduto" tabindex="-1" role="dialog" aria-labelledby="modalAdicionarProdutoLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalAdicionarProdutoLabel">Adicionar produto ao chamado</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idChamado" value="${chamado.id}" />
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="produto">Produto</label>
                                        <select class="form-control select2" style="width: 100%;" id="produto" name="produto">
                                            <option value="">Selecione o produto...</option>
                                            <c:forEach items="${produtos}" var="produto">
                                                <option value="${produto.id}">${produto.descricao}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-success">Adicionar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                            
        <form action="${pageContext.request.contextPath}/Chamado?op=removerProduto" method="POST">
            <div class="modal fade" id="modalRemoverProduto" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">Confirmar</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idChamado" value="${chamado.id}" />
                            <input type="hidden" name="idProduto" id="idProdutoRemover" />
                            Deseja realmente remover este produto?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Confirmar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                        
        <form action="${pageContext.request.contextPath}/Chamado?op=atribuirUsuario" method="POST">
            <div class="modal fade" id="modalAtribuirUsuario" tabindex="-1" role="dialog" aria-labelledby="modalAtribuirUsuarioLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalAtribuirUsuarioLabel">Atribuir usuário ao chamado</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="idChamado" value="${chamado.id}" />
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label for="">Usuário</label>
                                        <select class="form-control select2" style="width: 100%;" id="usuario" name="usuario">
                                            <option value="">Selecione o usuário...</option>
                                            <c:forEach items="${usuarios}" var="usuario">
                                                <option value="${usuario.id}" ${usuario.id == chamado.usuario.id ? 'selected' : ''}>${usuario.nome}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-success">Atribuir</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                            
        <form action="${pageContext.request.contextPath}/Chamado?op=removerArquivo" method="POST">
            <div class="modal fade" id="modalRemoverArquivo" tabindex="-1" role="dialog" aria-labelledby="modalRemoverArquivoLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalRemoverArquivoLabel">Confirmar</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="chamadoId" value="${chamado.id}" />
                            <input type="hidden" name="absolutePath" id="absolutePath" />
                            Deseja realmente remover este arquivo?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Confirmar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                            
        <form action="${pageContext.request.contextPath}/Chamado?op=anexarArquivos" method="POST" enctype="multipart/form-data">
            <div class="modal fade" id="modalAnexarArquivos" tabindex="-1" role="dialog" aria-labelledby="modalAnexarArquivosLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalAnexarArquivos">Anexar Arquivo(s)</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="chamadoId" value="${chamado.id}" />
                            <div class="form-group">
                                <label for="arquivos">Arquivos</label>
                                <input type="file" name="arquivos" class="form-control-file" id="arquivos" multiple>
                                <small class="text-info">Segure CTRL e selecione os arquivos</small>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Anexar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
                    
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/select2-4.0.5/dist/js/select2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/moment.js/moment-with-locales.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/chatChamado.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Chamado ${chamado.id}');
                }, 100);
                $('#produto').select2({
                    dropdownParent: $('#modalAdicionarProduto')
                });
                $('#usuario').select2({
                    dropdownParent: $('#modalAtribuirUsuario')
                });
                
                //CHAT
                var contextPath = '<%=request.getContextPath()%>';
                var chamadoId = '${chamado.id}';
                var logadoPessoa = '${not empty logado.cliente ? 'CLIENTE' : 'USUARIO'}';
                var logadoId = '${not empty logado.cliente ? logado.cliente.id : logado.usuario.id}';
                var scroll = true;
                loopChat(contextPath, chamadoId, logadoPessoa, logadoId);
                $("#btn-chat").click(function () {
                    var mensagem = $('#msg-chat').val();
                    enviarMensagem(contextPath, chamadoId, logadoPessoa, logadoId, mensagem);
                });
            });
            
            function confirmarRemoverProduto(idProduto) {
                $("#idProdutoRemover").val(idProduto);
                $("#modalRemoverProduto").modal("show");
            }
            
            function confirmarRemoverArquivo(absolutePath) {
                $("#absolutePath").val(absolutePath);
                $("#modalRemoverArquivo").modal("show");
            }
        </script>
    </body>
</html>