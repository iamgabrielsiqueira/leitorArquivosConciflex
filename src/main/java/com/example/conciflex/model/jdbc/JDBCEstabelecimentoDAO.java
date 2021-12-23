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
        int idCliente = resultSet.getInt("COD_CLIENTE");
        String codigoEstabelecimento = resultSet.getString("CODIGO_ESTABELECIMENTO");
        String cnpj = resultSet.getString("CNPJ_ESTABELECIMENTO");

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
        estabelecimento.setCliente(cliente);
        estabelecimento.setCodigoEstabelecimento(codigoEstabelecimento);
        estabelecimento.setCnpj(cnpj);

        return estabelecimento;
    }

    @Override
    public Estabelecimento search(String CNPJ) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement;

        String sql = "select * from cliente_operadora where CODIGO_ESTABELECIMENTO LIKE ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, CNPJ);

        ResultSet resultSet = preparedStatement.executeQuery();
        Estabelecimento estabelecimento = null;

        if(resultSet.next()) {
            estabelecimento = carregaEstabelecimento(resultSet);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return estabelecimento;
    }
}
