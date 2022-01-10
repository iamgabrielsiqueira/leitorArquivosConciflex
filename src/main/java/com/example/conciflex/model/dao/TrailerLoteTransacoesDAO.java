package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.TrailerLoteTransacao;

public interface TrailerLoteTransacoesDAO {
    void create(TrailerLoteTransacao trailerLoteTransacao, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
