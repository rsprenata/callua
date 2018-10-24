package com.callua.facade;

import com.callua.bean.Pessoa;
import com.callua.dao.LoginDao;
import com.callua.util.Login;


public class LoginFacade {
    private static final LoginDao LDAO = new LoginDao();

    public static Login carregarLogin(String cpfCnpj, String senha) {
        return LDAO.carregarLogin(cpfCnpj, senha);
    }
}
