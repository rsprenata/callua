package com.callua.facade;

import com.callua.bean.Pessoa;
import com.callua.dao.PessoaDao;


public class PessoaFacade {
    private static final PessoaDao PDAO = new PessoaDao();

    public static Pessoa validaLogin(String cpfCnpj, String senha) {
        PDAO.validaLogin(cpfCnpj, senha);
    }
}
