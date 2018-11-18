$('.cep').mask('00000-000');
                
var SPMaskBehavior = function (val) {
    return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
  },
  spOptions = {
    onKeyPress: function(val, e, field, options) {
        field.mask(SPMaskBehavior.apply({}, arguments), options);
      }
  };

  $('.telefoneCelular').mask(SPMaskBehavior, spOptions);

var options = {
    onKeyPress: function (cpf, ev, el, op) {
        var masks = ['000.000.000-000', '00.000.000/0000-00'];
        $('.cpfCnpj').mask((cpf.length > 14) ? masks[1] : masks[0], op);
    }
};

$('.cpfCnpj').length > 11 ? $('.cpfCnpj').mask('00.000.000/0000-00', options) : $('.cpfCnpj').mask('000.000.000-00#', options);