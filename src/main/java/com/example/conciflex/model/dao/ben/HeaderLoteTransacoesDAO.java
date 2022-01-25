package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.HeaderLoteTransacao;

public interface HeaderLoteTransacoesDAO {
    void create(HeaderLoteTransacao headerLoteTransacao, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
