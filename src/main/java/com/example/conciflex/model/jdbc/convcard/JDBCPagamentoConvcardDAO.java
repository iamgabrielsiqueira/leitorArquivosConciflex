package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.ComprovantePagamentoConvcard;
import com.example.conciflex.model.dao.convcard.PagamentoConvcardDAO;

import java.sql.*;

public class JDBCPagamentoConvcardDAO implements PagamentoConvcardDAO {
    private static JDBCPagamentoConvcardDAO instance;

    private JDBCPagamentoConvcardDAO(){}

    public static JDBCPagamentoConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCPagamentoConvcardDAO();
        }
        return instance;
    }

    @Override
    public long create(ComprovantePagamentoConvcard comprovantePagamentoConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String generatedColumns[] = { "CODIGO" };

        String sql = "insert into pagamentos_operadoras(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, NSU, COD_ADQUIRENTE, DATA_VENDA, DATA_PREV_PAG_ORIGINAL," +
                "DATA_PAGAMENTO, AUTORIZADOR, COD_FORMA_PAGAMENTO, COD_MEIO_CAPTURA, VALOR_BRUTO," +
                "VALOR_TAXA, VALOR_LIQUIDO, NUMERO_CARTAO, BANCO, COD_BANCO," +
                "AGENCIA, CONTA, CODIGO_AUTORIZACAO, COD_BANDEIRA, COD_PRODUTO," +
                "COD_STATUS_FINANCEIRO, NOME_ARQUIVO, PARCELA, TOTAL_PARCELAS, OBSERVACOES," +
                "TAXA_PERCENTUAL, HORA_VENDA, COD_TIPO_PAGAMENTO, COD_TIPO_LANCAMENTO, ID_LOJA," +
                "CHAVE_PAGAMENTO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);

        preparedStatement.setInt(1, comprovantePagamentoConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, comprovantePagamentoConvcard.getCnpjLoja());
        preparedStatement.setString(3, comprovantePagamentoConvcard.getEmpresa().getNomeEmpresa());
        preparedStatement.setInt(4, comprovantePagamentoConvcard.getEmpresa().getId());
        preparedStatement.setDate(5, dataImportacao);
        preparedStatement.setTime(6, horaImportacao);
        preparedStatement.setString(7, comprovantePagamentoConvcard.getNsuFormatado());
        preparedStatement.setInt(8, 123);
        preparedStatement.setDate(9, comprovantePagamentoConvcard.getDataTransacaoSQL());
        preparedStatement.setDate(10, comprovantePagamentoConvcard.getDataPagamentoSQL());
        preparedStatement.setDate(11, comprovantePagamentoConvcard.getDataPagamentoSQL());
        preparedStatement.setString(12, comprovantePagamentoConvcard.getAutorizador());
        preparedStatement.setInt(13, 3);
        preparedStatement.setInt(14, 13);
        preparedStatement.setDouble(15, comprovantePagamentoConvcard.getValorBrutoFormatado());
        preparedStatement.setDouble(16, comprovantePagamentoConvcard.getValorDescontoFormatado());
        preparedStatement.setDouble(17, comprovantePagamentoConvcard.getValorLiquidoFormatado());
        preparedStatement.setString(18, comprovantePagamentoConvcard.getNumeroCartaoFormatado());
        preparedStatement.setString(19, comprovantePagamentoConvcard.getBanco());
        preparedStatement.setInt(20, Integer.parseInt(comprovantePagamentoConvcard.getBanco()));
        preparedStatement.setString(21, comprovantePagamentoConvcard.getAgenciaFormatado());
        preparedStatement.setString(22, comprovantePagamentoConvcard.getContaFormatado());
        preparedStatement.setString(23, comprovantePagamentoConvcard.getAutorizacaoFormatado());
        preparedStatement.setInt(24, 185);
        preparedStatement.setInt(25, 22);
        preparedStatement.setInt(26, 1);
        preparedStatement.setString(27, arquivo);
        preparedStatement.setInt(28, 1);
        preparedStatement.setInt(29, 1);
        preparedStatement.setString(30, "A coluna Taxa % foi calculada pelo valor bruto e líquido");
        preparedStatement.setDouble(31, comprovantePagamentoConvcard.getTaxaPercentual());
        preparedStatement.setTime(32, comprovantePagamentoConvcard.getHoraTransacaoSQL());
        preparedStatement.setInt(33, 1);
        preparedStatement.setInt(34, 1);
        preparedStatement.setString(35, comprovantePagamentoConvcard.getCnpjLoja());
        preparedStatement.setString(36, comprovantePagamentoConvcard.getChavePagamento());

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        long id = 0;

        if(resultSet.next()) {
            id = resultSet.getLong(1);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return id;
    }

    @Override
    public Boolean verificarDuplicidade(String chavePagamento) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from pagamentos_operadoras where CHAVE_PAGAMENTO LIKE ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, chavePagamento);

        ResultSet resultSet = preparedStatement.executeQuery();

        Boolean verificar = false;

        if(resultSet.next()) {
            verificar = true;
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return verificar;
    }

    @Override
    public void updatePaymentStatus(String paymentKey, String saleKey) throws Exception {
        String sql = "update pagamentos_operadoras set COD_STATUS = ?, CHAVE_VENDA_OPERADORA = ? WHERE CHAVE_PAGAMENTO LIKE ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, saleKey);
        preparedStatement.setString(3, paymentKey);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
