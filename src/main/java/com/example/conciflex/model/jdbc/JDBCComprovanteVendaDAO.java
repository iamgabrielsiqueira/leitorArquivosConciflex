package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.Arquivo;
import com.example.conciflex.model.classes.Cliente;
import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Empresa;
import com.example.conciflex.model.dao.ComprovanteVendaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

public class JDBCComprovanteVendaDAO implements ComprovanteVendaDAO {
    private static JDBCComprovanteVendaDAO instance;
    private ObservableList<ComprovanteVenda> comprovanteVendaObservableList;

    private JDBCComprovanteVendaDAO(){
        comprovanteVendaObservableList = FXCollections.observableArrayList();
    }

    public static JDBCComprovanteVendaDAO getInstance() {
        if(instance == null) {
            instance = new JDBCComprovanteVendaDAO();
        }
        return instance;
    }

    @Override
    public void create(ComprovanteVenda comprovanteVenda, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_comprovante_venda(" +
                "COD_REGISTRO, IDENTIFICACAO_LOJA, NSU_HOST_TRANSACAO, NSU_TEF, NSU_TERMINAL, COD_ADQUIRENTE," +
                "DESCRICAO_ADQUIRENTE, DATA_TRANSACAO, HORARIO_TRANSACAO, COD_TIPO_LANCAMENTO, DESCRICAO_TIPO_LANCAMENTO," +
                "DATA_LANCAMENTO, TIPO_PRODUTO, COD_MEIO_CAPTURA, DESCRICAO_MEIO_CAPTURA, VALOR_BRUTO, VALOR_DESCONTO," +
                "VALOR_LIQUIDO, NUMERO_CARTAO, BANCO, AGENCIA, CONTA, COD_AUTORIZACAO, COD_BANDEIRA, COD_PRODUTO," +
                "DESCRICAO_PRODUTO, CODIGO_EC, NSEQ, NOME_ARQUIVO" +
                ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, comprovanteVenda.getCodigoRegistro());
        preparedStatement.setString(2, comprovanteVenda.getIdentificacaoLoja());
        preparedStatement.setString(3, comprovanteVenda.getNSUHostTransacao());

        if(comprovanteVenda.getNSUTEF() != null) {
            preparedStatement.setString(4, comprovanteVenda.getNSUTEF());
        } else {
            preparedStatement.setString(4, String.valueOf(java.sql.Types.NULL));
        }

        if(comprovanteVenda.getNSUTerminal() != null) {
            preparedStatement.setString(5, comprovanteVenda.getNSUTerminal());
        } else {
            preparedStatement.setString(5, String.valueOf(java.sql.Types.NULL));
        }

        preparedStatement.setInt(6, Integer.parseInt(comprovanteVenda.getCodigoAdquirente()));
        preparedStatement.setString(7, comprovanteVenda.getAdquirente().getDescricao());
        preparedStatement.setDate(8, comprovanteVenda.getDataTransacao());
        preparedStatement.setTime(9, comprovanteVenda.getHoraTransacao());
        preparedStatement.setInt(10, comprovanteVenda.getTipoLancamento().getId());
        preparedStatement.setString(11, comprovanteVenda.getTipoLancamento().getDescricao());
        preparedStatement.setDate(12, comprovanteVenda.getDataLancamento());
        preparedStatement.setString(13, comprovanteVenda.getTipoProduto());
        preparedStatement.setInt(14, comprovanteVenda.getMeioCaptura().getId());
        preparedStatement.setString(15, comprovanteVenda.getMeioCaptura().getDescricao());
        preparedStatement.setString(16, comprovanteVenda.getValorBrutoString());
        preparedStatement.setString(17, comprovanteVenda.getValorDescontoString());
        preparedStatement.setString(18, comprovanteVenda.getValorLiquidoString());
        preparedStatement.setString(19, comprovanteVenda.getNumeroCartao());
        preparedStatement.setString(20, comprovanteVenda.getBanco());
        preparedStatement.setString(21, comprovanteVenda.getAgencia());
        preparedStatement.setString(22, comprovanteVenda.getConta());
        preparedStatement.setString(23, comprovanteVenda.getCodigoAutorizacao());

        int codigoBandeira = Integer.parseInt(comprovanteVenda.getCodigoBandeira().replaceAll("\\s+",""));

        preparedStatement.setInt(24, codigoBandeira);
        preparedStatement.setInt(25, Integer.parseInt(comprovanteVenda.getCodigoProduto()));
        preparedStatement.setString(26, comprovanteVenda.getProduto().getDescricao());
        preparedStatement.setString(27, comprovanteVenda.getCodigoEC());
        preparedStatement.setString(28, comprovanteVenda.getNSEQ());
        preparedStatement.setString(29, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    @Override
    public ObservableList<ComprovanteVenda> listarPagamentos() throws Exception {
        comprovanteVendaObservableList.clear();

        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement;

            String sql = "SELECT * FROM edi_ben_comprovante_venda WHERE COD_TIPO_LANCAMENTO = 1";
            preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ComprovanteVenda comprovanteVenda = carregarComprovanteVenda(resultSet);
                comprovanteVendaObservableList.add(comprovanteVenda);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return comprovanteVendaObservableList;
    }

    private ComprovanteVenda carregarComprovanteVenda(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("CODIGO");
        Date dataTransacao = resultSet.getDate("DATA_TRANSACAO");
        String valorBrutoString = resultSet.getString("VALOR_BRUTO");
        String identificacaoLoja = resultSet.getString("IDENTIFICACAO_LOJA");

        Double valorBruto = converterValorDouble(valorBrutoString);

        ComprovanteVenda comprovanteVenda = new ComprovanteVenda();

        Empresa empresa = null;
        Cliente cliente = null;

        try {
            empresa = JDBCEmpresaDAO.getInstance().search(identificacaoLoja);
        } catch (Exception e) {
            System.out.println(e);
        }

        if(empresa == null) {
            try {
                empresa = JDBCEmpresaDAO.getInstance().search("22162885000168");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if(empresa != null) {
            cliente = empresa.getCliente();
            if(cliente != null) {
                empresa.setCliente(cliente);
            }
        }

        comprovanteVenda.setId(id);
        comprovanteVenda.setDataTransacao(dataTransacao);
        comprovanteVenda.setValorBruto(valorBruto);
        comprovanteVenda.setValorBrutoString(valorBrutoString);
        comprovanteVenda.setEmpresa(empresa);

        return comprovanteVenda;
    }

    public Double converterValorDouble(String valorString) {
        Double valorFinal = null;
        String valorFormatado = "";

        for (int i = 0; i < valorString.length(); i++) {
            valorFormatado += valorString.toCharArray()[i];
            if(i == (valorString.length() - 3)) {
                valorFormatado += ".";
            }
        }

        valorFormatado = valorFormatado.replaceFirst("^0+(?!$)", "");

        String primeiro = String.valueOf(valorFormatado.toCharArray()[0]);

        if(primeiro.equals(".")) {
            valorFormatado = "0" + valorFormatado;
        }

        valorFinal = Double.parseDouble(valorFormatado);

        return valorFinal;
    }

    @Override
    public void deletar(String arquivo) throws Exception {
        String sql = "delete from edi_ben_comprovante_venda where nome_arquivo like ?";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
