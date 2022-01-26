package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import com.example.conciflex.model.dao.convcard.ComprovanteVendaConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCComprovanteVendaConvcardDAO implements ComprovanteVendaConvcardDAO {
    private static JDBCComprovanteVendaConvcardDAO instance;

    private JDBCComprovanteVendaConvcardDAO(){}

    public static JDBCComprovanteVendaConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCComprovanteVendaConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVendaConvcard comprovanteVendaConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_comprovante_venda(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, CNPJ, NSU_TRANSACAO," +
                "DATA_TRANSACAO, HORA_TRANSACAO, NUMERO_CARTAO, NUMERO_AUTORIZACAO, NUMERO_PARCELA," +
                "QUANTIDADE_PARCELAS, DATA_LANCAMENTO, VALOR_BRUTO_VENDA, VALOR_DESCONTO, VALOR_LIQUIDO_VENDA," +
                "VALOR_BRUTO_PARCELA, VALOR_LIQUIDO_PARCELA, BANCO, AGENCIA, CONTA," +
                "NSEQ_LOTE, NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, comprovanteVendaConvcard.getTipoRegistro());
        preparedStatement.setString(2, comprovanteVendaConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, comprovanteVendaConvcard.getNseqRegistroLote());
        preparedStatement.setString(4, comprovanteVendaConvcard.getCnpj());
        preparedStatement.setString(5, comprovanteVendaConvcard.getNsuTransacao());
        preparedStatement.setString(6, comprovanteVendaConvcard.getDataTransacao());
        preparedStatement.setString(7, comprovanteVendaConvcard.getHoraTransacao());
        preparedStatement.setString(8, comprovanteVendaConvcard.getNumeroCartao());
        preparedStatement.setString(9, comprovanteVendaConvcard.getNumeroAutorizacao());
        preparedStatement.setString(10, comprovanteVendaConvcard.getNumeroParcela());
        preparedStatement.setString(11, comprovanteVendaConvcard.getQuantidadeParcelas());
        preparedStatement.setString(12, comprovanteVendaConvcard.getDataLancamento());
        preparedStatement.setString(13, comprovanteVendaConvcard.getValorBrutoVenda());
        preparedStatement.setString(14, comprovanteVendaConvcard.getValorDesconto());
        preparedStatement.setString(15, comprovanteVendaConvcard.getValorLiquidoVenda());
        preparedStatement.setString(16, comprovanteVendaConvcard.getValorBrutoParcela());
        preparedStatement.setString(17, comprovanteVendaConvcard.getValorLiquidoParcela());
        preparedStatement.setString(18, comprovanteVendaConvcard.getBanco());
        preparedStatement.setString(19, comprovanteVendaConvcard.getAgencia());
        preparedStatement.setString(20, comprovanteVendaConvcard.getConta());
        preparedStatement.setString(21, comprovanteVendaConvcard.getNseqLote());
        preparedStatement.setString(22, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
