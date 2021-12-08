package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.dao.ComprovanteVendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCComprovanteVendaDAO implements ComprovanteVendaDAO {
    private static JDBCComprovanteVendaDAO instance;
    private ObservableList<ComprovanteVenda> comprovanteVendaObservableList;

    private JDBCComprovanteVendaDAO(){
        comprovanteVendaObservableList = FXCollections.observableArrayList();
    }

    public static JDBCComprovanteVendaDAO getInstance() {
        if(instance == null) {
            instance = new JDBCComprovanteVendaDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVenda comprovanteVenda, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_comprovante_venda(" +
                "COD_REGISTRO, IDENTIFICACAO_LOJA, NSU_HOST_TRANSACAO, NSU_TEF, NSU_TERMINAL, COD_ADQUIRENTE," +
                "DESCRICAO_ADQUIRENTE, DATA_TRANSACAO, HORARIO_TRANSACAO, COD_TIPO_LANCAMENTO, DESCRICAO_TIPO_LANCAMENTO," +
                "DATA_LANCAMENTO, TIPO_PRODUTO, COD_MEIO_CAPTURA, DESCRICAO_MEIO_CAPTURA, VALOR_BRUTO, VALOR_DESCONTO," +
                "VALOR_LIQUIDO, NUMERO_CARTAO, BANCO, AGENCIA, CONTA, COD_AUTORIZACAO, COD_BANDEIRA, COD_PRODUTO," +
                "DESCRICAO_PRODUTO, CODIGO_EC, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, comprovanteVenda.getCodigoRegistro());
        preparedStatement.setString(2, comprovanteVenda.getIdentificacaoLoja());
        preparedStatement.setString(3, comprovanteVenda.getNSUHostTransacao());

        if(comprovanteVenda.getNSUTEF() != null) {
            preparedStatement.setString(4, comprovanteVenda.getNSUTEF());
        } else {
            preparedStatement.setString(4, String.valueOf(java.sql.Types.NULL));
        }

        if(comprovanteVenda.getNSUTerminal() != null) {
            preparedStatement.setString(5, comprovanteVenda.getNSUTerminal());
        } else {
            preparedStatement.setString(5, String.valueOf(java.sql.Types.NULL));
        }

        preparedStatement.setInt(6, Integer.parseInt(comprovanteVenda.getCodigoAdquirente()));

        if(comprovanteVenda.getAdquirente() != null) {
            preparedStatement.setString(7, comprovanteVenda.getAdquirente().getDescricao());
        } else {
            preparedStatement.setString(7, "teste");
        }

        Date dataTransacao = null;

        if(comprovanteVenda.getDataTransacao() != null) {
            dataTransacao = new SimpleDateFormat("yyyyMMdd").parse(comprovanteVenda.getDataTransacao());
        }

        long timeInMilliSeconds = dataTransacao.getTime();
        java.sql.Date dataTransacaoSQL = new java.sql.Date(timeInMilliSeconds);

        preparedStatement.setDate(8, dataTransacaoSQL);

        Date horaTransacao = null;
        Time horaTransacaoSQL = null;

        if(comprovanteVenda.getHoraTransacao() != null) {
            horaTransacao = new SimpleDateFormat("HHmmss").parse(comprovanteVenda.getHoraTransacao());
        }

        horaTransacaoSQL = new Time(horaTransacao.getTime());

        if(comprovanteVenda.getHoraTransacao() != null) {
            preparedStatement.setTime(9, horaTransacaoSQL);
        } else {
            preparedStatement.setNull(9, java.sql.Types.TIMESTAMP);
        }

        preparedStatement.setInt(10, comprovanteVenda.getTipoLancamento().getId());
        preparedStatement.setString(11, comprovanteVenda.getTipoLancamento().getDescricao());

        Date dataLancamento = null;

        if(comprovanteVenda.getDataLancamento() != null) {
            dataLancamento = new SimpleDateFormat("yyyyMMdd").parse(comprovanteVenda.getDataLancamento());
        }

        long timeInMilliSeconds2 = dataLancamento.getTime();
        java.sql.Date dataLancamentoSQL = new java.sql.Date(timeInMilliSeconds2);

        preparedStatement.setDate(12, dataLancamentoSQL);
        preparedStatement.setString(13, comprovanteVenda.getTipoProduto());

        preparedStatement.setInt(14, comprovanteVenda.getMeioCaptura().getId());
        preparedStatement.setString(15, comprovanteVenda.getMeioCaptura().getDescricao());
        preparedStatement.setString(16, comprovanteVenda.getValorBruto());
        preparedStatement.setString(17, comprovanteVenda.getValorDesconto());
        preparedStatement.setString(18, comprovanteVenda.getValorLiquido());
        preparedStatement.setString(19, comprovanteVenda.getNumeroCartao());
        preparedStatement.setString(20, comprovanteVenda.getBanco());
        preparedStatement.setString(21, comprovanteVenda.getAgencia());
        preparedStatement.setString(22, comprovanteVenda.getConta());
        preparedStatement.setString(23, comprovanteVenda.getCodigoAutorizacao());

        int codigoBandeira = Integer.parseInt(comprovanteVenda.getCodigoBandeira().replaceAll("\\s+",""));

        preparedStatement.setInt(24, codigoBandeira);
        preparedStatement.setInt(25, Integer.parseInt(comprovanteVenda.getCodigoProduto()));
        preparedStatement.setString(26, comprovanteVenda.getProduto().getDescricao());
        preparedStatement.setString(27, comprovanteVenda.getCodigoEC());
        preparedStatement.setString(28, comprovanteVenda.getNSEQ());
        preparedStatement.setString(29, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
