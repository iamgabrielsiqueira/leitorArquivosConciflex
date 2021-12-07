package com.example.conciflex.model.classes;

public class HeaderArquivo {
    private String codigoRegistro;
    private String versaoLayout;
    private String dataGeracao;
    private String horaGeracao;
    private String idMovimento;
    private String nomeAdministradora;
    private String identificacaoRemetente;
    private String identificacaoDestinatario;
    private TipoProcessamento tipoProcessamento;
    private String NSEQ;

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(String versaoLayout) {
        this.versaoLayout = versaoLayout;
    }

    public String getNomeAdministradora() {
        return nomeAdministradora;
    }

    public void setNomeAdministradora(String nomeAdministradora) {
        this.nomeAdministradora = nomeAdministradora;
    }

    public String getIdentificacaoRemetente() {
        return identificacaoRemetente;
    }

    public void setIdentificacaoRemetente(String identificacaoRemetente) {
        this.identificacaoRemetente = identificacaoRemetente;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }

    public String getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(String idMovimento) {
        this.idMovimento = idMovimento;
    }

    public String getIdentificacaoDestinatario() {
        return identificacaoDestinatario;
    }

    public void setIdentificacaoDestinatario(String identificacaoDestinatario) {
        this.identificacaoDestinatario = identificacaoDestinatario;
    }

    public TipoProcessamento getTipoProcessamento() {
        return tipoProcessamento;
    }

    public void setTipoProcessamento(TipoProcessamento tipoProcessamento) {
        this.tipoProcessamento = tipoProcessamento;
    }

    public String getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(String dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getHoraGeracao() {
        return horaGeracao;
    }

    public void setHoraGeracao(String horaGeracao) {
        this.horaGeracao = horaGeracao;
    }
}
