/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.util;

import com.callua.bean.Cliente;
import com.callua.bean.Usuario;

/**
 *
 * @author renata
 * Classe auxiliar para o login e também para ficar na sessão
 */
public class Login {
    private Cliente cliente;
    private Usuario usuario;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
