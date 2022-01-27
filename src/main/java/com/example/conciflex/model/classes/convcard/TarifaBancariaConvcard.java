package com.example.conciflex.model.classes.convcard;

import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;

import java.sql.Date;

public class TarifaBancariaConvcard {
    private String tipoRegistro;
    private String nseqRegistroArquivo;
    private String nseqRegistroLote;
    private String cnpjLoja;
    private String dataTransacao;
    private String valor;
    private String banco;
    private String agencia;
    private String conta;
    private String nseqLote;
    private String espacoReservado;

    private Empresa empresa;
    private Cliente cliente;
    private Date dataTransacaoSQL;
    private String autorizador;
    private String chaveTarifaBancaria;
    private String agenciaFormatada;
    private String contaFormatada;
    private Double valorFormatado;

    public Double getValorFormatado() {
        return valorFormatado;
    }

    public void setValorFormatado(Double valorFormatado) {
        this.valorFormatado = valorFormatado;
    }

    public String getAgenciaFormatada() {
        return agenciaFormatada;
    }

    public void setAgenciaFormatada(String agenciaFormatada) {
        this.agenciaFormatada = agenciaFormatada;
    }

    public String getContaFormatada() {
        return contaFormatada;
    }

    public void setContaFormatada(String contaFormatada) {
        this.contaFormatada = contaFormatada;
    }

    public String getChaveTarifaBancaria() {
        return chaveTarifaBancaria;
    }

    public void setChaveTarifaBancaria(String chaveTarifaBancaria) {
        this.chaveTarifaBancaria = chaveTarifaBancaria;
    }

    public String getAutorizador() {
        return autorizador;
    }

    public void setAutorizador(String autorizador) {
        this.autorizador = autorizador;
    }

    public Date getDataTransacaoSQL() {
        return dataTransacaoSQL;
    }

    public void setDataTransacaoSQL(Date dataTransacaoSQL) {
        this.dataTransacaoSQL = dataTransacaoSQL;
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

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
