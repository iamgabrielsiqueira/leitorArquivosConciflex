package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Pagamento;

public interface PagamentoDAO {
    void create(ComprovanteVenda comprovanteVenda) throws Exception;
    Pagamento search(ComprovanteVenda comprovanteVenda) throws Exception;
}
