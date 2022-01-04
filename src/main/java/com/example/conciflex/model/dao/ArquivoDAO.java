package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.Arquivo;
import javafx.collections.ObservableList;

public interface ArquivoDAO {
    void create(Arquivo arquivo) throws Exception;
    Boolean search(String nomeArquivo) throws Exception;
    ObservableList<Arquivo> listarArquivos() throws Exception;
}
