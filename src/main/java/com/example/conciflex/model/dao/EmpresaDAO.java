package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.Empresa;

public interface EmpresaDAO {
    Empresa search(String CNPJ) throws Exception;
}
