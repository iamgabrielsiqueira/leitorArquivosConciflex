package com.example.conciflex.model.classes;

public class MeioCaptura {
    private int id;
    private String descricao;

    public MeioCaptura(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
}
