package com.callua.facade;

import com.callua.bean.Chamado;
import com.callua.bean.Produto;
import com.callua.dao.ProdutoDao;
import com.callua.webclient.ProdutoClient;
import java.util.List;


public class ProdutoFacade {
    private static final ProdutoDao PDAO = new ProdutoDao();
    
    public static Produto carregarUm(Integer id) {
        return ProdutoClient.getProduto(id);
    }
    
    public static List<Produto> carregarTodos() {
        return ProdutoClient.getProdutos();
    }
    
    public static List<Produto> carregarByChamado(Integer id) {
        return PDAO.carregarByChamado(id);
    }
}
