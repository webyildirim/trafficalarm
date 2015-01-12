package com.accmee.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.accmee.trafficalarm.core.models.entities.BlogEntry;

/**
 * Created by webyildirim on 6/28/14.
 */
public class BlogEntryList {
    private List<BlogEntry> entries = new ArrayList<BlogEntry>();
    private Long blogId;

    public BlogEntryList(Long blogId, List<BlogEntry> entries) {
        this.blogId = blogId;
        this.entries = entries;
    }

    public List<BlogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
