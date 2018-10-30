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
public enum StatusChamado {
    ABERTO("Aberto"),
    RESOLVIDO("Resolvido");

    private final String descricao;

    private StatusChamado(String descricao) {
        this.descricao = descricao;
    }
}
