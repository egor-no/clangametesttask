package com.clangame.demo.data.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T, Long> {

    Optional<T> get(Long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(Long id);
}