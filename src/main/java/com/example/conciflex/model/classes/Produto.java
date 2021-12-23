package com.example.conciflex.model.classes;

public class Produto {
    private int id;
    private String descricao;
    private int idConciflex;
    private String descricaoConciflex;

    public Produto() {}

    public Produto(int id, String descricao, int idConciflex, String descricaoConciflex) {
        this.id = id;
        this.descricao = descricao;
        this.idConciflex = idConciflex;
        this.descricaoConciflex = descricaoConciflex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdConciflex() {
        return idConciflex;
    }

    public void setIdConciflex(int idConciflex) {
        this.idConciflex = idConciflex;
    }

    public String getDescricaoConciflex() {
        return descricaoConciflex;
    }

    public void setDescricaoConciflex(String descricaoConciflex) {
        this.descricaoConciflex = descricaoConciflex;
    }
}
