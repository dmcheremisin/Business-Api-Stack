package info.cheremisin.springbootcouchbase.repository;

import info.cheremisin.springbootcouchbase.entities.ProductOffering;
import org.springframework.data.repository.CrudRepository;

public interface ProductOfferingRepository extends CrudRepository<ProductOffering, Integer> {
}
