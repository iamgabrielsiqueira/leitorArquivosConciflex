package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import java.sql.Date;
import java.sql.Time;

public interface VendaConvcardDAO {
    void create(ComprovanteVendaConvcard comprovanteVendaConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Boolean verificarDuplicidade(String chaveVenda) throws Exception;
    ComprovanteVendaConvcard searchCancelamento(CancelamentoConvcard cancelamentoConvcard) throws Exception;
    int searchVendaCancelada(CancelamentoConvcard cancelamentoConvcard) throws Exception;
    void updateVendaCancelada(int id, CancelamentoConvcard cancelamentoConvcard) throws Exception;
}
