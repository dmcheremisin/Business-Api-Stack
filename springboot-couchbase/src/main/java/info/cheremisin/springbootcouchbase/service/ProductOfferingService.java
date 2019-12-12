package info.cheremisin.springbootcouchbase.service;

import info.cheremisin.springbootcouchbase.entities.ProductOffering;

public interface ProductOfferingService {

    ProductOffering save(ProductOffering offering);

    void delete(Integer id);

    ProductOffering find(Integer id);
}
