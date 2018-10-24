package com.callua.facade;

import com.callua.bean.Usuario;
import com.callua.dao.UsuarioDao;


public class UsuarioFacade {
    private static final UsuarioDao UDAO = new UsuarioDao();
    
    public static Usuario carregarUm(Integer id) {
        return UDAO.carregarUm(id);
    }
}
