package com.callua.facade;

import com.callua.bean.Estado;
import com.callua.dao.EstadoDao;
import java.util.List;

public class EstadoFacade {
    private static final EstadoDao EDAO = new EstadoDao();

    public static List<Estado> buscarTodos() {
        return EDAO.carregarTodos();
    }

    public static Estado carregarUm(Integer estadoId) {
        return EDAO.carregarUm(estadoId);
    }
}
