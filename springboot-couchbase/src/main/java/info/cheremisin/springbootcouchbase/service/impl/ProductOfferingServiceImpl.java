package info.cheremisin.springbootcouchbase.service.impl;

import info.cheremisin.springbootcouchbase.entities.ProductOffering;
import info.cheremisin.springbootcouchbase.repository.ProductOfferingRepository;
import info.cheremisin.springbootcouchbase.service.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOfferingServiceImpl implements ProductOfferingService {

    @Autowired
    ProductOfferingRepository repository;

    @Override
    public ProductOffering save(ProductOffering offering) {
        return repository.save(offering);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public ProductOffering find(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
