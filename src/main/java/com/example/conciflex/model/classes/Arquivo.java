package com.example.conciflex.model.classes;

import java.sql.Date;
import java.sql.Time;

public class Arquivo {
    private int id;
    private String arquivo;
    private String localizacao;
    private Date dataProcessamento;
    private Time horaProcessamento;
    private int idAdquirente;
    private String CNPJ;
    private Date dataArquivo;
    private Date dataMenorVenda;
    private Date dataMaiorVenda;
    private Date dataMenorPagamento;
    private Date dataMaiorPagamento;
    private String estabelecimentoCNPJ;
    private String arquivoFalha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Time getHoraProcessamento() {
        return horaProcessamento;
    }

    public void setHoraProcessamento(Time horaProcessamento) {
        this.horaProcessamento = horaProcessamento;
    }

    public int getIdAdquirente() {
        return idAdquirente;
    }

    public void setIdAdquirente(int idAdquirente) {
        this.idAdquirente = idAdquirente;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Date getDataArquivo() {
        return dataArquivo;
    }

    public void setDataArquivo(Date dataArquivo) {
        this.dataArquivo = dataArquivo;
    }

    public Date getDataMenorVenda() {
        return dataMenorVenda;
    }

    public void setDataMenorVenda(Date dataMenorVenda) {
        this.dataMenorVenda = dataMenorVenda;
    }

    public Date getDataMaiorVenda() {
        return dataMaiorVenda;
    }

    public void setDataMaiorVenda(Date dataMaiorVenda) {
        this.dataMaiorVenda = dataMaiorVenda;
    }

    public Date getDataMenorPagamento() {
        return dataMenorPagamento;
    }

    public void setDataMenorPagamento(Date dataMenorPagamento) {
        this.dataMenorPagamento = dataMenorPagamento;
    }

    public Date getDataMaiorPagamento() {
        return dataMaiorPagamento;
    }

    public void setDataMaiorPagamento(Date dataMaiorPagamento) {
        this.dataMaiorPagamento = dataMaiorPagamento;
    }

    public String getEstabelecimentoCNPJ() {
        return estabelecimentoCNPJ;
    }

    public void setEstabelecimentoCNPJ(String estabelecimentoCNPJ) {
        this.estabelecimentoCNPJ = estabelecimentoCNPJ;
    }

    public String getArquivoFalha() {
        return arquivoFalha;
    }

    public void setArquivoFalha(String arquivoFalha) {
        this.arquivoFalha = arquivoFalha;
    }
}
