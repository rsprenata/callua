package com.callua.facade;

import com.callua.bean.Chamado;
import com.callua.dao.ChamadoDao;


public class ChamadoFacade {
    private static final ChamadoDao CDAO = new ChamadoDao();

    public static void abrirUm(Chamado chamado) {
        CDAO.abrirUm(chamado);
    }
}
