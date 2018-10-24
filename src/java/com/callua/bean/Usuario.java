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
public class Usuario extends Pessoa implements java.io.Serializable {
    private boolean administrador;
    
    public Usuario() {}

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}
