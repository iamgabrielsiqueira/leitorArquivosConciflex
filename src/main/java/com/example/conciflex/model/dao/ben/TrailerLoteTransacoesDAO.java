package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.TrailerLoteTransacao;

public interface TrailerLoteTransacoesDAO {
    void create(TrailerLoteTransacao trailerLoteTransacao, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
