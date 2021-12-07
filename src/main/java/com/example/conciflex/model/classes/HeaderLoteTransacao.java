package com.example.conciflex.model.classes;

public class HeaderLoteTransacao {
    private String codigoRegistro;
    private String dataMovimento;
    private MoedaCorrente moedaCorrente;
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

    public MoedaCorrente getMoedaCorrente() {
        return moedaCorrente;
    }

    public void setMoedaCorrente(MoedaCorrente moedaCorrente) {
        this.moedaCorrente = moedaCorrente;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }
}
