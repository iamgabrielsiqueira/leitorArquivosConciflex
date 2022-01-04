package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.TrailerArquivo;
import com.example.conciflex.model.dao.TrailerArquivoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCTrailerArquivoDAO implements TrailerArquivoDAO {
    private static JDBCTrailerArquivoDAO instance;
    private ObservableList<TrailerArquivo> trailerArquivoObservableList;

    private JDBCTrailerArquivoDAO(){
        trailerArquivoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCTrailerArquivoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTrailerArquivoDAO();
        }
        return instance;
    }

    @Override
    public void create(TrailerArquivo trailerArquivo, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_trailer_arquivo_teste(" +
                "COD_REGISTRO, DATA_MOVIMENTO, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, trailerArquivo.getCodigoRegistro());
        preparedStatement.setString(2, trailerArquivo.getDataMovimento());
        preparedStatement.setString(3, trailerArquivo.getNSEQ());
        preparedStatement.setString(4, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
