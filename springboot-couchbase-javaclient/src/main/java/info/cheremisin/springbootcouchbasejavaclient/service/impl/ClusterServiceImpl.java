package info.cheremisin.springbootcouchbasejavaclient.service.impl;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import info.cheremisin.springbootcouchbasejavaclient.service.ClusterService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ClusterServiceImpl implements ClusterService {

    private Cluster cluster;

    @PostConstruct
    private void init() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();
        cluster = CouchbaseCluster.create(env, "localhost");
    }

    @Override
    public Bucket openBucket(String name, String password) {
        return cluster.openBucket(name, password);
    }
}
