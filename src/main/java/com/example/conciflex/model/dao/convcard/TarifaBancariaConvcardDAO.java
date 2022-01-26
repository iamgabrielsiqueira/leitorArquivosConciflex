package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.TarifaBancariaConvcard;

public interface TarifaBancariaConvcardDAO {
    void create(TarifaBancariaConvcard tarifaBancariaConvcard, String arquivo) throws Exception;
}
