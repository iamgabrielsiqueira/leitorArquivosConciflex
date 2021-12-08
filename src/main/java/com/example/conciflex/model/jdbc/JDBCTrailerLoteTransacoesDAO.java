package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ResumoVenda;
import com.example.conciflex.model.classes.TrailerLoteTransacao;
import com.example.conciflex.model.dao.TrailerLoteTransacoesDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCTrailerLoteTransacoesDAO implements TrailerLoteTransacoesDAO {
    private static JDBCTrailerLoteTransacoesDAO instance;
    private ObservableList<TrailerLoteTransacao> trailerLoteTransacaoObservableList;

    private JDBCTrailerLoteTransacoesDAO(){
        trailerLoteTransacaoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCTrailerLoteTransacoesDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTrailerLoteTransacoesDAO();
        }
        return instance;
    }

    @Override
    public void create(TrailerLoteTransacao trailerLoteTransacao, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_trailer_lote_transacoes(" +
                "COD_REGISTRO, TOTAL_REGISTROS, TOTAL_VALORES_CREDITO, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, trailerLoteTransacao.getCodigoRegistro());
        preparedStatement.setString(2, trailerLoteTransacao.getTotalRegistros());
        preparedStatement.setString(3, trailerLoteTransacao.getTotalValoresCredito());
        preparedStatement.setString(4, trailerLoteTransacao.getNSEQ());
        preparedStatement.setString(5, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
