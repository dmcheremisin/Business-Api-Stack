package info.cheremisin.springbootcouchbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"info.cheremisin.springbootcouchbase"})
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

    @Autowired
    Environment env;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(env.getProperty("couchbase.hosts").split(","));
    }

    @Override
    protected String getBucketName() {
        return env.getProperty("couchbase.bucket.name");
    }

    @Override
    protected String getBucketPassword() {
        return env.getProperty("couchbase.bucket.password");
    }

    @Override
    public String typeKey() {
        return "javaClass";
    }
}
