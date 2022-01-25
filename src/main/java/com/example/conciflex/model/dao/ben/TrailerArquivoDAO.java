package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.TrailerArquivo;

public interface TrailerArquivoDAO {
    void create(TrailerArquivo trailerArquivo, String arquivo) throws Exception;
    void deletar(String arquivo) throws Exception;
}
