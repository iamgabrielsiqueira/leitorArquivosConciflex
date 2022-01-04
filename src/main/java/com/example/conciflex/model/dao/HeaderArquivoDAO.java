package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.HeaderArquivo;

public interface HeaderArquivoDAO {
    void create(HeaderArquivo headerArquivo, String arquivo) throws Exception;
    Boolean search(String dataGeracao, String idMovimento) throws Exception;
}
