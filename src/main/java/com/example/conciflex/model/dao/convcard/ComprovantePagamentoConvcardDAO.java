package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.ComprovantePagamentoConvcard;

public interface ComprovantePagamentoConvcardDAO {
    void create(ComprovantePagamentoConvcard comprovantePagamentoConvcard, String arquivo) throws Exception;
}
