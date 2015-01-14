package com.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.Blog;

/**
 * Created by webyildirim on 6/30/14.
 */
public class BlogResource extends ResourceSupport {

    private String title;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blog toBlog() {
        Blog blog = new Blog();
        blog.setTitle(title);
        return blog;
    }
}
