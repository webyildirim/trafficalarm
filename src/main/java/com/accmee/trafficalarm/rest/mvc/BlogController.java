package com.accmee.trafficalarm.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accmee.trafficalarm.core.models.entities.Blog;
import com.accmee.trafficalarm.core.models.entities.BlogEntry;
import com.accmee.trafficalarm.core.services.BlogService;
import com.accmee.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.accmee.trafficalarm.core.services.util.BlogEntryList;
import com.accmee.trafficalarm.core.services.util.BlogList;
import com.accmee.trafficalarm.rest.exceptions.NotFoundException;
import com.accmee.trafficalarm.rest.resources.BlogEntryListResource;
import com.accmee.trafficalarm.rest.resources.BlogEntryResource;
import com.accmee.trafficalarm.rest.resources.BlogListResource;
import com.accmee.trafficalarm.rest.resources.BlogResource;
import com.accmee.trafficalarm.rest.resources.asm.BlogEntryListResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogEntryResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogListResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogResourceAsm;

import java.net.URI;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/blogs")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<BlogListResource> findAllBlogs()
    {
        BlogList blogList = blogService.findAllBlogs();
        BlogListResource blogListRes = new BlogListResourceAsm().toResource(blogList);
        return new ResponseEntity<BlogListResource>(blogListRes, HttpStatus.OK);
    }

    @RequestMapping(value="/{blogId}",
        method = RequestMethod.GET)
    public ResponseEntity<BlogResource> getBlog(@PathVariable Long blogId)
    {
        Blog blog = blogService.findBlog(blogId);
        if(blog != null) {
            BlogResource res = new BlogResourceAsm().toResource(blog);
            return new ResponseEntity<BlogResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{blogId}/blog-entries",
            method = RequestMethod.POST)
    public ResponseEntity<BlogEntryResource> createBlogEntry(
            @PathVariable Long blogId,
            @RequestBody BlogEntryResource sentBlogEntry
    ) {
        BlogEntry createdBlogEntry = null;
        try {
            createdBlogEntry = blogService.createBlogEntry(blogId, sentBlogEntry.toBlogEntry());
            BlogEntryResource createdResource = new BlogEntryResourceAsm().toResource(createdBlogEntry);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
            return new ResponseEntity<BlogEntryResource>(createdResource, headers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(value="/{blogId}/blog-entries")
    public ResponseEntity<BlogEntryListResource> findAllBlogEntries(
            @PathVariable Long blogId)
    {
        try {
            BlogEntryList list = blogService.findAllBlogEntries(blogId);
            BlogEntryListResource res = new BlogEntryListResourceAsm().toResource(list);
            return new ResponseEntity<BlogEntryListResource>(res, HttpStatus.OK);
        } catch(EntityNotFoundException exception)
        {
            throw new NotFoundException(exception);
        }
    }

}
