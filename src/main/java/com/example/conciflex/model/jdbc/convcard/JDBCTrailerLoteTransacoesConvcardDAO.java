package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.TrailerLoteTransacoesConvcard;
import com.example.conciflex.model.dao.convcard.TrailerLoteTransacoesConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCTrailerLoteTransacoesConvcardDAO implements TrailerLoteTransacoesConvcardDAO {
    private static JDBCTrailerLoteTransacoesConvcardDAO instance;

    private JDBCTrailerLoteTransacoesConvcardDAO(){}

    public static JDBCTrailerLoteTransacoesConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTrailerLoteTransacoesConvcardDAO();
        }
        return instance;
    }
    @Override
    public void create(TrailerLoteTransacoesConvcard trailerLoteTransacoesConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_trailer_lote_transacoes(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, QUANTIDADE_REGISTROS, VALOR_TOTAL_BRUTO," +
                "NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, trailerLoteTransacoesConvcard.getTipoRegistro());
        preparedStatement.setString(2, trailerLoteTransacoesConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, trailerLoteTransacoesConvcard.getNseqLote());
        preparedStatement.setString(4, trailerLoteTransacoesConvcard.getQuantidadeRegistrosLote());
        preparedStatement.setString(5, trailerLoteTransacoesConvcard.getValorTotalBrutoLote());
        preparedStatement.setString(6, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
