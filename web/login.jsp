<%-- 
    Document   : login
    Created on : 05/09/2018, 13:52:58
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
        <title>Callua System</title>
    </head>
    <body>
        <div id="header"></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <fieldset class="login">
                            <form id="formLogin" action="${pageContext.request.contextPath}/Pessoa?op=logar" method="POST">
                                <div class="form-group text-center">
                                    <a class="btn btn-default btn-light" href="${pageContext.request.contextPath}/Cliente?op=cadastrarForm" style="background-color: #e68f39;" data-toggle="tooltip" data-placement="right" title="Clique para se cadastrar">Sou novo aqui</a>
                                </div>
                                <div class="form-group">
                                    <label for="login">CPF/CNPJ</label>
                                    <input type="text" name="cpfCnpj" class="form-control cpfCnpj">
                                </div>
                                <div class="form-group">
                                    <label for="senha">Senha</label>
                                    <input type="password" name="senha" class="form-control">
                                </div>
                                <div class="form-group text-center">
                                    <button type="submit" class="btn btn-success" id="entrar" name="entrar">Entrar</button>
                                </div>
                            </form>
                        </fieldset>
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
                $('[data-toggle="tooltip"]').tooltip();
                $("#header").load("header.jsp"); 
                $("#footer").load("footer.jsp");
                setTimeout(() => {
                    $('header .titulo-header').text('Login');
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
                
                $("#formLogin").validate({
                    rules: {
                        cpfCnpj: {
                            required: true,
                            cpfValido: true,
                            cnpjValido: true
                        },
                        senha: {
                            required: true,
                            maxlength: 128
                        }
                    },
                    messages: {
                        cpfCnpj: {
                            required: "CPF/CNPJ é obrigatório !!!"
                        },
                        senha: {
                            required: "Senha é obrigatória !!!",
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        }
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
        </script>
    </body>
</html>
