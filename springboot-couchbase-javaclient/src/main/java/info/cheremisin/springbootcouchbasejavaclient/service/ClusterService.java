package info.cheremisin.springbootcouchbasejavaclient.service;

import com.couchbase.client.java.Bucket;

public interface ClusterService {

    Bucket openBucket(String name, String password);
}
