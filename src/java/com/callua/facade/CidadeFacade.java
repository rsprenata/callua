package com.callua.facade;

import com.callua.bean.Cidade;
import com.callua.dao.CidadeDao;
import java.util.List;

public class CidadeFacade {
    private static final  CidadeDao CDAO = new CidadeDao();

    public static List<Cidade> carregarByEstado(Integer estadoId) {
        return CDAO.carregarByEstado(estadoId);
    }

    public static Cidade carregarUma(Integer cidadeId) {
        return CDAO.carregarUma(cidadeId);
    }
}
