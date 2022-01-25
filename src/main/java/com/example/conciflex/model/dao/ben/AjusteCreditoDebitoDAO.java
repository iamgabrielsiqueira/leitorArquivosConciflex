package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.AjusteCreditoDebito;

public interface AjusteCreditoDebitoDAO {
    void create(AjusteCreditoDebito ajusteCreditoDebito, String arquivo) throws Exception;
}
