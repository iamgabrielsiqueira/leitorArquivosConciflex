package com.example.conciflex.model.jdbc;

import com.example.conciflex.model.ConnectionFactory;
import com.example.conciflex.model.classes.ResumoVenda;
import com.example.conciflex.model.dao.ResumoVendasDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCResumoVendasDAO implements ResumoVendasDAO {
    private static JDBCResumoVendasDAO instance;
    private ObservableList<ResumoVenda> resumoVendaObservableList;

    private JDBCResumoVendasDAO(){
        resumoVendaObservableList = FXCollections.observableArrayList();
    }

    public static JDBCResumoVendasDAO getInstance() {
        if(instance == null) {
            instance = new JDBCResumoVendasDAO();
        }
        return instance;
    }

    @Override
    public void create(ResumoVenda resumoVenda, String arquivo) throws Exception {
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into edi_ben_resumo_vendas_teste(" +
                "COD_REGISTRO, IDENTIFICACAO_LOJA, NUMERO_RV, DATA_RV, COD_TIPO_LANCAMENTO," +
                "TIPO_LANCAMENTO, DATA_PAGAMENTO, CVS_APROVADOS, CVS_REJEITADOS, TIPO_PRODUTO," +
                "CODIGO_PRODUTO, DESCRICAO_PRODUTO, BANCO, AGENCIA, CONTA_CORRENTE," +
                "VALOR_BRUTO, SINAL_VALOR_BRUTO, VALOR_LIQUIDO, SINAL_VALOR_LIQUIDO, VALOR_CREDITO," +
                "SINAL_VALOR_CREDITO, VALOR_COMISSAO, SINAL_VALOR_COMISSAO, IDENTIFICADOR_AJUSTE_RV, CODIGO_AJUSTES," +
                "DESCRICAO_AJUSTE, CODIGO_EC, CODIGO_ADQUIRENTE, DESCRICAO_ADQUIRENTE, NSEQ," +
                "NOME_ARQUIVO" +
                ") values(" +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?," +
                "?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        Date dataRV = null;
        Date dataPagamento = null;

        if(resumoVenda.getDataResumoVenda() != null) {
            dataRV = new SimpleDateFormat("yyyyMMdd").parse(resumoVenda.getDataResumoVenda());
        }

        if(resumoVenda.getDataPagamento() != null) {
            dataPagamento = new SimpleDateFormat("yyyyMMdd").parse(resumoVenda.getDataPagamento());
        }

        long timeInMilliSeconds = dataRV.getTime();
        java.sql.Date dataResumoVendas = new java.sql.Date(timeInMilliSeconds);

        long timeInMilliSeconds1 = dataPagamento.getTime();
        java.sql.Date dataPagamentoSQL = new java.sql.Date(timeInMilliSeconds1);

        preparedStatement.setString(1, resumoVenda.getCodigoRegistro());
        preparedStatement.setString(2, resumoVenda.getIdentificacaoLoja());
        preparedStatement.setString(3, resumoVenda.getNumeroResumoVenda());
        preparedStatement.setDate(4, dataResumoVendas);
        preparedStatement.setInt(5, resumoVenda.getTipoLancamento().getId());
        preparedStatement.setString(6, resumoVenda.getTipoLancamento().getDescricao());
        preparedStatement.setDate(7, dataPagamentoSQL);
        preparedStatement.setInt(8, Integer.parseInt(resumoVenda.getCVsAprovados()));
        preparedStatement.setInt(9, Integer.parseInt(resumoVenda.getCVsRejeitados()));
        preparedStatement.setString(10, resumoVenda.getTipoProduto());
        preparedStatement.setInt(11, resumoVenda.getProduto().getId());
        preparedStatement.setString(12, resumoVenda.getProduto().getDescricao());
        preparedStatement.setInt(13, Integer.parseInt(resumoVenda.getBanco()));
        preparedStatement.setInt(14, Integer.parseInt(resumoVenda.getAgencia()));
        preparedStatement.setString(15, resumoVenda.getContaCorrente());
        preparedStatement.setString(16, resumoVenda.getValorBruto());
        preparedStatement.setString(17, resumoVenda.getSinalValorBruto());
        preparedStatement.setString(18, resumoVenda.getValorLiquido());
        preparedStatement.setString(19, resumoVenda.getSinalValorLiquido());
        preparedStatement.setString(20, resumoVenda.getValorCredito());
        preparedStatement.setString(21, resumoVenda.getSinalValorCredito());
        preparedStatement.setString(22, resumoVenda.getValorComissao());
        preparedStatement.setString(23, resumoVenda.getSinalValorComissao());
        preparedStatement.setString(24, resumoVenda.getIdentificadorAjusteRV());

        if(resumoVenda.getCodigoAjustes() != null) {
            preparedStatement.setString(25, resumoVenda.getCodigoAjustes());
        } else {
            preparedStatement.setString(25, String.valueOf(java.sql.Types.NULL));
        }

        if(resumoVenda.getAjuste() != null) {
            preparedStatement.setString(26, resumoVenda.getAjuste().getDescricao());
        } else {
            preparedStatement.setString(26, String.valueOf(java.sql.Types.NULL));
        }

        preparedStatement.setString(27, resumoVenda.getCodigoEC());
        preparedStatement.setString(28, resumoVenda.getCodigoAdquirente());

        if(resumoVenda.getAdquirente() != null) {
            preparedStatement.setString(29, resumoVenda.getAdquirente().getDescricao());
        } else {
            preparedStatement.setString(29, String.valueOf(java.sql.Types.NULL));
        }

        preparedStatement.setString(30, resumoVenda.getNSEQ());
        preparedStatement.setString(31, arquivo);

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
