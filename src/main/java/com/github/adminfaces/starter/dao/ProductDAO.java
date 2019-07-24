package com.github.adminfaces.starter.dao;

import com.github.adminfaces.starter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {


}

