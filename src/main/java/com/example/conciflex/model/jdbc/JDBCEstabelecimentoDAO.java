package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.Estabelecimento;
import com.example.conciflex.model.dao.EstabelecimentoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCEstabelecimentoDAO implements EstabelecimentoDAO {
    private static JDBCEstabelecimentoDAO instance;

    public static JDBCEstabelecimentoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCEstabelecimentoDAO();
        }
        return instance;
    }

    private Estabelecimento carregaEstabelecimento(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("CODIGO");
        String CNPJ = resultSet.getString("CNPJ");
        String nomeEmpresa = resultSet.getString("NOME_EMPRESA");
        int idCliente = resultSet.getInt("COD_CLIENTE");

        Estabelecimento estabelecimento = new Estabelecimento();
        Cliente cliente = null;

        if(idCliente != 0) {
            try {
                cliente = JDBCClienteDAO.getInstance().search(idCliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        estabelecimento.setId(id);
        estabelecimento.setCNPJ(CNPJ);
        estabelecimento.setNomeEmpresa(nomeEmpresa);
        estabelecimento.setCliente(cliente);

        return estabelecimento;
    }

    @Override
    public Estabelecimento search(String CNPJ) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement;

        String sql = "select CODIGO, CNPJ, NOME_EMPRESA, COD_CLIENTE from grupos_clientes where CNPJ = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, CNPJ);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Estabelecimento estabelecimento = carregaEstabelecimento(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return estabelecimento;
    }
}
