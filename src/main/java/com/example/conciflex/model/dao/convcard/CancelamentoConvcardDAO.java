package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.CancelamentoConvcard;

public interface CancelamentoConvcardDAO {
    void create(CancelamentoConvcard cancelamentoConvcard, String arquivo) throws Exception;
}
