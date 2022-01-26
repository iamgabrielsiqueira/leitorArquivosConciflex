package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.ComprovantePagamentoConvcard;
import com.example.conciflex.model.dao.convcard.ComprovantePagamentoConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCComprovantePagamentoConvcardDAO implements ComprovantePagamentoConvcardDAO {
    private static JDBCComprovantePagamentoConvcardDAO instance;

    private JDBCComprovantePagamentoConvcardDAO(){}

    public static JDBCComprovantePagamentoConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCComprovantePagamentoConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovantePagamentoConvcard comprovantePagamentoConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_comprovante_pagamento(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, CNPJ_LOJA, NSU_TRANSACAO," +
                "DATA_TRANSACAO, HORA_TRANSACAO, NUMERO_CARTAO, NUMERO_AUTORIZACAO, NUMERO_PARCELA," +
                "DATA_PAGAMENTO, VALOR_BRUTO_PAGAMENTO, VALOR_DESCONTO, VALOR_LIQUIDO_PAGAMENTO, BANCO," +
                "AGENCIA, CONTA, NSEQ_LOTE, NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, comprovantePagamentoConvcard.getTipoRegistro());
        preparedStatement.setString(2, comprovantePagamentoConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, comprovantePagamentoConvcard.getNseqRegistroLote());
        preparedStatement.setString(4, comprovantePagamentoConvcard.getCnpjLoja());
        preparedStatement.setString(5, comprovantePagamentoConvcard.getNsuTransacao());
        preparedStatement.setString(6, comprovantePagamentoConvcard.getDataTransacao());
        preparedStatement.setString(7, comprovantePagamentoConvcard.getHoraTransacao());
        preparedStatement.setString(8, comprovantePagamentoConvcard.getNumeroCartao());
        preparedStatement.setString(9, comprovantePagamentoConvcard.getNumeroAutorizacao());
        preparedStatement.setString(10, comprovantePagamentoConvcard.getNumeroParcela());
        preparedStatement.setString(11, comprovantePagamentoConvcard.getDataPagamento());
        preparedStatement.setString(12, comprovantePagamentoConvcard.getValorBrutoPagamento());
        preparedStatement.setString(13, comprovantePagamentoConvcard.getValorDesconto());
        preparedStatement.setString(14, comprovantePagamentoConvcard.getValorLiquidoPagamento());
        preparedStatement.setString(15, comprovantePagamentoConvcard.getBanco());
        preparedStatement.setString(16, comprovantePagamentoConvcard.getAgencia());
        preparedStatement.setString(17, comprovantePagamentoConvcard.getConta());
        preparedStatement.setString(18, comprovantePagamentoConvcard.getNseqLote());
        preparedStatement.setString(19, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
