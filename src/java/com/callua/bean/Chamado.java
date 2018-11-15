/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.bean;

import java.util.Date;
import java.util.List;
import javax.servlet.http.Part;

/**
 *
 * @author renata
 */
public class Chamado implements java.io.Serializable {
    private Integer id;
    private String titulo;
    private String descricao;
    private Endereco endereco;
    private List <Part> partArquivos;
    private StatusChamado status;
    private Cliente cliente;
    private Usuario tecnico;
    private Date data;
    
    public Chamado() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Part> getPartArquivos() {
        return partArquivos;
    }

    public void setPartArquivos(List<Part> partArquivos) {
        this.partArquivos = partArquivos;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
