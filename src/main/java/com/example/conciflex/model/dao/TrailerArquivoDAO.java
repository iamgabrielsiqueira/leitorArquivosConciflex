package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.TrailerArquivo;

public interface TrailerArquivoDAO {
    void create(TrailerArquivo trailerArquivo, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
