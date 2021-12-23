package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Empresa;
import com.example.conciflex.model.dao.EmpresaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCEmpresaDAO implements EmpresaDAO {
    private static JDBCEmpresaDAO instance;

    public static JDBCEmpresaDAO getInstance() {
        if(instance == null) {
            instance = new JDBCEmpresaDAO();
        }
        return instance;
    }

    private Empresa carregaEmpresa(ResultSet resultSet) throws Exception {
        int id = resultSet.getInt("CODIGO");
        String cnpj = resultSet.getString("CNPJ");
        int idCliente = resultSet.getInt("COD_CLIENTE");
        String nomeEmpresa = resultSet.getString("NOME_EMPRESA");

        Cliente cliente = JDBCClienteDAO.getInstance().search(idCliente);

        Empresa empresa = new Empresa();

        empresa.setId(id);
        empresa.setCnpj(cnpj);
        empresa.setCliente(cliente);
        empresa.setNomeEmpresa(nomeEmpresa);

        return empresa;
    }

    @Override
    public Empresa search(String CNPJ) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement preparedStatement;

        String sql = "select * from grupos_clientes where CNPJ LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, CNPJ);
        ResultSet resultSet = preparedStatement.executeQuery();

        Empresa empresa = null;

        if(resultSet.next()) {
            empresa = carregaEmpresa(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
        return empresa;
    }
}
