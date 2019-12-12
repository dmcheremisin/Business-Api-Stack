package info.cheremisin.springbootcouchbasejavaclient.converters;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;

public interface JsonDocumentConverter<T> {

    JsonDocument toDocument(T t);

    T fromDocument(JsonDocument doc);

    ProductOffering fromJsonObject(JsonObject jsonObject);
}
