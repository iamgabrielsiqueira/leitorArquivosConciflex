package com.example.conciflex.model.jdbc.ben;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ben.HeaderArquivo;
import com.example.conciflex.model.dao.ben.HeaderArquivoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCHeaderArquivoDAO implements HeaderArquivoDAO {
    private static JDBCHeaderArquivoDAO instance;
    private ObservableList<HeaderArquivo> headerArquivoObservableList;

    private JDBCHeaderArquivoDAO(){
        headerArquivoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCHeaderArquivoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHeaderArquivoDAO();
        }
        return instance;
    }

    @Override
    public void create(HeaderArquivo headerArquivo, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_header_arquivo(" +
                "COD_REGISTRO, VERSAO_LAYOUT, DATA_GERACAO, HORA_GERACAO, ID_MOVIMENTO," +
                "NOME_ADMINISTRADORA, IDENTIFICACAO_REMETENTE, IDENTIFICACAO_DESTINATARIO," +
                "TIPO_PROCESSAMENTO, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, headerArquivo.getCodigoRegistro());
        preparedStatement.setString(2, headerArquivo.getVersaoLayout());

        Date dataGeracao = null;
        Date horaGeracao = null;
        Time horaGeracaoArquivo = null;

        if(headerArquivo.getDataGeracao() != null) {
            dataGeracao = new SimpleDateFormat("yyyyMMdd").parse(headerArquivo.getDataGeracao());
        }

        if(headerArquivo.getHoraGeracao() != null) {
            horaGeracao = new SimpleDateFormat("HHmmss").parse(headerArquivo.getHoraGeracao());
        }

        long timeInMilliSeconds = dataGeracao.getTime();
        java.sql.Date dataGeracaoArquivo = new java.sql.Date(timeInMilliSeconds);
        horaGeracaoArquivo = new Time(horaGeracao.getTime());

        preparedStatement.setDate(3, dataGeracaoArquivo);
        preparedStatement.setTime(4, horaGeracaoArquivo);
        preparedStatement.setString(5, headerArquivo.getIdMovimento());
        preparedStatement.setString(6, headerArquivo.getNomeAdministradora());
        preparedStatement.setString(7, headerArquivo.getIdentificacaoRemetente());
        preparedStatement.setString(8, headerArquivo.getIdentificacaoDestinatario());
        preparedStatement.setString(9, headerArquivo.getTipoProcessamento().getDescricao());
        preparedStatement.setString(10, headerArquivo.getNSEQ());
        preparedStatement.setString(11, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    private HeaderArquivo carregarHeaderArquivo(ResultSet resultSet) throws SQLException {
        String nomeArquivo = resultSet.getString("ID_MOVIMENTO");
        java.sql.Date dataGeracao = resultSet.getDate("DATA_GERACAO");

        HeaderArquivo headerArquivo = new HeaderArquivo();

        headerArquivo.setIdMovimento(nomeArquivo);
        headerArquivo.setDataGeracao(String.valueOf(dataGeracao));

        return headerArquivo;
    }

    @Override
    public Boolean search(String dataGeracao, String idMovimento, String idDestinatario) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from edi_ben_header_arquivo where DATA_GERACAO = ? and ID_MOVIMENTO = ? " +
                "and IDENTIFICACAO_DESTINATARIO = ?";
        preparedStatement = connection.prepareStatement(sql);

        Date data = new SimpleDateFormat("yyyyMMdd").parse(dataGeracao);
        long timeInMilliSeconds = data.getTime();
        java.sql.Date dataSQL = new java.sql.Date(timeInMilliSeconds);

        preparedStatement.setDate(1, dataSQL);
        preparedStatement.setString(2, idMovimento);
        preparedStatement.setString(3, idDestinatario);

        ResultSet resultSet = preparedStatement.executeQuery();
        Boolean verificar = false;

        if(resultSet.next()) {
            verificar = true;
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return verificar;
    }

    @Override
    public void deletar(String arquivo) throws Exception {
        String sql = "delete from edi_ben_header_arquivo where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

}
