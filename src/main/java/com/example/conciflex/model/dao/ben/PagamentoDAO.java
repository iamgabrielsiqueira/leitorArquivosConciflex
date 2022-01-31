package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import com.example.conciflex.model.classes.ben.Pagamento;
import java.sql.Date;
import java.sql.Time;

public interface PagamentoDAO {
    long create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Pagamento search(ComprovanteVenda comprovanteVenda) throws Exception;
    Boolean verificarDuplicidade(String chavePagamento) throws Exception;
    void deletarPagamentos(String arquivo) throws Exception;
    void updatePaymentStatusNotConc(long id) throws Exception;
}
