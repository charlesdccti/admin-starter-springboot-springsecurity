package com.github.adminfaces.starter.service;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.starter.model.Product;

import java.util.List;
import java.util.function.Predicate;

public interface ProductService {

    List<Product> findAll();

    void insert(Product product);

    Product findById(Integer id);

    void update(Product product);

    List<String> getModels(String query);

    void remove(Product product);

    long count(Filter<Product> filter);

    List<Product> paginate(Filter<Product> filter);

    List<Predicate<Product>> configFilter(Filter<Product> filter);
}
