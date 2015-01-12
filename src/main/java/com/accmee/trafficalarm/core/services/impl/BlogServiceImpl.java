package com.accmee.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accmee.trafficalarm.core.models.entities.Blog;
import com.accmee.trafficalarm.core.models.entities.BlogEntry;
import com.accmee.trafficalarm.core.repositories.BlogEntryRepo;
import com.accmee.trafficalarm.core.repositories.BlogRepo;
import com.accmee.trafficalarm.core.services.BlogService;
import com.accmee.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.accmee.trafficalarm.core.services.util.BlogEntryList;
import com.accmee.trafficalarm.core.services.util.BlogList;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private BlogEntryRepo entryRepo;

    @Override
    public BlogEntry createBlogEntry(Long blogId, BlogEntry data) {
        Blog blog = blogRepo.findBlog(blogId);
        if(blog == null)
        {
            throw new EntityNotFoundException();
        }
        BlogEntry entry = entryRepo.createBlogEntry(data);
        entry.setBlog(blog);
        return entry;
    }

    @Override
    public BlogList findAllBlogs() {
        return new BlogList(blogRepo.findAllBlogs());
    }

    @Override
    public BlogEntryList findAllBlogEntries(Long blogId) {
        Blog blog = blogRepo.findBlog(blogId);
        if(blog == null)
        {
            throw new EntityNotFoundException();
        }
        return new BlogEntryList(blogId, entryRepo.findByBlogId(blogId));
    }

    @Override
    public Blog findBlog(Long id) {
        return blogRepo.findBlog(id);
    }
}
