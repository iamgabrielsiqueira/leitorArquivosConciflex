package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.ComprovanteVendaConvcard;
import java.sql.Date;
import java.sql.Time;

public interface VendaConvcardDAO {
    void create(ComprovanteVendaConvcard comprovanteVendaConvcard, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
}
