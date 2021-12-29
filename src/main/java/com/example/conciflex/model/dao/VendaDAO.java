package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.ComprovanteVenda;
import com.example.conciflex.model.classes.Venda;
import java.sql.Date;
import java.sql.Time;

public interface VendaDAO {
    void create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    Venda search(ComprovanteVenda comprovanteVenda) throws Exception;
    Boolean verificarDuplicidade(String chaveVenda) throws Exception;
}
