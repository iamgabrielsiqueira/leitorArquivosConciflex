package com.example.conciflex.model.classes.convcard;

import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;

import java.sql.Date;
import java.sql.Time;

public class ComprovanteVendaConvcard {
    private String tipoRegistro;
    private String nseqRegistroArquivo;
    private String nseqRegistroLote;
    private String cnpj;
    private String nsuTransacao;
    private String dataTransacao;
    private String horaTransacao;
    private String numeroCartao;
    private String numeroAutorizacao;
    private String numeroParcela;
    private String quantidadeParcelas;
    private String dataLancamento;
    private String valorBrutoVenda;
    private String valorDesconto;
    private String valorLiquidoVenda;
    private String valorBrutoParcela;
    private String valorDescontoParcela;
    private String valorLiquidoParcela;
    private String banco;
    private String agencia;
    private String conta;
    private String nseqLote;
    private String espacoReservado;

    private Empresa empresa;
    private Cliente cliente;
    private String nsuFormatada;
    private Date dataTransacaoSQL;
    private Date dataLancamentoSQL;
    private Double valorBrutoFormatado;
    private Double valorDescontoFormatado;
    private Double valorLiquidoFormatado;
    private String numeroCartaoFormatado;
    private int bancoFormatado;
    private String agenciaFormatado;
    private String contaFormatado;
    private String autorizacaoFormatado;
    private Double valorTaxaPercentual;
    private String chaveVenda;
    private Time horaTransacaoSQL;
    private String autorizador;

    public String getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(String autorizador) {
        this.autorizador = autorizador;
    }

    public Time getHoraTransacaoSQL() {
        return horaTransacaoSQL;
    }

    public void setHoraTransacaoSQL(Time horaTransacaoSQL) {
        this.horaTransacaoSQL = horaTransacaoSQL;
    }

    public String getChaveVenda() {
        return chaveVenda;
    }

    public void setChaveVenda(String chaveVenda) {
        this.chaveVenda = chaveVenda;
    }

    public Double getValorTaxaPercentual() {
        return valorTaxaPercentual;
    }

    public void setValorTaxaPercentual(Double valorTaxaPercentual) {
        this.valorTaxaPercentual = valorTaxaPercentual;
    }

    public String getAutorizacaoFormatado() {
        return autorizacaoFormatado;
    }

    public void setAutorizacaoFormatado(String autorizacaoFormatado) {
        this.autorizacaoFormatado = autorizacaoFormatado;
    }

    public int getBancoFormatado() {
        return bancoFormatado;
    }

    public void setBancoFormatado(int bancoFormatado) {
        this.bancoFormatado = bancoFormatado;
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

    public Double getValorBrutoFormatado() {
        return valorBrutoFormatado;
    }

    public void setValorBrutoFormatado(Double valorBrutoFormatado) {
        this.valorBrutoFormatado = valorBrutoFormatado;
    }

    public Date getDataLancamentoSQL() {
        return dataLancamentoSQL;
    }

    public void setDataLancamentoSQL(Date dataLancamentoSQL) {
        this.dataLancamentoSQL = dataLancamentoSQL;
    }

    public Date getDataTransacaoSQL() {
        return dataTransacaoSQL;
    }

    public void setDataTransacaoSQL(Date dataTransacaoSQL) {
        this.dataTransacaoSQL = dataTransacaoSQL;
    }

    public String getNsuFormatada() {
        return nsuFormatada;
    }

    public void setNsuFormatada(String nsuFormatada) {
        this.nsuFormatada = nsuFormatada;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(String quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getValorBrutoVenda() {
        return valorBrutoVenda;
    }

    public void setValorBrutoVenda(String valorBrutoVenda) {
        this.valorBrutoVenda = valorBrutoVenda;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getValorLiquidoVenda() {
        return valorLiquidoVenda;
    }

    public void setValorLiquidoVenda(String valorLiquidoVenda) {
        this.valorLiquidoVenda = valorLiquidoVenda;
    }

    public String getValorBrutoParcela() {
        return valorBrutoParcela;
    }

    public void setValorBrutoParcela(String valorBrutoParcela) {
        this.valorBrutoParcela = valorBrutoParcela;
    }

    public String getValorDescontoParcela() {
        return valorDescontoParcela;
    }

    public void setValorDescontoParcela(String valorDescontoParcela) {
        this.valorDescontoParcela = valorDescontoParcela;
    }

    public String getValorLiquidoParcela() {
        return valorLiquidoParcela;
    }

    public void setValorLiquidoParcela(String valorLiquidoParcela) {
        this.valorLiquidoParcela = valorLiquidoParcela;
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
}
