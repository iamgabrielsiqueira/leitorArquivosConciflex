package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.HeaderLoteTransacao;

public interface HeaderLoteTransacoesDAO {
    void create(HeaderLoteTransacao headerLoteTransacao, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
