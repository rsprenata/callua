/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.util;

import com.callua.bean.Endereco;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author renata
 * Classe auxiliar para fazer as validações back-end do sistema
 */
public class Validator {
    public static Mensagem validarDescricao(String descricao) {
        Mensagem mensagem = null;
        
        if (descricao== null || "".equals(descricao)) {
            mensagem = new Mensagem("Descrição é obrigatória !!!");
        } else if (descricao.length() > 1024) {
            mensagem = new Mensagem("No máximo 1024 caracteres na descrição !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarTitulo(String titulo) {
        Mensagem mensagem = null;
        
        if (titulo== null || "".equals(titulo)) {
            mensagem = new Mensagem("Título é obrigatório !!!");
        } else if (titulo.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no título !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarEndereco(Endereco endereco) {
        Mensagem mensagem = null;
        
        if (endereco == null || endereco.getEndereco()== null || "".equals(endereco.getEndereco())) {
            mensagem = new Mensagem("Endereço é obrigatório !!!");
        } else if (endereco.getEndereco().length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no endereço !!!");
        } else if (endereco.getCep()== null || "".equals(endereco.getCep())) {
            mensagem = new Mensagem("CEP é obrigatório !!!");
        } else if (endereco.getCep().length() != 8 || !Validator.ehInteiro(endereco.getCep())) {
            mensagem = new Mensagem("CEP inválido !!!");
        } else if (endereco.getCidade().getEstado() == null 
                    || endereco.getCidade().getEstado().getUf() == null 
                    || "".equals(endereco.getCidade().getEstado().getUf())) {
            mensagem = new Mensagem("UF é obrigatório !!!");
        } else if (endereco.getCidade() == null) {
            mensagem = new Mensagem("Cidade é obrigatória !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarSenha(String senha, String confirmacaoSenha) {
        Mensagem mensagem = null;
        
        if (senha== null || "".equals(senha)) {
            mensagem = new Mensagem("Senha é obrigatória !!!");
        } else if (senha.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na senha !!!");
        } else if (confirmacaoSenha == null || "".equals(confirmacaoSenha)) {
            mensagem = new Mensagem("Confirmação de senha é obrigatória !!!");
        } else if (confirmacaoSenha.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres na confirmação da senha !!!");
        } else if (!senha.equals(confirmacaoSenha)) {
            mensagem = new Mensagem("Senha e confirmação diferentes !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarEmail(String email) {
        Mensagem mensagem = null;
        
        if (email== null || "".equals(email)) {
            mensagem = new Mensagem("Email é obrigatório !!!");
        } else if (email.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no email !!!");
        } else if (!Validator.emailValido(email)) {
            mensagem = new Mensagem("Email inválido !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarTelefoneCelular(String telefoneCelular) {
        Mensagem mensagem = null;
        
        if (telefoneCelular== null || "".equals(telefoneCelular)) {
            mensagem = new Mensagem("Telefone/Celular é obrigatório !!!");
        } else if (telefoneCelular.length() != 11  || !Validator.ehInteiro(telefoneCelular)) {
            mensagem = new Mensagem("Telefone/Celular inválido !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarCpfCnpj(String cpfCnpj) {
        Mensagem mensagem = null;
        
        if (cpfCnpj == null || "".equals(cpfCnpj)) {
            mensagem = new Mensagem("CPF/CNPJ é obrigatório !!!");
        } else if (cpfCnpj.length() <= 11 && !cpfValido(cpfCnpj)) {
            mensagem = new Mensagem("CPF inválido !!!");
        } else if (cpfCnpj.length() > 11 && !cnpjValido(cpfCnpj)) {
            mensagem = new Mensagem("CNPJ inválido !!!");
        }
        
        return mensagem;
    }
    
    public static Mensagem validarNome(String nome) {
        Mensagem mensagem = null;
        
        if (nome == null || "".equals(nome)) {
            mensagem = new Mensagem("Nome é obrigatório !!!");
        } else if (nome.length() > 128) {
            mensagem = new Mensagem("No máximo 128 caracteres no nome !!!");
        }
        
        return mensagem;
    }
    
    public static boolean cpfValido(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {              
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0         
        // (48 eh a posicao de '0' na tabela ASCII)         
            num = (int)(CPF.charAt(i) - 48); 
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
    
    public static boolean cnpjValido(String CNPJ) {
    // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
            CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
            CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
            CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
            CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
           (CNPJ.length() != 14))
           return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

    // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
    // Calculo do 1o. Digito Verificador
          sm = 0;
          peso = 2;
          for (i=11; i>=0; i--) {
    // converte o i-ésimo caractere do CNPJ em um número:
    // por exemplo, transforma o caractere '0' no inteiro 0
    // (48 eh a posição de '0' na tabela ASCII)
            num = (int)(CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig13 = '0';
          else dig13 = (char)((11-r) + 48);

    // Calculo do 2o. Digito Verificador
          sm = 0;
          peso = 2;
          for (i=12; i>=0; i--) {
            num = (int)(CNPJ.charAt(i)- 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
               peso = 2;
          }

          r = sm % 11;
          if ((r == 0) || (r == 1))
             dig14 = '0';
          else dig14 = (char)((11-r) + 48);

    // Verifica se os dígitos calculados conferem com os dígitos informados.
          if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
             return(true);
          else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
      }
    
    public static boolean emailValido(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
    public static boolean ehInteiro( String s ) {
        // cria um array de char
        char[] c = s.toCharArray();
        boolean d = true;
        for ( int i = 0; i < c.length; i++ ) {
            // verifica se o char não é um dígito
            if ( !Character.isDigit( c[ i ] ) ) {
                d = false;
                break;
            }
        }
        return d;
    }
}
