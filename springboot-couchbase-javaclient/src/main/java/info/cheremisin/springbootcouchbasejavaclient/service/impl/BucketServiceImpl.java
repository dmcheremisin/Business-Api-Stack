package info.cheremisin.springbootcouchbasejavaclient.service.impl;

import com.couchbase.client.java.Bucket;
import info.cheremisin.springbootcouchbasejavaclient.service.BucketService;
import info.cheremisin.springbootcouchbasejavaclient.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    private ClusterService clusterService;

    private Bucket bucket;

    @PostConstruct
    private void init() {
        bucket = clusterService.openBucket("catalog", "password123");
    }

    @Override
    public Bucket getBucket() {
        return bucket;
    }
}
