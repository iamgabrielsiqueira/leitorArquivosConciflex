package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Arquivo;
import com.example.conciflex.model.dao.ArquivoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.SimpleDateFormat;

public class JDBCArquivoDAO implements ArquivoDAO {
    private static JDBCArquivoDAO instance;
    private ObservableList<Arquivo> arquivoObservableList;

    private JDBCArquivoDAO(){
        arquivoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCArquivoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCArquivoDAO();
        }
        return instance;
    }

    private Arquivo carregarArquivo(ResultSet resultSet) throws Exception {

        String nomeArquivo = resultSet.getString("NOME_ARQUIVO");
        int idAdquirente = resultSet.getInt("COD_ADIQUIRENTE");
        String cnpj = resultSet.getString("CNPJ_CLIENTE");

        Arquivo arquivo = new Arquivo();

        arquivo.setArquivo(nomeArquivo);
        arquivo.setIdAdquirente(idAdquirente);
        arquivo.setCNPJ(cnpj);

        return arquivo;
    }

    @Override
    public void create(Arquivo arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into controle_arquivos_processados(" +
                "NOME_ARQUIVO, LOCALIZACAO, DATA_PROCESSAMENTO, HORA_PROCESSAMENTO, COD_ADIQUIRENTE," +
                "CNPJ_CLIENTE, DATA_ARQUIVO, DATA_MENOR_VENDA, DATA_MAIOR_VENDA, DATA_MENOR_PAGAMENTO," +
                "DATA_MAIOR_PAGAMENTO, ESTABELECIMENTO, ARQUIVO_COM_FALHA" +
                ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo.getArquivo());
        preparedStatement.setString(2, arquivo.getLocalizacao());
        preparedStatement.setDate(3, arquivo.getDataProcessamento());
        preparedStatement.setTime(4, arquivo.getHoraProcessamento());
        preparedStatement.setInt(5, arquivo.getIdAdquirente());
        preparedStatement.setString(6, arquivo.getCNPJ());
        preparedStatement.setDate(7, arquivo.getDataArquivo());
        preparedStatement.setDate(8, arquivo.getDataMenorVenda());
        preparedStatement.setDate(9, arquivo.getDataMaiorVenda());
        preparedStatement.setDate(10, arquivo.getDataMaiorPagamento());
        preparedStatement.setDate(11, arquivo.getDataMenorPagamento());
        preparedStatement.setString(12, arquivo.getEstabelecimentoCNPJ());
        preparedStatement.setString(13, arquivo.getArquivoFalha());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Boolean search(String nomeArquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from controle_arquivos_processados where NOME_ARQUIVO = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, nomeArquivo);

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
    public ObservableList<Arquivo> listarArquivos() throws Exception {
        arquivoObservableList.clear();

        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement;

            String sql = "SELECT NOME_ARQUIVO, DATA_GERACAO FROM edi_ben_header_arquivo GROUP BY NOME_ARQUIVO ORDER BY DATA_GERACAO";
            preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String nomeArquivo = resultSet.getString("NOME_ARQUIVO");
                Date dataGeracao = resultSet.getDate("DATA_GERACAO");
                String dataString = new SimpleDateFormat("dd/MM/yyyy").format(dataGeracao);

                Arquivo arquivo = new Arquivo();

                arquivo.setArquivo(nomeArquivo);
                arquivo.setDataArquivo(dataGeracao);
                arquivo.setDataArquivoString(dataString);

                arquivoObservableList.add(arquivo);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return arquivoObservableList;
    }

    @Override
    public void deletarControleArquivos(String arquivo) throws Exception {
        String sql = "delete from controle_arquivos_processados where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
