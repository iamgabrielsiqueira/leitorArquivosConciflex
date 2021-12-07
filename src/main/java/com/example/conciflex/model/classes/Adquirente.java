package com.example.conciflex.model.classes;

public class Adquirente {
    private int id;
    private String descricao;

    public Adquirente() {}

    public Adquirente(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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
}
