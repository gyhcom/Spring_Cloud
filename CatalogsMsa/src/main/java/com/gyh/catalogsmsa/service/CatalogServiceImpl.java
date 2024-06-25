package com.gyh.catalogsmsa.service;

import com.gyh.catalogsmsa.jpa.CatalogEntity;
import com.gyh.catalogsmsa.jpa.repository.CatalogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

    CatalogRepository catalogRepository;

    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return  catalogRepository.findAll();
    }
}
