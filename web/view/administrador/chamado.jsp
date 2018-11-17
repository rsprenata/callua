<%-- 
    Document   : chamado
    Created on : 05/09/2018, 14:32:37
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null || sessionScope.logado.usuario == null}">
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
                                    <div class=" col-md-12">
                                        <b>Título: </b><p class="d-inline">${chamado.titulo}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class=" col-md-12">
                                        <b>Endereço: </b><p class="d-inline">${chamado.endereco.endereco}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <c:if test="${chamado.status != 'RESOLVIDO'}">
                                <c:if test="${logado.usuario.administrador == true}">
                                <div class="row">
                                    <div class="col-md-4 offset-md-8">
                                        <div class="form-group">
                                            <button type="button" class="btn btn-warning btn-block">Atribuir</button>
                                        </div>
                                    </div>
                                </div>
                                </c:if>
                                
                                <div class="row">
                                    <div class="col-md-4 offset-md-8">
                                        <div class="form-group">
                                            <button type="button" class="btn btn-success btn-block" data-toggle="modal" data-target="#modalFecharChamado">Resolver</button>
                                        </div>
                                    </div>
                                </div>
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
                                                        <div class="msg_history">
                                                            <div class="incoming_msg">
                                                                <div class="incoming_msg_img"> <img src="https://cdn2.iconfinder.com/data/icons/person-gender-hairstyle-clothes-variations/48/Female-Side-comb-O-neck-512.png" alt="sunil"> </div>
                                                                <div class="received_msg">
                                                                    <div class="received_withd_msg">
                                                                        <strong>Renata</strong>
                                                                        <p>Quer um torrim amendoado?</p>
                                                                        <span class="time_date"> 11:01 AM    |    June 9</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="outgoing_msg">
                                                                <div class="sent_msg">
                                                                    <strong>Belchior</strong>
                                                                    <p>Não, obrigado, eu sou apenas um rapaz latinoamericano.</p>
                                                                    <span class="time_date"> 11:01 AM    |    June 9</span>
                                                                </div>
                                                           </div>
                                                            <div class="incoming_msg">
                                                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                                                <div class="received_msg">
                                                                    <div class="received_withd_msg">
                                                                        <strong>Renata</strong>
                                                                        <p>Mas se depois de cantar?</p>
                                                                        <span class="time_date"> 11:01 AM    |    Yesterday</span>
                                                                    </div>
                                                                </div>select2-4.0.5/dist/css/select2.min.css
                                                            </div>
                                                            <div class="outgoing_msg">
                                                                <div class="sent_msg">
                                                                    <strong>Belchior</strong>
                                                                    <p>Você ainda quiser me atirar?</p>
                                                                    <span class="time_date"> 11:01 AM    |    Today</span>
                                                                </div>
                                                            </div>
                                                            <div class="incoming_msg">
                                                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                                                <div class="received_msg">
                                                                    <div class="received_withd_msg">
                                                                        <strong>Renata</strong>
                                                                        <p>Mate me logo pois a tarde as três</p>
                                                                        <span class="time_date"> 11:01 AM    |    Today</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="type_msg">
                                                            <div class="input_msg_write">
                                                                <input type="text" class="write_msg" placeholder="Escreva o comentário" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <br/>
                                            <div class="col-md-4 offset-md-8">
                                                <div class="form-group">
                                                    <button type="button" class="btn btn-primary btn-block">Comentar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Materiais utilizados</h5>
                                        <div class="row text-center">
                                            <div class="col-md-12">
                                                <button type="button" class="card-link btn btn-primary" data-toggle="modal" data-target="#modalAdicionarProduto">Adicionar material</button>
                                            </div>
                                        </div>
                                        <br/>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <ul class="list-group">
                                                    <c:forEach items="${chamado.produtos}" var="produto">
                                                    <li class="list-group-item">${produto.descricao} <button class="btn btn-link float-right" onclick="confirmarRemoverProduto('${produto.id}')"><i class="fas fa-trash"></i></button></li>
                                                    </c:forEach>
                                                </ul>
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
                    
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/select2-4.0.5/dist/js/select2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Chamado ${chamado.id}');
                }, 100);
            });
            
            function confirmarRemoverProduto(idProduto) {
                $("#idProdutoRemover").val(idProduto);
                $("#modalRemoverProduto").modal("show");
            }
        </script>
    </body>
</html>