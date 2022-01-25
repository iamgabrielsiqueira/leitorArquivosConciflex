package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.ResumoVenda;

public interface ResumoVendasDAO {
    void create(ResumoVenda resumoVenda, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
