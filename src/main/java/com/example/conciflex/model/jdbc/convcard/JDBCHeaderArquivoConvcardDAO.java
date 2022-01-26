package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.HeaderArquivoConvcard;
import com.example.conciflex.model.dao.convcard.HeaderArquivoConvcardDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCHeaderArquivoConvcardDAO implements HeaderArquivoConvcardDAO {
    private static JDBCHeaderArquivoConvcardDAO instance;

    private JDBCHeaderArquivoConvcardDAO(){}

    public static JDBCHeaderArquivoConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHeaderArquivoConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(HeaderArquivoConvcard headerArquivoConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_header_arquivo(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, VERSAO_LAYOUT, DATA_GERACAO, HORA_GERACAO," +
                "CNPJ_GRUPO_LOJA, NOME_GRUPO_LOJA, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, headerArquivoConvcard.getTipoRegistro());
        preparedStatement.setString(2, headerArquivoConvcard.getNseqRegistro());
        preparedStatement.setString(3, headerArquivoConvcard.getVersaoLayout());
        preparedStatement.setString(4, headerArquivoConvcard.getDataGeracao());
        preparedStatement.setString(5, headerArquivoConvcard.getHoraGeracao());
        preparedStatement.setString(6, headerArquivoConvcard.getCnpjLojas());
        preparedStatement.setString(7, headerArquivoConvcard.getNomeLojas());
        preparedStatement.setString(8, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
