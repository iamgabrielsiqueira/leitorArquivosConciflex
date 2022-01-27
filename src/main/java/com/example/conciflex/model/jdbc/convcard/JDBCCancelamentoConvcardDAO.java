package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import com.example.conciflex.model.dao.convcard.CancelamentoConvcardDAO;

import java.sql.*;

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

    @Override
    public void createCancelamento(CancelamentoConvcard cancelamentoConvcard, Date dataImportacao, Time horaImportacao, String arquivo, ComprovanteVendaConvcard comprovanteVendaConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into pagamentos_operadoras(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, NSU, COD_ADQUIRENTE, DATA_VENDA, DATA_PREV_PAG_ORIGINAL," +
                "DATA_PAGAMENTO, DATA_CANCELAMENTO, AUTORIZADOR, COD_FORMA_PAGAMENTO, COD_MEIO_CAPTURA," +
                "VALOR_BRUTO, VALOR_TAXA, VALOR_LIQUIDO, NUMERO_CARTAO, BANCO," +
                "COD_BANCO, AGENCIA, CONTA, CODIGO_AUTORIZACAO, COD_BANDEIRA," +
                "COD_PRODUTO, COD_STATUS_FINANCEIRO, NOME_ARQUIVO, PARCELA, TOTAL_PARCELAS," +
                "OBSERVACOES, TAXA_PERCENTUAL, HORA_VENDA, COD_TIPO_PAGAMENTO, COD_TIPO_LANCAMENTO," +
                "ID_LOJA, CHAVE_PAGAMENTO, COD_AJUSTE" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        Double valorBruto = comprovanteVendaConvcard.getValorBrutoFormatado();
        Double valorLiquido = comprovanteVendaConvcard.getValorLiquidoFormatado();
        Double valorDesconto = comprovanteVendaConvcard.getValorDescontoFormatado();

        valorBruto *= -1;
        valorLiquido *= -1;
        valorDesconto *= -1;

        preparedStatement.setInt(1, cancelamentoConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, cancelamentoConvcard.getCnpjLoja());
        preparedStatement.setString(3, cancelamentoConvcard.getEmpresa().getNomeEmpresa());
        preparedStatement.setInt(4, cancelamentoConvcard.getEmpresa().getId());
        preparedStatement.setDate(5, dataImportacao);
        preparedStatement.setTime(6, horaImportacao);
        preparedStatement.setString(7, cancelamentoConvcard.getNsuFormatado());
        preparedStatement.setInt(8, 123);
        preparedStatement.setDate(9, cancelamentoConvcard.getDataTransacaoSQL());
        preparedStatement.setDate(10, cancelamentoConvcard.getDataCancelamentoSQL());
        preparedStatement.setDate(11, cancelamentoConvcard.getDataCancelamentoSQL());
        preparedStatement.setDate(12, cancelamentoConvcard.getDataCancelamentoSQL());
        preparedStatement.setString(13, cancelamentoConvcard.getAutorizador());
        preparedStatement.setInt(14, 3);
        preparedStatement.setInt(15, 13);
        preparedStatement.setDouble(16, valorBruto);
        preparedStatement.setDouble(17, valorDesconto);
        preparedStatement.setDouble(18, valorLiquido);
        preparedStatement.setString(19, cancelamentoConvcard.getNumeroCartaoFormatado());
        preparedStatement.setString(20, comprovanteVendaConvcard.getBanco());
        preparedStatement.setInt(21, Integer.parseInt(comprovanteVendaConvcard.getBanco()));
        preparedStatement.setString(22, comprovanteVendaConvcard.getAgenciaFormatado());
        preparedStatement.setString(23, comprovanteVendaConvcard.getContaFormatado());
        preparedStatement.setString(24, cancelamentoConvcard.getAutorizacaoFormatado());
        preparedStatement.setInt(25, 185);
        preparedStatement.setInt(26, 22);
        preparedStatement.setInt(27, 3);
        preparedStatement.setString(28, arquivo);
        preparedStatement.setInt(29, 1);
        preparedStatement.setInt(30, 1);
        preparedStatement.setString(31, "A coluna Taxa % foi calculada pelo valor bruto e líquido");
        preparedStatement.setDouble(32, comprovanteVendaConvcard.getValorTaxaPercentual());
        preparedStatement.setTime(33, cancelamentoConvcard.getHoraTransacaoSQL());
        preparedStatement.setInt(34, 1);
        preparedStatement.setInt(35, 2);
        preparedStatement.setString(36, cancelamentoConvcard.getCnpjLoja());
        preparedStatement.setString(37, cancelamentoConvcard.getChaveCancelamento());
        preparedStatement.setInt(38, 1174);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Boolean searchCancelamento(CancelamentoConvcard cancelamentoConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from edi_convcard_cancelamento where DATA_TRANSACAO LIKE ? AND NSU_TRANSACAO LIKE ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, cancelamentoConvcard.getDataTransacao());
        preparedStatement.setString(2, cancelamentoConvcard.getNsuTransacao());

        ResultSet resultSet = preparedStatement.executeQuery();

        Boolean verificaCancelamentoExiste = false;

        if(resultSet.next()) {
            verificaCancelamentoExiste = true;
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return verificaCancelamentoExiste;
    }


}
