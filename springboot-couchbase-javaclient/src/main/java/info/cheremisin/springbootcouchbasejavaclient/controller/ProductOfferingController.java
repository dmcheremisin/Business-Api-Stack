package info.cheremisin.springbootcouchbasejavaclient.controller;

import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;
import info.cheremisin.springbootcouchbasejavaclient.service.N1SQLQueryService;
import info.cheremisin.springbootcouchbasejavaclient.service.impl.ProductOfferingCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productOffering")
public class ProductOfferingController {

    @Autowired
    ProductOfferingCrudService productOfferingService;

    @Autowired
    N1SQLQueryService n1SQLQueryService;

    @GetMapping
    public ProductOffering get(@RequestParam String id) {
        return productOfferingService.get(id);
    }

    @PostMapping
    public ProductOffering save(@RequestBody ProductOffering offering) {
        return productOfferingService.create(offering);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        productOfferingService.delete(id);
    }

    @GetMapping("/{id}/exists")
    public boolean checkExists(@PathVariable String id){
        return productOfferingService.exists(id);
    }

    @PutMapping
    public ProductOffering update(@RequestBody ProductOffering offering) {
        return productOfferingService.update(offering);
    }

    @GetMapping("/all")
    public List<ProductOffering> printAll() {
        List<ProductOffering> productOfferings = n1SQLQueryService.printAll();
        return productOfferings;
    }



}
