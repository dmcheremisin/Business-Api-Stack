package com.business.api.controller;

import com.business.api.converters.DocumentToPostConverter;
import com.business.api.model.Post;
import com.business.api.rest.RestService;
import com.business.api.search.LuceneAdapter;
import org.apache.camel.ProducerTemplate;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    static final private Logger logger = LoggerFactory.getLogger(PostController.class);

    @Value("${rest.endpoint.url}")
    private String url;

    @Autowired
    private RestService restService;

    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    LuceneAdapter luceneAdapter;

    @Autowired
    DocumentToPostConverter documentToPostConverter;

    @PostConstruct
    private void cachePosts() {
        luceneAdapter.cleanIndexDir();
        List<Post> posts = getAllPosts();
        posts.forEach(p -> luceneAdapter.addObjectToIndex(p));
    }

    @GetMapping
    public List<Post> getAllPosts() {
        List<Post> posts = restService.get(url, new ParameterizedTypeReference<List<Post>>(){});
        return posts;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        Post response = restService.post(url, post, Post.class);
        producerTemplate.sendBodyAndHeader("direct:log.body.route", response, "env", "header");
        return response;
    }


    @GetMapping("/search")
    public List<Post> getPostsNew(@RequestParam String text) {
        List<Document> documents = luceneAdapter.searchFiles("body", text);
        List<Post> posts = new ArrayList<>();
        documents.forEach(d -> posts.add(documentToPostConverter.convert(d)));
        return posts;
    }
}
