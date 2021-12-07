package com.example.conciflex.model.classes;

public class Cancelamento {
    private String codigoRegistro;
    private String identificacaoLoja;
    private String NSUHostTransacaoOriginal;
    private String NSUTEF;
    private String NSUTerminal;
    private String codigoAdquirente;
    private Adquirente adquirente;
    private String dataTransacaoOriginal;
    private String NSUHostTransacao;
    private String dataTransacao;
    private String horaTransacao;
    private MeioCaptura meioCaptura;
    private String codigoEC;
    private String codigoAutorizacao;
    private String NSEQ;

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getIdentificacaoLoja() {
        return identificacaoLoja;
    }

    public void setIdentificacaoLoja(String identificacaoLoja) {
        this.identificacaoLoja = identificacaoLoja;
    }

    public String getNSUHostTransacaoOriginal() {
        return NSUHostTransacaoOriginal;
    }

    public void setNSUHostTransacaoOriginal(String NSUHostTransacaoOriginal) {
        this.NSUHostTransacaoOriginal = NSUHostTransacaoOriginal;
    }

    public String getNSUTEF() {
        return NSUTEF;
    }

    public void setNSUTEF(String NSUTEF) {
        this.NSUTEF = NSUTEF;
    }

    public String getNSUTerminal() {
        return NSUTerminal;
    }

    public void setNSUTerminal(String NSUTerminal) {
        this.NSUTerminal = NSUTerminal;
    }

    public String getCodigoAdquirente() {
        return codigoAdquirente;
    }

    public void setCodigoAdquirente(String codigoAdquirente) {
        this.codigoAdquirente = codigoAdquirente;
    }

    public String getDataTransacaoOriginal() {
        return dataTransacaoOriginal;
    }

    public void setDataTransacaoOriginal(String dataTransacaoOriginal) {
        this.dataTransacaoOriginal = dataTransacaoOriginal;
    }

    public String getNSUHostTransacao() {
        return NSUHostTransacao;
    }

    public void setNSUHostTransacao(String NSUHostTransacao) {
        this.NSUHostTransacao = NSUHostTransacao;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getHoraTransacao() {
        return horaTransacao;
    }

    public void setHoraTransacao(String horaTransacao) {
        this.horaTransacao = horaTransacao;
    }

    public MeioCaptura getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(MeioCaptura meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getCodigoEC() {
        return codigoEC;
    }

    public void setCodigoEC(String codigoEC) {
        this.codigoEC = codigoEC;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }

    public Adquirente getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(Adquirente adquirente) {
        this.adquirente = adquirente;
    }
}
