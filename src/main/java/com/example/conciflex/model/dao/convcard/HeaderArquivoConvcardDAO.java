package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.HeaderArquivoConvcard;

public interface HeaderArquivoConvcardDAO {
    void create(HeaderArquivoConvcard headerArquivoConvcard, String arquivo) throws Exception;
}
