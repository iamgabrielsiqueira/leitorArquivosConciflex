package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;
import com.example.conciflex.model.classes.convcard.TarifaBancariaConvcard;
import java.sql.Date;
import java.sql.Time;

public interface TarifaBancariaConvcardDAO {
    void create(TarifaBancariaConvcard tarifaBancariaConvcard, String arquivo) throws Exception;
    void createPagamento(TarifaBancariaConvcard tarifaBancariaConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Boolean searchTarifa(TarifaBancariaConvcard tarifaBancariaConvcard) throws Exception;
}
