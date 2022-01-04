package com.example.conciflex.controller;

import com.example.conciflex.MainApplication;
import com.example.conciflex.model.classes.*;
import com.example.conciflex.model.jdbc.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainController {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbMensagem;

    private ObservableList<TipoLancamento> listaTiposLancamento = FXCollections.observableArrayList();
    private ObservableList<MoedaCorrente> listaMoedasCorrente = FXCollections.observableArrayList();
    private ObservableList<TipoProcessamento> listaTiposProcessamento = FXCollections.observableArrayList();
    private ObservableList<MeioCaptura> listaMeioCaptura = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private ObservableList<Adquirente> listaAdquirentes = FXCollections.observableArrayList();
    private ObservableList<Ajuste> listaAjustes = FXCollections.observableArrayList();

    private File folder = new File("Z:\\SKYLINE");

    public void initialize() {

        listarTiposLancamento();
        listarMoedasCorrentes();
        listarTiposProcessamento();
        listarMeioCaptura();
        listarProdutos();
        listarAdquirentes();
        listarAjustes();

        lbMensagem.setVisible(false);
    }

    @FXML
    public void processar() {
        Thread threadProcesso = new Thread(() -> {
            while (true) {
                mostrarMensagem("Aguardando...");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    String pasta = "";
                    String arquivo = "";

                    Estabelecimento estabelecimento = null;
                    Boolean arquivoBuscar = false;

                    if (listOfFiles[i].isFile() && !listOfFiles[i].getName().contains("desktop.ini")) {
                        pasta = folder.getAbsolutePath();
                        arquivo = listOfFiles[i].getName();

                        Boolean verificarOperadora = false;

                        try {
                            //VERIFICA SE O ARQUIVO É BEN VISA VALE
                            verificarOperadora = verificarBenVisaVale(pasta, arquivo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(verificarOperadora == true) {
                            try {
                                arquivoBuscar = JDBCArquivoDAO.getInstance().search(arquivo);
                            } catch (Exception e) {
                                gravarLog("Erro #2: " + e);
                                mostrarMensagem("Erro #2" + e);
                            }

                            if(arquivoBuscar == false) {
                                try {
                                    estabelecimento = getFileEstabelecimento(pasta, arquivo);
                                } catch (IOException e) {
                                    gravarLog("Erro #1: " + e);
                                    mostrarMensagem("Erro #1" + e);
                                }

                                gravarLog("Lendo arquivo: " + arquivo);
                                mostrarMensagem("Lendo arquivo..." + arquivo);

                                try {
                                    lerArquivo(pasta, arquivo, estabelecimento);
                                } catch (IOException e) {
                                    gravarLog("Erro #3: " + e);
                                    mostrarMensagem("Erro #3" + e);
                                } catch (ParseException e) {
                                    gravarLog("Erro #4: " + e);
                                    mostrarMensagem("Erro #4" + e);
                                }

                                gravarLog("Arquivo lido: " + arquivo);
                                mostrarMensagem("Arquivo " + arquivo + " processado!");

                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException interruptedException) {
                                    gravarLog("Erro #5: " + interruptedException);
                                    mostrarMensagem("Erro #5" + interruptedException);
                                }
                            }
                        }

                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    gravarLog("Erro #6: " + interruptedException);
                    mostrarMensagem("Erro #6" + interruptedException);
                }
            }
        });

        threadProcesso.setDaemon(true);
        threadProcesso.start();
    }

    public Estabelecimento getFileEstabelecimento(String pasta, String arquivo) throws IOException {
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

            if(identificador.equals("RV") && flag == true) {
                for (int i = 2; i < 17; i++) { CNPJ += "" + line.toCharArray()[i]; }
                CNPJ = CNPJ.replaceFirst("^0+(?!$)", "");

                try {
                    estabelecimento = JDBCEstabelecimentoDAO.getInstance().search(CNPJ);
                } catch (Exception e) {
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

    public Boolean verificarBenVisaVale(String pasta, String arquivo) throws IOException {
        Boolean verificar = false;

        FileInputStream stream = new FileInputStream(pasta + "\\" + arquivo);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        String line = br.readLine();
        Integer currentLine = 0;

        while(line != null) {
            currentLine++;
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];

            if(identificador.equals("A0")) {
                String nomeAdministradora = "";

                try {
                    for (int i = 28; i < 88; i++) { nomeAdministradora += "" + line.toCharArray()[i]; }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //gravarLog("");
                }

                if(nomeAdministradora.contains("BEN VISA VALE")) {
                    verificar = true;
                }
            }

            line = br.readLine();
        }

        br.close();
        reader.close();
        stream.close();

        return verificar;
    }

    public void lerArquivo(String pasta, String arquivo, Estabelecimento estabelecimento) throws IOException, ParseException {
        FileInputStream stream = new FileInputStream(pasta + "\\" + arquivo);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        String line = br.readLine();
        Integer currentLine = 0;
        HeaderArquivo headerArquivo = new HeaderArquivo();
        HeaderLoteTransacao headerLoteTransacao = new HeaderLoteTransacao();
        ResumoVenda resumoVenda = new ResumoVenda();
        ComprovanteVenda comprovanteVenda = new ComprovanteVenda();
        AjusteCreditoDebito ajusteCreditoDebito = new AjusteCreditoDebito();
        Cancelamento cancelamento = new Cancelamento();
        TrailerLoteTransacao trailerLoteTransacao = new TrailerLoteTransacao();
        TrailerArquivo trailerArquivo = new TrailerArquivo();
        Boolean flag = true;
        int verificarProcesso = 0;

        while(line != null) {
            currentLine++;
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];

            if(flag == true) {
                if(identificador.equals("A0")) {
                    verificarProcesso++;

                    try {
                        headerArquivo = processarHeaderArquivo(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("1 - Erro de array");
                    }

                    if(headerArquivo != null) {
                        String dataGeracao = headerArquivo.getDataGeracao();
                        String idMovimento = headerArquivo.getIdMovimento();

                        Boolean headerBuscar = false;

                        try {
                            headerBuscar = JDBCHeaderArquivoDAO.getInstance().search(dataGeracao, idMovimento);
                        } catch (Exception e) {
                            gravarLog("Erro #11: " + e);
                            mostrarMensagem("Erro #11" + e);
                        }

                        if(headerBuscar == false) {
                            try {
                                JDBCHeaderArquivoDAO.getInstance().create(headerArquivo, arquivo);
                            } catch (Exception e) {
                                gravarLog("Erro #10: " + e);
                                mostrarMensagem("Erro #10" + e);
                            }
                        } else {
                            flag = false;
                        }
                    }
                } else if(identificador.equals("L0")) {
                    verificarProcesso++;

                    try {
                        headerLoteTransacao = processarHeaderLote(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("2 - Erro de array");
                    }

                    try {
                        JDBCHeaderLoteTransacoesDAO.getInstance().create(headerLoteTransacao, arquivo);
                    } catch (Exception e) {
                        mostrarMensagem("Erro #12" + e);
                    }
                } else if(identificador.equals("RV")) {
                    verificarProcesso++;

                    try {
                        resumoVenda = processarResumoVenda(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("3 - Erro de array");
                    }

                    try {
                        JDBCResumoVendasDAO.getInstance().create(resumoVenda, arquivo);
                    } catch (Exception e) {
                        gravarLog("#321" + e);
                        gravarLog("#323" + e.getCause());
                        mostrarMensagem("Erro #13" + e);
                    }
                } else if(identificador.equals("CV")) {
                    verificarProcesso++;

                    try {
                        comprovanteVenda = processarComprovanteVenda(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("4 - Erro de array");
                    }

                    java.util.Date data = new java.util.Date();
                    Date dataImportacao = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    Time horaImportacao = java.sql.Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(data));

                    Boolean verificar = false;

                    try {
                        JDBCComprovanteVendaDAO.getInstance().create(comprovanteVenda, arquivo);
                    } catch (Exception e) {
                        mostrarMensagem("Erro #14" + e);
                    }

                    if(comprovanteVenda.getTipoLancamento() != null) {
                        if(comprovanteVenda.getTipoLancamento().getId() == 0) {
                            /*VENDA*/

                            try {
                                verificar = JDBCVendaDAO.getInstance().verificarDuplicidade(comprovanteVenda.getChavePagamento());
                            } catch (Exception e) {
                                mostrarMensagem("Erro #15" + e);
                            }

                            // VERIFICA SE A VENDA JÁ EXISTE
                            if(verificar == false) {
                                try {
                                    JDBCVendaDAO.getInstance().create(comprovanteVenda, dataImportacao, horaImportacao, arquivo);
                                } catch (Exception e) {
                                    mostrarMensagem("Erro #16" + e);
                                }
                            }
                        } else {
                            /*PAGAMENTO*/

                            try {
                                verificar = JDBCPagamentoDAO.getInstance().verificarDuplicidade(comprovanteVenda.getChavePagamento());
                            } catch (Exception e) {
                                mostrarMensagem("Erro #17" + e);
                            }

                            // VERIFICA SE O PAGAMENTO JÁ EXISTE
                            if(verificar == false) {
                                try {
                                    long id = 0;
                                    id = JDBCPagamentoDAO.getInstance().create(comprovanteVenda, dataImportacao, horaImportacao, arquivo);

                                    if(id != 0) {
                                        Boolean verificarVenda = null;

                                        try {
                                            // VERIFICA SE EXISTE VENDA COM O PAGAMENTO
                                            verificarVenda = JDBCVendaDAO.getInstance().search(comprovanteVenda);
                                        } catch (Exception e) {
                                            mostrarMensagem("Erro #18" + e);
                                        }

                                        if(verificarVenda == true) {
                                            try {
                                                // ATUALIZA A VENDA COMO PAGA
                                                JDBCVendaDAO.getInstance().updateVendaPaga(comprovanteVenda, id);
                                            } catch (Exception e) {
                                                mostrarMensagem("Erro #19" + e);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    mostrarMensagem("Erro #20" + e);
                                }
                            }
                        }
                    }
                } else if(identificador.equals("L9")) {
                    verificarProcesso++;

                    try {
                        trailerLoteTransacao = processarTrailerLoteTransacoes(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("6 - Erro de array");
                    }

                    try {
                        JDBCTrailerLoteTransacoesDAO.getInstance().create(trailerLoteTransacao, arquivo);
                    } catch (Exception e) {
                        mostrarMensagem("Erro #21" + e);
                    }
                } else if(identificador.equals("A9")) {
                    verificarProcesso++;

                    try {
                        trailerArquivo = processarTrailerArquivo(line.toCharArray());
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        gravarLog("7 - Erro de array");
                    }

                    try {
                        JDBCTrailerArquivoDAO.getInstance().create(trailerArquivo, arquivo);
                    } catch (Exception e) {
                        mostrarMensagem("Erro #22" + e);
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

        if(flag == true && verificarProcesso > 0) {
            try {
                mostrarMensagem("Salvando arquivo processado!");
                salvarArquivoProcessado(arquivo, pasta, estabelecimento);
            } catch (Exception e) {
                mostrarMensagem("Erro #23" + e);
            }
        }
    }

    public void salvarArquivoProcessado(String arquivo, String pasta, Estabelecimento estabelecimento) {
        Date dataAgoraSQL = new Date(Calendar.getInstance().getTime().getTime());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Time horaAgora = Time.valueOf(dtf.format(now));

        Arquivo arquivoProcessado = new Arquivo();

        arquivoProcessado.setArquivo(arquivo);
        arquivoProcessado.setLocalizacao(pasta + "\\" +arquivo);
        arquivoProcessado.setDataProcessamento(dataAgoraSQL);
        arquivoProcessado.setHoraProcessamento(horaAgora);
        arquivoProcessado.setIdAdquirente(268);
        arquivoProcessado.setDataArquivo(null);
        arquivoProcessado.setDataMenorVenda(null);
        arquivoProcessado.setDataMaiorVenda(null);
        arquivoProcessado.setDataMenorPagamento(null);
        arquivoProcessado.setDataMaiorPagamento(null);

        if(estabelecimento == null) {
            arquivoProcessado.setCNPJ("22162885000168");
            arquivoProcessado.setEstabelecimentoCNPJ("");
        } else {
            arquivoProcessado.setCNPJ(estabelecimento.getCliente().getCnpj());
            arquivoProcessado.setEstabelecimentoCNPJ(estabelecimento.getCnpj());
        }

        try {
            JDBCArquivoDAO.getInstance().create(arquivoProcessado);
        } catch (Exception e) {
            mostrarMensagem("Erro #24" + e);
        }
    }

    public HeaderArquivo processarHeaderArquivo(char[] line) {
        HeaderArquivo headerArquivo = new HeaderArquivo();

        String codigoRegistro = line[0] + "" + line[1];
        String versaoLayout = "";
        String dataGeracao = "";
        String horaGeracao = "";
        String idMovimento = "";
        String nomeAdministradora = "";
        String identificacaoRemetente = "";
        String identificacaoDestinatario = "";
        TipoProcessamento tipoProcessamento = null;
        String nseq = "";
        String codigoTipoProcessamento = String.valueOf(line[98]);

        for (int i = 2; i < 8; i++) { versaoLayout += "" + line[i]; }
        for (int i = 8; i < 16; i++) { dataGeracao += "" + line[i]; }
        for (int i = 16; i < 22; i++) { horaGeracao += "" + line[i]; }
        for (int i = 22; i < 28; i++) { idMovimento += "" + line[i]; }
        for (int i = 28; i < 88; i++) { nomeAdministradora += "" + line[i]; }
        for (int i = 88; i < 92; i++) { identificacaoRemetente += "" + line[i]; }
        for (int i = 92; i < 98; i++) { identificacaoDestinatario += "" + line[i]; }
        for (int i = 99; i < 105; i++) { nseq += "" + line[i]; }

        for (TipoProcessamento tipo : listaTiposProcessamento) {
            if(tipo.getIdentificador().equals(codigoTipoProcessamento)) {
                tipoProcessamento = tipo;
            }
        }

        headerArquivo.setCodigoRegistro(codigoRegistro);
        headerArquivo.setVersaoLayout(versaoLayout);
        headerArquivo.setDataGeracao(dataGeracao);
        headerArquivo.setHoraGeracao(horaGeracao);
        headerArquivo.setIdMovimento(idMovimento);
        headerArquivo.setNomeAdministradora(nomeAdministradora.trim());
        headerArquivo.setIdentificacaoRemetente(identificacaoRemetente);
        headerArquivo.setIdentificacaoDestinatario(identificacaoDestinatario);
        headerArquivo.setTipoProcessamento(tipoProcessamento);
        headerArquivo.setNSEQ(nseq);

        return headerArquivo;
    }

    public HeaderLoteTransacao processarHeaderLote(char[] line) {
        HeaderLoteTransacao headerLoteTransacao = new HeaderLoteTransacao();

        String codigoRegistro = line[0] + "" + line[1];
        String dataMovimento = "";
        String idMoeda = "";
        MoedaCorrente moedaCorrente = null;
        String nseq = "";

        for (int i = 2; i < 10; i++) { dataMovimento += "" + line[i]; }
        for (int i = 10; i < 12; i++) { idMoeda += "" + line[i]; }
        for (int i = 12; i < 18; i++) { nseq += "" + line[i]; }

        for (MoedaCorrente moeda : listaMoedasCorrente) {
            if(moeda.getIdentificador().equals(idMoeda)) {
                moedaCorrente = moeda;
            }
        }

        headerLoteTransacao.setCodigoRegistro(codigoRegistro);
        headerLoteTransacao.setDataMovimento(dataMovimento);
        headerLoteTransacao.setMoedaCorrente(moedaCorrente);
        headerLoteTransacao.setNSEQ(nseq);

        return headerLoteTransacao;
    }

    public ResumoVenda processarResumoVenda(char[] line) {
        ResumoVenda resumoVenda = new ResumoVenda();

        String codigoRegistro = line[0] + "" + line[1];
        String identificacaoLoja = "";
        String numeroResumoVenda = "";
        String dataResumoVenda = "";
        String dataPagamentoVenda = "";
        String cvsAprovados = "";
        String cvsRejeitados = "";
        String tipoProduto = (String.valueOf(line[72]).equals("V")) ? "Voucher" : String.valueOf(line[72]);
        String codigoProduto = "";
        String banco = "";
        String agencia = "";
        String contaCorrente = "";
        String valorBruto = "";
        String sinalValorBruto = String.valueOf(line[111]);
        String valorLiquido = "";
        String sinalValorLiquido = String.valueOf(line[127]);
        String valorCredito = "";
        String sinalValorCredito = String.valueOf(line[143]);
        String valorComissao = "";
        String sinalValorComissao = String.valueOf(line[159]);
        String identificadorAjustesRV = String.valueOf(line[160]);
        String codigoAjustes = "";
        String codigoEC = "";
        String codigoAdquirente = "";
        String NSEQ = "";

        TipoLancamento tipoLancamento = null;
        Produto produto = null;
        Ajuste ajuste = null;
        Adquirente adquirente = null;

        int codigoTipoLancamento = Integer.parseInt(String.valueOf(line[45]));

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 37; i++) { numeroResumoVenda += "" + line[i]; }
        for (int i = 37; i < 45; i++) { dataResumoVenda += "" + line[i]; }
        for (int i = 46; i < 54; i++) { dataPagamentoVenda += "" + line[i]; }
        for (int i = 54; i < 63; i++) { cvsAprovados += "" + line[i]; }
        for (int i = 63; i < 72; i++) { cvsRejeitados += "" + line[i]; }
        for (int i = 73; i < 76; i++) { codigoProduto += "" + line[i]; }
        for (int i = 76; i < 79; i++) { banco += "" + line[i]; }
        for (int i = 79; i < 85; i++) { agencia += "" + line[i]; }
        for (int i = 85; i < 96; i++) { contaCorrente += "" + line[i]; }
        for (int i = 95; i < 111; i++) { valorBruto += "" + line[i]; }
        for (int i = 112; i < 127; i++) { valorLiquido += "" + line[i]; }
        for (int i = 128; i < 143; i++) { valorCredito += "" + line[i]; }
        for (int i = 144; i < 159; i++) { valorComissao += "" + line[i]; }
        for (int i = 161; i < 165; i++) { codigoAjustes += "" + line[i]; }

        try {
            for (int i = 165; i < 180; i++) { codigoEC += "" + line[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        try {
            for (int i = 180; i < 186; i++) { codigoAdquirente += "" + line[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        try {
            for (int i = 186; i < 192; i++) { NSEQ += "" + line[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        for (TipoLancamento tipo:listaTiposLancamento) {
            if(tipo.getId() == codigoTipoLancamento) {
                tipoLancamento = tipo;
            }
        }

        for (Produto produto1:listaProdutos) {
            if(produto1.getId() == Integer.parseInt(codigoProduto)) {
                produto = produto1;
            }
        }

        for (Ajuste ajuste1:listaAjustes) {
            if(ajuste1.getId() == Integer.parseInt(codigoAjustes)) {
                ajuste = ajuste1;
            }
        }

        if(!codigoAdquirente.isEmpty()) {
            for (Adquirente adquirente1:listaAdquirentes) {
                if(adquirente1.getId() == Integer.parseInt(codigoAdquirente)) {
                    adquirente = adquirente1;
                }
            }
        }

        resumoVenda.setCodigoRegistro(codigoRegistro);
        resumoVenda.setIdentificacaoLoja(identificacaoLoja);
        resumoVenda.setNumeroResumoVenda(numeroResumoVenda);
        resumoVenda.setDataResumoVenda(dataResumoVenda);
        resumoVenda.setTipoLancamento(tipoLancamento);
        resumoVenda.setDataPagamento(dataPagamentoVenda);
        resumoVenda.setCVsAprovados(cvsAprovados);
        resumoVenda.setCVsRejeitados(cvsRejeitados);
        resumoVenda.setTipoProduto(tipoProduto);
        resumoVenda.setCodigoProduto(codigoProduto);
        resumoVenda.setBanco(banco);
        resumoVenda.setAgencia(agencia);
        resumoVenda.setContaCorrente(contaCorrente);
        resumoVenda.setValorBruto(valorBruto);
        resumoVenda.setValorLiquido(valorLiquido);
        resumoVenda.setValorComissao(valorComissao);
        resumoVenda.setSinalValorBruto(sinalValorBruto);
        resumoVenda.setSinalValorComissao(sinalValorComissao);
        resumoVenda.setSinalValorLiquido(sinalValorLiquido);
        resumoVenda.setValorCredito(valorCredito);
        resumoVenda.setSinalValorCredito(sinalValorCredito);
        resumoVenda.setIdentificadorAjusteRV(identificadorAjustesRV);
        resumoVenda.setCodigoAjustes(codigoAjustes);
        resumoVenda.setCodigoEC(codigoEC);
        resumoVenda.setCodigoAdquirente(codigoAdquirente);
        resumoVenda.setNSEQ(NSEQ);
        resumoVenda.setProduto(produto);
        resumoVenda.setAjuste(ajuste);
        resumoVenda.setAdquirente(adquirente);

        return resumoVenda;
    }

    public ComprovanteVenda processarComprovanteVenda(char[] line) {
        ComprovanteVenda comprovanteVenda = new ComprovanteVenda();

        String codigoRegistro = line[0] + "" + line[1];
        String identificacaoLoja = "";
        String nsuHost = "";
        String nsuTef = "";
        String nsuTerminal = "";
        String codigoAdquirente = "";
        String dataTransacaoVenda = "";
        String horaTransacao = "";
        String tipoProduto = (String.valueOf(line[82]).equals("V")) ? "Voucher" : String.valueOf(line[82]);
        String valorBrutoString = "";
        String valorDescontoString = "";
        String valorLiquidoString = "";
        String numeroCartao = "";
        String banco = "";
        String agencia = "";
        String conta = "";
        String codigoAutorizacao = "";
        String codigoBandeira = "";
        String codigoProduto = "";
        String codigoEC = "";
        String nseq = "";
        String dataLancamento = "";

        int codigoMeioCaptura = Integer.parseInt(String.valueOf(line[83]));
        int codigoTipoLancamento = Integer.parseInt(String.valueOf(line[73]));

        TipoLancamento tipoLancamento = null;
        MeioCaptura meioCaptura = null;
        Adquirente adquirente = null;
        Produto produto = null;
        Estabelecimento estabelecimento = null;

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 29; i++) { nsuHost += "" + line[i]; }
        for (int i = 29; i < 41; i++) { nsuTef += "" + line[i]; }
        for (int i = 41; i < 53; i++) { nsuTerminal += "" + line[i]; }
        for (int i = 53; i < 59; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 59; i < 67; i++) { dataTransacaoVenda += "" + line[i]; }
        for (int i = 59; i < 67; i++) { horaTransacao += "" + line[i]; }
        for (int i = 74; i < 82; i++) { dataLancamento += "" + line[i]; }
        for (int i = 84; i < 95; i++) { valorBrutoString += "" + line[i]; }
        for (int i = 95; i < 106; i++) { valorDescontoString += "" + line[i]; }
        for (int i = 106; i < 117; i++) { valorLiquidoString += "" + line[i]; }
        for (int i = 117; i < 136; i++) { numeroCartao += "" + line[i]; }
        for (int i = 136; i < 139; i++) { banco += "" + line[i]; }
        for (int i = 139; i < 145; i++) { agencia += "" + line[i]; }
        for (int i = 145; i < 156; i++) { conta += "" + line[i]; }
        for (int i = 156; i < 168; i++) { codigoAutorizacao += "" + line[i]; }
        for (int i = 168; i < 171; i++) { codigoBandeira += "" + line[i]; }
        for (int i = 171; i < 174; i++) { codigoProduto += "" + line[i]; }

        try {
            for (int i = 174; i < 189; i++) { codigoEC += "" + line[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        try {
            for (int i = 189; i < 195; i++) { nseq += "" + line[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        for (TipoLancamento tipo:listaTiposLancamento) {
            if(tipo.getId() == codigoTipoLancamento) {
                tipoLancamento = tipo;
            }
        }

        for (MeioCaptura meio:listaMeioCaptura) {
            if(meio.getId() == codigoMeioCaptura) {
                meioCaptura = meio;
            }
        }

        for (Adquirente adquirente1:listaAdquirentes) {
            if(adquirente1.getId() == Integer.parseInt(codigoAdquirente)) {
                adquirente = adquirente1;
            }
        }

        for (Produto produto1:listaProdutos) {
            if(produto1.getId() == Integer.parseInt(codigoProduto)) {
                produto = produto1;
            }
        }

        identificacaoLoja = identificacaoLoja.replaceFirst("^0+(?!$)", "");
        codigoEC = codigoEC.replaceFirst("^0+(?!$)", "");

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(identificacaoLoja);
        } catch (Exception e) {
            mostrarMensagem("Erro #25" + e);
        }

        if(empresa == null) {
            try {
                empresa = JDBCEmpresaDAO.getInstance().search("22162885000168");
            } catch (Exception e) {
                mostrarMensagem("Erro #26" + e);
            }
        }

        if(empresa != null) {
            cliente = empresa.getCliente();
            if(cliente != null) {
                empresa.setCliente(cliente);
            }
        }

        Date dataTransacaoSQL = converterDataSQL(dataTransacaoVenda);
        Date dataLancamentoSQL = converterDataSQL(dataLancamento);

        Double valorBruto = converterValorDouble(valorBrutoString);
        Double valorDesconto = converterValorDouble(valorDescontoString);
        Double valorLiquido = converterValorDouble(valorLiquidoString);

        try {
            estabelecimento = JDBCEstabelecimentoDAO.getInstance().search(codigoEC);
        } catch (Exception e) {
            mostrarMensagem("Erro #27" + e);
        }

        String agenciaFormatada = agencia.replaceFirst("^0+(?!$)", "");
        String contaFormatada = conta.replaceFirst("^0+(?!$)", "");
        String autorizacaoFormatada = codigoAutorizacao.replaceFirst("^0+(?!$)", "");
        String nsuFormatada = nsuHost.replaceFirst("^0+(?!$)", "");
        String tidFormatada = nsuTef.replaceFirst("^0+(?!$)", "");
        String cartaoFormatada = numeroCartao.replaceFirst("^0+(?!$)", "");

        Double taxaPercentual = calcularTaxaPercentual(valorBruto, valorDesconto);

        Time horaTransacaoSQL = null;
        java.util.Date horaTransacaoDate = null;

        if(horaTransacao != null) {
            try {
                horaTransacaoDate = new SimpleDateFormat("HHmmss").parse(horaTransacao);
            } catch (ParseException e) {
                mostrarMensagem("Erro #28" + e);
            }
        }

        horaTransacaoSQL = new Time(horaTransacaoDate.getTime());

        String chavePagamento = "";

        if(cliente != null) {
            chavePagamento = cliente.getId()+"-268-"+dataLancamentoSQL+"-1-1-"+nsuFormatada+autorizacaoFormatada+"-238-3";
        }

        comprovanteVenda.setCodigoRegistro(codigoRegistro);
        comprovanteVenda.setIdentificacaoLoja(identificacaoLoja);
        comprovanteVenda.setNSUHostTransacao(nsuHost);
        comprovanteVenda.setNSUTEF(nsuTef);
        comprovanteVenda.setNSUTerminal(nsuTerminal);
        comprovanteVenda.setCodigoAdquirente(codigoAdquirente);
        comprovanteVenda.setTipoLancamento(tipoLancamento);
        comprovanteVenda.setTipoProduto(tipoProduto);
        comprovanteVenda.setMeioCaptura(meioCaptura);
        comprovanteVenda.setValorBruto(valorBruto);
        comprovanteVenda.setValorDesconto(valorDesconto);
        comprovanteVenda.setValorLiquido(valorLiquido);
        comprovanteVenda.setNumeroCartao(numeroCartao);
        comprovanteVenda.setBanco(banco);
        comprovanteVenda.setAgencia(agencia);
        comprovanteVenda.setConta(conta);
        comprovanteVenda.setCodigoAutorizacao(codigoAutorizacao);
        comprovanteVenda.setCodigoBandeira(codigoBandeira);
        comprovanteVenda.setCodigoProduto(codigoProduto);
        comprovanteVenda.setCodigoEC(codigoEC);
        comprovanteVenda.setNSEQ(nseq);
        comprovanteVenda.setAdquirente(adquirente);
        comprovanteVenda.setProduto(produto);
        comprovanteVenda.setDataTransacao(dataTransacaoSQL);
        comprovanteVenda.setHoraTransacao(horaTransacaoSQL);
        comprovanteVenda.setDataLancamento(dataLancamentoSQL);
        comprovanteVenda.setEmpresa(empresa);
        comprovanteVenda.setEstabelecimento(estabelecimento);
        comprovanteVenda.setAgenciaFormatado(agenciaFormatada);
        comprovanteVenda.setContaFormatada(contaFormatada);
        comprovanteVenda.setAutorizacaoFormatada(autorizacaoFormatada);
        comprovanteVenda.setNsuFormatada(nsuFormatada);
        comprovanteVenda.setTidFormatada(tidFormatada);
        comprovanteVenda.setTaxaPercentual(taxaPercentual);
        comprovanteVenda.setCartaoFormatado(cartaoFormatada);
        comprovanteVenda.setChavePagamento(chavePagamento);
        comprovanteVenda.setValorBrutoString(valorBrutoString);
        comprovanteVenda.setValorDescontoString(valorDescontoString);
        comprovanteVenda.setValorLiquidoString(valorLiquidoString);

        return comprovanteVenda;
    }

    public AjusteCreditoDebito processarAjusteCreditoDebito(char[] line) {
        AjusteCreditoDebito ajusteCreditoDebito = new AjusteCreditoDebito();

        String codigoRegistro = line[0] + "" + line[1];
        String identificacaoLoja = "";
        String nsuHostTransacaoOriginal = "";
        String nsuTef = "";
        String nsuTerminal = "";
        String codigoAdquirente = "";
        String dataTransacaoOriginal = "";
        String nsuHostTransacao = "";
        String dataTransacaoAJ = "";
        String horarioTransacaoAJ = "";
        int codigoTipoLancamento = Integer.parseInt(String.valueOf(line[93]));
        String dataLancamento = "";
        int codigoMeioCaptura = Integer.parseInt(String.valueOf(line[102]));
        int codigoTipoAjuste = Integer.parseInt(String.valueOf(line[103]));
        String tipoAjuste = (codigoTipoAjuste == 1) ? "Ajuste a Crédito" : "Ajuste a Débito";
        String codigoAjuste = "";
        String descricaoMotivoAjuste = "";
        String valorBruto = "";
        String valorDescontoComissao = "";
        String valorLiquido = "";
        String banco = "";
        String agencia = "";
        String conta = "";
        String numeroCartao = "";
        String codigoBandeira = "";
        String codigoProduto = "";
        String codigoEC = "";
        String codigoAutorizacao = "";
        String nseq = "";

        TipoLancamento tipoLancamento = null;
        MeioCaptura meioCaptura = null;
        Adquirente adquirente = null;
        Produto produto = null;
        Ajuste ajuste = null;

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 29; i++) { nsuHostTransacaoOriginal += "" + line[i]; }
        for (int i = 29; i < 41; i++) { nsuTef += "" + line[i]; }
        for (int i = 41; i < 53; i++) { nsuTerminal += "" + line[i]; }
        for (int i = 53; i < 59; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 59; i < 67; i++) { dataTransacaoOriginal += "" + line[i]; }
        for (int i = 67; i < 79; i++) { nsuHostTransacao += "" + line[i]; }
        for (int i = 79; i < 87; i++) { dataTransacaoAJ += "" + line[i]; }
        for (int i = 87; i < 93; i++) { horarioTransacaoAJ += "" + line[i]; }
        for (int i = 94; i < 102; i++) { dataLancamento += "" + line[i]; }
        for (int i = 104; i < 108; i++) { codigoAjuste += "" + line[i]; }
        for (int i = 108; i < 138; i++) { descricaoMotivoAjuste += "" + line[i]; }
        for (int i = 138; i < 149; i++) { valorBruto += "" + line[i]; }
        for (int i = 149; i < 160; i++) { valorDescontoComissao += "" + line[i]; }
        for (int i = 160; i < 171; i++) { valorLiquido += "" + line[i]; }
        for (int i = 171; i < 174; i++) { banco += "" + line[i]; }
        for (int i = 174; i < 180; i++) { agencia += "" + line[i]; }
        for (int i = 180; i < 191; i++) { conta += "" + line[i]; }
        for (int i = 191; i < 210; i++) { numeroCartao += "" + line[i]; }
        for (int i = 210; i < 213; i++) { codigoBandeira += "" + line[i]; }
        for (int i = 213; i < 216; i++) { codigoProduto += "" + line[i]; }
        for (int i = 216; i < 231; i++) { codigoEC += "" + line[i]; }
        for (int i = 231; i < 243; i++) { codigoAutorizacao += "" + line[i]; }
        for (int i = 243; i < 247; i++) { nseq += "" + line[i]; }

        for (TipoLancamento tipo:listaTiposLancamento) {
            if(tipo.getId() == codigoTipoLancamento) {
                tipoLancamento = tipo;
            }
        }

        for (MeioCaptura meio:listaMeioCaptura) {
            if(meio.getId() == codigoMeioCaptura) {
                meioCaptura = meio;
            }
        }

        for (Adquirente adquirente1:listaAdquirentes) {
            if(adquirente1.getId() == Integer.parseInt(codigoAdquirente)) {
                adquirente = adquirente1;
            }
        }

        for (Produto produto1:listaProdutos) {
            if(produto1.getId() == Integer.parseInt(codigoProduto)) {
                produto = produto1;
            }
        }

        for (Ajuste ajuste1:listaAjustes) {
            if(ajuste1.getId() == Integer.parseInt(codigoAjuste)) {
                ajuste = ajuste1;
            }
        }

        ajusteCreditoDebito.setCodigoRegistro(codigoRegistro);
        ajusteCreditoDebito.setIdentificacaoLoja(identificacaoLoja);
        ajusteCreditoDebito.setNSUHostTransacaoOriginal(nsuHostTransacaoOriginal);
        ajusteCreditoDebito.setNSUTEF(nsuTef);
        ajusteCreditoDebito.setNSUTerminal(nsuTerminal);
        ajusteCreditoDebito.setCodigoAdquirente(codigoAdquirente);
        ajusteCreditoDebito.setDataTransacaoOriginal(dataTransacaoOriginal);
        ajusteCreditoDebito.setNSUHostTransacao(nsuHostTransacao);
        ajusteCreditoDebito.setDataTransacaoAJ(dataTransacaoAJ);
        ajusteCreditoDebito.setHoraTransacaoAJ(horarioTransacaoAJ);
        ajusteCreditoDebito.setTipoLancamento(tipoLancamento);
        ajusteCreditoDebito.setDataLancamento(dataLancamento);
        ajusteCreditoDebito.setMeioCaptura(meioCaptura);
        ajusteCreditoDebito.setTipoAjuste(tipoAjuste);
        ajusteCreditoDebito.setCodigoAjuste(codigoAjuste);
        ajusteCreditoDebito.setDescricaoMotivoAjuste(descricaoMotivoAjuste);
        ajusteCreditoDebito.setValorBruto(valorBruto);
        ajusteCreditoDebito.setValorDesconto(valorDescontoComissao);
        ajusteCreditoDebito.setValorLiquido(valorLiquido);
        ajusteCreditoDebito.setBanco(banco);
        ajusteCreditoDebito.setAgencia(agencia);
        ajusteCreditoDebito.setConta(conta);
        ajusteCreditoDebito.setNumeroCartaoTransacaoOriginal(numeroCartao);
        ajusteCreditoDebito.setCodigoBandeira(codigoBandeira);
        ajusteCreditoDebito.setCodigoProduto(codigoProduto);
        ajusteCreditoDebito.setCodigoEC(codigoEC);
        ajusteCreditoDebito.setCodigoAutorizacao(codigoAutorizacao);
        ajusteCreditoDebito.setNSEQ(nseq);
        ajusteCreditoDebito.setAdquirente(adquirente);
        ajusteCreditoDebito.setAjuste(ajuste);
        ajusteCreditoDebito.setProduto(produto);

        return ajusteCreditoDebito;
    }

    public Cancelamento processarCancelamento(char[] line) {
        Cancelamento cancelamento = new Cancelamento();

        String codigoRegistro = line[0] + "" + line[1];
        String identificacaoLoja = "";
        String nsuHostTransacaoOriginal = "";
        String nsuTef = "";
        String nsuTerminal = "";
        String codigoAdquirente = "";
        String dataTransacaoOriginal = "";
        String nsuHostTransacao = "";
        String dataTransacao = "";
        String horarioTransacao = "";
        int codigoMeioCaptura = Integer.parseInt(String.valueOf(line[93]));
        String codigoEC = "";
        String codigoAutorizacao = "";
        String nseq = "";

        MeioCaptura meioCaptura = null;
        Adquirente adquirente = null;

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 29; i++) { nsuHostTransacaoOriginal += "" + line[i]; }
        for (int i = 29; i < 41; i++) { nsuTef += "" + line[i]; }
        for (int i = 41; i < 53; i++) { nsuTerminal += "" + line[i]; }
        for (int i = 53; i < 59; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 59; i < 67; i++) { dataTransacaoOriginal += "" + line[i]; }
        for (int i = 67; i < 79; i++) { nsuHostTransacao += "" + line[i]; }
        for (int i = 79; i < 87; i++) { dataTransacao += "" + line[i]; }
        for (int i = 87; i < 93; i++) { horarioTransacao += "" + line[i]; }
        for (int i = 94; i < 109; i++) { codigoEC += "" + line[i]; }
        for (int i = 109; i < 121; i++) { codigoAutorizacao += "" + line[i]; }
        for (int i = 121; i < 127; i++) { nseq += "" + line[i]; }

        for (MeioCaptura meio:listaMeioCaptura) {
            if(meio.getId() == codigoMeioCaptura) {
                meioCaptura = meio;
            }
        }

        for (Adquirente adquirente1:listaAdquirentes) {
            if(adquirente1.getId() == Integer.parseInt(codigoAdquirente)) {
                adquirente = adquirente1;
            }
        }

        cancelamento.setCodigoRegistro(codigoRegistro);
        cancelamento.setIdentificacaoLoja(identificacaoLoja);
        cancelamento.setNSUHostTransacaoOriginal(nsuHostTransacaoOriginal);
        cancelamento.setNSUTEF(nsuTef);
        cancelamento.setNSUTerminal(nsuTerminal);
        cancelamento.setCodigoAdquirente(codigoAdquirente);
        cancelamento.setDataTransacaoOriginal(dataTransacaoOriginal);
        cancelamento.setNSUHostTransacao(nsuHostTransacao);
        cancelamento.setDataTransacao(dataTransacao);
        cancelamento.setHoraTransacao(horarioTransacao);
        cancelamento.setMeioCaptura(meioCaptura);
        cancelamento.setCodigoEC(codigoEC);
        cancelamento.setCodigoAutorizacao(codigoAutorizacao);
        cancelamento.setNSEQ(nseq);
        cancelamento.setAdquirente(adquirente);

        return cancelamento;
    }

    public TrailerLoteTransacao processarTrailerLoteTransacoes(char[] line) {
        TrailerLoteTransacao trailerLoteTransacao = new TrailerLoteTransacao();

        String codigoRegistro = line[0] + "" + line[1];
        String totalRegistros = "";
        String valoresCredito = "";
        String nseq = "";

        for (int i = 2; i < 8; i++) { totalRegistros += "" + line[i]; }
        for (int i = 8; i < 22; i++) { valoresCredito += "" + line[i]; }
        for (int i = 22; i < 28; i++) { nseq += "" + line[i]; }

        trailerLoteTransacao.setCodigoRegistro(codigoRegistro);
        trailerLoteTransacao.setTotalRegistros(totalRegistros);
        trailerLoteTransacao.setTotalValoresCredito(valoresCredito);
        trailerLoteTransacao.setNSEQ(nseq);

        return trailerLoteTransacao;
    }

    public TrailerArquivo processarTrailerArquivo(char[] line) {
        TrailerArquivo trailerArquivo = new TrailerArquivo();

        String codigoRegistro = line[0] + "" + line[1];
        String data = "";
        String nseq = "";

        for (int i = 2; i < 8; i++) { data += "" + line[i]; }
        for (int i = 8; i < 14; i++) { nseq += "" + line[i]; }

        trailerArquivo.setCodigoRegistro(codigoRegistro);
        trailerArquivo.setDataMovimento(data);
        trailerArquivo.setNSEQ(nseq);

        return trailerArquivo;
    }

    public Date converterDataSQL(String dataString) {
        java.util.Date dataUtilFormat = null;

        if(dataString != null) {
            try {
                dataUtilFormat = new SimpleDateFormat("yyyyMMdd").parse(dataString);
            } catch (ParseException e) {
                mostrarMensagem("Erro #29" + e);
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

    public void listarTiposLancamento() {
        TipoLancamento previsao = new TipoLancamento(0, "Previsão");
        TipoLancamento liquidaçãoNormal = new TipoLancamento(1, "Liquidação Normal");
        TipoLancamento liquidaçãoAntecipada = new TipoLancamento(2, "Liquidação Antecipada");

        listaTiposLancamento.add(previsao);
        listaTiposLancamento.add(liquidaçãoNormal);
        listaTiposLancamento.add(liquidaçãoAntecipada);
    }

    public void listarMoedasCorrentes() {
        MoedaCorrente real = new MoedaCorrente("RE", "Real");
        MoedaCorrente dolar = new MoedaCorrente("DO", "Dólar");
        MoedaCorrente peso = new MoedaCorrente("PE", "Peso");

        listaMoedasCorrente.add(real);
        listaMoedasCorrente.add(dolar);
        listaMoedasCorrente.add(peso);
    }

    public void listarTiposProcessamento() {
        TipoProcessamento normal = new TipoProcessamento("N", "Normal");
        TipoProcessamento reprocessamento = new TipoProcessamento("R", "Reprocessamento");

        listaTiposProcessamento.add(normal);
        listaTiposProcessamento.add(reprocessamento);
    }

    public void listarMeioCaptura() {
        MeioCaptura manual = new MeioCaptura(1, "Manual", 12);
        MeioCaptura pos = new MeioCaptura(2, "POS", 1);
        MeioCaptura tef = new MeioCaptura(3, "TEF", 2);
        MeioCaptura trn = new MeioCaptura(4, "TRN Off", 0);
        MeioCaptura internet = new MeioCaptura(5, "Internet", 0);
        MeioCaptura ura = new MeioCaptura(6, "URA", 11);
        MeioCaptura indefinido = new MeioCaptura(8, "Indefinido", 13);
        MeioCaptura outros = new MeioCaptura(9, "Outros", 13);

        listaMeioCaptura.add(manual);
        listaMeioCaptura.add(pos);
        listaMeioCaptura.add(tef);
        listaMeioCaptura.add(trn);
        listaMeioCaptura.add(internet);
        listaMeioCaptura.add(ura);
        listaMeioCaptura.add(indefinido);
        listaMeioCaptura.add(outros);
    }

    public void listarProdutos() {
        Produto alimentacao = new Produto(1, "PAT - Alimentação", 2, "Alimentação");
        Produto refeicao = new Produto(2, "PAT - Refeição", 3, "Refeição");

        listaProdutos.add(alimentacao);
        listaProdutos.add(refeicao);
    }

    public void listarAdquirentes() {
        Adquirente getnet = new Adquirente(1, "GETNET");
        Adquirente stone = new Adquirente(2, "STONE");
        Adquirente safra = new Adquirente(3, "SAFRA");
        Adquirente pagseguro = new Adquirente(4, "PAGSEGURO");
        Adquirente cielo = new Adquirente(5, "CIELO");

        Adquirente rede = new Adquirente(6, "REDE");
        Adquirente onyo = new Adquirente(100, "ONYO");
        Adquirente ifood = new Adquirente(101, "IFOOD");

        listaAdquirentes.add(getnet);
        listaAdquirentes.add(stone);
        listaAdquirentes.add(safra);
        listaAdquirentes.add(pagseguro);
        listaAdquirentes.add(cielo);
        listaAdquirentes.add(rede);
        listaAdquirentes.add(onyo);
        listaAdquirentes.add(ifood);
    }

    public void listarAjustes() {
        Ajuste ajusteCreditoAgenda = new Ajuste(22, "Ajuste Crédito Agenda");
        Ajuste ajusteDebitoAgenda = new Ajuste(23, "Ajuste Débito Agenda");
        Ajuste compensacaoSaldoDevedor = new Ajuste(32, " Compensação saldo devedor remanescente");
        Ajuste compensacaoSaldoCredor = new Ajuste(115, "Compensação saldo credor remanescente");
        Ajuste operacoesPagar = new Ajuste(116, "Operações a pagar Lojista");
        Ajuste chargeback = new Ajuste(303, "Charge Back Venda a Vista Pré-Pago BEN Visa Vale");
        Ajuste estornoComissao = new Ajuste(304, "Estorno Comissão Venda a Vista Pré-Pago BEN Visa Vale");
        Ajuste estornoTarifas = new Ajuste(430, "Estorno de Tarifas");
        Ajuste comissaoAntecipada = new Ajuste(369, "Comissão Antecipação de Vendas");
        Ajuste rav = new Ajuste(370, "RAV (Recebimento Antecipado de Vendas) a ser Pago");
        Ajuste tarifaRav = new Ajuste(371, "Tarifa RAV (Recebimento Antecipado de Vendas)");
        Ajuste estornoVenda = new Ajuste(962, "Estorno de Venda a Vista");

        listaAjustes.add(ajusteCreditoAgenda);
        listaAjustes.add(ajusteDebitoAgenda);
        listaAjustes.add(compensacaoSaldoDevedor);
        listaAjustes.add(compensacaoSaldoCredor);
        listaAjustes.add(operacoesPagar);
        listaAjustes.add(chargeback);
        listaAjustes.add(estornoComissao);
        listaAjustes.add(estornoTarifas);
        listaAjustes.add(comissaoAntecipada);
        listaAjustes.add(rav);
        listaAjustes.add(tarifaRav);
        listaAjustes.add(estornoVenda);
    }

    @FXML
    public void mostrarArquivos() {
        trocarJanela("view/janelaArquivos.fxml");
    }

    public void trocarJanela(String address){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(address));

            try {
                Parent layoutWindow = loader.load();

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), layoutWindow);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();

                Stage stage = (Stage)mainWindow.getScene().getWindow();
                stage.setScene(new Scene(layoutWindow,780, 590));
                stage.setResizable(false);
            } catch (IOException e){
                mostrarMensagem("Erro #30" + e);
            }
        });
    }

    private void mostrarMensagem(String mensagem) {
        Platform.runLater(() -> {
            lbMensagem.setVisible(true);
            lbMensagem.setText(mensagem);
        });
    }

    public void gravarLog(String log) {
        PrintWriter printer = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("C:\\Users\\opc\\Documents\\LeitorArquivos\\leitor-log.txt", true);
            //fw = new FileWriter("C:\\Users\\Gabriel\\IdeaProjects\\LeituraArquivosConciflex\\out\\artifacts\\LeituraArquivosConciflex_jar\\leitor-log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        printer = new PrintWriter(fw);

        printer.println(log);
        printer.close();
    }
}