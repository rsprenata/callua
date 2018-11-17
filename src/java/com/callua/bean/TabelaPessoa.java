/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.bean;

/**
 *
 * @author renata
 */
public enum TabelaPessoa {
    CLIENTE("CLIENTE"),
    USUARIO("USUARIO");

    private final String tabela;

    private TabelaPessoa(String tabela) {
        this.tabela = tabela;
    }
}
