package com.example.conciflex.controller;

import com.example.conciflex.model.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {

    private ObservableList<TipoLancamento> listaTiposLancamento = FXCollections.observableArrayList();
    private ObservableList<MoedaCorrente> listaMoedasCorrente = FXCollections.observableArrayList();
    private ObservableList<TipoProcessamento> listaTiposProcessamento = FXCollections.observableArrayList();
    private ObservableList<MeioCaptura> listaMeioCaptura = FXCollections.observableArrayList();

    public void initialize() {
        TipoLancamento previsao = new TipoLancamento(0, "Previsão");
        TipoLancamento liquidaçãoNormal = new TipoLancamento(1, "Liquidação Normal");
        TipoLancamento liquidaçãoAntecipada = new TipoLancamento(2, "Liquidação Antecipada");

        listaTiposLancamento.add(previsao);
        listaTiposLancamento.add(liquidaçãoNormal);
        listaTiposLancamento.add(liquidaçãoAntecipada);

        MoedaCorrente real = new MoedaCorrente("RE", "Real");
        MoedaCorrente dolar = new MoedaCorrente("DO", "Dólar");
        MoedaCorrente peso = new MoedaCorrente("PE", "Peso");

        listaMoedasCorrente.add(real);
        listaMoedasCorrente.add(dolar);
        listaMoedasCorrente.add(peso);

        TipoProcessamento normal = new TipoProcessamento("N", "Normal");
        TipoProcessamento reprocessamento = new TipoProcessamento("R", "Reprocessamento");

        listaTiposProcessamento.add(normal);
        listaTiposProcessamento.add(reprocessamento);

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

    @FXML
    public void processar() throws IOException, ParseException {
        File folder = new File("C:\\Users\\Gabriel\\IdeaProjects\\LeituraArquivosConciflex\\arquivos");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                lerArquivo(folder.getAbsolutePath() + "\\" + listOfFiles[i].getName());
            }
        }

    }

    public void lerArquivo(String arquivo) throws IOException, ParseException {
        FileInputStream stream = new FileInputStream(arquivo);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        String line = br.readLine();
        Integer currentLine = 0;
        HeaderArquivo headerArquivo = new HeaderArquivo();
        HeaderLoteTransacao headerLoteTransacao = new HeaderLoteTransacao();
        ResumoVenda resumoVenda = new ResumoVenda();
        ComprovanteVenda comprovanteVenda = new ComprovanteVenda();

        while(line != null) {
            currentLine++;
            String identificador = line.toCharArray()[0] + "" + line.toCharArray()[1];

            if(identificador.equals("A0")) {
                headerArquivo = processarHeaderArquivo(line.toCharArray());
            } else if(identificador.equals("L0")) {
                headerLoteTransacao = processarHeaderLote(line.toCharArray());
            } else if(identificador.equals("RV")) {
                resumoVenda = processarResumoVenda(line.toCharArray());
            } else if(identificador.equals("CV")) {
                comprovanteVenda = processarComprovanteVenda(line.toCharArray());
            }

            line = br.readLine();
        }

        //mostrarHeaderArquivo(headerArquivo);
        //mostrarHeaderLoteTransacao(headerLoteTransacao);
        //mostrarResumoVendas(resumoVenda);

        br.close();
        reader.close();
        stream.close();
    }

    public HeaderArquivo processarHeaderArquivo(char[] line) {
        HeaderArquivo headerArquivo = new HeaderArquivo();

        String codigoRegistro = line[0] + "" + line[1];
        String versaoLayout = "";
        String data = "";
        String hora = "";
        String idMovimento = "";
        String nomeAdministradora = "";
        String identificacaoRemetente = "";
        String identificacaoDestinatario = "";
        TipoProcessamento tipoProcessamento = null;
        String nseq = "";

        for (int i = 2; i < 8; i++) { versaoLayout += "" + line[i]; }
        for (int i = 8; i < 16; i++) { data += "" + line[i]; }
        for (int i = 16; i < 22; i++) { hora += "" + line[i]; }
        for (int i = 22; i < 28; i++) { idMovimento += "" + line[i]; }
        for (int i = 28; i < 88; i++) { nomeAdministradora += "" + line[i]; }
        for (int i = 88; i < 92; i++) { identificacaoRemetente += "" + line[i]; }
        for (int i = 92; i < 98; i++) { identificacaoDestinatario += "" + line[i]; }
        for (int i = 99; i < 105; i++) { nseq += "" + line[i]; }

        String codigoTipoProcessamento = String.valueOf(line[98]);

        for (TipoProcessamento tipo : listaTiposProcessamento) {
            if(tipo.getIdentificador().equals(codigoTipoProcessamento)) {
                tipoProcessamento = tipo;
            }
        }

        Date dataGeracao = null;

        try {
            dataGeracao = new SimpleDateFormat("yyyyMMdd HHmmss").parse(data + " " + hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        headerArquivo.setCodigoRegistro(codigoRegistro);
        headerArquivo.setVersaoLayout(versaoLayout);
        headerArquivo.setDataGeracao(dataGeracao);
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
        String data = "";
        String idMoeda = "";
        MoedaCorrente moedaCorrente = null;
        String nseq = "";

        Date dataMovimento = null;

        for (int i = 2; i < 10; i++) { data += "" + line[i]; }
        for (int i = 10; i < 12; i++) { idMoeda += "" + line[i]; }
        for (int i = 12; i < 18; i++) { nseq += "" + line[i]; }

        try {
            dataMovimento = new SimpleDateFormat("yyyyMMdd").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        String identificacaoLoja = "", numeroResumoVenda = "";
        String data = "", dataPag = "", cvsAprovados = "", cvsRejeitados = "";
        String tipoProduto = (String.valueOf(line[72]).equals("V")) ? "Voucher" : String.valueOf(line[72]);
        String codigoProduto = "", banco = "", agencia = "", contaCorrente = "";
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
        int codigoTipoLancamento = Integer.parseInt(String.valueOf(line[45]));
        Date dataResumoVenda = null;
        Date dataPagamentoVenda = null;

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 37; i++) { numeroResumoVenda += "" + line[i]; }
        for (int i = 37; i < 45; i++) { data += "" + line[i]; }
        for (int i = 46; i < 54; i++) { dataPag += "" + line[i]; }
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

        try {
            dataResumoVenda = new SimpleDateFormat("yyyyMMdd").parse(data);
            dataPagamentoVenda = new SimpleDateFormat("yyyyMMdd").parse(dataPag);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (TipoLancamento tipo:listaTiposLancamento) {
            if(tipo.getId() == codigoTipoLancamento) {
                tipoLancamento = tipo;
            }
        }

        resumoVenda.setCodigoRegistro(codigoRegistro);
        resumoVenda.setIdentificacaoLoja(identificacaoLoja);
        resumoVenda.setNumeroResumoVenda(numeroResumoVenda);
        resumoVenda.setDataResumoVenda(dataResumoVenda);
        resumoVenda.setTipoLancamento(tipoLancamento);
        resumoVenda.setDataPagamento(dataPagamentoVenda);
        resumoVenda.setCVsAprovados(Integer.parseInt(cvsAprovados));
        resumoVenda.setCVsRejeitados(Integer.parseInt(cvsRejeitados));
        resumoVenda.setTipoProduto(tipoProduto);
        resumoVenda.setCodigoProduto(Integer.parseInt(codigoProduto));
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
        String dataTransacao = "";
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

        int codigoMeioCaptura = Integer.parseInt(String.valueOf(line[83]));
        int codigoTipoLancamento = Integer.parseInt(String.valueOf(line[73]));

        Date dataTransacaoVenda = null;

        TipoLancamento tipoLancamento = null;
        MeioCaptura meioCaptura = null;

        for (int i = 2; i < 17; i++) { identificacaoLoja += "" + line[i]; }
        for (int i = 17; i < 29; i++) { nsuHost += "" + line[i]; }
        for (int i = 29; i < 41; i++) { nsuTef += "" + line[i]; }
        for (int i = 41; i < 53; i++) { nsuTerminal += "" + line[i]; }
        for (int i = 53; i < 59; i++) { codigoAdquirente += "" + line[i]; }
        for (int i = 59; i < 67; i++) { dataTransacao += "" + line[i]; }
        for (int i = 59; i < 67; i++) { horaTransacao += "" + line[i]; }

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

        try {
            dataTransacaoVenda = new SimpleDateFormat("yyyyMMdd HHmmss").parse(dataTransacao + " " + horaTransacao);
        } catch (ParseException e) {
            e.printStackTrace();
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

        comprovanteVenda.setCodigoRegistro(codigoRegistro);
        comprovanteVenda.setIdentificacaoLoja(identificacaoLoja);
        comprovanteVenda.setNSUHostTransacao(nsuHost);
        comprovanteVenda.setNSUTEF(nsuTef);
        comprovanteVenda.setNSUTerminal(nsuTerminal);
        comprovanteVenda.setCodigoAdquirente(codigoAdquirente);
        comprovanteVenda.setDataTransacao(dataTransacaoVenda);
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

        return comprovanteVenda;
    }

    public void mostrarHeaderArquivo(HeaderArquivo headerArquivo) {
        System.out.println("Código de registro: " + headerArquivo.getCodigoRegistro());
        System.out.println("Versão do layout: " + headerArquivo.getVersaoLayout());
        System.out.println("Data de geração: " + headerArquivo.getDataGeracao());
        System.out.println("Id do Movimento: " + headerArquivo.getIdMovimento());
        System.out.println("Nome da Administradora: " + headerArquivo.getNomeAdministradora());
        System.out.println("Identificação Remetente: " + headerArquivo.getIdentificacaoRemetente());
        System.out.println("Identificação Destinatário: " + headerArquivo.getIdentificacaoDestinatario());
        System.out.println("Tipo de Processamento: " + headerArquivo.getTipoProcessamento().getDescricao());
        System.out.println("NSEQ: " + headerArquivo.getNSEQ());
    }

    public void mostrarHeaderLoteTransacao(HeaderLoteTransacao headerLoteTransacao) {
        System.out.println("Código de registro: " + headerLoteTransacao.getCodigoRegistro());
        System.out.println("Data do movimento: " + headerLoteTransacao.getDataMovimento());
        System.out.println("Identificação da moeda: " + headerLoteTransacao.getMoedaCorrente().getDescricao());
        System.out.println("NSEQ: " + headerLoteTransacao.getNSEQ());
    }

    public void mostrarResumoVendas(ResumoVenda resumoVenda) {
        System.out.println("Código de registro: " + resumoVenda.getCodigoRegistro());
        System.out.println("Identificação da Loja: " + resumoVenda.getIdentificacaoLoja());
        System.out.println("Número do RV: " + resumoVenda.getNumeroResumoVenda());
        System.out.println("Data do RV: " + resumoVenda.getDataResumoVenda());
        System.out.println("Tipo de lançamento: " + resumoVenda.getTipoLancamento().getDescricao());
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
    }
}