package com.example.conciflex.model.classes.ben;

public class TipoLancamento {
    private int id;
    private String descricao;

    public TipoLancamento(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public TipoLancamento() {}

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
