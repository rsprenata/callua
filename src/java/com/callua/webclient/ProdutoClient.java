/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callua.webclient;

import com.callua.bean.Produto;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author renata
 */
public class ProdutoClient {
    public static String enderecoServidor = "http://localhost:8080/calluastock/webresources";
    
    public static List<Produto> getProdutos() {
        Client client = ClientBuilder.newClient();
        Response resp = client
                              .target(enderecoServidor + "/produtos")
                              .request(MediaType.APPLICATION_JSON)
                              .get();
        List<Produto> lista = 
                            resp.readEntity(
                            new GenericType<List<Produto>>() {}
                            );
        return lista;
    }
    
    public static Produto getProduto(Integer id) {
        Client client = ClientBuilder.newClient();
        
        Produto produto = client
                          .target(enderecoServidor + "/produtos/" + id)
                          .request(MediaType.APPLICATION_JSON)
                          .get(Produto.class);
        return produto;
    }
    
    public static Response utilizar(Produto produto) {
        Client client = ClientBuilder.newClient();
        
        Response response = client.target(enderecoServidor + "/produtos/utilizar/" + produto.getId())
                          .request()
                          .put(Entity.json(produto));
        
        return response;
    }
}
