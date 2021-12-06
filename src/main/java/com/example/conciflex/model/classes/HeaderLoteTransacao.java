package com.example.conciflex.model.classes;

import java.util.Date;

public class HeaderLoteTransacao {
    private String codigoRegistro;
    private Date dataMovimento;
    private MoedaCorrente moedaCorrente;
    private String NSEQ;

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }

    public MoedaCorrente getMoedaCorrente() {
        return moedaCorrente;
    }

    public void setMoedaCorrente(MoedaCorrente moedaCorrente) {
        this.moedaCorrente = moedaCorrente;
    }
}
