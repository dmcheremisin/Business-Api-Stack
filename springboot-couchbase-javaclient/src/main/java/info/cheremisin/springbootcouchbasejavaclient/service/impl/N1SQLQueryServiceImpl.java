package info.cheremisin.springbootcouchbasejavaclient.service.impl;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import info.cheremisin.springbootcouchbasejavaclient.converters.impl.ProductOfferingDocumentConverterImpl;
import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;
import info.cheremisin.springbootcouchbasejavaclient.service.BucketService;
import info.cheremisin.springbootcouchbasejavaclient.service.N1SQLQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class N1SQLQueryServiceImpl implements N1SQLQueryService {

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
    public List<ProductOffering> printAll() {
        N1qlQueryResult result = bucket.query(N1qlQuery.simple("SELECT * FROM catalog"));
        List<N1qlQueryRow> n1qlQueryRows = result.allRows();
        List<ProductOffering> productOfferings = n1qlQueryRows.stream()
                .map(r -> converter.fromJsonObject(r.value()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return productOfferings;
    }
}
