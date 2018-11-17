/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.bean;

import java.util.Date;

/**
 *
 * @author renata
 */
public class MensagemChamado implements java.io.Serializable {
    private Integer id;
    private Usuario usuario;
    private Cliente cliente;
    private Chamado chamado;
    private String mensagem;
    private Date data;
    private TabelaPessoa tabelaPessoa;
    
    public MensagemChamado() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TabelaPessoa getTabelaPessoa() {
        return tabelaPessoa;
    }

    public void setTabelaPessoa(TabelaPessoa tabelaPessoa) {
        this.tabelaPessoa = tabelaPessoa;
    }
}
