package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import com.example.conciflex.model.dao.convcard.VendaConvcardDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

public class JDBCVendaConvcardDAO implements VendaConvcardDAO {
    private static JDBCVendaConvcardDAO instance;

    private JDBCVendaConvcardDAO(){}

    public static JDBCVendaConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCVendaConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVendaConvcard comprovanteVendaConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into vendas(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, NSU, ADQID, ADQUIRENTE, DATA_VENDA," +
                "AUTORIZADOR, DATA_PREVISTA_PAGTO, CODIGO_MODALIDADE, COD_MEIO_CAPTURA, VALOR_BRUTO," +
                "VALOR_TAXA, VALOR_LIQUIDO, CARTAO, BANCO, AGENCIA," +
                "CONTA, AUTORIZACAO, COD_BANDEIRA, BANDEIRA, COD_PRODUTO," +
                "CODIGO_PRODUTO, PRODUTO, ESTABELECIMENTO, COD_STATUS_FINANCEIRO, COD_STATUS_CONCILIACAO," +
                "NOME_ARQUIVO, PARCELA, TOTAL_PARCELAS, OBSERVACOES, PERCENTUAL_TAXA," +
                "HORA_TRANSACAO, CHAVE_VENDA" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, comprovanteVendaConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, comprovanteVendaConvcard.getCnpj());
        preparedStatement.setString(3, comprovanteVendaConvcard.getEmpresa().getNomeEmpresa());
        preparedStatement.setInt(4, comprovanteVendaConvcard.getEmpresa().getId());
        preparedStatement.setDate(5, dataImportacao);
        preparedStatement.setTime(6, horaImportacao);
        preparedStatement.setString(7, comprovanteVendaConvcard.getNsuFormatada());
        preparedStatement.setInt(8, 123);
        preparedStatement.setString(9, "CONVCARD");
        preparedStatement.setDate(10, comprovanteVendaConvcard.getDataTransacaoSQL());
        preparedStatement.setString(11, comprovanteVendaConvcard.getAutorizador());
        preparedStatement.setDate(12, comprovanteVendaConvcard.getDataLancamentoSQL());
        preparedStatement.setInt(13, 3);
        preparedStatement.setInt(14, 13);
        preparedStatement.setDouble(15, comprovanteVendaConvcard.getValorBrutoFormatado());
        preparedStatement.setDouble(16, comprovanteVendaConvcard.getValorDescontoFormatado());
        preparedStatement.setDouble(17, comprovanteVendaConvcard.getValorLiquidoFormatado());
        preparedStatement.setString(18, comprovanteVendaConvcard.getNumeroCartaoFormatado());
        preparedStatement.setInt(19, comprovanteVendaConvcard.getBancoFormatado());
        preparedStatement.setString(20, comprovanteVendaConvcard.getAgenciaFormatado());
        preparedStatement.setString(21, comprovanteVendaConvcard.getContaFormatado());
        preparedStatement.setString(22, comprovanteVendaConvcard.getAutorizacaoFormatado());
        preparedStatement.setInt(23, 185);
        preparedStatement.setString(24, "CONVCARD");
        preparedStatement.setInt(25, 22);
        preparedStatement.setInt(26, 22);
        preparedStatement.setString(27, "Voucher");
        preparedStatement.setString(28, comprovanteVendaConvcard.getCnpj());
        preparedStatement.setInt(29, 1);
        preparedStatement.setInt(30, 2);
        preparedStatement.setString(31, arquivo);
        preparedStatement.setInt(32, 1);
        preparedStatement.setInt(33, 1);
        preparedStatement.setString(34, "A coluna Taxa % foi calculada pelo valor bruto e líquido");
        preparedStatement.setDouble(35, comprovanteVendaConvcard.getValorTaxaPercentual());
        preparedStatement.setTime(36, comprovanteVendaConvcard.getHoraTransacaoSQL());
        preparedStatement.setString(37, comprovanteVendaConvcard.getChavePagamento());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
