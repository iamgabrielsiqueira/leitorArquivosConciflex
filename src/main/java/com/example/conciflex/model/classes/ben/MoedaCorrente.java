package com.example.conciflex.model.classes.ben;

public class MoedaCorrente {
    private String identificador;
    private String descricao;

    public MoedaCorrente(String identificador, String descricao) {
        this.identificador = identificador;
        this.descricao = descricao;
    }

    public MoedaCorrente() {}

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
