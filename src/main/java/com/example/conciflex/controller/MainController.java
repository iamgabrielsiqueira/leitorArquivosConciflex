package com.example.conciflex.controller;

import com.example.conciflex.model.classes.*;
import com.example.conciflex.model.jdbc.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainController {

    private ObservableList<TipoLancamento> listaTiposLancamento = FXCollections.observableArrayList();
    private ObservableList<MoedaCorrente> listaMoedasCorrente = FXCollections.observableArrayList();
    private ObservableList<TipoProcessamento> listaTiposProcessamento = FXCollections.observableArrayList();
    private ObservableList<MeioCaptura> listaMeioCaptura = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private ObservableList<Adquirente> listaAdquirentes = FXCollections.observableArrayList();
    private ObservableList<Ajuste> listaAjustes = FXCollections.observableArrayList();

    public void initialize() {
        listarTiposLancamento();
        listarMoedasCorrentes();
        listarTiposProcessamento();
        listarMeioCaptura();
        listarProdutos();
        listarAdquirentes();
        listarAjustes();
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
        MeioCaptura manual = new MeioCaptura(1, "Manual");
        MeioCaptura pos = new MeioCaptura(2, "POS");
        MeioCaptura tef = new MeioCaptura(3, "TEF");
        MeioCaptura trn = new MeioCaptura(4, "TRN Off");
        MeioCaptura internet = new MeioCaptura(5, "Internet");
        MeioCaptura ura = new MeioCaptura(6, "URA");
        MeioCaptura indefinido = new MeioCaptura(8, "Indefinido");
        MeioCaptura outros = new MeioCaptura(9, "Outros");

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
        Produto alimentacao = new Produto(1, "PAT - Alimentação");
        Produto refeicao = new Produto(2, "PAT - Refeição");

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
    public void processar() throws IOException, ParseException {

        File folder = new File("C:\\Users\\Gabriel\\Desktop\\teste");
        File[] listOfFiles = folder.listFiles();

        String pasta = "";
        String arquivo = "";

        Estabelecimento estabelecimento = null;
        Cliente cliente = null;
        Arquivo arquivoBuscar = null;

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                pasta = folder.getAbsolutePath();
                arquivo = listOfFiles[i].getName();
                estabelecimento = getFileEstabelecimento(pasta, arquivo);

                if(estabelecimento != null) {
                    cliente = estabelecimento.getCliente();
                    if(cliente != null) {
                        try {
                            arquivoBuscar = JDBCArquivoDAO.getInstance().search(arquivo, 268, cliente.getCnpj());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(arquivoBuscar == null) {
                            lerArquivo(pasta, arquivo, estabelecimento);
                        }
                    }
                }
            }
        }

        System.out.println("Fim!");
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
                    e.printStackTrace();
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

        while(line != null) {
            currentLine++;
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];

            if(identificador.equals("A0")) {
                headerArquivo = processarHeaderArquivo(line.toCharArray());

                /*try {
                    JDBCHeaderArquivoDAO.getInstance().create(headerArquivo, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            } else if(identificador.equals("L0")) {
                headerLoteTransacao = processarHeaderLote(line.toCharArray());

               /* try {
                    JDBCHeaderLoteTransacoesDAO.getInstance().create(headerLoteTransacao, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            } else if(identificador.equals("RV")) {
                resumoVenda = processarResumoVenda(line.toCharArray());

                /*try {
                    JDBCResumoVendasDAO.getInstance().create(resumoVenda, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            } else if(identificador.equals("CV")) {
                comprovanteVenda = processarComprovanteVenda(line.toCharArray());

                /*try {
                    JDBCComprovanteVendaDAO.getInstance().create(comprovanteVenda, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            } else if(identificador.equals("AJ")) {
                //ajusteCreditoDebito = processarAjusteCreditoDebito(line.toCharArray());
                //mostrarAjusteCreditoDebito(ajusteCreditoDebito);
            } else if(identificador.equals("CC")) {
                //cancelamento = processarCancelamento(line.toCharArray());
                //mostrarCancelamento(cancelamento);
            } else if(identificador.equals("L9")) {
                trailerLoteTransacao = processarTrailerLoteTransacoes(line.toCharArray());

                /*try {
                    JDBCTrailerLoteTransacoesDAO.getInstance().create(trailerLoteTransacao, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            } else if(identificador.equals("A9")) {
                trailerArquivo = processarTrailerArquivo(line.toCharArray());

                /*try {
                    JDBCTrailerArquivoDAO.getInstance().create(trailerArquivo, arquivo);
                } catch (Exception e) {
                    System.out.println(e);
                }*/
            }

            line = br.readLine();
        }

        br.close();
        reader.close();
        stream.close();

        try {
            salvarArquivoProcessado(arquivo, pasta, estabelecimento);
        } catch (Exception e) {
            System.out.println(e);
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
        arquivoProcessado.setCNPJ(estabelecimento.getCliente().getCnpj());
        arquivoProcessado.setDataArquivo(null);
        arquivoProcessado.setDataMenorVenda(null);
        arquivoProcessado.setDataMaiorVenda(null);
        arquivoProcessado.setDataMenorPagamento(null);
        arquivoProcessado.setDataMaiorPagamento(null);
        arquivoProcessado.setEstabelecimentoCNPJ(estabelecimento.getCNPJ());

        try {
            JDBCArquivoDAO.getInstance().create(arquivoProcessado);
        } catch (Exception e) {
            e.printStackTrace();
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
        for (int i = 165; i < 180; i++) { codigoEC += "" + line[i]; }
        for (int i = 180; i < 186; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 186; i < 192; i++) { NSEQ += "" + line[i]; }

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

        for (Adquirente adquirente1:listaAdquirentes) {
            if(adquirente1.getId() == Integer.parseInt(codigoAdquirente)) {
                adquirente = adquirente1;
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
        String valorBruto = "";
        String valorDesconto = "";
        String valorLiquido = "";
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

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 29; i++) { nsuHost += "" + line[i]; }
        for (int i = 29; i < 41; i++) { nsuTef += "" + line[i]; }
        for (int i = 41; i < 53; i++) { nsuTerminal += "" + line[i]; }
        for (int i = 53; i < 59; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 59; i < 67; i++) { dataTransacaoVenda += "" + line[i]; }
        for (int i = 59; i < 67; i++) { horaTransacao += "" + line[i]; }
        for (int i = 74; i < 82; i++) { dataLancamento += "" + line[i]; }
        for (int i = 84; i < 95; i++) { valorBruto += "" + line[i]; }
        for (int i = 95; i < 106; i++) { valorDesconto += "" + line[i]; }
        for (int i = 106; i < 117; i++) { valorLiquido += "" + line[i]; }
        for (int i = 117; i < 136; i++) { numeroCartao += "" + line[i]; }
        for (int i = 136; i < 139; i++) { banco += "" + line[i]; }
        for (int i = 139; i < 145; i++) { agencia += "" + line[i]; }
        for (int i = 145; i < 156; i++) { conta += "" + line[i]; }
        for (int i = 156; i < 168; i++) { codigoAutorizacao += "" + line[i]; }
        for (int i = 168; i < 171; i++) { codigoBandeira += "" + line[i]; }
        for (int i = 171; i < 174; i++) { codigoProduto += "" + line[i]; }
        for (int i = 174; i < 189; i++) { codigoEC += "" + line[i]; }
        for (int i = 189; i < 195; i++) { nseq += "" + line[i]; }

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
        comprovanteVenda.setDataTransacao(dataTransacaoVenda);
        comprovanteVenda.setHoraTransacao(horaTransacao);
        comprovanteVenda.setDataLancamento(dataLancamento);

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

    public void mostrarHeaderArquivo(HeaderArquivo headerArquivo) {
        System.out.println("Código de registro: " + headerArquivo.getCodigoRegistro());
        System.out.println("Versão do layout: " + headerArquivo.getVersaoLayout());
        System.out.println("Data de geração: " + headerArquivo.getDataGeracao());
        System.out.println("Id do Movimento: " + headerArquivo.getIdMovimento());
        System.out.println("Nome da Administradora: " + headerArquivo.getNomeAdministradora());
        System.out.println("Identificação Remetente: " + headerArquivo.getIdentificacaoRemetente());
        System.out.println("Identificação Destinatário: " + headerArquivo.getIdentificacaoDestinatario());

        if(headerArquivo.getTipoProcessamento() != null) {
            System.out.println("Tipo de Processamento: " + headerArquivo.getTipoProcessamento().getDescricao());
        }

        System.out.println("NSEQ: " + headerArquivo.getNSEQ());
    }

    public void mostrarHeaderLoteTransacao(HeaderLoteTransacao headerLoteTransacao) {
        System.out.println("Código de registro: " + headerLoteTransacao.getCodigoRegistro());
        System.out.println("Data do movimento: " + headerLoteTransacao.getDataMovimento());

        if(headerLoteTransacao.getMoedaCorrente() != null) {
            System.out.println("Identificação da moeda: " + headerLoteTransacao.getMoedaCorrente().getDescricao());
        }

        System.out.println("NSEQ: " + headerLoteTransacao.getNSEQ());
    }

    public void mostrarResumoVendas(ResumoVenda resumoVenda) {
        System.out.println("Código de registro: " + resumoVenda.getCodigoRegistro());
        System.out.println("Identificação da Loja: " + resumoVenda.getIdentificacaoLoja());
        System.out.println("Número do RV: " + resumoVenda.getNumeroResumoVenda());
        System.out.println("Data do RV: " + resumoVenda.getDataResumoVenda());

        if(resumoVenda.getTipoLancamento() != null) {
            System.out.println("Tipo de lançamento: " + resumoVenda.getTipoLancamento().getDescricao());
        }

        System.out.println("Data de pagamento: " + resumoVenda.getDataPagamento());
        System.out.println("CV´s Aprovados: " + resumoVenda.getCVsAprovados());
        System.out.println("CV´s Rejeitados: " + resumoVenda.getCVsRejeitados());
        System.out.println("Tipo do Produto: " + resumoVenda.getTipoProduto());
        System.out.println("Código do Produto: " + resumoVenda.getCodigoProduto());
        System.out.println("Banco: " + resumoVenda.getBanco());
        System.out.println("Agência: " + resumoVenda.getAgencia());
        System.out.println("Conta corrente: " + resumoVenda.getContaCorrente());
        System.out.println("Valor Bruto: " + resumoVenda.getValorBruto());
        System.out.println("Sinal do Valor Bruto: " + resumoVenda.getSinalValorBruto());
        System.out.println("Valor Líquido: " + resumoVenda.getValorLiquido());
        System.out.println("Sinal do Valor Líquido: " + resumoVenda.getSinalValorLiquido());
        System.out.println("Valor Crédito: " + resumoVenda.getValorCredito());
        System.out.println("Sinal do Valor de Crédito: " + resumoVenda.getSinalValorCredito());
        System.out.println("Valor Comissão: " + resumoVenda.getValorComissao());
        System.out.println("Sinal do Valor Comissão: " + resumoVenda.getSinalValorComissao());
        System.out.println("Identificador de Ajuste de RV: " + resumoVenda.getIdentificadorAjusteRV());
        System.out.println("Código de Ajustes: " + resumoVenda.getCodigoAjustes());
        System.out.println("Código do EC: " + resumoVenda.getCodigoEC());
        System.out.println("Código da adquirente: " + resumoVenda.getCodigoAdquirente());
        System.out.println("NSEQ: " + resumoVenda.getNSEQ());

        if(resumoVenda.getProduto() != null) {
            System.out.println("Produto: " + resumoVenda.getProduto().getDescricao());
        }

        if(resumoVenda.getAjuste() != null) {
            System.out.println("Ajuste: " + resumoVenda.getAjuste().getDescricao());
        }
    }

    public void mostrarComprovanteVenda(ComprovanteVenda comprovanteVenda) {
        System.out.println("Código do registro: " + comprovanteVenda.getCodigoRegistro());
        System.out.println("Identificação da Loja: " + comprovanteVenda.getIdentificacaoLoja());
        System.out.println("NSU Host da transação: " + comprovanteVenda.getNSUHostTransacao());
        System.out.println("NSU TEF: " + comprovanteVenda.getNSUTEF());
        System.out.println("NSU Terminal: " + comprovanteVenda.getNSUTerminal());
        System.out.println("Código do Adquirente: " + comprovanteVenda.getCodigoAdquirente());
        System.out.println("Data da transação: " + comprovanteVenda.getDataTransacao());

        if(comprovanteVenda.getTipoLancamento() != null) {
            System.out.println("Tipo de lançamento: " + comprovanteVenda.getTipoLancamento().getDescricao());
        }

        System.out.println("Data de lançamento: " + comprovanteVenda.getDataLancamento());
        System.out.println("Tipo do Produto: " + comprovanteVenda.getTipoProduto());

        if(comprovanteVenda.getMeioCaptura() != null) {
            System.out.println("Meio de Captura: " + comprovanteVenda.getMeioCaptura().getDescricao());
        }

        System.out.println("Valor Bruto da Venda: " + comprovanteVenda.getValorBruto());
        System.out.println("Valor do Desconto: " + comprovanteVenda.getValorDesconto());
        System.out.println("Valor Líq da Venda: " + comprovanteVenda.getValorLiquido());
        System.out.println("Número do Cartão: " + comprovanteVenda.getNumeroCartao());
        System.out.println("Banco: " + comprovanteVenda.getBanco());
        System.out.println("Agência: " + comprovanteVenda.getAgencia());
        System.out.println("Conta: " + comprovanteVenda.getConta());
        System.out.println("Código de Autorização: " + comprovanteVenda.getCodigoAutorizacao());
        System.out.println("Código da Bandeira: " + comprovanteVenda.getCodigoBandeira());
        System.out.println("Código do Produto: " + comprovanteVenda.getCodigoProduto());
        System.out.println("Código do EC: " + comprovanteVenda.getCodigoEC());
        System.out.println("NSEQ: " + comprovanteVenda.getNSEQ());

        if(comprovanteVenda.getAdquirente() != null) {
            System.out.println("Adquirente: " + comprovanteVenda.getAdquirente().getDescricao());
        }

        if(comprovanteVenda.getProduto() != null) {
            System.out.println("Produto: " + comprovanteVenda.getProduto().getDescricao());
        }
    }

    public void mostrarAjusteCreditoDebito(AjusteCreditoDebito ajusteCreditoDebito) {
        System.out.println("Código do registro: " + ajusteCreditoDebito.getCodigoRegistro());
        System.out.println("Identificação da Loja: " + ajusteCreditoDebito.getIdentificacaoLoja());
        System.out.println("NSU Host da transação original: " + ajusteCreditoDebito.getNSUHostTransacaoOriginal());
        System.out.println("NSU TEF: " + ajusteCreditoDebito.getNSUTEF());
        System.out.println("NSU Terminal: " + ajusteCreditoDebito.getNSUTerminal());
        System.out.println("Código do Adquirente: " + ajusteCreditoDebito.getCodigoAdquirente());
        System.out.println("Data da transação original: " + ajusteCreditoDebito.getDataTransacaoOriginal());
        System.out.println("NSU Host da transação: " + ajusteCreditoDebito.getNSUHostTransacao());
        System.out.println("Data da transação AJ: " + ajusteCreditoDebito.getDataTransacaoAJ());
        System.out.println("Horário da transação: " + ajusteCreditoDebito.getHoraTransacaoAJ());

        if(ajusteCreditoDebito.getTipoLancamento() != null) {
            System.out.println("Tipo de lançamento: " + ajusteCreditoDebito.getTipoLancamento().getDescricao());
        }

        System.out.println("Data de lançamento: " + ajusteCreditoDebito.getDataLancamento());

        if(ajusteCreditoDebito.getMeioCaptura() != null) {
            System.out.println("Meio de Captura do Ajuste: " + ajusteCreditoDebito.getMeioCaptura().getDescricao());
        }

        System.out.println("Tipo de Ajuste: " + ajusteCreditoDebito.getTipoAjuste());
        System.out.println("Código do Ajuste: " + ajusteCreditoDebito.getCodigoAjuste());
        System.out.println("Descrição do Motivo do Ajuste: " + ajusteCreditoDebito.getDescricaoMotivoAjuste());
        System.out.println("Valor Bruto: " + ajusteCreditoDebito.getValorBruto());
        System.out.println("Valor do Desconto ou Comissão: " + ajusteCreditoDebito.getValorDesconto());
        System.out.println("Valor Líquido: " + ajusteCreditoDebito.getValorLiquido());
        System.out.println("Banco: " + ajusteCreditoDebito.getBanco());
        System.out.println("Agência: " + ajusteCreditoDebito.getAgencia());
        System.out.println("Conta: " + ajusteCreditoDebito.getConta());
        System.out.println("Número do Cartão da transação original: " + ajusteCreditoDebito.getNumeroCartaoTransacaoOriginal());
        System.out.println("Código da Bandeira: " + ajusteCreditoDebito.getCodigoBandeira());
        System.out.println("Código do Produto: " + ajusteCreditoDebito.getCodigoProduto());
        System.out.println("Código do EC: " + ajusteCreditoDebito.getCodigoEC());
        System.out.println("Código de autorização: " + ajusteCreditoDebito.getCodigoAutorizacao());
        System.out.println("NSEQ: " + ajusteCreditoDebito.getNSEQ());

        if(ajusteCreditoDebito.getAdquirente() != null) {
            System.out.println("Adquirente: " + ajusteCreditoDebito.getAdquirente().getDescricao());
        }

        if(ajusteCreditoDebito.getAjuste() != null) {
            System.out.println("Ajuste: " + ajusteCreditoDebito.getAjuste().getDescricao());
        }

        if(ajusteCreditoDebito.getProduto() != null) {
            System.out.println("Produto: " + ajusteCreditoDebito.getProduto().getDescricao());
        }
    }

    public void mostrarCancelamento(Cancelamento cancelamento) {
        System.out.println("Código do registro: " + cancelamento.getCodigoRegistro());
        System.out.println("Identificação da Loja: " + cancelamento.getIdentificacaoLoja());
        System.out.println("NSU Host da transação original: " + cancelamento.getNSUHostTransacaoOriginal());
        System.out.println("NSU TEF: " + cancelamento.getNSUTEF());
        System.out.println("NSU Terminal: " + cancelamento.getNSUTerminal());
        System.out.println("Código do Adquirente: " + cancelamento.getCodigoAdquirente());
        System.out.println("Data da transação original: " + cancelamento.getDataTransacaoOriginal());
        System.out.println("NSU Host da transação: " + cancelamento.getNSUHostTransacao());
        System.out.println("Data da transação: " + cancelamento.getDataTransacao());
        System.out.println("Horário da transação: " + cancelamento.getHoraTransacao());

        if(cancelamento.getMeioCaptura() != null) {
            System.out.println("Meio de Captura: " + cancelamento.getMeioCaptura().getDescricao());
        }

        System.out.println("Código do EC: " + cancelamento.getCodigoEC());
        System.out.println("Código de autorização: " + cancelamento.getCodigoAutorizacao());
        System.out.println("NSEQ: " + cancelamento.getNSEQ());

        if(cancelamento.getAdquirente() != null) {
            System.out.println("Adquirente: " + cancelamento.getAdquirente().getDescricao());
        }
    }

    public void mostrarTrailerLoteTransacao(TrailerLoteTransacao trailerLoteTransacao) {
        System.out.println("Código do registro: " + trailerLoteTransacao.getCodigoRegistro());
        System.out.println("Total de registros de transação: " + trailerLoteTransacao.getTotalRegistros());
        System.out.println("Total de valores de créditos: " + trailerLoteTransacao.getTotalValoresCredito());
        System.out.println("NSEQ: " + trailerLoteTransacao.getNSEQ());
    }

    public void mostrarTrailerArquivo(TrailerArquivo trailerArquivo) {
        System.out.println("Código do registro: " + trailerArquivo.getCodigoRegistro());
        System.out.println("Data do Movimento: " + trailerArquivo.getDataMovimento());
        System.out.println("NSEQ: " + trailerArquivo.getNSEQ());
    }
}