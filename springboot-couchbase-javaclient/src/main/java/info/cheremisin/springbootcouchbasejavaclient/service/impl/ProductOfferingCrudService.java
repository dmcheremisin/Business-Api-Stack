package info.cheremisin.springbootcouchbasejavaclient.service.impl;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.document.JsonDocument;
import info.cheremisin.springbootcouchbasejavaclient.converters.impl.ProductOfferingDocumentConverterImpl;
import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;
import info.cheremisin.springbootcouchbasejavaclient.service.BucketService;
import info.cheremisin.springbootcouchbasejavaclient.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
public class ProductOfferingCrudService implements CrudService<ProductOffering> {

    @Autowired
    BucketService bucketService;

    @Autowired
    ProductOfferingDocumentConverterImpl converter;

    private Bucket bucket;

    @PostConstruct
    private void init() {
        bucket = bucketService.getBucket();
    }

    @Override
    public ProductOffering create(ProductOffering productOffering) {
        if(productOffering.getId() == null){
            productOffering.setId(UUID.randomUUID().variant());
        }
        JsonDocument jsonDocument = converter.toDocument(productOffering);
        JsonDocument insert = bucket.insert(jsonDocument);
        ProductOffering savedProductOffering = converter.fromDocument(insert);
        return savedProductOffering;
    }

    @Override
    public ProductOffering get(String id) {
        JsonDocument doc = bucket.get(id);
        return (doc != null ? converter.fromDocument(doc) : null);
    }

    @Override
    public ProductOffering getFromReplica(String id) {
        List<JsonDocument> docs = bucket.getFromReplica(id, ReplicaMode.FIRST);
        return (docs.isEmpty() ? null : converter.fromDocument(docs.get(0)));
    }

    @Override
    public ProductOffering update(ProductOffering productOffering) {
        JsonDocument document = converter.toDocument(productOffering);
        JsonDocument jsonDocument = bucket.upsert(document);
        ProductOffering updatedProductOffering = converter.fromDocument(jsonDocument);
        return updatedProductOffering;
    }

    @Override
    public void delete(String id) {
        bucket.remove(id);
    }

    @Override
    public boolean exists(String id) {
        return bucket.exists(id);
    }
}
