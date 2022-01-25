package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.Cancelamento;

public interface CancelamentoDAO {
    void create(Cancelamento cancelamento, String arquivo) throws Exception;
}
