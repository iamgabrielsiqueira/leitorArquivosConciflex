package com.example.conciflex.model.classes;

import com.example.conciflex.model.classes.Cliente;

public class Estabelecimento {
    private int id;
    private Cliente cliente;
    private String codigoEstabelecimento;
    private String cnpj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCodigoEstabelecimento() {
        return codigoEstabelecimento;
    }

    public void setCodigoEstabelecimento(String codigoEstabelecimento) {
        this.codigoEstabelecimento = codigoEstabelecimento;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
