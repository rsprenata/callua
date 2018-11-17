package com.callua.facade;

import com.callua.bean.Cliente;
import com.callua.dao.ClienteDao;
import java.util.List;


public class ClienteFacade {
    private static final ClienteDao CDAO = new ClienteDao();

    public static void adicionarUm(Cliente cliente) {
        CDAO.adicionarUm(cliente);
    }

    public static List<Cliente> carregar() {
        return CDAO.carregar();
    }

    public static Cliente carregarUm(Integer id) {
        return CDAO.carregarUm(id);
    }

    public static boolean senhaAtualValida(Cliente cliente, String senhaAtual) {
        return CDAO.senhaAtualValida(cliente, senhaAtual);
    }

    public static void editarUm(Cliente cliente) {
        CDAO.editarUm(cliente);
    }
}
