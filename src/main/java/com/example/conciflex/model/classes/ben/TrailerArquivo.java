package com.example.conciflex.model.classes.ben;

import java.util.Date;

public class TrailerArquivo {
    private String codigoRegistro;
    private String dataMovimento;
    private String NSEQ;

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }
}
