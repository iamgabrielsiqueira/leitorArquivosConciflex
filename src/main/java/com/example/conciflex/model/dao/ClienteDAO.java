package com.example.conciflex.model.dao;

import com.example.conciflex.model.classes.Cliente;

public interface ClienteDAO {
    Cliente search(int id) throws Exception;
}
