package com.business.api.converters;

import com.business.api.model.Post;
import org.apache.lucene.document.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentToPostConverter {

    public Post convert(Document document) {
        Post post = new Post();
        post.setId(Integer.parseInt(document.get("id")));
        post.setUserId(Integer.parseInt(document.get("userId")));
        post.setTitle(document.get("title"));
        post.setBody(document.get("body"));
        return post;
    }
}
