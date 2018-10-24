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
                                            <input type="text" name="cpfCnpj" class="form-control cpfCnpj" value="${cliente.cpfCnpj}" placeholder="CPF/CNPJ">
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
                                        <input type="text" id="endereco" name="endereco" class="form-control" value="${cliente.endereco}" placeholder="Endereço">
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-3">
                                            <label for="cep">CEP</label>
                                            <input type="text" name="cep" class="form-control cep" value="${cliente.cep}" placeholder="CEP">
                                        </div>
                                        <div class="form-group col-md-3">
                                            <label for="selectUf">UF</label>
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
        <script> 
            $(function(){
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Novo Cliente');
                }, 100);
            });
            
            jQuery.validator.addMethod("cpfValido", function(value, element) {
                var cpf = $(element).cleanVal();
                if(cpf.length <= 11){
                    while(cpf.length < 11) cpf = "0"+ cpf;
                    var expReg = /^0+$|^1+$|^2+$|^3+$|^4+$|^5+$|^6+$|^7+$|^8+$|^9+$/;
                    var a = [];
                    var b = new Number;
                    var c = 11;
                    for (i=0; i<11; i++){
                        a[i] = cpf.charAt(i);
                        if (i < 9) b += (a[i] * --c);
                    }
                    if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
                    b = 0;
                    c = 11;
                    for (y=0; y<10; y++) b += (a[y] * c--);
                    if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }

                    var retorno = true;
                    if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10]) || cpf.match(expReg)) retorno = false;

                    return this.optional(element) || retorno;
                } else {
                    return true;
                }
            }, "CPF inválido !!!");
            
            jQuery.validator.addMethod("cnpjValido", function(value, element) {
                var cnpj = $(element).cleanVal();
                if(cnpj.length > 11){
                    if(cnpj == '') return false;

                    if (cnpj.length != 14)
                        return false;

                    // Elimina CNPJs invalidos conhecidos
                    if (cnpj == "00000000000000" || 
                        cnpj == "11111111111111" || 
                        cnpj == "22222222222222" || 
                        cnpj == "33333333333333" || 
                        cnpj == "44444444444444" || 
                        cnpj == "55555555555555" || 
                        cnpj == "66666666666666" || 
                        cnpj == "77777777777777" || 
                        cnpj == "88888888888888" || 
                        cnpj == "99999999999999")
                        return false;

                    // Valida DVs
                    tamanho = cnpj.length - 2
                    numeros = cnpj.substring(0,tamanho);
                    digitos = cnpj.substring(tamanho);
                    soma = 0;
                    pos = tamanho - 7;
                    for (i = tamanho; i >= 1; i--) {
                      soma += numeros.charAt(tamanho - i) * pos--;
                      if (pos < 2)
                            pos = 9;
                    }
                    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                    if (resultado != digitos.charAt(0))
                        return false;

                    tamanho = tamanho + 1;
                    numeros = cnpj.substring(0,tamanho);
                    soma = 0;
                    pos = tamanho - 7;
                    for (i = tamanho; i >= 1; i--) {
                      soma += numeros.charAt(tamanho - i) * pos--;
                      if (pos < 2)
                            pos = 9;
                    }
                    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
                    if (resultado != digitos.charAt(1))
                          return false;

                    return true;
                } else {
                    return true;
                }
            }, "CNPJ inválido !!!");
            
            $(document).ready(function(){
                $("#uf").change(function() {
                  getCidades();
                });
                
                $('.cep').mask('00000-000');
                
                $('.telefoneCelular').mask('(00) 0 0000-0000');
                
                $(".cpfCnpj").keydown(function(){
                    $(this).unmask();

                    var tamanho = $(this).val().length;

                    if(tamanho < 11){
                        $(this).mask("999.999.999-99");
                    } else {
                        $(this).mask("99.999.999/9999-99");
                    }

                    var elem = this;
                    setTimeout(function(){
                        elem.selectionStart = elem.selectionEnd = 10000;
                    }, 0);
                    var currentValue = $(this).val();
                    $(this).val('');
                    $(this).val(currentValue);
                });
                
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
        
                <c:if test="${not empty mensagem}">
                    swal({
                        title: '${mensagemTipo == "error" ? "Erro" : "Sucesso"}!',
                        text: '${mensagem}',
                        type: '${mensagemTipo}',
                        confirmButtonText: 'Ok'
                    });
                </c:if>
            });
            
            function getCidades(){
                var idEstado = $("#uf").val();
                if (idEstado) {
                    var url = "AJAXServlet";
                    $.ajax({
                        url : url, // URL da sua Servlet
                        data : {
                            idEstado : idEstado
                        }, // Parâmetro passado para a Servlet
                        dataType : 'json',
                        success : function(data) {
                            // Se sucesso, limpa e preenche a combo de cidade
                            // alert(JSON.stringify(data));
                            $("#cidade").empty();
                            $("#cidade").append('<option value="">Cidade</option>');
                            $.each(data, function(i, obj) {
                                var option = '<option value=' + obj.id;
                                var clienteCidadeId = '${cliente.cidade.id}';
                                if (clienteCidadeId != '' && obj.id == clienteCidadeId) {
                                    option += ' selected ';
                                }
                                option += '>' + obj.nome + '</option>';
                                $("#cidade").append(option);
                            });
                        },
                        error : function(request, textStatus, errorThrown) {
                            alert(request.status + ', Error: ' + request.statusText);
                             // Erro
                        }
                    });
                }
            } 
            getCidades();
        </script>
    </body>
</html>
