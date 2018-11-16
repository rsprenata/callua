package com.callua.facade;

import com.callua.bean.Usuario;
import com.callua.dao.UsuarioDao;
import java.util.List;


public class UsuarioFacade {
    private static final UsuarioDao UDAO = new UsuarioDao();
    
    public static List<Usuario> carregar() {
        return UDAO.carregar();
    }
    
    public static List<Usuario> carregarMenosUm(Usuario usuario) {
        return UDAO.carregarMenosUm(usuario);
    }
    
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

    public static void editar(Usuario usuario) {
        UDAO.editar(usuario);
    }

    //necessário pois atualiza os dados e senha e não o nivel acesso.
    public static void editarDados(Usuario usuario) {
        UDAO.editarDados(usuario);
    }

    public static void remover(Integer usuarioId) {
        UDAO.remover(usuarioId);
    }

    public static boolean senhaAtualValida(Usuario usuario, String senhaAtual) {
        return UDAO.senhaAtualValida(usuario, senhaAtual);
    }
}
