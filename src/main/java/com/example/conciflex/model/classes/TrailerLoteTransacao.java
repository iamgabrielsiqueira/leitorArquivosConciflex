package com.example.conciflex.model.classes;

public class TrailerLoteTransacao {
    private String codigoRegistro;
    private String totalRegistros;
    private String totalValoresCredito;
    private String NSEQ;

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(String totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public String getTotalValoresCredito() {
        return totalValoresCredito;
    }

    public void setTotalValoresCredito(String totalValoresCredito) {
        this.totalValoresCredito = totalValoresCredito;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }
}
