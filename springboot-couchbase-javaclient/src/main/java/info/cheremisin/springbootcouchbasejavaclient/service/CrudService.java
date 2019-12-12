package info.cheremisin.springbootcouchbasejavaclient.service;

import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;

public interface CrudService<T> {

    T create(T t);

    T get(String id);

    T getFromReplica(String id);

    ProductOffering update(T t);

    void delete(String id);

    boolean exists(String id);
}
