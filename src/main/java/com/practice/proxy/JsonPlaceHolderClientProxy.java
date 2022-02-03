package com.practice.proxy;

import com.practice.config.JsonPlaceHolderConfiguration;
import com.practice.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "jsonplaceholder",
        url = "https://jsonplaceholder.typicode.com/",
        configuration = JsonPlaceHolderConfiguration.class)
public interface JsonPlaceHolderClientProxy {

    @PostMapping("/posts")
    Post createPost(@RequestBody Post post);

    @GetMapping("/posts")
    List<Post> getPosts(@RequestHeader String headerId);

    @GetMapping("/posts/{postId}")
    Post getPostById(@PathVariable("postId") Long postId);

}
