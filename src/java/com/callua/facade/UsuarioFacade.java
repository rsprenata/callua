package com.callua.facade;

import com.callua.bean.Usuario;
import com.callua.dao.UsuarioDao;


public class UsuarioFacade {
    private static final UsuarioDao UDAO = new UsuarioDao();
    
    public static Usuario carregarUm(Integer id) {
        return UDAO.carregarUm(id);
    }

    public static void adicionarUmTecnico(Usuario tecnico, String url) {
        UDAO.adicionarUmTecnico(tecnico, url);
    }

    public static Boolean tokenValidoCriarSenhaTecnico(String token, Integer idTecnico) {
        return UDAO.tokenValidoCriarSenhaTecnico(token, idTecnico);
    }

    public static void criarSenhaTecnico(Usuario tecnico) {
        UDAO.criarSenhaTecnico(tecnico);
    }
}
