package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Arquivo;
import com.example.conciflex.model.dao.ArquivoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    public Arquivo search(String nomeArquivo, int idAdquirente, String CNPJ) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;
        String sql = "select * from controle_arquivos_processados " +
                "where NOME_ARQUIVO = ? and COD_ADIQUIRENTE = ? and CNPJ_CLIENTE = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, nomeArquivo);
        preparedStatement.setInt(2, idAdquirente);
        preparedStatement.setString(3, CNPJ);

        ResultSet resultSet = preparedStatement.executeQuery();
        Arquivo arquivo = null;

        if(resultSet.next()) {
            arquivo = carregarArquivo(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return arquivo;
    }
}
