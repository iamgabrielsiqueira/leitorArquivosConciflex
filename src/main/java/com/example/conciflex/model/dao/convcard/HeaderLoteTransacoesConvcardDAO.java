package com.example.conciflex.model.dao.convcard;

import com.example.conciflex.model.classes.convcard.HeaderLoteTransacoesConvcard;

public interface HeaderLoteTransacoesConvcardDAO {
    void create(HeaderLoteTransacoesConvcard headerLoteTransacoesConvcard, String arquivo) throws Exception;
    String searchAutorizador(String nseqLote) throws Exception;
}
