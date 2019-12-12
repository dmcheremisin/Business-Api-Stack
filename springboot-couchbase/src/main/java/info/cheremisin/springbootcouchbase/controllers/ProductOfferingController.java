package info.cheremisin.springbootcouchbase.controllers;

import info.cheremisin.springbootcouchbase.entities.ProductOffering;
import info.cheremisin.springbootcouchbase.service.ProductOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productOffering")
public class ProductOfferingController {

    @Autowired
    ProductOfferingService productOfferingService;

    @GetMapping
    public ProductOffering get(@RequestParam Integer id) {
        return productOfferingService.find(id);
    }

    @PostMapping
    public ProductOffering save(@RequestBody ProductOffering offering) {
        return productOfferingService.save(offering);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        productOfferingService.delete(id);
    }


}
