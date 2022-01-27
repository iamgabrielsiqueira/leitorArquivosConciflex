package com.example.conciflex.model.classes.convcard;

import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;

import java.sql.Date;
import java.sql.Time;

public class ComprovantePagamentoConvcard {
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
    private String dataPagamento;
    private String valorBrutoPagamento;
    private String valorDesconto;
    private String valorLiquidoPagamento;
    private String banco;
    private String agencia;
    private String conta;
    private String nseqLote;
    private String espacoReservado;

    private Empresa empresa;
    private Cliente cliente;
    private String nsuFormatado;
    private Date dataTransacaoSQL;
    private Date dataPagamentoSQL;
    private String autorizador;
    private Double valorBrutoFormatado;
    private Double valorDescontoFormatado;
    private Double valorLiquidoFormatado;
    private Double taxaPercentual;
    private String numeroCartaoFormatado;
    private String agenciaFormatado;
    private String contaFormatado;
    private String autorizacaoFormatado;
    private Time horaTransacaoSQL;
    private String chavePagamento;

    public String getChavePagamento() {
        return chavePagamento;
    }

    public void setChavePagamento(String chavePagamento) {
        this.chavePagamento = chavePagamento;
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

    public String getAgenciaFormatado() {
        return agenciaFormatado;
    }

    public void setAgenciaFormatado(String agenciaFormatado) {
        this.agenciaFormatado = agenciaFormatado;
    }

    public String getContaFormatado() {
        return contaFormatado;
    }

    public void setContaFormatado(String contaFormatado) {
        this.contaFormatado = contaFormatado;
    }

    public String getNumeroCartaoFormatado() {
        return numeroCartaoFormatado;
    }

    public void setNumeroCartaoFormatado(String numeroCartaoFormatado) {
        this.numeroCartaoFormatado = numeroCartaoFormatado;
    }

    public Double getTaxaPercentual() {
        return taxaPercentual;
    }

    public void setTaxaPercentual(Double taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }

    public Double getValorBrutoFormatado() {
        return valorBrutoFormatado;
    }

    public void setValorBrutoFormatado(Double valorBrutoFormatado) {
        this.valorBrutoFormatado = valorBrutoFormatado;
    }

    public Double getValorDescontoFormatado() {
        return valorDescontoFormatado;
    }

    public void setValorDescontoFormatado(Double valorDescontoFormatado) {
        this.valorDescontoFormatado = valorDescontoFormatado;
    }

    public Double getValorLiquidoFormatado() {
        return valorLiquidoFormatado;
    }

    public void setValorLiquidoFormatado(Double valorLiquidoFormatado) {
        this.valorLiquidoFormatado = valorLiquidoFormatado;
    }

    public String getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(String autorizador) {
        this.autorizador = autorizador;
    }

    public Date getDataPagamentoSQL() {
        return dataPagamentoSQL;
    }

    public void setDataPagamentoSQL(Date dataPagamentoSQL) {
        this.dataPagamentoSQL = dataPagamentoSQL;
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

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getValorBrutoPagamento() {
        return valorBrutoPagamento;
    }

    public void setValorBrutoPagamento(String valorBrutoPagamento) {
        this.valorBrutoPagamento = valorBrutoPagamento;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getValorLiquidoPagamento() {
        return valorLiquidoPagamento;
    }

    public void setValorLiquidoPagamento(String valorLiquidoPagamento) {
        this.valorLiquidoPagamento = valorLiquidoPagamento;
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
}
