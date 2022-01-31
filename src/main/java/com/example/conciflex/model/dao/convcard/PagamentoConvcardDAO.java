package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.ComprovantePagamentoConvcard;
import java.sql.Date;
import java.sql.Time;

public interface PagamentoConvcardDAO {
    long create(ComprovantePagamentoConvcard comprovantePagamentoConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Boolean verificarDuplicidade(String chavePagamento) throws Exception;
    void updatePaymentStatus(String paymentKey, String saleKey) throws Exception;
}