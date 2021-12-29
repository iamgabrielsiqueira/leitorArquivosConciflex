package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Pagamento;
import java.sql.Date;
import java.sql.Time;

public interface PagamentoDAO {
    void create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Pagamento search(ComprovanteVenda comprovanteVenda) throws Exception;
    Boolean verificarDuplicidade(String chavePagamento) throws Exception;
}
