package com.example.conciflex.model.classes;

public class TipoProcessamento {
    private String identificador;
    private String descricao;

    public TipoProcessamento(String identificador, String descricao) {
        this.identificador = identificador;
        this.descricao = descricao;
    }

    public TipoProcessamento() {}

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
