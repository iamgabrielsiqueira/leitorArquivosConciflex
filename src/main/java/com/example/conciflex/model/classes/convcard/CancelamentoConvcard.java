package com.example.conciflex.model.classes.convcard;

import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;

import java.sql.Date;
import java.sql.Time;

public class CancelamentoConvcard {
    private String tipoRegistro;
    private String nseqRegistroArquivo;
    private String nseqRegistroLote;
    private String cnpjLoja;
    private String nsuTransacao;
    private String dataTransacao;
    private String horaTransacao;
    private String numeroCartao;
    private String numeroAutorizacao;
    private String numeroParcela;
    private String dataCancelamento;
    private String nseqLote;
    private String espacoReservado;

    private Empresa empresa;
    private Cliente cliente;
    private String nsuFormatado;
    private Date dataTransacaoSQL;
    private Date dataCancelamentoSQL;
    private String autorizador;
    private String autorizacaoFormatado;
    private Time horaTransacaoSQL;
    private String chaveCancelamento;
    private String numeroCartaoFormatado;

    public String getNumeroCartaoFormatado() {
        return numeroCartaoFormatado;
    }

    public void setNumeroCartaoFormatado(String numeroCartaoFormatado) {
        this.numeroCartaoFormatado = numeroCartaoFormatado;
    }

    public String getChaveCancelamento() {
        return chaveCancelamento;
    }

    public void setChaveCancelamento(String chaveCancelamento) {
        this.chaveCancelamento = chaveCancelamento;
    }

    public Time getHoraTransacaoSQL() {
        return horaTransacaoSQL;
    }

    public void setHoraTransacaoSQL(Time horaTransacaoSQL) {
        this.horaTransacaoSQL = horaTransacaoSQL;
    }

    public String getAutorizacaoFormatado() {
        return autorizacaoFormatado;
    }

    public void setAutorizacaoFormatado(String autorizacaoFormatado) {
        this.autorizacaoFormatado = autorizacaoFormatado;
    }

    public String getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(String autorizador) {
        this.autorizador = autorizador;
    }

    public Date getDataCancelamentoSQL() {
        return dataCancelamentoSQL;
    }

    public void setDataCancelamentoSQL(Date dataCancelamentoSQL) {
        this.dataCancelamentoSQL = dataCancelamentoSQL;
    }

    public Date getDataTransacaoSQL() {
        return dataTransacaoSQL;
    }

    public void setDataTransacaoSQL(Date dataTransacaoSQL) {
        this.dataTransacaoSQL = dataTransacaoSQL;
    }

    public String getNsuFormatado() {
        return nsuFormatado;
    }

    public void setNsuFormatado(String nsuFormatado) {
        this.nsuFormatado = nsuFormatado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNseqRegistroArquivo() {
        return nseqRegistroArquivo;
    }

    public void setNseqRegistroArquivo(String nseqRegistroArquivo) {
        this.nseqRegistroArquivo = nseqRegistroArquivo;
    }

    public String getNseqRegistroLote() {
        return nseqRegistroLote;
    }

    public void setNseqRegistroLote(String nseqRegistroLote) {
        this.nseqRegistroLote = nseqRegistroLote;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public String getNsuTransacao() {
        return nsuTransacao;
    }

    public void setNsuTransacao(String nsuTransacao) {
        this.nsuTransacao = nsuTransacao;
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

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(String numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(String dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public String getNseqLote() {
        return nseqLote;
    }

    public void setNseqLote(String nseqLote) {
        this.nseqLote = nseqLote;
    }

    public String getEspacoReservado() {
        return espacoReservado;
    }

    public void setEspacoReservado(String espacoReservado) {
        this.espacoReservado = espacoReservado;
    }
}
