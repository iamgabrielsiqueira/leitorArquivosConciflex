package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Estabelecimento;
import com.example.conciflex.model.dao.ClienteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCClienteDAO implements ClienteDAO {
    private static JDBCClienteDAO instance;

    public static JDBCClienteDAO getInstance() {
        if(instance == null) {
            instance = new JDBCClienteDAO();
        }
        return instance;
    }

    private Cliente carregaCliente(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("CODIGO");
        String nome = resultSet.getString("NOME");
        String cnpj = resultSet.getString("CPF_CNPJ");

        Cliente cliente = new Cliente();

        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("/", "");
        cnpj = cnpj.replace("-", "");

        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCnpj(cnpj);

        return cliente;
    }

    @Override
    public Cliente search(int id) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement;

        String sql = "select CODIGO, NOME, CPF_CNPJ from clientes where CODIGO = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Cliente cliente = null;

        if(resultSet.next()) {
            cliente = carregaCliente(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return cliente;
    }
}
