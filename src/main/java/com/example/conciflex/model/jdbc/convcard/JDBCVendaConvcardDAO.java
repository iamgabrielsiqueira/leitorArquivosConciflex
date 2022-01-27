package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.classes.convcard.ComprovantePagamentoConvcard;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import com.example.conciflex.model.dao.convcard.VendaConvcardDAO;

import java.sql.*;

public class JDBCVendaConvcardDAO implements VendaConvcardDAO {
    private static JDBCVendaConvcardDAO instance;

    private JDBCVendaConvcardDAO(){}

    public static JDBCVendaConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCVendaConvcardDAO();
        }
        return instance;
    }

    private ComprovanteVendaConvcard carregarComprovanteVendaConvcard(ResultSet resultSet) throws Exception {
        String valorBruto = resultSet.getString("VALOR_BRUTO_VENDA");
        String valorLiquido = resultSet.getString("VALOR_LIQUIDO_VENDA");
        String valorDesconto = resultSet.getString("VALOR_DESCONTO");
        String banco = resultSet.getString("BANCO");
        String agencia = resultSet.getString("AGENCIA");
        String conta = resultSet.getString("CONTA");

        String agenciaFormatado = agencia.replaceFirst("^0+(?!$)", "");
        String contaFormatado = conta.replaceFirst("^0+(?!$)", "");

        Double valorBrutoFormatado = converterValorDouble(valorBruto);
        Double valorLiquidoFormatado = converterValorDouble(valorLiquido);
        Double valorDescontoFormatado = converterValorDouble(valorDesconto);

        ComprovanteVendaConvcard comprovanteVendaConvcard = new ComprovanteVendaConvcard();

        comprovanteVendaConvcard.setValorBrutoVenda(valorBruto);
        comprovanteVendaConvcard.setValorLiquidoVenda(valorLiquido);
        comprovanteVendaConvcard.setValorDesconto(valorDesconto);
        comprovanteVendaConvcard.setValorBrutoFormatado(valorBrutoFormatado);
        comprovanteVendaConvcard.setValorLiquidoFormatado(valorLiquidoFormatado);
        comprovanteVendaConvcard.setValorDescontoFormatado(valorDescontoFormatado);
        comprovanteVendaConvcard.setBanco(banco);
        comprovanteVendaConvcard.setAgencia(agencia);
        comprovanteVendaConvcard.setConta(conta);
        comprovanteVendaConvcard.setAgenciaFormatado(agenciaFormatado);
        comprovanteVendaConvcard.setContaFormatado(contaFormatado);
        comprovanteVendaConvcard.setValorTaxaPercentual(calcularTaxaPercentual(valorBrutoFormatado, valorDescontoFormatado));

        return comprovanteVendaConvcard;
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

        Double valorBruto = comprovanteVendaConvcard.getValorBrutoFormatado();
        Double valorLiquido = comprovanteVendaConvcard.getValorLiquidoFormatado();
        Double valorDesconto = comprovanteVendaConvcard.getValorDescontoFormatado();

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
        preparedStatement.setDouble(15, valorBruto);
        preparedStatement.setDouble(16, valorDesconto);
        preparedStatement.setDouble(17, valorLiquido);
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
        preparedStatement.setString(37, comprovanteVendaConvcard.getChaveVenda());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Boolean verificarDuplicidade(String chaveVenda) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from vendas where CHAVE_VENDA LIKE ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, chaveVenda);

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
    public ComprovanteVendaConvcard searchCancelamento(CancelamentoConvcard cancelamentoConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from edi_convcard_comprovante_venda where NSU_TRANSACAO LIKE ? and DATA_TRANSACAO LIKE ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, cancelamentoConvcard.getNsuTransacao());
        preparedStatement.setString(2, cancelamentoConvcard.getDataTransacao());

        ResultSet resultSet = preparedStatement.executeQuery();

        ComprovanteVendaConvcard comprovanteVendaConvcard = null;

        if(resultSet.next()) {
            comprovanteVendaConvcard = carregarComprovanteVendaConvcard(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return comprovanteVendaConvcard;
    }

    @Override
    public int searchVendaCancelada(CancelamentoConvcard cancelamentoConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from vendas where COD_CLIENTE = ? AND DATA_VENDA = ? AND ADQID = ? AND NSU LIKE ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, cancelamentoConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setDate(2, cancelamentoConvcard.getDataTransacaoSQL());
        preparedStatement.setInt(3, 123);
        preparedStatement.setString(4, cancelamentoConvcard.getNsuFormatado());

        ResultSet resultSet = preparedStatement.executeQuery();

        int id = 0;

        if(resultSet.next()) {
            id = resultSet.getInt("CODIGO");
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return id;
    }

    @Override
    public void updateVendaCancelada(int id, CancelamentoConvcard cancelamentoConvcard) throws Exception {
        String sql = "update vendas set DATA_CANCELAMENTO = ?, COD_STATUS_FINANCEIRO = ? where CODIGO = ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setDate(1, cancelamentoConvcard.getDataCancelamentoSQL());
        preparedStatement.setInt(2, 3);
        preparedStatement.setInt(3, id);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Boolean search(ComprovantePagamentoConvcard comprovantePagamentoConvcard) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;

        String sql = "select * from vendas where ADQID = 123 and COD_CLIENTE = ? " +
                "and DATA_VENDA = ? and VALOR_BRUTO = ? and COD_STATUS_FINANCEIRO = 1";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, comprovantePagamentoConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setDate(2, comprovantePagamentoConvcard.getDataTransacaoSQL());
        preparedStatement.setDouble(3, comprovantePagamentoConvcard.getValorBrutoFormatado());

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
    public void updateVendaPaga(ComprovantePagamentoConvcard comprovantePagamentoConvcard, long id) throws Exception {
        String sql = "update vendas set COD_STATUS_FINANCEIRO = ?, CHAVE_PAGAMENTO = ?, COD_PAGAMENTO = ? " +
                "where ADQID = 123 and COD_CLIENTE = ? and DATA_VENDA = ? " +
                "and VALOR_BRUTO = ? and COD_STATUS_FINANCEIRO = 1";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, comprovantePagamentoConvcard.getChavePagamento());
        preparedStatement.setLong(3, id);
        preparedStatement.setInt(4, comprovantePagamentoConvcard.getEmpresa().getCliente().getId());
        preparedStatement.setDate(5, comprovantePagamentoConvcard.getDataTransacaoSQL());
        preparedStatement.setDouble(6, comprovantePagamentoConvcard.getValorBrutoFormatado());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
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
}
