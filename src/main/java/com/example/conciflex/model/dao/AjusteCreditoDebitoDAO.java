package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.AjusteCreditoDebito;

public interface AjusteCreditoDebitoDAO {
    void create(AjusteCreditoDebito ajusteCreditoDebito, String arquivo) throws Exception;
}
