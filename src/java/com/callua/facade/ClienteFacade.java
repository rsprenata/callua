package com.callua.facade;

import com.callua.bean.Cliente;
import com.callua.dao.ClienteDao;


public class ClienteFacade {
    private static final ClienteDao CDAO = new ClienteDao();

    public static void adicionarUm(Cliente c) {
        CDAO.adicionarUm(c);
    }
}
