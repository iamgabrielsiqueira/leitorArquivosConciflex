package com.example.conciflex.model.jdbc.ben;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import com.example.conciflex.model.classes.ben.Pagamento;
import com.example.conciflex.model.dao.ben.PagamentoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class JDBCPagamentoDAO implements PagamentoDAO {
    private static JDBCPagamentoDAO instance;
    private ObservableList<Pagamento> pagamentoObservableList;

    private JDBCPagamentoDAO(){
        pagamentoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCPagamentoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCPagamentoDAO();
        }
        return instance;
    }

    @Override
    public long create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String generatedColumns[] = { "CODIGO" };

        String sql = "insert into pagamentos_operadoras(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, NSU, NUMERO_TERMINAL, TID, COD_ADQUIRENTE," +
                "DATA_VENDA, DATA_PREV_PAG_ORIGINAL, DATA_PAGAMENTO, AUTORIZADOR, COD_FORMA_PAGAMENTO," +
                "COD_MEIO_CAPTURA, VALOR_BRUTO, VALOR_TAXA, VALOR_LIQUIDO, NUMERO_CARTAO," +
                "BANCO, COD_BANCO, AGENCIA, CONTA, CODIGO_AUTORIZACAO," +
                "COD_BANDEIRA, COD_PRODUTO, COD_STATUS_FINANCEIRO, NOME_ARQUIVO, PARCELA," +
                "TOTAL_PARCELAS, OBSERVACOES, TAXA_PERCENTUAL, HORA_VENDA, COD_TIPO_PAGAMENTO," +
                "COD_TIPO_LANCAMENTO, ID_LOJA, CHAVE_PAGAMENTO, COD_STATUS" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);

        preparedStatement.setInt(1, comprovanteVenda.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, comprovanteVenda.getIdentificacaoLoja());
        preparedStatement.setString(3, comprovanteVenda.getEmpresa().getNomeEmpresa());
        preparedStatement.setInt(4, comprovanteVenda.getEmpresa().getId());
        preparedStatement.setDate(5, dataImportacao);

        preparedStatement.setTime(6, horaImportacao);
        preparedStatement.setString(7, comprovanteVenda.getNsuFormatada());
        preparedStatement.setString(8, comprovanteVenda.getNSUTerminal());
        preparedStatement.setString(9, comprovanteVenda.getTidFormatada());
        preparedStatement.setInt(10, 268);

        preparedStatement.setDate(11, comprovanteVenda.getDataTransacao());
        preparedStatement.setDate(12, comprovanteVenda.getDataLancamento());
        preparedStatement.setDate(13, comprovanteVenda.getDataLancamento());
        preparedStatement.setString(14, comprovanteVenda.getAdquirente().getDescricao());
        preparedStatement.setInt(15, 3);

        preparedStatement.setInt(16, comprovanteVenda.getMeioCaptura().getIdConciflex());
        preparedStatement.setDouble(17, comprovanteVenda.getValorBruto());
        preparedStatement.setDouble(18, comprovanteVenda.getValorDesconto());
        preparedStatement.setDouble(19, comprovanteVenda.getValorLiquido());
        preparedStatement.setString(20, comprovanteVenda.getCartaoFormatado());

        preparedStatement.setString(21, comprovanteVenda.getBanco());
        preparedStatement.setInt(22, Integer.parseInt(comprovanteVenda.getBanco()));
        preparedStatement.setString(23, comprovanteVenda.getAgenciaFormatado());
        preparedStatement.setString(24, comprovanteVenda.getContaFormatada());
        preparedStatement.setString(25, comprovanteVenda.getAutorizacaoFormatada());

        preparedStatement.setInt(26, 238);
        preparedStatement.setInt(27, comprovanteVenda.getProduto().getIdConciflex());
        preparedStatement.setInt(28, 1);
        preparedStatement.setString(29, arquivo);
        preparedStatement.setInt(30, 1);

        preparedStatement.setInt(31, 1);
        preparedStatement.setString(32, "A coluna Taxa % foi calculada pelo valor bruto e l??quido");
        preparedStatement.setDouble(33, comprovanteVenda.getTaxaPercentual());
        preparedStatement.setTime(34, comprovanteVenda.getHoraTransacao());
        preparedStatement.setInt(35, comprovanteVenda.getTipoLancamento().getId());

        preparedStatement.setInt(36, comprovanteVenda.getTipoLancamento().getId());
        preparedStatement.setString(37, comprovanteVenda.getIdentificacaoLoja());
        preparedStatement.setString(38, comprovanteVenda.getChavePagamento());

        preparedStatement.setInt(39, 2);

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
    public Pagamento search(ComprovanteVenda comprovanteVenda) throws Exception {
        return null;
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
    public void deletarPagamentos(String arquivo) throws Exception {
        String sql = "delete from pagamentos_operadoras where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void updatePaymentStatusNotConc(long id) throws Exception {
        String sql = "update pagamentos_operadoras set COD_STATUS = ? WHERE COD_STATUS = ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 2);
        preparedStatement.setLong(2, id);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
