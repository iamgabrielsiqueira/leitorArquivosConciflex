package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;

import java.sql.Date;
import java.sql.Time;

public interface CancelamentoConvcardDAO {
    void create(CancelamentoConvcard cancelamentoConvcard, String arquivo) throws Exception;
    void createCancelamento(CancelamentoConvcard cancelamentoConvcard, Date dataImportacao, Time horaImportacao, String arquivo, ComprovanteVendaConvcard comprovanteVendaConvcard) throws Exception;
    Boolean searchCancelamento(CancelamentoConvcard cancelamentoConvcard) throws Exception;
}
