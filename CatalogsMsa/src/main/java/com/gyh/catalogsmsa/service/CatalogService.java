package com.gyh.catalogsmsa.service;

import com.gyh.catalogsmsa.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
