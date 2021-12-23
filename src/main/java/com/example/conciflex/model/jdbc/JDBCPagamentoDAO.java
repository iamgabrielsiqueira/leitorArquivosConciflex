package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Pagamento;
import com.example.conciflex.model.dao.PagamentoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCPagamentoDAO implements PagamentoDAO {
    private static JDBCPagamentoDAO instance;
    private ObservableList<Pagamento> pagamentoObservableList;

    private JDBCPagamentoDAO(){
        pagamentoObservableList = FXCollections.observableArrayList();
    }

    public static JDBCPagamentoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCPagamentoDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVenda comprovanteVenda) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into pagamentos_operadoras(" +
                "COD_CLIENTE, CNPJ, EMPRESA " +
                ") " +
                "values(" +
                "?, ?, ?" +
                ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, comprovanteVenda.getEmpresa().getCliente().getId());
        preparedStatement.setString(2, comprovanteVenda.getIdentificacaoLoja());
        preparedStatement.setString(3, comprovanteVenda.getEmpresa().getNomeEmpresa());

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public Pagamento search(ComprovanteVenda comprovanteVenda) throws Exception {
        return null;
    }
}
