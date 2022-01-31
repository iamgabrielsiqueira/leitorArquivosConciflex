package com.example.conciflex.model.jdbc.ben;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import com.example.conciflex.model.classes.ben.Venda;
import com.example.conciflex.model.dao.ben.VendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JDBCVendaDAO implements VendaDAO {
    private static JDBCVendaDAO instance;
    private ObservableList<Venda> vendaObservableList;

    private JDBCVendaDAO(){
        vendaObservableList = FXCollections.observableArrayList();
    }

    public static JDBCVendaDAO getInstance() {
        if(instance == null) {
            instance = new JDBCVendaDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into vendas(" +
                "COD_CLIENTE, CNPJ, EMPRESA, COD_GRUPO_CLIENTE, DATA_PROCESSAMENTO," +
                "HORA_PROCESSAMENTO, NSU, TERMINAL, TID, ADQID," +
                "ADQUIRENTE, DATA_VENDA, AUTORIZADOR, DATA_PREVISTA_PAGTO, CODIGO_MODALIDADE," +
                "COD_MEIO_CAPTURA, VALOR_BRUTO, VALOR_TAXA, VALOR_LIQUIDO, CARTAO," +
                "BANCO, AGENCIA, CONTA, AUTORIZACAO, COD_BANDEIRA," +
                "BANDEIRA, COD_PRODUTO, CODIGO_PRODUTO, PRODUTO, ESTABELECIMENTO," +
                "COD_STATUS_FINANCEIRO, COD_STATUS_CONCILIACAO, NOME_ARQUIVO, PARCELA, TOTAL_PARCELAS," +
                "OBSERVACOES, PERCENTUAL_TAXA, HORA_TRANSACAO, CHAVE_VENDA" +
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

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

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
        preparedStatement.setString(11, "BEN VISA VALE");
        preparedStatement.setDate(12, comprovanteVenda.getDataTransacao());
        preparedStatement.setString(13, comprovanteVenda.getAdquirente().getDescricao());
        preparedStatement.setDate(14, comprovanteVenda.getDataLancamento());
        preparedStatement.setInt(15, 3);
        preparedStatement.setInt(16, comprovanteVenda.getMeioCaptura().getIdConciflex());
        preparedStatement.setDouble(17, comprovanteVenda.getValorBruto());
        preparedStatement.setDouble(18, comprovanteVenda.getValorDesconto());
        preparedStatement.setDouble(19, comprovanteVenda.getValorLiquido());
        preparedStatement.setString(20, comprovanteVenda.getCartaoFormatado());
        preparedStatement.setInt(21, Integer.parseInt(comprovanteVenda.getBanco()));
        preparedStatement.setString(22, comprovanteVenda.getAgenciaFormatado());
        preparedStatement.setString(23, comprovanteVenda.getContaFormatada());
        preparedStatement.setString(24, comprovanteVenda.getAutorizacaoFormatada());
        preparedStatement.setInt(25, 238);
        preparedStatement.setString(26, "BEN VISA VALE");
        preparedStatement.setInt(27, comprovanteVenda.getProduto().getIdConciflex());
        preparedStatement.setInt(28, comprovanteVenda.getProduto().getIdConciflex());
        preparedStatement.setString(29, comprovanteVenda.getProduto().getDescricaoConciflex());
        preparedStatement.setString(30, comprovanteVenda.getCodigoEC());
        preparedStatement.setInt(31, 1);
        preparedStatement.setInt(32, 2);
        preparedStatement.setString(33, arquivo);
        preparedStatement.setInt(34, 1);
        preparedStatement.setInt(35, 1);
        preparedStatement.setString(36, "A coluna Taxa % foi calculada pelo valor bruto e líquido");
        preparedStatement.setDouble(37, comprovanteVenda.getTaxaPercentual());
        preparedStatement.setTime(38, comprovanteVenda.getHoraTransacao());
        preparedStatement.setString(39, comprovanteVenda.getChavePagamento());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public String search(ComprovanteVenda comprovanteVenda) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;

        String sql = "select CHAVE_VENDA from vendas where ADQID = 268 and cod_cliente = ? " +
                "and DATA_VENDA = ? and VALOR_BRUTO = ? and COD_STATUS_FINANCEIRO = 1";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, comprovanteVenda.getEmpresa().getCliente().getId());
        preparedStatement.setDate(2, comprovanteVenda.getDataTransacao());
        preparedStatement.setDouble(3, comprovanteVenda.getValorBruto());

        ResultSet resultSet = preparedStatement.executeQuery();

        String saleKey = "";

        if(resultSet.next()) {
            saleKey = resultSet.getString("CHAVE_VENDA");
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return saleKey;
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
    public void updateVendaPaga(ComprovanteVenda comprovanteVenda, long id) throws Exception {
        String sql = "update vendas set COD_STATUS_FINANCEIRO = ?, CHAVE_PAGAMENTO = ?, COD_PAGAMENTO = ? " +
                "where ADQID = 268 and cod_cliente = ? and DATA_VENDA = ? " +
                "and VALOR_BRUTO = ? and COD_STATUS_FINANCEIRO = 1";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, comprovanteVenda.getChavePagamento());
        preparedStatement.setLong(3, id);
        preparedStatement.setInt(4, comprovanteVenda.getEmpresa().getCliente().getId());
        preparedStatement.setDate(5, comprovanteVenda.getDataTransacao());
        preparedStatement.setDouble(6, comprovanteVenda.getValorBruto());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void deletarVendas(String arquivo) throws Exception {
        String sql = "delete from vendas where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

}
