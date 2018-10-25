package com.callua.facade;

import com.callua.bean.Cliente;
import com.callua.dao.ClienteDao;


public class ClienteFacade {
    private static final ClienteDao CDAO = new ClienteDao();

    public static void adicionarUm(Cliente cliente) {
        CDAO.adicionarUm(cliente);
    }

    public static Cliente carregarUm(Integer id) {
        return CDAO.carregarUm(id);
    }
}
