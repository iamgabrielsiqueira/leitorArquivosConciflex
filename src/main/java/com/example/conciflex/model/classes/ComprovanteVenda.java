package com.example.conciflex.model.classes;

import java.sql.Time;
import java.util.Date;

public class ComprovanteVenda {
    private String codigoRegistro;
    private String identificacaoLoja;
    private String NSUHostTransacao;
    private String NSUTEF;
    private String NSUTerminal;
    private String codigoAdquirente;
    private Date dataTransacao;
    private Time horaTransacao;
    private TipoLancamento tipoLancamento;
    private Date dataLancamento;
    private String tipoProduto;
    private MeioCaptura meioCaptura;
    private String valorBruto;
    private String valorDesconto;
    private String valorLiquido;
    private String numeroCartao;
    private String banco;
    private String agencia;
    private String conta;
    private String codigoAutorizacao;
    private String codigoBandeira;
    private String codigoProduto;
    private String codigoEC;
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

    public String getNSUHostTransacao() {
        return NSUHostTransacao;
    }

    public void setNSUHostTransacao(String NSUHostTransacao) {
        this.NSUHostTransacao = NSUHostTransacao;
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

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Time getHoraTransacao() {
        return horaTransacao;
    }

    public void setHoraTransacao(Time horaTransacao) {
        this.horaTransacao = horaTransacao;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public MeioCaptura getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(MeioCaptura meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(String valorBruto) {
        this.valorBruto = valorBruto;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(String valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getCodigoBandeira() {
        return codigoBandeira;
    }

    public void setCodigoBandeira(String codigoBandeira) {
        this.codigoBandeira = codigoBandeira;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getCodigoEC() {
        return codigoEC;
    }

    public void setCodigoEC(String codigoEC) {
        this.codigoEC = codigoEC;
    }

    public String getNSEQ() {
        return NSEQ;
    }

    public void setNSEQ(String NSEQ) {
        this.NSEQ = NSEQ;
    }
}
