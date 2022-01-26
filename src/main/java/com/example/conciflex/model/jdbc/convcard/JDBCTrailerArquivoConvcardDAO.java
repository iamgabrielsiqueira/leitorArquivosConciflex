package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.TrailerArquivoConvcard;
import com.example.conciflex.model.dao.convcard.TrailerArquivoConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCTrailerArquivoConvcardDAO implements TrailerArquivoConvcardDAO {
    private static JDBCTrailerArquivoConvcardDAO instance;

    private JDBCTrailerArquivoConvcardDAO(){}

    public static JDBCTrailerArquivoConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTrailerArquivoConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(TrailerArquivoConvcard trailerArquivoConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_trailer_arquivo(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, QUANTIDADE_REGISTROS, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, trailerArquivoConvcard.getTipoRegistro());
        preparedStatement.setString(2, trailerArquivoConvcard.getNseqRegistro());
        preparedStatement.setString(3, trailerArquivoConvcard.getQuantidadeRegistros());
        preparedStatement.setString(4, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
