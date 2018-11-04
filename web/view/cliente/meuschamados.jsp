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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua - Meus chamados</title>
    </head>
    <body>
        <div id="header"><%@ include file="../public/header.jsp" %></div>

        <main role="main">
            <div class="py-5 bg-light">
            <div class="container">
                <div class="row">
                <div class="col-md-12 order-md-1">
                    <!-- <h4 class="mb-3">Novo Chamado</h4> -->
                    <div class="form-group">
                        <a type="button" class="btn btn-success" href="${pageContext.request.contextPath}/Chamado?op=abrirForm">Abrir chamado</a>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <div class="row text-center">
                                <div class="col-md-5">
                                    <h5 class="card-title">Chamados em aberto</h5>
                                    <c:forEach items="${chamadosAbertos}" var="chamado">
                                        <button type="button" onclick="visualizarChamado('${chamado.id}')" class="btn btn-light btn-block btn-lg" data-toggle="tooltip" data-placement="right" title="Clique no chamado para abrir e ter mais opções">
                                            <h5 class="card-title">Chamado ${chamado.id}</h5>
                                            <h6 class="card-subtitle mb-2 text-muted">${chamado.titulo}</h6>
                                            <h6 class="card-text">Cliente: ${chamado.cliente.nome}</h6>
                                        </button>
                                    </c:forEach>
                                </div>
                                <div class="offset-md-2 col-md-5">
                                    <h5 class="card-title">Chamado resolvidos</h5>
                                    <c:forEach items="${chamadosResolvidos}" var="chamado">
                                        <button type="button" onclick="visualizarChamado('${chamado.id}')" class="btn btn-light btn-block btn-lg" data-toggle="tooltip" data-placement="right" title="Clique no chamado para abrir e ter mais opções">
                                            <h5 class="card-title">Chamado ${chamado.id}</h5>
                                            <h6 class="card-subtitle mb-2 text-muted">${chamado.titulo}</h6>
                                            <h6 class="card-text">Cliente: ${chamado.cliente.nome}</h6>
                                        </button>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
            </div>

        </main>
                    
        <div class="modal fade" id="modalVisualizarChamado" tabindex="-1" role="dialog" aria-labelledby="modalVisualizarChamadoLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalVisualizarChamadoLabel">Chamado <p id="mpChamado" class="d-inline"></p></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <b>Cliente: </b><p id="mpCliente" class="d-inline"></p>
                            </div>
                            <div class="col-md-6">
                                <b>Situação: </b><p id="mpSituacao" class="d-inline"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <b>Título: </b><p id="mpTitulo" class="d-inline"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <b>Endereço: </b><p id="mpEndereco" class="d-inline"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="mpDescricao"><b>Descrição:</b></label>
                                    <textarea class="form-control" id="mpDescricao" rows="3" readonly></textarea>
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
                                                        </div>
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
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                            <div class="col-md-2 offset-md-10">
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary btn-block">Comentar</button>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-success btn-block" data-dismiss="modal">Fechar</button>
                                </div>
                            </div>
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
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Meus chamados');
                }, 100);
            });
            
            function visualizarChamado(idChamado){
                var url = "Chamado";
                $.ajax({
                    url : url, // URL da sua Servlet
                    data : {
                        op: "carregarViaAjax",
                        idChamado : idChamado
                    }, // Parâmetro passado para a Servlet
                    dataType : 'json',
                    success : function(data) {
                        $('#mpChamado').html(data.id);
                        $('#mpCliente').html(data.cliente.nome);
                        $('#mpSituacao').html(data.status);
                        $('#mpTitulo').html(data.titulo);
                        $('#mpEndereco').html(data.endereco.endereco);
                        $('#mpDescricao').html(data.descricao);
                        $('#modalVisualizarChamado').modal("show");
                    },
                    error : function(request, textStatus, errorThrown) {
                        alert(request.status + ', Error: ' + request.statusText);
                         // Erro
                    }
                });
            } 
        </script>
    </body>
</html>
