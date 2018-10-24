$('.cep').mask('00000-000');
                
$('.telefoneCelular').mask('(00) 0 0000-0000');

var options = {
    onKeyPress: function (cpf, ev, el, op) {
        var masks = ['000.000.000-000', '00.000.000/0000-00'];
        $('.cpfCnpj').mask((cpf.length > 14) ? masks[1] : masks[0], op);
    }
};

$('.cpfCnpj').length > 11 ? $('.cpfCnpj').mask('00.000.000/0000-00', options) : $('.cpfCnpj').mask('000.000.000-00#', options);