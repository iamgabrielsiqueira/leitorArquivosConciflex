package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import javafx.collections.ObservableList;

public interface ComprovanteVendaDAO {
    void create(ComprovanteVenda comprovanteVendaDAO, String arquivo) throws Exception;
    ObservableList<ComprovanteVenda> listarPagamentos() throws Exception;
    void deletar(String arquivo) throws Exception;
}
