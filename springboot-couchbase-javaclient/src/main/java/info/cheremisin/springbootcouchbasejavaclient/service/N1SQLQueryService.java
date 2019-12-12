package info.cheremisin.springbootcouchbasejavaclient.service;

import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;

import java.util.List;

public interface N1SQLQueryService {

    List<ProductOffering> printAll();
}
