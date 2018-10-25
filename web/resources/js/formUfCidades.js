$(document).ready(function(){
    $("#uf").change(function() {
      getCidades(0);
    });
});

function getCidades(cidadeSelecionada){
    var idEstado = $("#uf").val();
    if (idEstado) {
        var url = "AJAXServlet";
        $.ajax({
            url : url, // URL da sua Servlet
            data : {
                idEstado : idEstado
            }, // Parâmetro passado para a Servlet
            dataType : 'json',
            async: true,
            success : function(data) {
                // Se sucesso, limpa e preenche a combo de cidade
                // alert(JSON.stringify(data));
                $("#cidade").empty();
                $("#cidade").append('<option value="">Cidade</option>');
                $.each(data, function(i, obj) {
                    var option = '<option value=' + obj.id;
                    if (cidadeSelecionada != '' && obj.id == cidadeSelecionada) {
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