package com.callua.facade;

import com.callua.bean.Chamado;
import com.callua.bean.MensagemChamado;
import com.callua.dao.MensagemChamadoDao;
import java.util.List;


public class MensagemChamadoFacade {
    private static final MensagemChamadoDao MCDAO = new MensagemChamadoDao();
    
    public static List<MensagemChamado> carregar(Chamado chamado) {
        return MCDAO.carregar(chamado);
    }
    
    public static void enviar(Chamado chamado, MensagemChamado mensagem) {
        MCDAO.enviar(chamado, mensagem);
    }
}
