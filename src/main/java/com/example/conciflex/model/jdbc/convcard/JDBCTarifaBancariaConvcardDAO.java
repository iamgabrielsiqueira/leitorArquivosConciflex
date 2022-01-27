package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.TarifaBancariaConvcard;
import com.example.conciflex.model.dao.convcard.TarifaBancariaConvcardDAO;
import java.sql.*;

public class JDBCTarifaBancariaConvcardDAO implements TarifaBancariaConvcardDAO {
    private static JDBCTarifaBancariaConvcardDAO instance;

    private JDBCTarifaBancariaConvcardDAO(){}

    public static JDBCTarifaBancariaConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTarifaBancariaConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(TarifaBancariaConvcard tarifaBancariaConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_tarifa_bancaria(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, CNPJ_LOJA, DATA_TRANSACAO," +
                "VALOR, BANCO, AGENCIA, CONTA, NSEQ_LOTE," +
                "NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, tarifaBancariaConvcard.getTipoRegistro());
        preparedStatement.setString(2, tarifaBancariaConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, tarifaBancariaConvcard.getNseqRegistroLote());
        preparedStatement.setString(4, tarifaBancariaConvcard.getCnpjLoja());
        preparedStatement.setString(5, tarifaBancariaConvcard.getDataTransacao());
        preparedStatement.setString(6, tarifaBancariaConvcard.getValor());
        preparedStatement.setString(7, tarifaBancariaConvcard.getBanco());
        preparedStatement.setString(8, tarifaBancariaConvcard.getAgencia());
        preparedStatement.setString(9, tarifaBancariaConvcard.getConta());
        preparedStatement.setString(10, tarifaBancariaConvcard.getNseqLote());
        preparedStatement.setString(11, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void createPagamento(TarifaBancariaConvcard tarifaBancariaConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into pagamentos_operadoras(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, COD_ADQUIRENTE, DATA_VENDA, DATA_PREV_PAG_ORIGINAL, DATA_PAGAMENTO," +
                "DATA_CANCELAMENTO, AUTORIZADOR, COD_FORMA_PAGAMENTO, COD_MEIO_CAPTURA, VALOR_BRUTO," +
                "VALOR_LIQUIDO, BANCO, COD_BANCO, AGENCIA, CONTA," +
                "COD_BANDEIRA, COD_PRODUTO, COD_STATUS_FINANCEIRO, NOME_ARQUIVO, PARCELA," +
                "TOTAL_PARCELAS, COD_TIPO_PAGAMENTO, COD_TIPO_LANCAMENTO, ID_LOJA, COD_AJUSTE" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        Double valorBruto = tarifaBancariaConvcard.getValorFormatado();
        Double valorLiquido = tarifaBancariaConvcard.getValorFormatado();

        valorBruto *= -1;
        valorLiquido *= -1;

        preparedStatement.setInt(1, tarifaBancariaConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, tarifaBancariaConvcard.getCnpjLoja());
        preparedStatement.setString(3, tarifaBancariaConvcard.getEmpresa().getNomeEmpresa());
        preparedStatement.setInt(4, tarifaBancariaConvcard.getEmpresa().getId());
        preparedStatement.setDate(5, dataImportacao);
        preparedStatement.setTime(6, horaImportacao);
        preparedStatement.setInt(7, 123);
        preparedStatement.setDate(8, tarifaBancariaConvcard.getDataTransacaoSQL());
        preparedStatement.setDate(9, tarifaBancariaConvcard.getDataTransacaoSQL());
        preparedStatement.setDate(10, tarifaBancariaConvcard.getDataTransacaoSQL());
        preparedStatement.setDate(11, tarifaBancariaConvcard.getDataTransacaoSQL());
        preparedStatement.setString(12, tarifaBancariaConvcard.getAutorizador());
        preparedStatement.setInt(13, 3);
        preparedStatement.setInt(14, 13);
        preparedStatement.setDouble(15, valorBruto);
        preparedStatement.setDouble(16, valorLiquido);
        preparedStatement.setString(17, tarifaBancariaConvcard.getBanco());
        preparedStatement.setInt(18, Integer.parseInt(tarifaBancariaConvcard.getBanco()));
        preparedStatement.setString(19, tarifaBancariaConvcard.getAgenciaFormatada());
        preparedStatement.setString(20, tarifaBancariaConvcard.getContaFormatada());
        preparedStatement.setInt(21, 185);
        preparedStatement.setInt(22, 22);
        preparedStatement.setInt(23, 3);
        preparedStatement.setString(24, arquivo);
        preparedStatement.setInt(25, 1);
        preparedStatement.setInt(26, 1);
        preparedStatement.setInt(27, 1);
        preparedStatement.setInt(28, 2);
        preparedStatement.setString(29, tarifaBancariaConvcard.getCnpjLoja());
        preparedStatement.setInt(30, 1173);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Boolean searchTarifa(TarifaBancariaConvcard tarifaBancariaConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from edi_convcard_tarifa_bancaria where DATA_TRANSACAO LIKE ? " +
                "AND NSEQ_LOTE LIKE ? AND CNPJ_LOJA = ? AND VALOR = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, tarifaBancariaConvcard.getDataTransacao());
        preparedStatement.setString(2, tarifaBancariaConvcard.getNseqLote());
        preparedStatement.setString(3, tarifaBancariaConvcard.getCnpjLoja());
        preparedStatement.setString(4, tarifaBancariaConvcard.getValor());

        ResultSet resultSet = preparedStatement.executeQuery();

        Boolean verificaTarifaBancariaExiste = false;

        if(resultSet.next()) {
            verificaTarifaBancariaExiste = true;
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return verificaTarifaBancariaExiste;
    }
}
