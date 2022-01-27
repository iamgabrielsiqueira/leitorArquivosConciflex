package com.example.conciflex.controller;

import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;
import com.example.conciflex.model.classes.Estabelecimento;
import com.example.conciflex.model.classes.convcard.*;
import com.example.conciflex.model.dao.convcard.VendaConvcardDAO;
import com.example.conciflex.model.jdbc.JDBCEmpresaDAO;
import com.example.conciflex.model.jdbc.JDBCEstabelecimentoDAO;
import com.example.conciflex.model.jdbc.ben.JDBCVendaDAO;
import com.example.conciflex.model.jdbc.convcard.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ConvcardController {

    @FXML
    private Label lbMensagem;

    private ObservableList<ComprovanteVendaConvcard> listaVendas = FXCollections.observableArrayList();
    private ObservableList<ComprovantePagamentoConvcard> listaPagamentos = FXCollections.observableArrayList();
    private ObservableList<CancelamentoConvcard> listaCancelamento = FXCollections.observableArrayList();
    private ObservableList<TarifaBancariaConvcard> listaTarifaBancaria = FXCollections.observableArrayList();

    public void setLbMensagem(Label lbMensagem) {
        this.lbMensagem = lbMensagem;
    }

    public void lerArquivoConvcard(String pasta, String arquivo, Estabelecimento estabelecimento) throws IOException, ParseException {

        listaVendas.clear();
        listaPagamentos.clear();
        listaCancelamento.clear();
        listaTarifaBancaria.clear();

        FileInputStream stream = new FileInputStream(pasta + "\\" + arquivo);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();

        HeaderArquivoConvcard headerArquivoConvcard = new HeaderArquivoConvcard();
        HeaderLoteTransacoesConvcard headerLoteTransacoesConvcard = new HeaderLoteTransacoesConvcard();
        ComprovanteVendaConvcard comprovanteVendaConvcard = new ComprovanteVendaConvcard();
        ComprovantePagamentoConvcard comprovantePagamentoConvcard = new ComprovantePagamentoConvcard();
        CancelamentoConvcard cancelamentoConvcard = new CancelamentoConvcard();
        TarifaBancariaConvcard tarifaBancariaConvcard = new TarifaBancariaConvcard();
        TrailerLoteTransacoesConvcard trailerLoteTransacoesConvcard = new TrailerLoteTransacoesConvcard();
        TrailerArquivoConvcard trailerArquivoConvcard = new TrailerArquivoConvcard();

        Boolean flag = true;
        int verificarProcesso = 0;

        java.util.Date data = new java.util.Date();
        Date dataImportacao = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Time horaImportacao = java.sql.Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(data));

        System.out.println("Lendo arquivo: " + arquivo);

        while(line != null) {
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];

            if(flag == true) {
                if(identificador.equals("A0")) {
                    verificarProcesso++;

                    try {
                        headerArquivoConvcard = processarHeaderArquivoConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("1 - Erro de array");
                    }

                    try {
                        JDBCHeaderArquivoConvcardDAO.getInstance().create(headerArquivoConvcard, arquivo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(identificador.equals("L0")) {
                    verificarProcesso++;

                    try {
                        headerLoteTransacoesConvcard = processarHeaderLoteConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("2 - Erro de array");
                    }

                    try {
                        JDBCHeaderLoteTransacoesConvcardDAO.getInstance().create(headerLoteTransacoesConvcard, arquivo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(identificador.equals("CV")) {
                    verificarProcesso++;

                    try {
                        comprovanteVendaConvcard = processarComprovanteVendaConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("3 - Erro de array");
                    }

                    Boolean verificaVendaExiste = false;

                    try {
                        verificaVendaExiste = JDBCVendaConvcardDAO.getInstance().verificarDuplicidade(comprovanteVendaConvcard.getChaveVenda());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(verificaVendaExiste == false) {
                        try {
                            JDBCComprovanteVendaConvcardDAO.getInstance().create(comprovanteVendaConvcard, arquivo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        listaVendas.add(comprovanteVendaConvcard);
                    }
                } else if(identificador.equals("CP")) {
                    verificarProcesso++;

                    try {
                        comprovantePagamentoConvcard = processarComprovantePagamentoConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("4 - Erro de array");
                    }

                    Boolean verificaPagamentoExiste = false;

                    try {
                        verificaPagamentoExiste = JDBCPagamentoConvcardDAO.getInstance().verificarDuplicidade(comprovantePagamentoConvcard.getChavePagamento());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(verificaPagamentoExiste == false) {
                        try {
                            JDBCComprovantePagamentoConvcardDAO.getInstance().create(comprovantePagamentoConvcard, arquivo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        listaPagamentos.add(comprovantePagamentoConvcard);
                    }
                } else if(identificador.equals("CC")) {
                    verificarProcesso++;

                    try {
                        cancelamentoConvcard = processarCancelamentoConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("5 - Erro de array");
                    }

                    Boolean verificaCancelamentoExiste = false;

                    try {
                        verificaCancelamentoExiste = JDBCCancelamentoConvcardDAO.getInstance().searchCancelamento(cancelamentoConvcard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(verificaCancelamentoExiste == false) {
                        try {
                            JDBCCancelamentoConvcardDAO.getInstance().create(cancelamentoConvcard, arquivo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        listaCancelamento.add(cancelamentoConvcard);
                    }
                } else if(identificador.equals("TB")) {
                    verificarProcesso++;

                    try {
                        tarifaBancariaConvcard = processarTarifaBancariaConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("6 - Erro de array");
                    }

                    Boolean verificaTarifaBancariaExiste = false;

                    try {
                        verificaTarifaBancariaExiste = JDBCTarifaBancariaConvcardDAO.getInstance().searchTarifa(tarifaBancariaConvcard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(verificaTarifaBancariaExiste == false) {
                        try {
                            JDBCTarifaBancariaConvcardDAO.getInstance().create(tarifaBancariaConvcard, arquivo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        listaTarifaBancaria.add(tarifaBancariaConvcard);
                    }
                } else if(identificador.equals("L9")) {
                    verificarProcesso++;

                    try {
                        trailerLoteTransacoesConvcard = processarTrailerLoteTransacoesConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("7 - Erro de array");
                    }

                    try {
                        JDBCTrailerLoteTransacoesConvcardDAO.getInstance().create(trailerLoteTransacoesConvcard, arquivo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(identificador.equals("A9")) {
                    verificarProcesso++;

                    try {
                        trailerArquivoConvcard = processarTrailerArquivoConvcard(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("8 - Erro de array");
                    }

                    try {
                        JDBCTrailerArquivoConvcardDAO.getInstance().create(trailerArquivoConvcard, arquivo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    flag = false;
                }
            }

            line = br.readLine();
        }

        br.close();
        reader.close();
        stream.close();

        for (ComprovanteVendaConvcard venda:listaVendas) {
            System.out.println("Enviando venda..." + venda.getDataTransacaoSQL());

            try {
                JDBCVendaConvcardDAO.getInstance().create(venda, dataImportacao, horaImportacao, arquivo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (ComprovantePagamentoConvcard pagamento:listaPagamentos) {
            long id = 0;

            try {
                System.out.println("Enviando pagamento..." + pagamento.getDataTransacaoSQL());

                id = JDBCPagamentoConvcardDAO.getInstance().create(pagamento, dataImportacao, horaImportacao, arquivo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(id != 0) {
                Boolean verificarVenda = false;

                try {
                    verificarVenda = JDBCVendaConvcardDAO.getInstance().search(pagamento);
                } catch (Exception e) {
                    gravarLog("Erro #19: " + e + " " + arquivo);
                    mostrarMensagem("Erro #19" + e);
                }

                if(verificarVenda == true) {
                    try {
                        System.out.println("Atualizando venda/pagamento..." + pagamento.getDataTransacaoSQL());

                        JDBCVendaConvcardDAO.getInstance().updateVendaPaga(pagamento, id);
                    } catch (Exception e) {
                        gravarLog("Erro #20: " + e + " " + arquivo);
                        mostrarMensagem("Erro #20" + e);
                    }
                }
            }
        }

        for (CancelamentoConvcard cancelamento:listaCancelamento) {
            ComprovanteVendaConvcard vendaCancelamento = null;

            try {
                vendaCancelamento = JDBCVendaConvcardDAO.getInstance().searchCancelamento(cancelamento);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(vendaCancelamento != null) {
                try {
                    JDBCCancelamentoConvcardDAO.getInstance().createCancelamento(cancelamentoConvcard, dataImportacao, horaImportacao, arquivo, vendaCancelamento);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int idVendaCancelada = 0;

                try {
                    idVendaCancelada = JDBCVendaConvcardDAO.getInstance().searchVendaCancelada(cancelamentoConvcard);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(idVendaCancelada != 0) {
                    try {
                        JDBCVendaConvcardDAO.getInstance().updateVendaCancelada(idVendaCancelada, cancelamentoConvcard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (TarifaBancariaConvcard tarifaBancaria:listaTarifaBancaria) {
            try {
                JDBCTarifaBancariaConvcardDAO.getInstance().createPagamento(tarifaBancaria, dataImportacao, horaImportacao, arquivo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Estabelecimento getFileEstabelecimentoConvcard(String pasta, String arquivo) throws IOException {
        FileInputStream stream = new FileInputStream(pasta + "\\" + arquivo);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        Cliente cliente = null;
        Boolean flag = true;
        Estabelecimento estabelecimento = null;

        while(line != null) {
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];
            String CNPJ = "";

            if(identificador.equals("A0") && flag == true) {
                for (int i = 28; i < 42; i++) { CNPJ += "" + line.toCharArray()[i]; }
                CNPJ = CNPJ.replaceFirst("^0+(?!$)", "");

                try {
                    estabelecimento = JDBCEstabelecimentoDAO.getInstance().search(CNPJ);
                } catch (Exception e) {
                    gravarLog("Erro #9: " + e + " " + arquivo);
                    mostrarMensagem("Erro #9" + e);
                }

                if(estabelecimento != null) {
                    cliente = estabelecimento.getCliente();
                    if(cliente != null) {
                        flag = false;
                    }
                }
            }

            line = br.readLine();
        }

        br.close();
        reader.close();
        stream.close();

        return estabelecimento;
    }

    public HeaderArquivoConvcard processarHeaderArquivoConvcard(char[] line) {
        HeaderArquivoConvcard headerArquivoConvcard = new HeaderArquivoConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseq = "";
        String versaoLayout = "";
        String dataGeracao = "";
        String horaGeracao = "";
        String cnpjLoja = "";
        String nomeLoja = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseq += "" + line[i]; }
        for (int i = 8; i < 14; i++) { versaoLayout += "" + line[i]; }
        for (int i = 14; i < 22; i++) { dataGeracao += "" + line[i]; }
        for (int i = 22; i < 28; i++) { horaGeracao += "" + line[i]; }
        for (int i = 28; i < 42; i++) { cnpjLoja += "" + line[i]; }
        for (int i = 42; i < 72; i++) { nomeLoja += "" + line[i]; }
        for (int i = 72; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ: " + nseq);
        System.out.println("Versão Layout: " + versaoLayout);
        System.out.println("Data Geração: " + dataGeracao);
        System.out.println("Hora Geração: " + horaGeracao);
        System.out.println("CNPJ Loja: " + cnpjLoja);
        System.out.println("Nome Loja: " + nomeLoja);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        headerArquivoConvcard.setTipoRegistro(tipoRegistro);
        headerArquivoConvcard.setNseqRegistro(nseq);
        headerArquivoConvcard.setVersaoLayout(versaoLayout);
        headerArquivoConvcard.setDataGeracao(dataGeracao);
        headerArquivoConvcard.setHoraGeracao(horaGeracao);
        headerArquivoConvcard.setCnpjLojas(cnpjLoja);
        headerArquivoConvcard.setNomeLojas(nomeLoja);
        headerArquivoConvcard.setEspacoReservado(espacoReservado);

        return headerArquivoConvcard;
    }

    public HeaderLoteTransacoesConvcard processarHeaderLoteConvcard(char[] line) {
        HeaderLoteTransacoesConvcard headerLoteTransacoesConvcard = new HeaderLoteTransacoesConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistro = "";
        String dataMovimento = "";
        String nseqLote = "";
        String cnpjAdm = "";
        String nomeAdm = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistro += "" + line[i]; }
        for (int i = 8; i < 16; i++) { dataMovimento += "" + line[i]; }
        for (int i = 16; i < 22; i++) { nseqLote += "" + line[i]; }
        for (int i = 22; i < 36; i++) { cnpjAdm += "" + line[i]; }
        for (int i = 36; i < 66; i++) { nomeAdm += "" + line[i]; }
        for (int i = 66; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro: " + nseqRegistro);
        System.out.println("Data Movimento: " + dataMovimento);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("CNPJ Admin: " + cnpjAdm);
        System.out.println("Nome Admin: " + nomeAdm);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        headerLoteTransacoesConvcard.setTipoRegistro(tipoRegistro);
        headerLoteTransacoesConvcard.setNseqRegistro(nseqRegistro);
        headerLoteTransacoesConvcard.setDataMovimento(dataMovimento);
        headerLoteTransacoesConvcard.setNseqLote(nseqLote);
        headerLoteTransacoesConvcard.setCnpjAdm(cnpjAdm);
        headerLoteTransacoesConvcard.setNomeAdm(nomeAdm);
        headerLoteTransacoesConvcard.setEspacoReservado(espacoReservado);

        return headerLoteTransacoesConvcard;
    }

    public ComprovanteVendaConvcard processarComprovanteVendaConvcard(char[] line) {
        ComprovanteVendaConvcard comprovanteVendaConvcard = new ComprovanteVendaConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistroArquivo = "";
        String nseqRegistroLote = "";
        String cnpj = "";
        String nsuTransacao = "";
        String dataTransacao = "";
        String horaTransacao = "";
        String numeroCartao = "";
        String numeroAutorizacao = "";
        String numeroParcela = "";
        String quantidadeParcelas = "";
        String dataLancamento = "";
        String valorBrutoVenda = "";
        String valorDesconto = "";
        String valorLiquidoVenda = "";
        String valorBrutoParcela = "";
        String valorDescontoParcela = "";
        String valorLiquidoParcela = "";
        String banco = "";
        String agencia = "";
        String conta = "";
        String nseqLote = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistroArquivo += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseqRegistroLote += "" + line[i]; }
        for (int i = 14; i < 28; i++) { cnpj += "" + line[i]; }
        for (int i = 28; i < 40; i++) { nsuTransacao += "" + line[i]; }
        for (int i = 40; i < 48; i++) { dataTransacao += "" + line[i]; }
        for (int i = 48; i < 54; i++) { horaTransacao += "" + line[i]; }
        for (int i = 54; i < 73; i++) { numeroCartao += "" + line[i]; }
        for (int i = 73; i < 85; i++) { numeroAutorizacao += "" + line[i]; }
        for (int i = 85; i < 87; i++) { numeroParcela += "" + line[i]; }
        for (int i = 87; i < 89; i++) { quantidadeParcelas += "" + line[i]; }
        for (int i = 89; i < 97; i++) { dataLancamento += "" + line[i]; }
        for (int i = 97; i < 108; i++) { valorBrutoVenda += "" + line[i]; }
        for (int i = 108; i < 119; i++) { valorDesconto += "" + line[i]; }
        for (int i = 119; i < 130; i++) { valorLiquidoVenda += "" + line[i]; }
        for (int i = 130; i < 141; i++) { valorBrutoParcela += "" + line[i]; }
        for (int i = 141; i < 152; i++) { valorDescontoParcela += "" + line[i]; }
        for (int i = 152; i < 163; i++) { valorLiquidoParcela += "" + line[i]; }
        for (int i = 163; i < 166; i++) { banco += "" + line[i]; }
        for (int i = 166; i < 172; i++) { agencia += "" + line[i]; }
        for (int i = 172; i < 183; i++) { conta += "" + line[i]; }
        for (int i = 183; i < 189; i++) { nseqLote += "" + line[i]; }
        for (int i = 189; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro Arquivo: " + nseqRegistroArquivo);
        System.out.println("NSEQ Registro Lote: " + nseqRegistroLote);
        System.out.println("CNPJ: " + cnpj);
        System.out.println("NSU Transação: " + nsuTransacao);
        System.out.println("Data Transação: " + dataTransacao);
        System.out.println("Hora Transação: " + horaTransacao);
        System.out.println("Número Cartão: " + numeroCartao);
        System.out.println("Número Autorização: " + numeroAutorizacao);
        System.out.println("Numero parcela: " + numeroParcela);
        System.out.println("Quantidade Parcelas: " + quantidadeParcelas);
        System.out.println("Data Lancamento: " + dataLancamento);
        System.out.println("Valor Bruto Venda: " + valorBrutoVenda);
        System.out.println("Valor Desconto: " + valorDesconto);
        System.out.println("Valor Liquido Venda: " + valorLiquidoVenda);
        System.out.println("Valor Bruto Parcela: " + valorBrutoParcela);
        System.out.println("Valor Desconto Parcela: " + valorDescontoParcela);
        System.out.println("Valor Liquido Parcela: " + valorLiquidoParcela);
        System.out.println("Banco: " + banco);
        System.out.println("Agência: " + agencia);
        System.out.println("Conta: " + conta);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(empresa != null) {
            if(empresa.getCliente() != null) {
                cliente = empresa.getCliente();
            }
        }

        String nsuFormatada = nsuTransacao.replaceFirst("^0+(?!$)", "");
        Date dataTransacaoSQL = converterDataSQL(dataTransacao);
        Date dataLancamentoSQL = converterDataSQL(dataLancamento);
        Double valorBrutoFormatado = converterValorDouble(valorBrutoVenda);
        Double valorDescontoFormatado = converterValorDouble(valorDesconto);
        Double valorLiquidoFormatado = converterValorDouble(valorLiquidoVenda);
        String numeroCartaoFormatado = numeroCartao.replaceFirst("^0+(?!$)", "");
        int bancoFormatado = Integer.parseInt(banco);
        String agenciaFormatada = agencia.replaceFirst("^0+(?!$)", "");
        String contaFormatada = conta.replaceFirst("^0+(?!$)", "");
        String autorizacaoFormatada = numeroAutorizacao.replaceFirst("^0+(?!$)", "");

        while(autorizacaoFormatada.length() < 6) {
            autorizacaoFormatada = "0" + autorizacaoFormatada;
        }

        Double taxaPercentual = calcularTaxaPercentual(valorBrutoFormatado, valorDescontoFormatado);
        String chavePagamento = null;

        if(cliente != null) {
            chavePagamento = cliente.getId()+"-123-"+dataLancamentoSQL+"-1-1-"+nsuFormatada+autorizacaoFormatada+"-185-3";
        }

        Time horaTransacaoSQL = null;
        java.util.Date horaTransacaoDate = null;

        if(horaTransacao != null) {
            try {
                horaTransacaoDate = new SimpleDateFormat("HHmmss").parse(horaTransacao);
            } catch (ParseException e) {
                gravarLog("#29: " + e);
                mostrarMensagem("Erro #29: " + e);
            }
        }

        horaTransacaoSQL = new Time(horaTransacaoDate.getTime());

        String autorizador = "";

        try {
            autorizador = JDBCHeaderLoteTransacoesConvcardDAO.getInstance().searchAutorizador(nseqLote);
        } catch (Exception e) {
            e.printStackTrace();
        }

        comprovanteVendaConvcard.setTipoRegistro(tipoRegistro);
        comprovanteVendaConvcard.setNseqRegistroArquivo(nseqRegistroArquivo);
        comprovanteVendaConvcard.setNseqRegistroLote(nseqRegistroLote);
        comprovanteVendaConvcard.setCnpj(cnpj);
        comprovanteVendaConvcard.setNsuTransacao(nsuTransacao);
        comprovanteVendaConvcard.setDataTransacao(dataTransacao);
        comprovanteVendaConvcard.setHoraTransacao(horaTransacao);
        comprovanteVendaConvcard.setNumeroCartao(numeroCartao);
        comprovanteVendaConvcard.setNumeroAutorizacao(numeroAutorizacao);
        comprovanteVendaConvcard.setNumeroParcela(numeroParcela);
        comprovanteVendaConvcard.setQuantidadeParcelas(quantidadeParcelas);
        comprovanteVendaConvcard.setDataLancamento(dataLancamento);
        comprovanteVendaConvcard.setValorBrutoVenda(valorBrutoVenda);
        comprovanteVendaConvcard.setValorDesconto(valorDesconto);
        comprovanteVendaConvcard.setValorLiquidoVenda(valorLiquidoVenda);
        comprovanteVendaConvcard.setValorBrutoParcela(valorBrutoParcela);
        comprovanteVendaConvcard.setValorDescontoParcela(valorDescontoParcela);
        comprovanteVendaConvcard.setValorLiquidoParcela(valorLiquidoParcela);
        comprovanteVendaConvcard.setBanco(banco);
        comprovanteVendaConvcard.setAgencia(agencia);
        comprovanteVendaConvcard.setConta(conta);
        comprovanteVendaConvcard.setNseqLote(nseqLote);
        comprovanteVendaConvcard.setEspacoReservado(espacoReservado);
        comprovanteVendaConvcard.setEmpresa(empresa);
        comprovanteVendaConvcard.setCliente(cliente);
        comprovanteVendaConvcard.setNsuFormatada(nsuFormatada);
        comprovanteVendaConvcard.setDataTransacaoSQL(dataTransacaoSQL);
        comprovanteVendaConvcard.setDataLancamentoSQL(dataLancamentoSQL);
        comprovanteVendaConvcard.setValorBrutoFormatado(valorBrutoFormatado);
        comprovanteVendaConvcard.setValorDescontoFormatado(valorDescontoFormatado);
        comprovanteVendaConvcard.setValorLiquidoFormatado(valorLiquidoFormatado);
        comprovanteVendaConvcard.setNumeroCartaoFormatado(numeroCartaoFormatado);
        comprovanteVendaConvcard.setBancoFormatado(bancoFormatado);
        comprovanteVendaConvcard.setAgenciaFormatado(agenciaFormatada);
        comprovanteVendaConvcard.setContaFormatado(contaFormatada);
        comprovanteVendaConvcard.setAutorizacaoFormatado(autorizacaoFormatada);
        comprovanteVendaConvcard.setValorTaxaPercentual(taxaPercentual);
        comprovanteVendaConvcard.setChaveVenda(chavePagamento);
        comprovanteVendaConvcard.setHoraTransacaoSQL(horaTransacaoSQL);
        comprovanteVendaConvcard.setAutorizador(autorizador);

        return comprovanteVendaConvcard;
    }

    public ComprovantePagamentoConvcard processarComprovantePagamentoConvcard(char[] line) {
        ComprovantePagamentoConvcard comprovantePagamentoConvcard = new ComprovantePagamentoConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistroArquivo = "";
        String nseqRegistroLote = "";
        String cnpjLoja = "";
        String nsuTransacao = "";
        String dataTransacao = "";
        String horaTransacao = "";
        String numeroCartao = "";
        String numeroAutorizacao = "";
        String numeroParcela = "";
        String dataPagamento = "";
        String valorBrutoPagamento = "";
        String valorDesconto = "";
        String valorLiquidoPagamento = "";
        String banco = "";
        String agencia = "";
        String conta = "";
        String nseqLote = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistroArquivo += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseqRegistroLote += "" + line[i]; }
        for (int i = 14; i < 28; i++) { cnpjLoja += "" + line[i]; }
        for (int i = 28; i < 40; i++) { nsuTransacao += "" + line[i]; }
        for (int i = 40; i < 48; i++) { dataTransacao += "" + line[i]; }
        for (int i = 48; i < 54; i++) { horaTransacao += "" + line[i]; }
        for (int i = 54; i < 73; i++) { numeroCartao += "" + line[i]; }
        for (int i = 73; i < 85; i++) { numeroAutorizacao += "" + line[i]; }
        for (int i = 85; i < 87; i++) { numeroParcela += "" + line[i]; }
        for (int i = 87; i < 95; i++) { dataPagamento += "" + line[i]; }
        for (int i = 95; i < 106; i++) { valorBrutoPagamento += "" + line[i]; }
        for (int i = 106; i < 117; i++) { valorDesconto += "" + line[i]; }
        for (int i = 117; i < 128; i++) { valorLiquidoPagamento += "" + line[i]; }
        for (int i = 128; i < 131; i++) { banco += "" + line[i]; }
        for (int i = 131; i < 137; i++) { agencia += "" + line[i]; }
        for (int i = 137; i < 148; i++) { conta += "" + line[i]; }
        for (int i = 148; i < 154; i++) { nseqLote += "" + line[i]; }
        for (int i = 154; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro Arquivo: " + nseqRegistroArquivo);
        System.out.println("NSEQ Registro Lote: " + nseqRegistroLote);
        System.out.println("CNPJ Loja: " + cnpjLoja);
        System.out.println("NSU Transação: " + nsuTransacao);
        System.out.println("Data Transação: " + dataTransacao);
        System.out.println("Hora Transação: " + horaTransacao);
        System.out.println("Número Cartão: " + numeroCartao);
        System.out.println("Número Autorização: " + numeroAutorizacao);
        System.out.println("Numero parcela: " + numeroParcela);
        System.out.println("Data Pagamento: " + dataPagamento);
        System.out.println("Valor Bruto Pagamento: " + valorBrutoPagamento);
        System.out.println("Valor Desconto: " + valorDesconto);
        System.out.println("Valor Liquido Pagamento: " + valorLiquidoPagamento);
        System.out.println("Banco: " + banco);
        System.out.println("Agência: " + agencia);
        System.out.println("Conta: " + conta);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        String nsuFormatado = nsuTransacao.replaceFirst("^0+(?!$)", "");
        Date dataTransacaoSQL = converterDataSQL(dataTransacao);
        Date dataPagamentoSQL = converterDataSQL(dataPagamento);

        Double valorBrutoFormatado = converterValorDouble(valorBrutoPagamento);
        Double valorDescontoFormatado = converterValorDouble(valorDesconto);
        Double valorLiquidoFormatado = converterValorDouble(valorLiquidoPagamento);
        Double taxaPercentual = calcularTaxaPercentual(valorBrutoFormatado, valorDescontoFormatado);
        String cartaoFormatado = numeroCartao.replaceFirst("^0+(?!$)", "");
        String agenciaFormatado = agencia.replaceFirst("^0+(?!$)", "");
        String contaFormatado = conta.replaceFirst("^0+(?!$)", "");
        String autorizacaoFormatada = numeroAutorizacao.replaceFirst("^0+(?!$)", "");

        while(autorizacaoFormatada.length() < 6) {
            autorizacaoFormatada = "0" + autorizacaoFormatada;
        }

        String autorizador = "";

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(cnpjLoja);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(empresa != null) {
            if(empresa.getCliente() != null) {
                cliente = empresa.getCliente();
            }
        }

        try {
            autorizador = JDBCHeaderLoteTransacoesConvcardDAO.getInstance().searchAutorizador(nseqLote);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Time horaTransacaoSQL = null;
        java.util.Date horaTransacaoDate = null;

        if(horaTransacao != null) {
            try {
                horaTransacaoDate = new SimpleDateFormat("HHmmss").parse(horaTransacao);
            } catch (ParseException e) {
                gravarLog("#29: " + e);
                mostrarMensagem("Erro #29: " + e);
            }
        }

        horaTransacaoSQL = new Time(horaTransacaoDate.getTime());

        String chavePagamento = null;

        if(cliente != null) {
            chavePagamento = cliente.getId()+"-123-"+dataPagamentoSQL+"-1-1-"+nsuFormatado+autorizacaoFormatada+"-185-3";
        }

        comprovantePagamentoConvcard.setTipoRegistro(tipoRegistro);
        comprovantePagamentoConvcard.setNseqRegistroArquivo(nseqRegistroArquivo);
        comprovantePagamentoConvcard.setNseqRegistroLote(nseqRegistroLote);
        comprovantePagamentoConvcard.setCnpjLoja(cnpjLoja);
        comprovantePagamentoConvcard.setNsuTransacao(nsuTransacao);
        comprovantePagamentoConvcard.setDataTransacao(dataTransacao);
        comprovantePagamentoConvcard.setHoraTransacao(horaTransacao);
        comprovantePagamentoConvcard.setNumeroCartao(numeroCartao);
        comprovantePagamentoConvcard.setNumeroAutorizacao(numeroAutorizacao);
        comprovantePagamentoConvcard.setNumeroParcela(numeroParcela);
        comprovantePagamentoConvcard.setDataPagamento(dataPagamento);
        comprovantePagamentoConvcard.setValorBrutoPagamento(valorBrutoPagamento);
        comprovantePagamentoConvcard.setValorDesconto(valorDesconto);
        comprovantePagamentoConvcard.setValorLiquidoPagamento(valorLiquidoPagamento);
        comprovantePagamentoConvcard.setBanco(banco);
        comprovantePagamentoConvcard.setAgencia(agencia);
        comprovantePagamentoConvcard.setConta(conta);
        comprovantePagamentoConvcard.setNseqLote(nseqLote);
        comprovantePagamentoConvcard.setEspacoReservado(espacoReservado);

        comprovantePagamentoConvcard.setNsuFormatado(nsuFormatado);
        comprovantePagamentoConvcard.setDataTransacaoSQL(dataTransacaoSQL);
        comprovantePagamentoConvcard.setDataPagamentoSQL(dataPagamentoSQL);
        comprovantePagamentoConvcard.setAutorizador(autorizador);
        comprovantePagamentoConvcard.setValorBrutoFormatado(valorBrutoFormatado);
        comprovantePagamentoConvcard.setValorDescontoFormatado(valorDescontoFormatado);
        comprovantePagamentoConvcard.setValorLiquidoFormatado(valorLiquidoFormatado);
        comprovantePagamentoConvcard.setTaxaPercentual(taxaPercentual);
        comprovantePagamentoConvcard.setNumeroCartaoFormatado(cartaoFormatado);
        comprovantePagamentoConvcard.setAgenciaFormatado(agenciaFormatado);
        comprovantePagamentoConvcard.setContaFormatado(contaFormatado);
        comprovantePagamentoConvcard.setAutorizacaoFormatado(autorizacaoFormatada);
        comprovantePagamentoConvcard.setHoraTransacaoSQL(horaTransacaoSQL);
        comprovantePagamentoConvcard.setEmpresa(empresa);
        comprovantePagamentoConvcard.setCliente(cliente);
        comprovantePagamentoConvcard.setChavePagamento(chavePagamento);

        return comprovantePagamentoConvcard;
    }

    public CancelamentoConvcard processarCancelamentoConvcard(char[] line) {
        CancelamentoConvcard cancelamentoConvcard = new CancelamentoConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistroArquivo = "";
        String nseqRegistroLote = "";
        String cnpjLoja = "";
        String nsuTransacao = "";
        String dataTransacao = "";
        String horaTransacao = "";
        String numeroCartao = "";
        String numeroAutorizacao = "";
        String numeroParcela = "";
        String dataCancelamento = "";
        String nseqLote = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistroArquivo += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseqRegistroLote += "" + line[i]; }
        for (int i = 14; i < 28; i++) { cnpjLoja += "" + line[i]; }
        for (int i = 28; i < 40; i++) { nsuTransacao += "" + line[i]; }
        for (int i = 40; i < 48; i++) { dataTransacao += "" + line[i]; }
        for (int i = 48; i < 54; i++) { horaTransacao += "" + line[i]; }
        for (int i = 54; i < 73; i++) { numeroCartao += "" + line[i]; }
        for (int i = 73; i < 85; i++) { numeroAutorizacao += "" + line[i]; }
        for (int i = 85; i < 87; i++) { numeroParcela += "" + line[i]; }
        for (int i = 87; i < 95; i++) { dataCancelamento += "" + line[i]; }
        for (int i = 95; i < 101; i++) { nseqLote += "" + line[i]; }
        for (int i = 101; i < 240; i++) { espacoReservado += "" + line[i]; }

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(cnpjLoja);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(empresa != null) {
            if(empresa.getCliente() != null) {
                cliente = empresa.getCliente();
            }
        }

        String nsuFormatada = nsuTransacao.replaceFirst("^0+(?!$)", "");
        Date dataTransacaoSQL = converterDataSQL(dataTransacao);
        Date dataCancelamentoSQL = converterDataSQL(dataCancelamento);
        String autorizador = "";
        String autorizacaoFormatada = numeroAutorizacao.replaceFirst("^0+(?!$)", "");
        String numeroCartaoFormatado = numeroCartao.replaceFirst("^0+(?!$)", "");

        while(autorizacaoFormatada.length() < 6) {
            autorizacaoFormatada = "0" + autorizacaoFormatada;
        }

        try {
            autorizador = JDBCHeaderLoteTransacoesConvcardDAO.getInstance().searchAutorizador(nseqLote);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Time horaTransacaoSQL = null;
        java.util.Date horaTransacaoDate = null;

        if(horaTransacao != null) {
            try {
                horaTransacaoDate = new SimpleDateFormat("HHmmss").parse(horaTransacao);
            } catch (ParseException e) {
                gravarLog("#29: " + e);
                mostrarMensagem("Erro #29: " + e);
            }
        }

        horaTransacaoSQL = new Time(horaTransacaoDate.getTime());

        String chaveCancelamento = "";

        if(cliente != null) {
            chaveCancelamento = cliente.getId()+"-123-"+dataCancelamentoSQL+"-1-1-"+nsuFormatada+autorizacaoFormatada+"-185-3";
        }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro Arquivo: " + nseqRegistroArquivo);
        System.out.println("NSEQ Registro Lote: " + nseqRegistroLote);
        System.out.println("CNPJ: " + cnpjLoja);
        System.out.println("NSU Transacao: " + nsuTransacao);
        System.out.println("Data Transacao: " + dataTransacao);
        System.out.println("Hora Transacao: " + horaTransacao);
        System.out.println("Número Cartão: " + numeroCartao);
        System.out.println("Número Autorização: " + numeroAutorizacao);
        System.out.println("Número Parcela: " + numeroParcela);
        System.out.println("Data Cancelamento: " + dataCancelamento);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        cancelamentoConvcard.setTipoRegistro(tipoRegistro);
        cancelamentoConvcard.setNseqRegistroArquivo(nseqRegistroArquivo);
        cancelamentoConvcard.setNseqRegistroLote(nseqRegistroLote);
        cancelamentoConvcard.setCnpjLoja(cnpjLoja);
        cancelamentoConvcard.setNsuTransacao(nsuTransacao);
        cancelamentoConvcard.setDataTransacao(dataTransacao);
        cancelamentoConvcard.setHoraTransacao(horaTransacao);
        cancelamentoConvcard.setNumeroCartao(numeroCartao);
        cancelamentoConvcard.setNumeroAutorizacao(numeroAutorizacao);
        cancelamentoConvcard.setNumeroParcela(numeroParcela);
        cancelamentoConvcard.setDataCancelamento(dataCancelamento);
        cancelamentoConvcard.setNseqLote(nseqLote);
        cancelamentoConvcard.setEspacoReservado(espacoReservado);

        cancelamentoConvcard.setEmpresa(empresa);
        cancelamentoConvcard.setCliente(cliente);
        cancelamentoConvcard.setNsuFormatado(nsuFormatada);
        cancelamentoConvcard.setDataTransacaoSQL(dataTransacaoSQL);
        cancelamentoConvcard.setDataCancelamentoSQL(dataCancelamentoSQL);
        cancelamentoConvcard.setAutorizador(autorizador);
        cancelamentoConvcard.setAutorizacaoFormatado(autorizacaoFormatada);
        cancelamentoConvcard.setHoraTransacaoSQL(horaTransacaoSQL);
        cancelamentoConvcard.setChaveCancelamento(chaveCancelamento);
        cancelamentoConvcard.setNumeroCartaoFormatado(numeroCartaoFormatado);

        return cancelamentoConvcard;
    }

    public TarifaBancariaConvcard processarTarifaBancariaConvcard(char[] line) {
        TarifaBancariaConvcard tarifaBancariaConvcard = new TarifaBancariaConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistroArquivo = "";
        String nseqRegistroLote = "";
        String cnpjLoja = "";
        String dataTransacao = "";
        String valor = "";
        String banco = "";
        String agencia = "";
        String conta = "";
        String nseqLote = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistroArquivo += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseqRegistroLote += "" + line[i]; }
        for (int i = 14; i < 28; i++) { cnpjLoja += "" + line[i]; }
        for (int i = 28; i < 36; i++) { dataTransacao += "" + line[i]; }
        for (int i = 36; i < 47; i++) { valor += "" + line[i]; }
        for (int i = 47; i < 50; i++) { banco += "" + line[i]; }
        for (int i = 50; i < 56; i++) { agencia += "" + line[i]; }
        for (int i = 56; i < 67; i++) { conta += "" + line[i]; }
        for (int i = 67; i < 73; i++) { nseqLote += "" + line[i]; }
        for (int i = 73; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro Arquivo: " + nseqRegistroArquivo);
        System.out.println("NSEQ Registro Lote: " + nseqRegistroLote);
        System.out.println("CNPJ: " + cnpjLoja);
        System.out.println("Data Transacao: " + dataTransacao);
        System.out.println("Valor: " + valor);
        System.out.println("Banco: " + banco);
        System.out.println("Agência: " + agencia);
        System.out.println("Conta: " + conta);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(cnpjLoja);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(empresa != null) {
            if(empresa.getCliente() != null) {
                cliente = empresa.getCliente();
            }
        }

        Date dataTransacaoSQL = converterDataSQL(dataTransacao);

        String autorizador = "";

        try {
            autorizador = JDBCHeaderLoteTransacoesConvcardDAO.getInstance().searchAutorizador(nseqLote);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Double valorFormatado = converterValorDouble(valor);
        String agenciaFormatada = agencia.replaceFirst("^0+(?!$)", "");
        String contaFormatada = conta.replaceFirst("^0+(?!$)", "");

        tarifaBancariaConvcard.setTipoRegistro(tipoRegistro);
        tarifaBancariaConvcard.setNseqRegistroArquivo(nseqRegistroArquivo);
        tarifaBancariaConvcard.setNseqRegistroLote(nseqRegistroLote);
        tarifaBancariaConvcard.setCnpjLoja(cnpjLoja);
        tarifaBancariaConvcard.setDataTransacao(dataTransacao);
        tarifaBancariaConvcard.setValor(valor);
        tarifaBancariaConvcard.setBanco(banco);
        tarifaBancariaConvcard.setAgencia(agencia);
        tarifaBancariaConvcard.setConta(conta);
        tarifaBancariaConvcard.setNseqLote(nseqLote);
        tarifaBancariaConvcard.setEspacoReservado(espacoReservado);
        tarifaBancariaConvcard.setEmpresa(empresa);
        tarifaBancariaConvcard.setCliente(cliente);
        tarifaBancariaConvcard.setDataTransacaoSQL(dataTransacaoSQL);
        tarifaBancariaConvcard.setAutorizador(autorizador);
        tarifaBancariaConvcard.setValorFormatado(valorFormatado);
        tarifaBancariaConvcard.setAgenciaFormatada(agenciaFormatada);
        tarifaBancariaConvcard.setContaFormatada(contaFormatada);

        return tarifaBancariaConvcard;
    }

    public TrailerLoteTransacoesConvcard processarTrailerLoteTransacoesConvcard(char[] line) {
        TrailerLoteTransacoesConvcard trailerLoteTransacoesConvcard = new TrailerLoteTransacoesConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistroArquivo = "";
        String nseqLote = "";
        String quantidadeRegistrosLote = "";
        String valorTotalBrutoLote = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistroArquivo += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseqLote += "" + line[i]; }
        for (int i = 14; i < 20; i++) { quantidadeRegistrosLote += "" + line[i]; }
        for (int i = 20; i < 32; i++) { valorTotalBrutoLote += "" + line[i]; }
        for (int i = 32; i < 240; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro Arquivo: " + nseqRegistroArquivo);
        System.out.println("NSEQ Lote: " + nseqLote);
        System.out.println("Quantidade Registros Lote: " + quantidadeRegistrosLote);
        System.out.println("Valor Total Bruto Lote: " + valorTotalBrutoLote);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        trailerLoteTransacoesConvcard.setTipoRegistro(tipoRegistro);
        trailerLoteTransacoesConvcard.setNseqRegistroArquivo(nseqRegistroArquivo);
        trailerLoteTransacoesConvcard.setNseqLote(nseqLote);
        trailerLoteTransacoesConvcard.setQuantidadeRegistrosLote(quantidadeRegistrosLote);
        trailerLoteTransacoesConvcard.setValorTotalBrutoLote(valorTotalBrutoLote);
        trailerLoteTransacoesConvcard.setEspacoReservado(espacoReservado);

        return trailerLoteTransacoesConvcard;
    }

    public TrailerArquivoConvcard processarTrailerArquivoConvcard(char[] line) {
        TrailerArquivoConvcard trailerArquivoConvcard = new TrailerArquivoConvcard();

        String tipoRegistro = line[0] + "" + line[1];
        String nseqRegistro = "";
        String quantidadeRegistros = "";
        String espacoReservado = "";

        for (int i = 2; i < 8; i++) { nseqRegistro += "" + line[i]; }
        for (int i = 8; i < 17; i++) { quantidadeRegistros += "" + line[i]; }
        for (int i = 17; i < 223; i++) { espacoReservado += "" + line[i]; }

        /*System.out.println("Tipo Registro: " + tipoRegistro);
        System.out.println("NSEQ Registro: " + nseqRegistro);
        System.out.println("Quantidade Registros: " + quantidadeRegistros);
        System.out.println("Espaço Reservado: " + espacoReservado);*/

        trailerArquivoConvcard.setTipoRegistro(tipoRegistro);
        trailerArquivoConvcard.setNseqRegistro(nseqRegistro);
        trailerArquivoConvcard.setQuantidadeRegistros(quantidadeRegistros);
        trailerArquivoConvcard.setEspacoReservado(espacoReservado);

        return trailerArquivoConvcard;
    }

    private void mostrarMensagem(String mensagem) {
        Platform.runLater(() -> {
            lbMensagem.setVisible(true);
            lbMensagem.setText(mensagem);
        });
    }

    public void gravarLog(String log) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        PrintWriter printer = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("C:\\Users\\Gabriel\\IdeaProjects\\LeituraArquivosConciflex\\out\\artifacts\\LeituraArquivosConciflex_jar\\leitor-log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        printer = new PrintWriter(fw);
        printer.println(dtf.format(now) + " - " +log);
        printer.close();
    }

    public Date converterDataSQL(String dataString) {
        java.util.Date dataUtilFormat = null;

        if(dataString != null) {
            try {
                dataUtilFormat = new SimpleDateFormat("ddMMyyyy").parse(dataString);
            } catch (ParseException e) {
                gravarLog("#30: " + e);
                mostrarMensagem("Erro #30: " + e);
            }
        }

        long timeInMilliSeconds = dataUtilFormat.getTime();
        Date dataSQL = new java.sql.Date(timeInMilliSeconds);

        return dataSQL;
    }

    public Double converterValorDouble(String valorString) {
        Double valorFinal = null;
        String valorFormatado = "";

        for (int i = 0; i < valorString.length(); i++) {
            valorFormatado += valorString.toCharArray()[i];
            if(i == (valorString.length() - 3)) {
                valorFormatado += ".";
            }
        }

        valorFormatado = valorFormatado.replaceFirst("^0+(?!$)", "");

        String primeiro = String.valueOf(valorFormatado.toCharArray()[0]);

        if(primeiro.equals(".")) {
            valorFormatado = "0" + valorFormatado;
        }

        valorFinal = Double.parseDouble(valorFormatado);

        return valorFinal;
    }

    public Double calcularTaxaPercentual(Double valorBruto, Double valorDesconto) {
        Double taxaPercentual = 0.0;
        taxaPercentual = (valorDesconto * 100) / valorBruto;

        return taxaPercentual;
    }
}
