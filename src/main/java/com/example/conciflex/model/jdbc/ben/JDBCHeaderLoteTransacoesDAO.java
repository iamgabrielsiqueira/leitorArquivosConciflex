package com.example.conciflex.model.jdbc.ben;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ben.HeaderLoteTransacao;
import com.example.conciflex.model.dao.ben.HeaderLoteTransacoesDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCHeaderLoteTransacoesDAO implements HeaderLoteTransacoesDAO {
    private static JDBCHeaderLoteTransacoesDAO instance;
    private ObservableList<HeaderLoteTransacao> headerLoteTransacaoObservableList;

    private JDBCHeaderLoteTransacoesDAO(){
        headerLoteTransacaoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCHeaderLoteTransacoesDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHeaderLoteTransacoesDAO();
        }
        return instance;
    }

    @Override
    public void create(HeaderLoteTransacao headerLoteTransacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_header_lote_transacoes(" +
                "COD_REGISTRO, DATA_MOVIMENTO, IDENTIFICACAO_MOEDA, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        Date dataMovimento = null;

        if(headerLoteTransacao.getDataMovimento() != null) {
            dataMovimento = new SimpleDateFormat("yyyyMMdd").parse(headerLoteTransacao.getDataMovimento());
        }

        long timeInMilliSeconds = dataMovimento.getTime();
        java.sql.Date dataMovimentoLote = new java.sql.Date(timeInMilliSeconds);

        preparedStatement.setString(1, headerLoteTransacao.getCodigoRegistro());
        preparedStatement.setDate(2, dataMovimentoLote);
        preparedStatement.setString(3, headerLoteTransacao.getMoedaCorrente().getDescricao());
        preparedStatement.setString(4, headerLoteTransacao.getNSEQ());
        preparedStatement.setString(5, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public void deletar(String arquivo) throws Exception {
        String sql = "delete from edi_ben_header_lote_transacoes where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
