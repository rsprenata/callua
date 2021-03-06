/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.bean;

import java.math.BigDecimal;

/**
 *
 * @author renata
 */
public class Produto implements java.io.Serializable {
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private Integer quantidade;
    
    public Produto() {}

    public Integer getId() {
    	return this.id;
    }

    public void setId(Integer id) {
    	this.id = id;
    }

    public String getDescricao() {
    	return this.descricao;
    }

    public void setDescricao(String descricao) {
    	this.descricao = descricao;
    }

    public BigDecimal getValor() {
    	return this.valor;
    }

    public void setValor(BigDecimal valor) {
    	this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Produto)) {
            return false;
        }
        Produto produto = (Produto)anObject;
        return produto.getId().equals(this.getId());
    }
}
