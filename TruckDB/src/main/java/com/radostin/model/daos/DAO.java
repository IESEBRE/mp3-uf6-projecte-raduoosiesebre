package com.radostin.model.daos;

import com.radostin.model.exceptions.DAOException;

import java.util.List;

public interface DAO <T>{

    T get(Long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void save(T obj) throws DAOException;

    //Tots els mètodes necessaris per interactuar en la BD

}