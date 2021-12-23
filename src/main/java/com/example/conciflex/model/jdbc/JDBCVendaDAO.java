package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Venda;
import com.example.conciflex.model.dao.VendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

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
                "OBSERVACOES, PERCENTUAL_TAXA, HORA_TRANSACAO" +
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
        preparedStatement.setString(20, comprovanteVenda.getNumeroCartao());
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

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Venda search(ComprovanteVenda comprovanteVenda) throws Exception {
        return null;
    }
}
