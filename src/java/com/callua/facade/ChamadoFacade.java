package com.callua.facade;

import com.callua.bean.Chamado;
import com.callua.bean.Cliente;
import com.callua.dao.ChamadoDao;
import java.util.List;


public class ChamadoFacade {
    private static final ChamadoDao CDAO = new ChamadoDao();

    public static void abrirUm(Chamado chamado, String applicationPath) {
        CDAO.abrirUm(chamado, applicationPath);
    }

    public static List<Chamado> buscarTodosByCliente(Cliente cliente) {
        return CDAO.buscarTodosByCliente(cliente);
    }

    public static Chamado carregarById(Integer id) {
        return CDAO.carregarById(id);
    }
}
