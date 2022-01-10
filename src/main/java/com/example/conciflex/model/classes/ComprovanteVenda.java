package com.example.conciflex.model.classes;

import java.sql.Date;
import java.sql.Time;

public class ComprovanteVenda {
    private String codigoRegistro;
    private String identificacaoLoja;
    private String NSUHostTransacao;
    private String NSUTEF;
    private String NSUTerminal;
    private String codigoAdquirente;
    private Adquirente adquirente;
    private Date dataTransacao;
    private Time horaTransacao;
    private TipoLancamento tipoLancamento;
    private Date dataLancamento;
    private String tipoProduto;
    private MeioCaptura meioCaptura;
    private String valorBrutoString;
    private String valorDescontoString;
    private String valorLiquidoString;
    private Double valorBruto;
    private Double valorDesconto;
    private Double valorLiquido;
    private String numeroCartao;
    private String banco;
    private String agencia;
    private String conta;
    private String codigoAutorizacao;
    private String codigoBandeira;
    private String codigoProduto;
    private Produto produto;
    private String codigoEC;
    private String NSEQ;
    private Empresa empresa;
    private Estabelecimento estabelecimento;
    private String agenciaFormatado;
    private String contaFormatada;
    private String autorizacaoFormatada;
    private String nsuFormatada;
    private String tidFormatada;
    private Double taxaPercentual;
    private String cartaoFormatado;
    private String chavePagamento;
    private long id;

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

    public Adquirente getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(Adquirente adquirente) {
        this.adquirente = adquirente;
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

    public String getValorBrutoString() {
        return valorBrutoString;
    }

    public void setValorBrutoString(String valorBrutoString) {
        this.valorBrutoString = valorBrutoString;
    }

    public String getValorDescontoString() {
        return valorDescontoString;
    }

    public void setValorDescontoString(String valorDescontoString) {
        this.valorDescontoString = valorDescontoString;
    }

    public String getValorLiquidoString() {
        return valorLiquidoString;
    }

    public void setValorLiquidoString(String valorLiquidoString) {
        this.valorLiquidoString = valorLiquidoString;
    }

    public Double getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(Double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(Double valorLiquido) {
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getAgenciaFormatado() {
        return agenciaFormatado;
    }

    public void setAgenciaFormatado(String agenciaFormatado) {
        this.agenciaFormatado = agenciaFormatado;
    }

    public String getContaFormatada() {
        return contaFormatada;
    }

    public void setContaFormatada(String contaFormatada) {
        this.contaFormatada = contaFormatada;
    }

    public String getAutorizacaoFormatada() {
        return autorizacaoFormatada;
    }

    public void setAutorizacaoFormatada(String autorizacaoFormatada) {
        this.autorizacaoFormatada = autorizacaoFormatada;
    }

    public String getNsuFormatada() {
        return nsuFormatada;
    }

    public void setNsuFormatada(String nsuFormatada) {
        this.nsuFormatada = nsuFormatada;
    }

    public String getTidFormatada() {
        return tidFormatada;
    }

    public void setTidFormatada(String tidFormatada) {
        this.tidFormatada = tidFormatada;
    }

    public Double getTaxaPercentual() {
        return taxaPercentual;
    }

    public void setTaxaPercentual(Double taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }

    public String getCartaoFormatado() {
        return cartaoFormatado;
    }

    public void setCartaoFormatado(String cartaoFormatado) {
        this.cartaoFormatado = cartaoFormatado;
    }

    public String getChavePagamento() {
        return chavePagamento;
    }

    public void setChavePagamento(String chavePagamento) {
        this.chavePagamento = chavePagamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
