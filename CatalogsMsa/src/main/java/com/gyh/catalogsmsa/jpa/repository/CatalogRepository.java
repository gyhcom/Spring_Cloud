package com.gyh.catalogsmsa.jpa.repository;

import com.gyh.catalogsmsa.jpa.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {

    CatalogEntity findByProductId(String productId);
}
