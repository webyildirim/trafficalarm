package com.accmee.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accmee.trafficalarm.core.models.entities.BlogEntry;
import com.accmee.trafficalarm.core.repositories.BlogEntryRepo;
import com.accmee.trafficalarm.core.services.BlogEntryService;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService {

    @Autowired
    private BlogEntryRepo entryRepo;

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return entryRepo.findBlogEntry(id);
    }

    @Override
    public BlogEntry deleteBlogEntry(Long id) {
        return entryRepo.deleteBlogEntry(id);
    }

    @Override
    public BlogEntry updateBlogEntry(Long id, BlogEntry data) {
        return entryRepo.updateBlogEntry(id, data);
    }
}
