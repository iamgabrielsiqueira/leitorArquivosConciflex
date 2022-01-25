package com.example.conciflex.model.classes.convcard;

public class HeaderLoteTransacoesConvcard {
    private String tipoRegistro;
    private String nseqRegistro;
    private String dataMovimento;
    private String nseqLote;
    private String cnpjAdm;
    private String nomeAdm;
    private String espacoReservado;

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNseqRegistro() {
        return nseqRegistro;
    }

    public void setNseqRegistro(String nseqRegistro) {
        this.nseqRegistro = nseqRegistro;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getNseqLote() {
        return nseqLote;
    }

    public void setNseqLote(String nseqLote) {
        this.nseqLote = nseqLote;
    }

    public String getCnpjAdm() {
        return cnpjAdm;
    }

    public void setCnpjAdm(String cnpjAdm) {
        this.cnpjAdm = cnpjAdm;
    }

    public String getNomeAdm() {
        return nomeAdm;
    }

    public void setNomeAdm(String nomeAdm) {
        this.nomeAdm = nomeAdm;
    }

    public String getEspacoReservado() {
        return espacoReservado;
    }

    public void setEspacoReservado(String espacoReservado) {
        this.espacoReservado = espacoReservado;
    }
}
