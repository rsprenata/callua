//Atualiza o chat
function updateChat(contextPath, chamadoId, logadoPessoa, logadoId) {
    $.ajax({
        url: contextPath + '/MensagemChamado?op=carregarViaAjax',
        type: 'GET',
        dataType: 'json',
        data: {
            "chamadoId": chamadoId
        },
        async : true,
        success: function (mensagens) {
            html = "";
            mensagens.forEach(function (v, k) {
                if ((logadoPessoa == 'CLIENTE' && v.tabelaPessoa == 'CLIENTE' && v.cliente.id == logadoId) || (logadoPessoa == 'USUARIO' && v.tabelaPessoa == 'USUARIO' && v.usuario.id == logadoId)) {
                    html += '<div class="outgoing_msg"><div class="sent_msg"><strong>';
                } else {
                    html += '<div class="incoming_msg"><div class="incoming_msg_img"> <img src="https://cdn2.iconfinder.com/data/icons/person-gender-hairstyle-clothes-variations/48/Female-Side-comb-O-neck-512.png" alt="sunil"> </div><div class="received_msg"><div class="received_withd_msg"><strong>';

                }
                if (v.tabelaPessoa == 'CLIENTE') {
                    html += v.cliente.nome;
                } else {
                    html += v.usuario.nome;
                }
                html += '</strong><p>'+v.mensagem+'</p><span class="time_date">'+moment(v.data, "MMM DD, YYYY hh:mm:ss a", "en").format('DD/MM/YYYY') + ' | ' + moment(v.data, "MMM DD, YYYY hh:mm:ss a", "en").locale("pt-br").fromNow()+'</span></div></div>';
            });
            $('#chatChamado').html(html);
            if (scroll) {
                $("#chatChamado").scrollTop($("#chatChamado")[0].scrollHeight);
                scroll = false;
            }
        }
    });
}

//Loop para atualizar o chat
function loopChat(contextPath, chamadoId, logadoPessoa, logadoId) {
    updateChat(contextPath, chamadoId, logadoPessoa, logadoId);
    setTimeout(function () {
        loopChat(contextPath, chamadoId, logadoPessoa, logadoId);
    }, 3000);
}

//Trigger para o enter na msg ativar o botÃ£o
$("#msg-chat").keydown(function (event) {
    if (event.keyCode === 13) {
        $("#btn-chat").click();
    }
});

function enviarMensagem(contextPath, chamadoId, logadoPessoa, logadoId, mensagem) {
    if (mensagem) {
        $.ajax({
            url: contextPath + '/MensagemChamado?op=enviarViaAjax',
            type: 'POST',
            dataType: 'json',
            data: {
                "chamadoId": chamadoId,
                "logadoPessoa": logadoPessoa,
                "logadoId": logadoId,
                "mensagem": mensagem
            },
            async: true,
            success: function(e) {
                updateChat(contextPath, chamadoId, logadoPessoa, logadoId);
                $('#msg-chat').val('');
                scroll = true;
            },
            error: function (error) {
                console.log(error);
                alert('Erro ao enviar mensagem, tente novamente mais tarde.');
            }
        });
    }
}