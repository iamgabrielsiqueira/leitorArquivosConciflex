package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.Cancelamento;

public interface CancelamentoDAO {
    void create(Cancelamento cancelamento, String arquivo) throws Exception;
}
