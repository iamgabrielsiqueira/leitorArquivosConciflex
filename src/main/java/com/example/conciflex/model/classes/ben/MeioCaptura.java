package com.example.conciflex.model.classes.ben;

public class MeioCaptura {
    private int id;
    private String descricao;
    private int idConciflex;

    public MeioCaptura(int id, String descricao, int idConciflex) {
        this.id = id;
        this.descricao = descricao;
        this.idConciflex = idConciflex;
    }

    public MeioCaptura() {}

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
}
