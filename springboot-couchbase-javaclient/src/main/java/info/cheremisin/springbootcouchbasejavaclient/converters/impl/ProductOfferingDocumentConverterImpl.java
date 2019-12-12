package info.cheremisin.springbootcouchbasejavaclient.converters.impl;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import info.cheremisin.springbootcouchbasejavaclient.converters.JsonDocumentConverter;
import info.cheremisin.springbootcouchbasejavaclient.entity.ProductOffering;
import org.springframework.stereotype.Service;

@Service
public class ProductOfferingDocumentConverterImpl implements JsonDocumentConverter<ProductOffering> {

    @Override
    public JsonDocument toDocument(ProductOffering productOffering) {
        JsonObject jsonObject = JsonObject.empty()
                .put("id", productOffering.getId())
                .put("name", productOffering.getName())
                .put("category", productOffering.getCategory())
                .put("price", productOffering.getPrice());
        return JsonDocument.create(productOffering.getId().toString(), jsonObject);
    }

    @Override
    public ProductOffering fromDocument(JsonDocument doc) {
        JsonObject content = doc.content();
        ProductOffering productOffering = new ProductOffering();
        productOffering.setId(Integer.parseInt(doc.id()));
        productOffering.setName(content.getString("name"));
        productOffering.setCategory(content.getString("category"));
        productOffering.setPrice(content.getString("price"));
        return productOffering;
    }

    @Override
    public ProductOffering fromJsonObject(JsonObject jsonObject) {
        if(jsonObject.get("catalog") instanceof JsonObject) {
            JsonObject catalogJsonObject = (JsonObject) jsonObject.get("catalog");
            ProductOffering productOffering = new ProductOffering();
            productOffering.setId((Integer) catalogJsonObject.get("id"));
            productOffering.setName(catalogJsonObject.getString("name"));
            productOffering.setCategory(catalogJsonObject.getString("category"));
            productOffering.setPrice(catalogJsonObject.getString("price"));
            return productOffering;
        }
        return null;
    }
}
