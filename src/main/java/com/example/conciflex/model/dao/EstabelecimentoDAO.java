package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.Estabelecimento;

public interface EstabelecimentoDAO {
    Estabelecimento search(String CNPJ) throws Exception;
}
