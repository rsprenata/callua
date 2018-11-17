package com.callua.facade;

import com.callua.bean.Chamado;
import com.callua.bean.Cliente;
import com.callua.bean.Produto;
import com.callua.bean.Usuario;
import com.callua.dao.ChamadoDao;
import java.util.List;
import javax.servlet.http.Part;


public class ChamadoFacade {
    private static final ChamadoDao CDAO = new ChamadoDao();

    public static void abrirUm(Chamado chamado, String applicationPath) {
        CDAO.abrirUm(chamado, applicationPath);
    }

    public static List<Chamado> buscarTodosByCliente(Cliente cliente) {
        return CDAO.buscarTodosByCliente(cliente);
    }

    public static List<Chamado> buscarTodosByUsuario(Usuario tecnico) {
        return CDAO.buscarTodosByUsuario(tecnico);
    }

    public static List<Chamado> buscarTodos() {
        return CDAO.buscarTodos();
    }

    public static Chamado carregarById(Integer id) {
        return CDAO.carregarById(id);
    }

    public static void fecharUm(Chamado chamado) {
        CDAO.alterarStatus(chamado, "RESOLVIDO");
    }

    public static void reabrir(Chamado chamado) {
        CDAO.alterarStatus(chamado, "ABERTO");
    }

    public static void adicionarProduto(Chamado chamado, Produto produto) throws Exception {
        CDAO.adicionarProduto(chamado, produto);
    }

    public static void removerProduto(Chamado chamado, Produto produto) throws Exception {
        CDAO.removerProduto(chamado, produto);
    }

    public static void atribuirUsuario(Chamado chamado, Usuario usuario) {
        CDAO.atribuirUsuario(chamado, usuario);
    }

    public static void anexarArquivos(Chamado chamado, List <Part> partArquivos, String uploadLocation) {
        CDAO.anexarArquivos(chamado, partArquivos, uploadLocation);
    }
}
