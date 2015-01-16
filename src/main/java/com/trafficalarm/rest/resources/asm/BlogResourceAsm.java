package com.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.Blog;
import com.trafficalarm.rest.mvc.AccountController;
import com.trafficalarm.rest.mvc.BlogController;
import com.trafficalarm.rest.resources.BlogResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by webyildirim on 6/30/14.
 */
public class BlogResourceAsm extends ResourceAssemblerSupport<Blog, BlogResource> {
    public BlogResourceAsm() {
        super(BlogController.class, BlogResource.class);
    }

    @Override
    public BlogResource toResource(Blog blog) {
        BlogResource resource = new BlogResource();
        resource.setTitle(blog.getTitle());
        resource.add(linkTo(BlogController.class).slash(blog.getId()).withSelfRel());
        resource.add(linkTo(BlogController.class).slash(blog.getId()).slash("blog-entries").withRel("entries"));
        resource.setRid(blog.getId());
        if(blog.getOwner() != null)
            resource.add(linkTo(AccountController.class).slash(blog.getOwner().getId()).withRel("owner"));
        return resource;
    }
}
