package com.example.conciflex.model.jdbc.convcard;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.convcard.TarifaBancariaConvcard;
import com.example.conciflex.model.dao.convcard.TarifaBancariaConvcardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCTarifaBancariaConvcardDAO implements TarifaBancariaConvcardDAO {
    private static JDBCTarifaBancariaConvcardDAO instance;

    private JDBCTarifaBancariaConvcardDAO(){}

    public static JDBCTarifaBancariaConvcardDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTarifaBancariaConvcardDAO();
        }
        return instance;
    }

    @Override
    public void create(TarifaBancariaConvcard tarifaBancariaConvcard, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_convcard_tarifa_bancaria(" +
                "TIPO_REGISTRO, NSEQ_REGISTRO_ARQUIVO, NSEQ_REGISTRO_LOTE, CNPJ_LOJA, DATA_TRANSACAO," +
                "VALOR, BANCO, AGENCIA, CONTA, NSEQ_LOTE," +
                "NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, tarifaBancariaConvcard.getTipoRegistro());
        preparedStatement.setString(2, tarifaBancariaConvcard.getNseqRegistroArquivo());
        preparedStatement.setString(3, tarifaBancariaConvcard.getNseqRegistroLote());
        preparedStatement.setString(4, tarifaBancariaConvcard.getCnpjLoja());
        preparedStatement.setString(5, tarifaBancariaConvcard.getDataTransacao());
        preparedStatement.setString(6, tarifaBancariaConvcard.getValor());
        preparedStatement.setString(7, tarifaBancariaConvcard.getBanco());
        preparedStatement.setString(8, tarifaBancariaConvcard.getAgencia());
        preparedStatement.setString(9, tarifaBancariaConvcard.getConta());
        preparedStatement.setString(10, tarifaBancariaConvcard.getNseqLote());
        preparedStatement.setString(11, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
