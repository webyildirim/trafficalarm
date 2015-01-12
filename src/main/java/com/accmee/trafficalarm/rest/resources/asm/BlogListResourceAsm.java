package com.accmee.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.accmee.trafficalarm.core.services.util.BlogList;
import com.accmee.trafficalarm.rest.mvc.BlogController;
import com.accmee.trafficalarm.rest.resources.BlogListResource;

/**
 * Created by Chris on 7/1/14.
 */
public class BlogListResourceAsm extends ResourceAssemblerSupport<BlogList, BlogListResource> {

    public BlogListResourceAsm()
    {
        super(BlogController.class, BlogListResource.class);
    }

    @Override
    public BlogListResource toResource(BlogList blogList) {
        BlogListResource res = new BlogListResource();
        res.setBlogs(new BlogResourceAsm().toResources(blogList.getBlogs()));
        return res;
    }
}
