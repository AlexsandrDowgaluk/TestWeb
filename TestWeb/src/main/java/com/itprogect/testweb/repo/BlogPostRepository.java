package com.itprogect.testweb.repo;

import com.itprogect.testweb.models.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
}
