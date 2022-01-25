package com.example.conciflex.model.classes.ben;

import com.example.conciflex.model.classes.Adquirente;

public class AjusteCreditoDebito {
    private String codigoRegistro;
    private String identificacaoLoja;
    private String NSUHostTransacaoOriginal;
    private String NSUTEF;
    private String NSUTerminal;
    private String codigoAdquirente;
    private Adquirente adquirente;
    private String dataTransacaoOriginal;
    private String NSUHostTransacao;
    private String dataTransacaoAJ;
    private String horaTransacaoAJ;
    private TipoLancamento tipoLancamento;
    private String dataLancamento;
    private MeioCaptura meioCaptura;
    private String tipoAjuste;
    private String codigoAjuste;
    private Ajuste ajuste;
    private String descricaoMotivoAjuste;
    private String valorBruto;
    private String valorDesconto;
    private String valorLiquido;
    private String banco;
    private String agencia;
    private String conta;
    private String numeroCartaoTransacaoOriginal;
    private String codigoBandeira;
    private String codigoProduto;
    private Produto produto;
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

    public String getDataTransacaoAJ() {
        return dataTransacaoAJ;
    }

    public void setDataTransacaoAJ(String dataTransacaoAJ) {
        this.dataTransacaoAJ = dataTransacaoAJ;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public MeioCaptura getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(MeioCaptura meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public String getCodigoAjuste() {
        return codigoAjuste;
    }

    public void setCodigoAjuste(String codigoAjuste) {
        this.codigoAjuste = codigoAjuste;
    }

    public String getDescricaoMotivoAjuste() {
        return descricaoMotivoAjuste;
    }

    public void setDescricaoMotivoAjuste(String descricaoMotivoAjuste) {
        this.descricaoMotivoAjuste = descricaoMotivoAjuste;
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

    public String getNumeroCartaoTransacaoOriginal() {
        return numeroCartaoTransacaoOriginal;
    }

    public void setNumeroCartaoTransacaoOriginal(String numeroCartaoTransacaoOriginal) {
        this.numeroCartaoTransacaoOriginal = numeroCartaoTransacaoOriginal;
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

    public String getHoraTransacaoAJ() {
        return horaTransacaoAJ;
    }

    public void setHoraTransacaoAJ(String horaTransacaoAJ) {
        this.horaTransacaoAJ = horaTransacaoAJ;
    }

    public Adquirente getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(Adquirente adquirente) {
        this.adquirente = adquirente;
    }

    public Ajuste getAjuste() {
        return ajuste;
    }

    public void setAjuste(Ajuste ajuste) {
        this.ajuste = ajuste;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
