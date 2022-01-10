package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.ResumoVenda;

public interface ResumoVendasDAO {
    void create(ResumoVenda resumoVenda, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
