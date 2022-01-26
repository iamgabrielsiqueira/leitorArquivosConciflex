package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.HeaderLoteTransacoesConvcard;
import com.example.conciflex.model.dao.convcard.HeaderLoteTransacoesConvcardDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCHeaderLoteTransacoesConvcardDAO implements HeaderLoteTransacoesConvcardDAO {
    private static JDBCHeaderLoteTransacoesConvcardDAO instance;

    private JDBCHeaderLoteTransacoesConvcardDAO(){}

    public static JDBCHeaderLoteTransacoesConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHeaderLoteTransacoesConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(HeaderLoteTransacoesConvcard headerLoteTransacoesConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_header_lote_transacoes(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, DATA_MOVIMENTO, NSEQ_LOTE, CNPJ_ADM," +
                "NOME_ADM, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, headerLoteTransacoesConvcard.getTipoRegistro());
        preparedStatement.setString(2, headerLoteTransacoesConvcard.getNseqRegistro());
        preparedStatement.setString(3, headerLoteTransacoesConvcard.getDataMovimento());
        preparedStatement.setString(4, headerLoteTransacoesConvcard.getNseqLote());
        preparedStatement.setString(5, headerLoteTransacoesConvcard.getCnpjAdm());
        preparedStatement.setString(6, headerLoteTransacoesConvcard.getNomeAdm());
        preparedStatement.setString(7, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
