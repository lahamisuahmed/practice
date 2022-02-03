package com.practice.hystrix;

import com.practice.model.Post;
import com.practice.proxy.JsonPlaceHolderClientProxy;

import java.util.Collections;
import java.util.List;

public class JSONPlaceHolderFallback implements JsonPlaceHolderClientProxy {
    @Override
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public List<Post> getPosts(String headerId) {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }
}
