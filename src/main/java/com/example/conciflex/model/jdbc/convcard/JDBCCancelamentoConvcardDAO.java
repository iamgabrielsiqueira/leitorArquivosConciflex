package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.dao.convcard.CancelamentoConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCCancelamentoConvcardDAO implements CancelamentoConvcardDAO {
    private static JDBCCancelamentoConvcardDAO instance;

    private JDBCCancelamentoConvcardDAO(){}

    public static JDBCCancelamentoConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCCancelamentoConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(CancelamentoConvcard cancelamentoConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_cancelamento(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, CNPJ_LOJA, NSU_TRANSACAO," +
                "DATA_TRANSACAO, HORA_TRANSACAO, NUMERO_CARTAO, NUMERO_AUTORIZACAO, NUMERO_PARCELA," +
                "DATA_CANCELAMENTO, NSEQ_LOTE, NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, cancelamentoConvcard.getTipoRegistro());
        preparedStatement.setString(2, cancelamentoConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, cancelamentoConvcard.getNseqRegistroLote());
        preparedStatement.setString(4, cancelamentoConvcard.getCnpjLoja());
        preparedStatement.setString(5, cancelamentoConvcard.getNsuTransacao());
        preparedStatement.setString(6, cancelamentoConvcard.getDataTransacao());
        preparedStatement.setString(7, cancelamentoConvcard.getHoraTransacao());
        preparedStatement.setString(8, cancelamentoConvcard.getNumeroCartao());
        preparedStatement.setString(9, cancelamentoConvcard.getNumeroAutorizacao());
        preparedStatement.setString(10, cancelamentoConvcard.getNumeroParcela());
        preparedStatement.setString(11, cancelamentoConvcard.getDataCancelamento());
        preparedStatement.setString(12, cancelamentoConvcard.getNseqLote());
        preparedStatement.setString(13, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
