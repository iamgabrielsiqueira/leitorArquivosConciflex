package com.example.conciflex.model.dao.ben;

import com.example.conciflex.model.classes.ben.ComprovanteVenda;
import java.sql.Date;
import java.sql.Time;

public interface VendaDAO {
    void create(ComprovanteVenda comprovanteVenda, Date dataImportacao, Time horaImportacao, String arquivo) throws Exception;
    String search(ComprovanteVenda comprovanteVenda) throws Exception;
    Boolean verificarDuplicidade(String chaveVenda) throws Exception;
    void updateVendaPaga(ComprovanteVenda comprovanteVenda, long id) throws Exception;
    void deletarVendas(String arquivo) throws Exception;
}
